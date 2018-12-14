package util;

import exceptions.AccessException;
import exceptions.InvalidArgumentsException;
import exceptions.InvalidNumberOfArgumentsException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

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

    public void checkCommandArguments(List<String> arguments) throws InvalidNumberOfArgumentsException, InvalidArgumentsException{

        switch (arguments.get(0)) {
            case "open":
                if (arguments.size() != 3) {
                    throw new InvalidNumberOfArgumentsException("Передано неверное количество аргументов. Необходимо: 2." +
                            " Первый аргумент - url, второй - время timeout");
                }

                if (!arguments.get(2).matches("[+]?\\d+"))
                {
                    throw  new InvalidArgumentsException("Время ожидания timeout должно быть целым положительным числом");
                }

                break;

            default:

                if (arguments.size() != 2){
                    throw new InvalidNumberOfArgumentsException("Передано неверное количество аргументов. Необходимо: 1.");
                }
                break;

        }
    }
}
