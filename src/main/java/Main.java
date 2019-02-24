public class Main {
    private static int port;
    private static Mp3Player player;

    public static void main(String[] args) {
        try {
            setUp(args);
        } catch (Exception e) {
            System.out.println("Illegal arguments. Use argument -h for help.");
        }
        WebListener webListener = new WebListener(port, player);
        webListener.open();
    }

    private static void setUp(String[] args) {
        if (args.length >= 2) {
            handleArgument(args[0], args[1]);
            if (args.length >= 4) {
                handleArgument(args[2], args[3]);
                if (args.length == 6) {
                    handleArgument(args[4], args[5]);
                }
            }
        }

        if (player == null) player = new JLayerPlayer();
        if (port == 0) port = 8081;
    }

    private static void handleArgument(String arg1, String arg2) {
        switch (arg1) {
            case "-p":
                try {
                    port = Integer.parseInt(arg2);
                } catch (NumberFormatException e) {
                    System.out.println("Argument for -p (port number) must be an integer");
                }
                break;
            case "-m":
                if (arg2.toLowerCase().equals("omxplayer")) {
                    player = new OmxPlayer();
                } else if (arg2.toLowerCase().equals("jlayer")) {
                    player = new JLayerPlayer();
                } else {
                    throw new RuntimeException("Argument for -m (which mp3 player to use) must be either omxplayer or" +
                            " jlayer");
                }
                break;
            case "-h":
                System.out.println("The following arguments are available:" +
                        "\n-h    display help" +
                        "\n-p    set port number" +
                        "\n-m    choose which mp3 player to use (jlayer or omxplayer)");
                break;
        }
    }
}