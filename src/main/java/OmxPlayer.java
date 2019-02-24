import java.io.IOException;
import java.util.Scanner;

public class OmxPlayer implements Mp3Player {
    public void play(String fileName) {
        System.out.println("Entered hyperdrive");
        Runtime runTime = Runtime.getRuntime();
        try {
            System.out.println("Playing mp3 file");
            Scanner scanner = new Scanner(runTime.exec("omxplayer " + fileName).getInputStream());
            String output = getOutput(scanner).replace('\n', '|');
            System.out.println("Command executed, with output '" + output + "'");
        } catch (IOException e) {
            System.out.println("Command not found. OmxPlayer not installed?");
            e.printStackTrace();
        }
    }

    private static String getOutput(Scanner scanner) {
        StringBuilder sb = new StringBuilder();
        while(scanner.hasNext()) {
            sb.append(scanner.nextLine()).append('\n');
        }
        return sb.toString();
    }

    public String toString() {
        return "OmxPlayer";
    }
}
