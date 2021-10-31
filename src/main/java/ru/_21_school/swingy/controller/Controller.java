package ru._21_school.swingy.controller;

import ru._21_school.swingy.model.equipment.Aid;
import ru._21_school.swingy.model.equipment.Equipment;
import ru._21_school.swingy.model.person.Person;

import java.util.List;

public interface Controller {

    void selectHero(List<Person> persons);
    void play();
    void fight(Person person1, Person person2, int damage);
    void equip(boolean isEquip, Equipment equipment);
    void applyAid(Aid aid);
    void fightOrFlee(Person enemy);
}
