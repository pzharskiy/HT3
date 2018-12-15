package util;

import exceptions.AccessException;
import exceptions.InvalidArgumentsException;
import exceptions.InvalidNumberOfArgumentsException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Checker {

    public Checker() {
    }

    public void checkConsoleArguments (String[] args) throws InvalidNumberOfArgumentsException, FileNotFoundException{
        if (args.length!=2) {
            throw new InvalidNumberOfArgumentsException("Передано неверное количество аргументов. Необходимо: 2." +
                    " Первый аргумент - путь к файлу с инструкцией, второй - путь, по которому будет создан файл с логом");
        }

        File file = new File(args[0]);
        if (!file.isFile() && !file.exists()) {
            throw new FileNotFoundException("Файл не найден или не сущестует");
        }
        else if (!file.canRead()) {
            throw new AccessException("Ошибка доступа к файлу");
        }

    }

    public void checkCommandArguments( Map<String, List<String>> commandsMap) throws InvalidNumberOfArgumentsException, InvalidArgumentsException{

        Iterator<Map.Entry<String, List<String>>> iter = commandsMap.entrySet().iterator();
        while (iter.hasNext())
        { Map.Entry<String, List<String>> entry = iter.next();
            switch (entry.getKey()) {
                case "open":
                    if (entry.getValue().size() != 2) {
                        throw new InvalidNumberOfArgumentsException("В команду ["+entry.getKey()+"] передано неверное количество аргументов. Необходимо: 2." +
                                " Первый аргумент - url, второй - время timeout");
                    }

                    if (!entry.getValue().get(1).matches("[+]?\\d+")) {
                        throw new InvalidArgumentsException("В команде ["+entry.getKey()+"] время ожидания timeout должно быть целым положительным числом");
                    }

                    break;

                default:

                    if (entry.getValue().size() != 1) {
                        throw new InvalidNumberOfArgumentsException("В команду ["+entry.getKey()+"] передано неверное количество аргументов. Необходимо: 1.");
                    }
                    break;
            }
        }
    }
}
