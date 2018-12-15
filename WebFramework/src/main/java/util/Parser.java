package util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Parser {

    Checker checker = new Checker();

    public List<String> readFile(String FILE_PATH) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                new FileInputStream(FILE_PATH), StandardCharsets.UTF_8));
        List<String> commands = new ArrayList<>();

        //Получаем построчно комманды
        String line;
        while ((line = reader.readLine()) != null) {
            //Добавляем все команды в список
            commands.add(line);

        }
        return commands;
    }

    public Map<String,List<String>> parse(List<String> commands) {

        Map<String, List<String>> commandsMap = new LinkedHashMap<>();
        for (String command: commands
             ) {

            //Отделяем аргументы от операции из строки
            String argument = command.substring(command.indexOf(" ") + 1, command.length());
            List<String> parsedCommand = new ArrayList<String>();
            //Если аргументов > 1, то разбиваем на части и заносим отдельно
            if (argument.substring(1, argument.length() - 1).contains("\"")) {
                String arguments[] = argument.replaceAll("\"", "").split(" ");
                for (String a : arguments) {
                    parsedCommand.add(a);
                }
            } else parsedCommand.add(argument.substring(1, argument.length() - 1));

            //Заносим в карту
            commandsMap.put(command.substring(0, command.indexOf(" ")).trim(), parsedCommand);

        }
        checker.checkCommandArguments(commandsMap);
        return commandsMap;
    }
}
