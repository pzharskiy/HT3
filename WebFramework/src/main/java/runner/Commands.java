package runner;

import util.Parser;
import util.Timer;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class Commands {

    //Лист для хранения строк операций и их аргументов
    List<String> commands = new ArrayList<>();
    //Карта, где ключ - операция, значения  - список аргуметов
    Map<String, List<String>> commandsMap = new LinkedHashMap<>();
    //Хранит html код страницы
    String page;
    //Таймер
    Timer timer = new Timer();
    //Переменная, в которую будет записываться лог
    private StringBuilder log = new StringBuilder();
    //Файл лога
    private File logFile;
    //Переменная для обозначения того, что тест пройден ("+") или нет ("!")
    String succes_message;


    public void readFromFile(String FILE_PATH) throws IOException {
        Parser parser = new Parser();
        commands = parser.readFile(FILE_PATH);
    }

    public void setLogFile(String LOGFILE_PATH) {

        logFile = new File(LOGFILE_PATH);
    }

    public void logToFile() throws IOException {
        if (logFile != null) {

            //проверяем, что если файл не существует то создаем его
            if (!logFile.exists()) {
                logFile.createNewFile();
            }

            //PrintWriter обеспечит возможности записи в файл
            PrintWriter out = new PrintWriter(logFile.getAbsoluteFile());

            try {
                //Записываем текст у файл
                out.print(log.toString());
            } finally {
                //После чего мы должны закрыть файл
                //Иначе файл не запишется
                out.close();
            }

        } else System.out.println("Путь к файлу не задан. Вызовите функцию setLogFile(FILE_PATH)");

    }

    public void execute() throws Exception {
        Operations operation = new Operations();
        Parser parser = new Parser();
        commandsMap = parser.parse(commands);
        //Проходимся по карте команд для операций
        //ключ - операция, значения - список аргументов
        Iterator<Map.Entry<String, List<String>>> iter = commandsMap.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<String, List<String>> entry = iter.next();
            switch (entry.getKey()) {
                case "open":
                    timer.start();
                    page = operation.open(entry.getValue());
                    timer.end();
                    if (!page.equals("")) {
                        succes_message = "+";
                    } else {
                        succes_message = "!";
                    }
                    log.append(succes_message + " [" + entry.getKey() + " \"" + entry.getValue().get(0) + "\" \"" + entry.getValue().get(1) + "\"] " +
                            timer.duration() + "\n");
                    timer.reset();
                    //System.out.println(page);
                    break;

                case "checkLinkPresentByHref":
                    timer.start();
                    if (operation.checkLinkPresentByHref(page, entry.getValue())) {
                        timer.end();
                        succes_message = "+";
                    } else {
                        timer.end();
                        succes_message = "!";
                    }
                    log.append(succes_message + " [" + entry.getKey() + " \"" + entry.getValue().get(0) + "\"] " +
                            timer.duration() + "\n");
                    timer.reset();
                    break;

                case "checkLinkPresentByName":
                    timer.start();
                    if (operation.checkLinkPresentByName(page, entry.getValue())) {
                        timer.end();
                        succes_message = "+";
                    } else {
                        timer.end();
                        succes_message = "!";
                    }
                    log.append(succes_message + " [" + entry.getKey() + " \"" + entry.getValue().get(0) + "\"] " +
                            timer.duration() + "\n");
                    timer.reset();
                    break;

                case "checkPageTitle":
                    timer.start();
                    if (operation.checkPageTitle(page, entry.getValue())) {
                        timer.end();
                        succes_message = "+";
                    } else {
                        timer.end();
                        succes_message = "!";
                    }
                    log.append(succes_message + " [" + entry.getKey() + " \"" + entry.getValue().get(0) + "\"] " +
                            timer.duration() + "\n");
                    timer.reset();
                    break;

                case "checkPageContains":
                    timer.start();
                    if (operation.checkPageContains(page, entry.getValue())) {
                        timer.end();
                        succes_message = "+";
                    } else {
                        timer.end();
                        succes_message = "!";
                    }
                    log.append(succes_message + " [" + entry.getKey() + " \"" + entry.getValue().get(0) + "\"] " +
                            timer.duration() + "\n");
                    timer.reset();
                    break;

                default:
                    log.append("? " + "[unknown command: " + entry.getKey() + "]\n");
                    //throw new UnknownOperationException("Несуществующая комманда. Проверьте введенные данные");
            }
        }
        log.append("Total tests: " + operation.getAmountOfCommands() + "\n");
        log.append("Passed/Failed: " + operation.getPassedTests() + "/" + operation.getFailedTests() + "\n");
        log.append("Total time: " + timer.totalTime() + "\n");
        log.append("Average time: " + timer.averageTime(operation.getAmountOfCommands()) + "\n");
        System.out.println(log.toString());
    }
}
