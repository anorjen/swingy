package ru._21_school.swingy;

import ru._21_school.swingy.controller.ConsoleController;
import ru._21_school.swingy.controller.Controller;
import ru._21_school.swingy.view.swingUI.GameImages;
import ru._21_school.swingy.validator.PersonValidator;
import ru._21_school.swingy.controller.SwingController;
import ru._21_school.swingy.database.DatabaseService;
import ru._21_school.swingy.exception.SwingyException;
import ru._21_school.swingy.model.Area;
import ru._21_school.swingy.model.equipment.Aid;
import ru._21_school.swingy.model.equipment.Equipment;
import ru._21_school.swingy.model.equipment.EquipmentFactory;
import ru._21_school.swingy.model.person.Person;

import java.util.List;

public class Game {

    private static Game instance;
    public static final int MAX_GAME_LEVEL = 10;

    private int gameMode;
    private Controller controller;
    private Area area;
    private Person hero;
    private Person enemy;
    private final DatabaseService database;
    private final PersonValidator validator = new PersonValidator();

    public void validateHero() {
        if (!validator.validate(hero)) {
            hero = null;
            exitGame();
        }
    }

    public static Game getInstance() {
        if (instance == null) {
            throw new SwingyException("Game not initialize!");
        }
        return instance;
    }

    public static Game getInstance(int mode) {
        if (instance == null)
            instance = new Game(mode);
        return instance;
    }

    private Game(int mode) {
        this.gameMode = mode;
        if (gameMode == 1) {
            controller = new SwingController();
        } else {
            controller = new ConsoleController();
        }
        database = DatabaseService.getInstance();
    }

    public int getMode() {
        return gameMode;
    }

    public Person getHero() {
        return hero;
    }

    public void setHero(Person hero) {
        this.hero = hero;
    }

    public Person getEnemy() {
        return enemy;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public void start() {
        List<Person> personList = database.getAll();
        for (Person p : personList) {
            p.setLabel(GameImages.getInstance().getPersonLabel(p.getRace()));
            p.setIcon(GameImages.getInstance().getPersonIcon(p.getRace()));
        }
        controller.selectHero(database.getAll());
    }

    public void play() {
        controller.play();
    }

    public void exitGame() {
        if (hero != null && validator.validate(hero)) {
            database.savePerson(hero);
        }
        database.close();
        System.exit(0);
    }

    public boolean moveHero(int dX, int dY) {
        int newX = hero.getPosition().getX() + dX;
        int newY = hero.getPosition().getY() + dY;
        if (newX < 0 || newX >= area.getSize()
                || newY < 0 || newY >= area.getSize()) {
            hero.addExperience(hero.getExperience() / 10);
            hero.setHitPoints(hero.getFullHitPoints());
            area = new Area(hero);
            return hero.getLevel() < MAX_GAME_LEVEL;
        }
        Object obj = area.getObject(newX, newY);
        if (obj instanceof Aid) {
            Aid aid = (Aid) obj;
            hero.applyAid(aid);
            area.delete(newX, newY);
            controller.applyAid(aid);
        }
        else if (obj instanceof Person) {
            enemy = (Person) obj;
            controller.fightOrFlee(enemy);
            area.delete(newX, newY);
        }
        area.moveHero(dX, dY);
        return true;
    }

    public void trophy() {
        hero.addExperience((enemy.getLevel() + 1) * 300 + 200);
        if (Math.random() * 100 < 50) {
            Equipment equipment = EquipmentFactory.newEquip();
            boolean isEquip = hero.setEquip(equipment);
            controller.equip(isEquip, equipment);
        }
        enemy = null;
    }

    public boolean tryToFlee() {
        return Math.random() > 0.5;
    }

    public void fight() {
        int damage = 0;
        while (!hero.isDead() && !enemy.isDead()) {
            damage = hero.attack(enemy);
            controller.fightLog(hero, enemy, damage);
            if (!enemy.isDead()) {
                damage = enemy.attack(hero);
                controller.fightLog(enemy, hero, damage);
            }
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void changeGameMode() {
        gameMode = (gameMode == 0 ? 1 : 0);
        controller = (gameMode == 1 ? new SwingController() : new ConsoleController());
        controller.play();
    }

    public void heroIsDead() {
        hero.resumeExperience((hero.getLevel() + 1) * 100);
    }
}
