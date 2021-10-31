package ru._21_school.swingy;

public class Swingy {

    public static void main(String[] args) {

        if (args.length == 1 && args[0].equalsIgnoreCase("console")) {
            Game.getInstance(0).start();
        } else if (args.length == 1 && args[0].equalsIgnoreCase("gui")) {
            Game.getInstance(1).start();
        } else {
            printUsage();
        }
    }

    private static void printUsage() {
        System.out.println("Usage: java -jar swingy.jar [console || gui ]");
    }
}
