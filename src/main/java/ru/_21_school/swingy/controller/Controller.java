package ru._21_school.swingy.controller;

import ru._21_school.swingy.model.Area;
import ru._21_school.swingy.model.equipment.Aid;
import ru._21_school.swingy.model.equipment.Equipment;
import ru._21_school.swingy.model.person.Person;
import ru._21_school.swingy.model.person.PersonRace;

import java.util.List;

public interface Controller {

    Person selectHero(List<Person> persons);
//    Integer chooseHero(List<Person> persons);
//    Person createNewHero(List<PersonRace> races);
//    Integer deleteHero(List<Person> persons);

    void gameOver(boolean isWin);
    String play(Person hero, Area area);
    void gameInfo();
    void fight(Person person1, Person person2, int damage);
    void equip(boolean isEquip, Equipment equipment);
    void applyAid(Aid aid);
    boolean isFight(Person enemy);
}
