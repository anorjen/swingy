package ru._21_school.swingy.controller;

import ru._21_school.swingy.Game;
import ru._21_school.swingy.database.DatabaseService;
import ru._21_school.swingy.model.Area;
import ru._21_school.swingy.model.equipment.Aid;
import ru._21_school.swingy.model.equipment.Equipment;
import ru._21_school.swingy.exception.SwingyException;
import ru._21_school.swingy.model.person.Person;
import ru._21_school.swingy.model.person.PersonFactory;
import ru._21_school.swingy.model.person.PersonRace;
import ru._21_school.swingy.view.ConsoleUI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

public class ConsoleController implements Controller {

    static private final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static private final ConsoleUI ui = new ConsoleUI();

    @Override
    public void selectHero(List<Person> personList) {
        ui.helloScreen();
        List<PersonRace> races = Arrays.asList(PersonRace.values());

        Person person = null;
        while (person == null) {
            int choose = chooseHero(personList);
            if (choose == personList.size() + 1) {
                int deleteNumber = deleteHero(personList);
                if (deleteNumber >= 0 && deleteNumber < personList.size()) {
                    DatabaseService.getInstance().deletePerson(personList.get(deleteNumber));
                    personList.remove(personList.get(deleteNumber));
                }
            }
            else if (choose == personList.size()) {
                Person newHero = createNewHero(races);
                if (newHero != null) {
                    personList.add(newHero);
                }
            }
            else {
                person = personList.get(choose);
            }
        }
        ui.gameInfo();
        Game.getInstance().setHero(person);
        Game.getInstance().validateHero();
        Game.getInstance().setArea(new Area(person));
        Game.getInstance().play();
    }

    private Integer deleteHero(List<Person> persons) {
        ui.chooseHero(persons);
        ui.info("Choose hero to delete:");
        Integer nPerson = null;
        while (nPerson == null) {
            try {
                String input = reader.readLine();
                if (input.equals("return")) {
                    return -1;
                }
                nPerson = Integer.parseInt(input);
                if (nPerson < 0 || nPerson >= persons.size()) {
                    throw new SwingyException("Wrong choose: " + input + " !");
                }
            } catch (Exception ex) {
                nPerson = null;
                ui.info("ERROR: "+  ex.getMessage() + "| For return type \"return\" |" + " Repeat input:");
            }
        }
        return nPerson;
    }

    private Integer chooseHero(List<Person> persons) {
        ui.chooseHero(persons);
        ui.info(String.format("%d) %10s\n%d) %10s\n", persons.size(), "Create NEW Hero", persons.size() + 1, "Delete Hero"));
        ui.info("Choose hero:");
        Integer nPerson = null;
        while (nPerson == null) {
            try {
                String input = reader.readLine();
                if (input.equals("exit")) {
                    Game.getInstance().exitGame();
                }
                nPerson = Integer.parseInt(input);
                // Список героев и пункты создать удалить
                if (nPerson < 0 || nPerson > persons.size() + 1) {
                    throw new SwingyException("Wrong choose: " + input + " !");
                }
            } catch (Exception ex) {
                nPerson = null;
                ui.info("ERROR: " + ex.getMessage() + "| Repeat input:");
            }
        }
        return nPerson;
    }

    private Person createNewHero(List<PersonRace> races) {
        ui.chooseRace(races);
        PersonRace race = chooseRace(races);
        if (race == null) {
            return null;
        }
        ui.info("Write hero name:");
        String name = null;
        while (name == null || name.isEmpty()) {
            try {
                name = reader.readLine().trim();

                if (name.isEmpty()){
                    throw new SwingyException("Empty name!");
                }
                else if (name.length() > 20) {
                    throw new SwingyException("Name must be 1-20 symbols");
                }
            } catch (Exception ex) {
                name = null;
                ui.info("ERROR: " + ex.getMessage() + "| Repeat input:");
            }
        }
        return PersonFactory.newPerson(race, name);
    }

