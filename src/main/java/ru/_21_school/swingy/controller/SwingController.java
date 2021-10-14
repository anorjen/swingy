package ru._21_school.swingy.controller;

import ru._21_school.swingy.database.DatabaseService;
import ru._21_school.swingy.model.Area;
import ru._21_school.swingy.model.equipment.Aid;
import ru._21_school.swingy.model.equipment.Equipment;
import ru._21_school.swingy.model.person.Person;
import ru._21_school.swingy.model.person.PersonFactory;
import ru._21_school.swingy.model.person.PersonRace;
import ru._21_school.swingy.ui.ConsoleUI;
import ru._21_school.swingy.ui.SwingUI;
import ru._21_school.swingy.ui.swing.ChooseHero;
import ru._21_school.swingy.ui.swing.CreateHero;
import ru._21_school.swingy.ui.swing.GameArea;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.util.List;

public class SwingController implements Controller {

    static private final SwingUI ui = new SwingUI();

    @Override
    public Person selectHero(final List<Person> persons) {
        final ChooseHero view = new ChooseHero();

        view.getAddButton().setAction(new AbstractAction("add") {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                Person hero = createNewHero();
                view.getPersonListModel().addElement(hero);
            }
        });

        view.getDeleteButton().setAction(new AbstractAction("delete") {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                Person person = view.getPersonList().getSelectedValue();
                DatabaseService.getInstance().deletePerson(person);
                view.getPersonListModel().removeElement(person);
            }
        });

        view.getPlayButton().setAction(new AbstractAction("play") {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                Person hero = view.getPersonList().getSelectedValue();
                view.setHero(hero);
                view.getMainFrame().dispose();
            }
        });
        return view.getHero();
    }

    private Person createNewHero() {
        List<PersonRace> races = Arrays.asList(PersonRace.values());
        final CreateHero view = new CreateHero();

        Person person = null;
        String name = null;
        PersonRace personRace = null;
        view.getCreateButton().setAction(new AbstractAction("create") {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                PersonRace race = view.getPersonList().getSelectedValue();
                String name = view.getNameField().getText().trim();

                view.setHero(PersonFactory.newPerson(race, name.trim()));
                view.getMainFrame().dispose();
            }
        });
        return view.getHero();
    }

    @Override
    public void gameOver(boolean isWin) {

    }

    @Override
    public String play(Person hero, Area area) {
        GameArea view = new GameArea();
        view.updateMap();

        return null;
    }

    @Override
    public void fight(Person person1, Person person2, int damage) {

    }

    @Override
    public void equip(boolean isEquip, Equipment equipment) {

    }

    @Override
    public void applyAid(Aid aid) {

    }

    @Override
    public void gameInfo() {

    }

    @Override
    public boolean isFight(Person enemy) {
        return false;
    }
}
