package ru._21_school.swingy.ui;

import ru._21_school.swingy.model.Area;
import ru._21_school.swingy.model.equipment.Aid;
import ru._21_school.swingy.model.equipment.Equipment;
import ru._21_school.swingy.model.person.Person;
import ru._21_school.swingy.model.person.PersonRace;

import java.util.List;

public interface UserInterface {

    void helloScreen();
    void chooseHero(List<Person> persons);
    void chooseRace(List<PersonRace> races);
    void updateGameArea(Area area);
    void gameOverScreen(boolean isWin);
    void gameInfo();
    void info(String message);
    void fightInfo(Person person1, Person person2, int damage);
    void equipInfo(boolean isEquip, Equipment equipment);
    void aidInfo(Aid aid);
    void printPersonStat(Person person);
    void isFight();
}
