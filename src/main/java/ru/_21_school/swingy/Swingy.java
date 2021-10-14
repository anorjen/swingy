package ru._21_school.swingy;

public class Swingy {

    public static void main(String[] args) {

        Game game;

        if (args.length == 1 && args[0].equalsIgnoreCase("console")) {
            game = new Game(0);
            game.start();
        } else if (args.length == 1 && args[0].equalsIgnoreCase("gui")) {
            game = new Game(1);
            game.start();
        } else {
            printUsage();
        }
    }

    private static void printUsage() {
        System.out.println("Usage: java -jar swingy.jar [console || gui ]");
    }
}
