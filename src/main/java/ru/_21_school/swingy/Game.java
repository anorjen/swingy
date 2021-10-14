package ru._21_school.swingy;

import ru._21_school.swingy.controller.ConsoleController;
import ru._21_school.swingy.controller.Controller;
import ru._21_school.swingy.controller.SwingController;
import ru._21_school.swingy.database.DatabaseService;
import ru._21_school.swingy.model.Area;
import ru._21_school.swingy.model.equipment.Aid;
import ru._21_school.swingy.model.equipment.Equipment;
import ru._21_school.swingy.model.equipment.EquipmentFactory;
import ru._21_school.swingy.model.person.Person;
import ru._21_school.swingy.model.person.PersonFactory;
import ru._21_school.swingy.model.person.PersonRace;

import java.util.Arrays;
import java.util.List;

public class Game {

    private static final int MAX_GAME_LEVEL = 7;
    private int gameMode;
    private Controller controller;
    private Area area;
    private Person hero;
    private int gameLevel = 0;
    private final DatabaseService database;

    public Game(int gameMode) {
        this.gameMode = gameMode;
        if (gameMode == 1) {
            controller = new SwingController();
        } else {
            controller = new ConsoleController();
        }
        database = DatabaseService.getInstance();
    }

    public int getGameMode() {
        return gameMode;
    }

    public void start() {
        hero = controller.selectHero(database.getAll());
        boolean isWin = true;
        area = new Area(gameLevel, hero);
        int prevGameLevel = gameLevel;
        while (gameLevel <= MAX_GAME_LEVEL) {
            if (gameLevel > prevGameLevel) {
                area = new Area(gameLevel, hero);
                prevGameLevel = gameLevel;
            }
            String action = controller.play(hero, area);
            if (!takeAction(action)) {
                isWin = false;
                break;
            }
        }
        database.savePerson(hero);
        controller.gameOver(isWin);
    }

    private boolean takeAction(String action) {
        switch (action) {
            case "w" :
                return moveHero(0, -1);
            case "s":
                return moveHero(0, 1);
            case "a":
                return moveHero(-1, 0);
            case "d":
                return moveHero(1, 0);
            case "m":
                changeGameMode();
                break;
            case "i":
                controller.gameInfo();
                break;
            case "exit":
                return false;
            case "iddqd":
                hero.setHitPoints(1000);
                break;
        }
        return true;
    }

    private boolean moveHero(int dX, int dY) {
        int newX = hero.getPosition().getX() + dX;
        int newY = hero.getPosition().getY() + dY;
        if (newX < 0 || newX >= area.getSize()
                || newY < 0 || newY >= area.getSize()) {
            gameLevel++;
            hero.addExperience(hero.getExperience() / 10);
            hero.setHitPoints(hero.getFullHitPoints());
            return true;
        }
        Object obj = area.getObject(newX, newY);
        if (obj instanceof Aid) {
            Aid aid = (Aid) obj;
            hero.applyAid(aid);
            area.delete(newX, newY);
            controller.applyAid(aid);
        }
        else if (obj instanceof Person) {
            Person enemy = (Person) obj;
            if (isFight(enemy)) {
                if (fight(hero, enemy)) {

                    hero.addExperience((((Person) obj).getLevel() + 1) * 300 + 200);
                    if (Math.random() * 100 < 50 + hero.getLuck()) {
                        Equipment equipment = EquipmentFactory.newEquip();
                        boolean isEquip = hero.setEquip(equipment);
                        controller.equip(isEquip, equipment);
                    }
                    area.delete(newX, newY);
                }
                else {
                    return false;
                }
            }
            else {
                return true;
            }
        }
        area.moveHero(dX, dY);
        return true;
    }

    private boolean isFight(Person enemy) {
        boolean isFight = controller.isFight(enemy);
        if (!isFight) {
            return Math.random() > 0.5;
        }
        return true;
    }

    private boolean fight(Person hero, Person enemy) {
        int damage = 0;
        while (!hero.isDead() && !enemy.isDead()) {
            damage = hero.attack(enemy);
            controller.fight(hero, enemy, damage);
            if (!enemy.isDead()) {
                damage = enemy.attack(hero);
                controller.fight(enemy, hero, damage);
            }
        }
        return !hero.isDead();
    }

    public void changeGameMode() {
        gameMode = (gameMode == 0 ? 1 : 0);
        controller = (gameMode == 1 ? new SwingController() : new ConsoleController());
    }
}
