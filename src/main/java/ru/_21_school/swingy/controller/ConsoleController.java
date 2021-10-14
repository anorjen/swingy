package ru._21_school.swingy.controller;

import ru._21_school.swingy.database.DatabaseService;
import ru._21_school.swingy.model.Area;
import ru._21_school.swingy.model.equipment.Aid;
import ru._21_school.swingy.model.equipment.Equipment;
import ru._21_school.swingy.exception.SwingyException;
import ru._21_school.swingy.model.person.Person;
import ru._21_school.swingy.model.person.PersonFactory;
import ru._21_school.swingy.model.person.PersonRace;
import ru._21_school.swingy.ui.ConsoleUI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

public class ConsoleController implements Controller {

    static private final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static private final ConsoleUI ui = new ConsoleUI();

    @Override
    public Person selectHero(List<Person> personList) {
        List<PersonRace> races = Arrays.asList(PersonRace.values());

        Person person = null;
        while (person == null) {
            int choose = chooseHero(personList);
            if (choose == personList.size() + 1) {
                int deleteNumber = deleteHero(personList);
                DatabaseService.getInstance().deletePerson(personList.get(deleteNumber));
                personList.remove(personList.get(deleteNumber));
            }
            else if (choose == personList.size()) {
                personList.add(createNewHero(races));
            }
            else {
                person = personList.get(choose);
            }
        }
        return person;
    }

    private Integer deleteHero(List<Person> persons) {
        ui.chooseHero(persons);
        ui.info("Choose hero to delete:");
        Integer nPerson = null;
        while (nPerson == null) {
            try {
                String choose = reader.readLine();
                nPerson = Integer.parseInt(choose);
                if (nPerson < 0 || nPerson >= persons.size()) {
                    throw new SwingyException("Wrong choose!");
                }
            } catch (Exception ex) {
                ui.info(ex.getMessage() + " Repeat input:");
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
                nPerson = Integer.parseInt(input);
                if (nPerson < 0 || nPerson >= persons.size() + 1) {
                    throw new SwingyException("Wrong choose!");
                }
            } catch (Exception ex) {
                ui.info(ex.getMessage() + " Repeat input:");
            }
        }
        return nPerson;
    }

    private Person createNewHero(List<PersonRace> races) {
        ui.chooseRace(races);
        PersonRace race = chooseRace(races);
        ui.info("Write hero name:");
        String name = null;
        while (name == null || name.isEmpty()) {
            try {
                name = reader.readLine().trim();
                if (name.isEmpty()){
                    throw new SwingyException("Empty name!");
                }
            } catch (Exception ex) {
                ui.info(ex.getMessage() + " Repeat input:");
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
                int nRace = Integer.parseInt(choose);
                if (nRace < 0 || nRace >= races.size()) {
                    throw new SwingyException("Wrong choose!");
                }
                race = races.get(nRace);
            } catch (Exception ex) {
                ui.info(ex.getMessage() + " Repeat input:");
            }
        }
        return race;
    }

    public String play(Person hero, Area area) {
        ui.updateGameArea(area);
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
                return action;
            }
            else {
                ui.info("Repeat input:");
            }
        }
    }

    @Override
    public void gameInfo() {
        ui.gameInfo();
        try {
            reader.readLine();
        }
        catch (IOException ex) {
            ui.info(ex.getMessage());
        }
    }

    @Override
    public boolean isFight(Person enemy) {
        ui.printPersonStat(enemy);
        ui.isFight();
        String answer = "";
        try {
            answer = reader.readLine().trim().toLowerCase();
        }
        catch (IOException ex) {
            ui.info(ex.getMessage());
        }
        return !answer.equals("n") && !answer.equals("no");
    }

    @Override
    public void fight(Person person1, Person person2, int damage) {
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

    @Override
    public void gameOver(boolean isWin) {
        ui.gameOverScreen(isWin);
    }
}
