import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Executor {
    private String path;

    public Executor(String path) {
        this.path = path;
    }

    public void execute(String script) {
        File file = new File(path + "/" + script);
        if (!file.exists()) {
            System.out.println("No such script found");
            return;
        }
        System.out.println("Executing script: " + script);
        Runtime runTime = Runtime.getRuntime();
        try {
            String absolutePath = file.toString();
            Scanner scanner = new Scanner(runTime.exec(absolutePath).getInputStream());
            String output = getOutput(scanner).replace('\n', '|');
            if (output.equals("")) {
                System.out.println("No output");
            } else {
                System.out.println("Output: " + output);
            }
        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    private static String getOutput(Scanner scanner) {
        StringBuilder sb = new StringBuilder();
        while(scanner.hasNext()) {
            sb.append(scanner.nextLine()).append('\n');
        }
        return sb.toString();
    }
}
