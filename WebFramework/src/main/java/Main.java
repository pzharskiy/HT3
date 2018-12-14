import runner.Commands;
import util.Checker;

public class Main {

    public static void main(String[] args) {
        String[] arg = {"src/main/resources/test.txt", "src/main/resources/log.txt"};
        Checker checker = new Checker();
        Commands commands = new Commands();
        try {
            checker.checkConsoleArguments(arg);
            commands.readFromFile(arg[0]);
            commands.setLogFile(arg[1]);
            commands.execute();
            commands.logToFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

