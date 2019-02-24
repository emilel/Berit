import com.google.cloud.texttospeech.v1.SsmlVoiceGender;

import java.io.*;
import java.net.*;
import java.util.Enumeration;

public class WebListener {
    private int port;
    private Speaker speaker;
    private Executor executor;
    private String playerName;

    public WebListener(int port, Mp3Player player) {
        this.port = port;
        this.speaker = new Speaker("en-GB", SsmlVoiceGender.FEMALE, player);
        this.executor = new Executor("scripts");
        this.playerName = player.toString();
    }

    public void open() {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Created server socket." +
                    "\nPort: " + port +
                    "\nExternal ip: " + getExternalIp() +
                    "\nLocal site ip: " + getLocalSiteIp() +
                    "\nMp3Player: " + playerName);

            while (true) {
                Socket client = serverSocket.accept();
                BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                try {
                    String ip = getIpFromConnection(client);
                    System.out.println("Accepted new connection from ip: " + ip);
                    String content = getContent(in);
                    if (!handle(content)) {
                        break;
                    }
                    System.out.println();
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println("Packet is not an HTTP Request, or has invalid content.");
                } catch (SocketException e) {
                    System.out.println("Error handling incoming packet.");
                } catch (NullPointerException e) {
                    System.out.println("Programming error, or resource not found.");
                    e.printStackTrace();
                }
                client.close();
            }
        } catch (IOException e) {
            System.out.println("Unable to open");
            e.printStackTrace();
        }
    }

    private String getContent(BufferedReader in) throws IOException {
        return in.readLine().split("/")[1].split(" ")[0];
    }

    private String getIpFromConnection(Socket client) {
        return client.getInetAddress().toString().substring(1);
    }

    private boolean handle(String content) {
        System.out.println("Handling packet with content: " + content);
        String[] split = content.split("_", 2);

        if (split.length == 1 | split[0].length() != 3 | split[1].length() == 0) {
            System.out.println("Invalid request, doing nothing");
            return true;
        }

        switch (split[0]) {
            case "exe":
                executor.execute(split[1]);
                break;
            case "spk":
                speaker.speak(split[1]);
                break;
            case "trm":
                return false;
            default:
                System.out.println("Unknown content");
                break;
        }

        return true;
    }

    private static String getExternalIp() {
        try {
            URL ipCheckingWebsite = new URL("http://bot.whatismyipaddress.com/");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(ipCheckingWebsite.openStream()));
            return bufferedReader.readLine().trim();
        } catch (IOException e) {
            System.out.println("Could not retrieve external ip");
            return "Unknown external ip";
        }
    }

    private static String getLocalSiteIp() {
        try {
            Enumeration enumeration = NetworkInterface.getNetworkInterfaces();
            while (enumeration.hasMoreElements()) {
                NetworkInterface n = (NetworkInterface) enumeration.nextElement();
                enumeration = n.getInetAddresses();
                while (enumeration.hasMoreElements()) {
                    InetAddress inetAddress = ((InetAddress) enumeration.nextElement());
                    if (inetAddress.isSiteLocalAddress()) {
                        return inetAddress.toString().substring(1);
                    }
                }
            }
        } catch (SocketException e) {
            System.out.println("Unable to get local site ip");
        }
        return "Unknown local site ip";
    }
}