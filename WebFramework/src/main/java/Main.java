import runner.Commands;
import util.Checker;

public class Main {

    public static void main(String[] args) {
        //String[] args = {"src/main/resources/test.txt", "src/main/resources/log.txt"};
        Checker checker = new Checker();
        Commands commands = new Commands();
        try {
            checker.checkConsoleArguments(args);
            commands.readFromFile(args[0]);
            commands.setLogFile(args[1]);
            commands.execute();
            commands.logToFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