    private PersonRace chooseRace(List<PersonRace> races) {
        ui.info("Choose hero race:");
        PersonRace race = null;
        while (race == null) {
            try {
                String choose = reader.readLine();
                if (choose.equals("return")) {
                    return null;
                }
                int nRace = Integer.parseInt(choose);
                if (nRace < 0 || nRace >= races.size()) {
                    throw new SwingyException("Wrong choose!");
                }
                race = races.get(nRace);
            } catch (Exception ex) {
                ui.info("ERROR: " + ex.getMessage() + " Repeat input:");
            }
        }
        return race;
    }

    private boolean takeAction(String action) {
        switch (action) {
            case "w" :
                return Game.getInstance().moveHero(0, -1);
            case "s":
                return Game.getInstance().moveHero(0, 1);
            case "a":
                return Game.getInstance().moveHero(-1, 0);
            case "d":
                return Game.getInstance().moveHero(1, 0);
            case "m":
                Game.getInstance().changeGameMode();
                return false;
            case "i":
                gameInfo();
                break;
            case "exit":
                return false;
            case "iddqd":
                Game.getInstance().getHero().setHitPoints(1000);
                break;
        }
        return true;
    }

    public void play() {
        boolean isPlay = true;
        while (isPlay) {
            ui.updateGameArea(Game.getInstance().getArea());
            String action = null;

            while (true) {
                try {
                    action = reader.readLine().trim().toLowerCase();
                } catch (IOException e) {
                    ui.info(e.getMessage());
                }
                if (action != null && !action.isEmpty()
                        && ("w".equals(action)
                        || "s".equals(action)
                        || "a".equals(action)
                        || "d".equals(action)
                        || "m".equals(action)
                        || "i".equals(action))
                        || "exit".equals(action)
                        || "iddqd".equals(action)) {
                    break;
                } else {
                    ui.info("Repeat input:");
                }
            }
            isPlay = takeAction(action);
        }
        gameOver();
    }

    private void gameInfo() {
        ui.gameInfo();
        try {
            reader.readLine();
        }
        catch (IOException ex) {
            ui.info(ex.getMessage());
        }
    }

    @Override
    public void fightOrFlee(Person enemy) {
        ui.printPersonStat(enemy);
        ui.isFight();
        String answer = "";
        try {
            answer = reader.readLine().trim().toLowerCase();
        }
        catch (IOException ex) {
            ui.info(ex.getMessage());
        }
        if ((answer.equals("n") || answer.equals("no")) && Game.getInstance().tryToFlee()) {
            ui.info("You have carefully bypassed the enemy.");
        }
        else {
            if ((answer.equals("n") || answer.equals("no"))) {
                ui.info("You tried to sneak unnoticed, but the enemy noticed you.");
            }
            else {
                ui.info("You rush into battle.");
            }
            Game.getInstance().fight();
            if (!Game.getInstance().getHero().isDead()) {
                Game.getInstance().trophy();
            }
            else {
                ui.gameOverScreen(false);
                Game.getInstance().exitGame();
            }
        }
    }

    @Override
    public void fightLog(Person person1, Person person2, int damage) {
        ui.fightInfo(person1, person2, damage);
    }

    @Override
    public void equip(boolean isEquip, Equipment equipment) {
        ui.equipInfo(isEquip, equipment);
    }

    @Override
    public void applyAid(Aid aid) {
        ui.aidInfo(aid);
    }

    public void gameOver() {
        if (Game.getInstance().getMode() == 1) {
            ui.info("Switch to GUI");
        } else {
            Person hero = Game.getInstance().getHero();
            if (hero.isDead()) {
                Game.getInstance().heroIsDead();
                ui.gameOverScreen(false);
            } else if (hero.getLevel() >= Game.MAX_GAME_LEVEL) {
                ui.gameOverScreen(true);
            } else {
                ui.info("You are chicken!");
            }
            Game.getInstance().exitGame();
        }
    }
}
