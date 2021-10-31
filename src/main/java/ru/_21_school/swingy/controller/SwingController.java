package ru._21_school.swingy.controller;

import ru._21_school.swingy.Game;
import ru._21_school.swingy.database.DatabaseService;
import ru._21_school.swingy.model.Area;
import ru._21_school.swingy.model.equipment.Aid;
import ru._21_school.swingy.model.equipment.Equipment;
import ru._21_school.swingy.model.person.Person;
import ru._21_school.swingy.model.person.PersonFactory;
import ru._21_school.swingy.model.person.PersonRace;
import ru._21_school.swingy.view.swingUI.ChooseHero;
import ru._21_school.swingy.view.swingUI.CreateHero;
import ru._21_school.swingy.view.swingUI.GameArea;
import ru._21_school.swingy.view.swingUI.GameImages;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.*;
import java.util.List;

public class SwingController implements Controller {

    private ChooseHero chooseHeroWindow;
    private CreateHero createHeroWindow;
    private GameArea gameAreaWindow;

    @Override
    public void selectHero(final List<Person> persons) {
        chooseHeroWindow = new ChooseHero();

        chooseHeroWindow.getMainFrame().addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                Game.getInstance().exitGame();
            }
        });

        for (Person p : persons) {
            chooseHeroWindow.getPersonListModel().addElement(p);
        }
        chooseHeroWindow.getPersonList().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                chooseHeroWindow.getDeleteButton().setEnabled(chooseHeroWindow.getPersonList().getSelectedIndex() >= 0);
                Person select = chooseHeroWindow.getPersonList().getSelectedValue();
                if (select != null) {
                    chooseHeroWindow.getIconLabel().setIcon(select.getLabel());
                    chooseHeroWindow.getStatPane().setText(String.format("%s (%s)\nHP: %d\nA: %d\nD: %d\nAg: %d",
                            select.getName(),
                            select.getRace(),
                            select.getHitPoints(),
                            select.getAttack(),
                            select.getDefense(),
                            select.getAgility()));
                    chooseHeroWindow.getPlayButton().setEnabled(true);
                }
            }
        });

        chooseHeroWindow.getAddButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                chooseHeroWindow.getMainFrame().setVisible(false);
                createNewHero();
            }
        });

        chooseHeroWindow.getDeleteButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Person person = chooseHeroWindow.getPersonList().getSelectedValue();
                DatabaseService.getInstance().deletePerson(person);
                chooseHeroWindow.getPersonListModel().removeElement(person);
                chooseHeroWindow.getIconLabel().setIcon(null);
                chooseHeroWindow.getStatPane().setText(null);
            }
        });

        chooseHeroWindow.getPlayButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                Person hero = chooseHeroWindow.getPersonList().getSelectedValue();
                Game.getInstance().setHero(hero);
                Game.getInstance().validateHero();
                Game.getInstance().setArea(new Area(hero));
                chooseHeroWindow.getMainFrame().dispose();
                Game.getInstance().play();
            }
        });
    }

    private void createNewHero() {
        createHeroWindow = new CreateHero();

        createHeroWindow.getPersonList().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                PersonRace select = createHeroWindow.getPersonList().getSelectedValue();
                createHeroWindow.getIconLabel().setIcon(GameImages.getInstance().getPersonLabel(select.name()));
                createHeroWindow.getStatPane().setText(String.format("%s\nHP: %d\nA: %d\nD: %d\nAg: %d",
                                                        select.name(),
                                                        select.getHitPoints(),
                                                        select.getAttack(),
                                                        select.getDefense(),
                                                        select.getAgility()));
                createHeroWindow.getCreateButton().setEnabled(!isEmpty(createHeroWindow.getNameField().getText()));
            }
        });

        createHeroWindow.getNameField().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent keyEvent) {}

            @Override
            public void keyPressed(KeyEvent keyEvent) {}

            @Override
            public void keyReleased(KeyEvent keyEvent) {
                createHeroWindow.getCreateButton().setEnabled(createHeroWindow.getPersonList().getSelectedValue() != null && !isEmpty(createHeroWindow.getNameField().getText()));
            }
        });

        createHeroWindow.getCreateButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                PersonRace race = createHeroWindow.getPersonList().getSelectedValue();
                String name = createHeroWindow.getNameField().getText().trim();
                if (name.length() <= 20) {
                    chooseHeroWindow.getPersonListModel().addElement(PersonFactory.newPerson(race, name));
                    chooseHeroWindow.getMainFrame().setVisible(true);
                    createHeroWindow.getMainFrame().dispose();

                }
                else {
                    JOptionPane.showMessageDialog(createHeroWindow.getMainFrame(), "Name so long! (Max 20 symbols)", "Create error", JOptionPane.INFORMATION_MESSAGE, null);
                }
            }
        });
    }

    @Override
    public void play() {
        gameAreaWindow = new GameArea(Game.getInstance().getArea(), Game.getInstance().getHero());
        updateHeroPane(Game.getInstance().getHero());

        gameAreaWindow.getMainFrame().setVisible(true);

        gameAreaWindow.getMainFrame().addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                gameAreaWindow.getMainFrame().dispose();
                Game.getInstance().exitGame();
            }
        });


        gameAreaWindow.getUpButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (!Game.getInstance().moveHero(0, -1)) {
                    isWin();
                }
                gameAreaWindow.updateMap(Game.getInstance().getArea(), Game.getInstance().getHero());
                updateHeroPane(Game.getInstance().getHero());
            }
        });

        gameAreaWindow.getDownButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (!Game.getInstance().moveHero(0, 1)){
                    isWin();
                }
                gameAreaWindow.updateMap(Game.getInstance().getArea(), Game.getInstance().getHero());
                updateHeroPane(Game.getInstance().getHero());
            }
        });

        gameAreaWindow.getLeftButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (!Game.getInstance().moveHero(-1, 0)) {
                    isWin();
                }
                gameAreaWindow.updateMap(Game.getInstance().getArea(), Game.getInstance().getHero());
                updateHeroPane(Game.getInstance().getHero());
            }
        });

        gameAreaWindow.getRightButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (!Game.getInstance().moveHero(1, 0)) {
                    isWin();
                }
                gameAreaWindow.updateMap(Game.getInstance().getArea(), Game.getInstance().getHero());
                updateHeroPane(Game.getInstance().getHero());
            }
        });

        gameAreaWindow.getFleeButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (Game.getInstance().tryToFlee()) {
                    escapeBattle();
                } else {
                    logger("You tried to sneak unnoticed, but the enemy noticed you." );
                    Game.getInstance().fight();
                    isDied();
                    Game.getInstance().trophy();
                }
                updateHeroPane(Game.getInstance().getHero());
                updateEnemyPane(null);
                resetButtons();
            }
        });

        gameAreaWindow.getFightButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                logger("You rush into battle.");
                Game.getInstance().fight();
                isDied();
                Game.getInstance().trophy();
                updateHeroPane(Game.getInstance().getHero());
                updateEnemyPane(null);
                resetButtons();

            }
        });

        gameAreaWindow.getSwitchButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                gameAreaWindow.getMainFrame().dispose();
                Game.getInstance().changeGameMode();
            }
        });

    }

    private void logger(String s) {
        if (gameAreaWindow != null) {
            gameAreaWindow.getLogTextArea().append(String.format("%s\n", s));
        }
    }

    private void isWin() {
        if (!Game.getInstance().getHero().isDead()) {
            JOptionPane.showMessageDialog(gameAreaWindow.getMainFrame(), "YOU WIN!", "Game Over", JOptionPane.INFORMATION_MESSAGE, null);
            gameAreaWindow.getMainFrame().dispose();
            Game.getInstance().exitGame();
        }
    }

    private void isDied() {
        if (Game.getInstance().getHero().isDead()) {
            JOptionPane.showMessageDialog(gameAreaWindow.getMainFrame(), "YOU DIED!", "Game Over", JOptionPane.INFORMATION_MESSAGE, null);
            gameAreaWindow.getMainFrame().dispose();
            Game.getInstance().heroIsDead();
            Game.getInstance().exitGame();
        }
    }

    private void resetButtons() {
        gameAreaWindow.getFleeButton().setEnabled(false);
        gameAreaWindow.getFightButton().setEnabled(false);

        gameAreaWindow.getUpButton().setEnabled(true);
        gameAreaWindow.getDownButton().setEnabled(true);
        gameAreaWindow.getLeftButton().setEnabled(true);
        gameAreaWindow.getRightButton().setEnabled(true);

        gameAreaWindow.getSwitchButton().setEnabled(true);
    }

    private void escapeBattle() {
        logger("You have carefully bypassed the enemy.");
    }

    @Override
    public void fight(Person person1, Person person2, int damage) {
        String who = String.format("%-30s", person1.getName() + "(" + person1.getRace() + ")[" + person1.getHitPoints() + "/" + person1.getFullHitPoints() + "]: ");
        if (damage == 0) {
            logger(who + "attack and miss.");
        }
        else {
            logger(who + "does damage " + damage + " hp.");
        }
        if (person2.isDead()) {
            who = String.format("%-30s", person2.getName() + "(" + person2.getRace() + ")[" + person2.getHitPoints() + "/" + person2.getFullHitPoints() + "]: ");
            logger(who + "is dead.");
        }
        updateEnemyPane(Game.getInstance().getEnemy());
        updateHeroPane(Game.getInstance().getHero());
    }

    @Override
    public void equip(boolean isEquip, Equipment equipment) {
        if (isEquip) {
            logger("You found " + equipment.getName().toUpperCase());
            updateHeroPane(Game.getInstance().getHero());
        }
        else {
            logger("You found " + equipment.getName().toUpperCase() + ", but it has low stat and you throw it");
        }
    }

    @Override
    public void applyAid(Aid aid) {
        logger("You found " + aid.getName()
                + " | HP+" + aid.getBonusHitPoints()
                + " | A+" + aid.getBonusAttack()
                + " | D+" + aid.getBonusDefense()
                + " | Ag+" + aid.getBonusAgility()
                + " | Exp+" + aid.getBonusExperience());
        updateHeroPane(Game.getInstance().getHero());
    }

    private void updateHeroPane(Person hero) {
        if (gameAreaWindow.getHeroIconLabel().getIcon() == null) {
            gameAreaWindow.getHeroIconLabel().setIcon(hero.getLabel());

            gameAreaWindow.getHeroNameLabel().setText(hero.getName() + '/' + hero.getRace());
        }

        gameAreaWindow.getHeroHpBar().setMaximum(hero.getFullHitPoints());
        gameAreaWindow.getHeroHpBar().setValue(hero.getHitPoints());
        gameAreaWindow.getHeroHpBar().setString("HP: " + hero.getHitPoints() + "/" + hero.getFullHitPoints());

        gameAreaWindow.getHeroExpBar().setMaximum(hero.getNextLevelExperience());
        gameAreaWindow.getHeroExpBar().setValue(hero.getExperience());
        gameAreaWindow.getHeroExpBar().setString("EXP: " + hero.getExperience() + "/" + hero.getNextLevelExperience());

        gameAreaWindow.getHeroStatPane().setText(hero.toStat());
    }

    private void updateEnemyPane(Person enemy) {
        if (enemy == null) {
            gameAreaWindow.getEnemyIconLabel().setIcon(null);
            gameAreaWindow.getEnemyNameLabel().setVisible(false);

            gameAreaWindow.getEnemyHpBar().setVisible(false);
            gameAreaWindow.getEnemyExpBar().setVisible(false);

            gameAreaWindow.getEnemyStatPane().setText(null);
        }
        else {
            gameAreaWindow.getEnemyIconLabel().setIcon(enemy.getLabel());

            gameAreaWindow.getEnemyNameLabel().setText(enemy.getName() + '/' + enemy.getRace());
            gameAreaWindow.getEnemyNameLabel().setVisible(true);

            gameAreaWindow.getEnemyHpBar().setMaximum(enemy.getFullHitPoints());
            gameAreaWindow.getEnemyHpBar().setValue(enemy.getHitPoints());
            gameAreaWindow.getEnemyHpBar().setString("HP: " + enemy.getHitPoints() + "/" + enemy.getFullHitPoints());
            gameAreaWindow.getEnemyHpBar().setVisible(true);

            gameAreaWindow.getEnemyExpBar().setMaximum(enemy.getNextLevelExperience());
            gameAreaWindow.getEnemyExpBar().setValue(enemy.getExperience());
            gameAreaWindow.getEnemyExpBar().setString("EXP: " + enemy.getExperience() + "/" + enemy.getNextLevelExperience());
            gameAreaWindow.getEnemyExpBar().setVisible(true);

            gameAreaWindow.getEnemyStatPane().setText(enemy.toStat());
        }
    }

    @Override
    public void fightOrFlee(Person enemy) {

        gameAreaWindow.getFleeButton().setEnabled(true);
        gameAreaWindow.getFightButton().setEnabled(true);

        gameAreaWindow.getUpButton().setEnabled(false);
        gameAreaWindow.getDownButton().setEnabled(false);
        gameAreaWindow.getLeftButton().setEnabled(false);
        gameAreaWindow.getRightButton().setEnabled(false);

        gameAreaWindow.getSwitchButton().setEnabled(false);

        updateEnemyPane(enemy);
    }

    public boolean isEmpty(String s) {
        return s == null || s.trim().isEmpty();
    }
}
