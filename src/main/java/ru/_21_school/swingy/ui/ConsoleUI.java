package ru._21_school.swingy.ui;

import ru._21_school.swingy.model.Area;
import ru._21_school.swingy.model.equipment.Aid;
import ru._21_school.swingy.model.equipment.Block;
import ru._21_school.swingy.model.equipment.Equipment;
import ru._21_school.swingy.model.person.Person;
import ru._21_school.swingy.model.person.PersonRace;

import java.util.List;

public class ConsoleUI implements UserInterface {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
    public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

    @Override
    public void helloScreen() {
        System.out.println("Swingy");
        System.out.println("Select hero race:");
        for(PersonRace r: PersonRace.values()) {
            System.out.println(r.name());
        }
//        String race = controller.getInput();

    }

    @Override
    public void chooseHero(List<Person> persons) {
        for (int i = 0; i < persons.size(); i++) {
            System.out.printf("%d) %10s | Race: %10s | Level %3s\n", i,
                    persons.get(i).getName(),
                    persons.get(i).getRace(),
                    persons.get(i).getLevel());
        }
    }

    @Override
    public void chooseRace(List<PersonRace> races) {
        for (int i = 0; i < races.size(); i++) {
            System.out.printf("%d) %10s | HP: %3d | A %3d | D %3d | Ag %3d |\n", i,
                                races.get(i).name(),
                                races.get(i).getHitPoints(),
                                races.get(i).getAttack(),
                                races.get(i).getDefense(),
                                races.get(i).getAgility());
        }
    }

    @Override
    public void printPersonStat(Person p) {
        System.out.printf("\n==================== %s ====================\n", p.getName().toUpperCase());
        System.out.println(p.getRace() + " L" + p.getLevel() + " " + p.getExperience() + "/" + p.getNextLevelExperience());
        System.out.print("HP: " + p.getHitPoints() + "/" + p.getFullHitPoints() + " | ");
        System.out.print("A: " + p.getAttack() + " | ");
        System.out.print("D: " + p.getDefense() + " | ");
        System.out.println("Ag: " + p.getAgility() + " | ");
        for (Equipment e : p.getEquipments()) {
            System.out.print(e.getName() + " | ");
        }
        System.out.println();
    }

    private void printMap(Area area) {
        System.out.println("==================== MAP =====================");
        int size = area.getSize();
        for (int j = 0; j < size; j++) {
            for (int i = 0; i < size; i++) {
                Object obj = area.getObject(i, j);
                if (i == area.getHeroPositionX() && j == area.getHeroPositionY()) {
                    System.out.print(ANSI_BLUE_BACKGROUND + "& " + ANSI_RESET);
                }
                else if (obj != null) {
                    if (obj instanceof Aid) {
                        System.out.print(ANSI_GREEN_BACKGROUND + "A " + ANSI_RESET);
                    }
                    else if (obj instanceof Block) {
                        System.out.print(ANSI_BLACK_BACKGROUND + "# " + ANSI_RESET);
                    }
                    else if (obj instanceof Person) {
                        System.out.print(ANSI_RED_BACKGROUND + "E " + ANSI_RESET);
                    }
                }
                else {
                    System.out.print(ANSI_WHITE_BACKGROUND + ". " + ANSI_RESET);
                }
            }
            System.out.println();
        }
    }

    public void clearScreen() {
        System.out.print("\033[H\033[2J");
    }

    @Override
    public void updateGameArea(Area area) {
        clearScreen();
        System.out.printf("==================== LEVEL %d =====================\n", area.getLevel());
        printPersonStat(area.getHero());
        printMap(area);
        System.out.println("Choose action:");
    }

    @Override
    public void gameOverScreen(boolean isWin) {
        if (isWin) {
            System.out.printf("\n\n==================== YOU WIN! =====================\n");
        }
        else {
            System.out.printf("\n\n==================== YOU LOSE! ====================\n");
        }
    }

    @Override
    public void gameInfo() {
        clearScreen();
        System.out.printf("w - up\n" +
                          "s - down\n" +
                          "a - left\n" +
                          "d - right\n" +
                          "m - switch mode\n" +
                          "i - info\n" +
                          "exit - exit\n");
    }

    @Override
    public void info(String message) {
        System.out.println(message);
    }

    @Override
    public void isFight() {
        System.out.println("Do you want fight? (Y/n)");
    }

    @Override
    public void fightInfo(Person person1, Person person2, int damage) {

        System.out.printf("%-30s", person1.getName() + "(" + person1.getRace() + ")[" + person1.getHitPoints() + "/" + person1.getFullHitPoints() + "]: ");
        if (damage == 0) {
            System.out.println("attack and miss.");
        }
        else {
            System.out.println("does damage " + damage + " hp.");
        }
        if (person2.isDead()) {
            System.out.printf("%-30s", person2.getName() + "(" + person2.getRace() + ")[" + person2.getHitPoints() + "/" + person2.getFullHitPoints() + "]: ");
            System.out.println("is dead.");
        }
    }

    @Override
    public void equipInfo(boolean isEquip, Equipment equipment) {
        if (isEquip) {
            System.out.println("You found " + equipment.getName());
        }
        else {
            System.out.println("You found " + equipment.getName() + ", but it has low stat and you throw it");
        }
    }

    @Override
    public void aidInfo(Aid aid) {
        System.out.println("You found " + aid.getName()
                    + " | HP+" + aid.getBonusHitPoints()
                    + " | A+" + aid.getBonusAttack()
                    + " | D+" + aid.getBonusDefense()
                    + " | Ag+" + aid.getBonusAgility()
                    + " | Exp+" + aid.getBonusExperience());
    }
}
