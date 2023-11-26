package org.example;

public class Main {
    public static void main(String[] args) {

        // контекст это объект с конфигами и данными из json файла
        Context context = Utils.loadConfig(args);
        Utils.printHelp();
        Utils.startLoop(context);
    }
}