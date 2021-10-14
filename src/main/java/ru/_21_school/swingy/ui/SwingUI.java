package ru._21_school.swingy.ui;

import ru._21_school.swingy.model.Area;
import ru._21_school.swingy.controller.Controller;
import ru._21_school.swingy.model.equipment.Aid;
import ru._21_school.swingy.model.equipment.Equipment;
import ru._21_school.swingy.model.person.Person;
import ru._21_school.swingy.model.person.PersonRace;
import ru._21_school.swingy.ui.swing.ChooseHero;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class SwingUI implements UserInterface {

    @Override
    public void helloScreen() {

    }

    @Override
    public void updateGameArea(Area area) {

    }

    @Override
    public void gameOverScreen(boolean isWin) {

    }

    @Override
    public void fightInfo(Person person1, Person person2, int damage) {

    }

    @Override
    public void equipInfo(boolean isEquip, Equipment equipment) {

    }

    @Override
    public void chooseRace(List<PersonRace> races) {

    }

    @Override
    public void chooseHero(List<Person> persons) {
        final ChooseHero view = new ChooseHero();

        view.getAddButton().setAction(new AbstractAction("add") {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                model.setFirstName("");
                model.setLastName("");
                model.setAddress("");
            }
        });

        view.getDeleteButton().setAction(new AbstractAction("delete") {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                int choose = view.getPersonList().getSelectedIndex();
            }
        });
    }

    @Override
    public void aidInfo(Aid aid) {

    }

    @Override
    public void gameInfo() {

    }

    @Override
    public void info(String message) {

    }

    @Override
    public void printPersonStat(Person person) {

    }

    @Override
    public void isFight() {

    }

    private void setUpViewEvents(){
        view.getBtnClear().setAction(new AbstractAction("Clear") {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                model.setFirstName("");
                model.setLastName("");
                model.setAddress("");
            }
        });

        view.getBtnSave().setAction(new AbstractAction("Save") {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                ...
                //validate etc.
                ...
                model.setFirstName(view.getTxtFName().getText());
                model.setLastName(view.getTxtLName().getText());
                model.setAddress(view.getTxtAddress().getText());
                model.save();
            }
        });
    }
}
