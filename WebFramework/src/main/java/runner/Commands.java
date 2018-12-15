package runner;

import util.Checker;
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

    //Две различные реализации функции execute. Одна построеная на карте, другая на списке.
    //В реализации со списком (execute) можно реализовать более сложный сценарий проверок (множество раз искать
    //различные ссылки, открывать разные страницы и все в одном сценарии), т.к. команды(с аргументами) являются строками-элементами
    //обычного списка. В свою очередь минусом является неочевидность работы с такими элементами
    public void execute() throws Exception {
        Operations operation = new Operations();
        Parser parser = new Parser();
        //Проходимся по списку команд для операций
        for (String eachCommand : commands) {
            //Строку с коммандой и ее аргументами разбиваем
            List<String> command = parser.parse(eachCommand);
            //Мы получаем лист, где 0 элемент - операция, остальные - аргументы
            switch (command.remove(0)) {
                case "open":
                    timer.start();
                    page = operation.open(command);
                    timer.end();
                    if (!page.equals("")) {
                        succes_message = "+";
                    } else {
                        succes_message = "!";
                    }
                    log.append(succes_message + " [open \"" + command.get(0) + "\" \"" + command.get(1) + "\"] " +
                            timer.duration() + "\n");
                    timer.reset();
                    //System.out.println(page);
                    break;

                case "checkLinkPresentByHref":
                    timer.start();
                    if (operation.checkLinkPresentByHref(page, command)) {
                        timer.end();
                        succes_message = "+";
                    } else {
                        timer.end();
                        succes_message = "!";
                    }
                    log.append(succes_message + " [checkLinkPresentByHref \"" + command.get(0) + "\"] " +
                            timer.duration() + "\n");
                    timer.reset();
                    break;

                case "checkLinkPresentByName":
                    timer.start();
                    if (operation.checkLinkPresentByName(page, command)) {
                        timer.end();
                        succes_message = "+";
                    } else {
                        timer.end();
                        succes_message = "!";
                    }
                    log.append(succes_message + " [checkLinkPresentByName \"" + command.get(0) + "\"] " +
                            timer.duration() + "\n");
                    timer.reset();
                    break;

                case "checkPageTitle":
                    timer.start();
                    if (operation.checkPageTitle(page, command)) {
                        timer.end();
                        succes_message = "+";
                    } else {
                        timer.end();
                        succes_message = "!";
                    }
                    log.append(succes_message + " [checkPageTitle \"" + command.get(0) + "\"] " +
                            timer.duration() + "\n");
                    timer.reset();
                    break;

                case "checkPageContains":
                    timer.start();
                    if (operation.checkPageContains(page, command)) {
                        timer.end();
                        succes_message = "+";
                    } else {
                        timer.end();
                        succes_message = "!";
                    }
                    log.append(succes_message + " [checkPageContains \"" + command.get(0) + "\"] " +
                            timer.duration() + "\n");
                    timer.reset();
                    break;

                default:
                    log.append("? " + "[unknown command]\n");
                    //throw new UnknownOperationException("Несуществующая комманда. Проверьте введенные данные");
            }

        }

        log.append("Total tests: " + operation.getAmountOfCommands() + "\n");
        log.append("Passed/Failed: " + operation.getPassedTests() + "/" + operation.getFailedTests() + "\n");
        log.append("Total time: " + timer.totalTime() + "\n");
        log.append("Average time: " + timer.averageTime(operation.getAmountOfCommands()) + "\n");
        System.out.println(log.toString());
    }

    //Данная реализация построена на карте, где ключ-команда, значение-аргументы. Простота, удобство,
    //легкость понимания, лаконичность - плюсы данной реализации.
    //Однако из-за такой реализации мы не можем выполнять одинаковые операции с различными аргументами, т.к.
    //последняя операция будет переписывать предыдущие.
    public void executeByMap() throws Exception {
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
