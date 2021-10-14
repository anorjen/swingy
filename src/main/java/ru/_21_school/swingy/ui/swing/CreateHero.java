package ru._21_school.swingy.ui.swing;

import ru._21_school.swingy.model.person.Person;
import ru._21_school.swingy.model.person.PersonFactory;
import ru._21_school.swingy.model.person.PersonRace;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;

import static javax.swing.GroupLayout.Alignment.BASELINE;
import static javax.swing.GroupLayout.Alignment.LEADING;

public class CreateHero {
    private JFrame mainFrame;
    private JLabel iconLabel;
    private JTextPane statPane;
    private JList<PersonRace> personList;
    private JButton createButton;
    private JTextField nameField;
    private int width = 400;
    private int height = 400;
    private Person hero;

    public CreateHero() {
        prepareWindow();
    }

    public static void main(String[] args) {
        CreateHero chooseHero = new CreateHero();
    }

    private void prepareWindow() {
        mainFrame = new JFrame("Create Hero");
        mainFrame.setSize(width, height);
//        mainFrame.setMinimumSize(new Dimension(width, height));
        mainFrame.setPreferredSize(new Dimension(width, height));
        mainFrame.setResizable(false);

        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                System.exit(0);
            }
        });

        GroupLayout layout = new GroupLayout(mainFrame.getContentPane());
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        mainFrame.setLayout(layout);

        //Компоненты
        iconLabel = new JLabel();
//        new ImageIcon("/home/dmitry/21-school/swingy/src/main/resources/human.gif")
//        iconLabel.setPreferredSize(new Dimension(width / 2, height / 2));
//        iconLabel.setMaximumSize(new Dimension(width / 2, height / 2));
        iconLabel.setMinimumSize(new Dimension(120, 120));
        iconLabel.setBackground(Color.BLACK);


        statPane = new JTextPane();
//        statPane.setPreferredSize(new Dimension(width / 2, height / 2));
//        statPane.setMaximumSize(new Dimension(width / 2, height / 2));
//        statPane.setMinimumSize(new Dimension(150, 120));

        DefaultListModel<PersonRace> personListModel = new DefaultListModel<>();
        personList = new JList<>(personListModel);
        personList.setPreferredSize(new Dimension(width, height / 2));
        personList.setMinimumSize(new Dimension(width, height / 2));
        personList.setListData(PersonRace.values());
//        personList.setSelectedIndex(0);

        personList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                PersonRace select = personList.getSelectedValue();
                iconLabel.setIcon(new ImageIcon("src/main/resources/img/personLabels/" + select.name().toLowerCase() + ".gif"));
                statPane.setText(String.format("%s\nHP: %d\nA: %d\nD: %d\nAg: %d",
                                        select.name(),
                                        select.getHitPoints(),
                                        select.getAttack(),
                                        select.getDefense(),
                                        select.getAgility()));
                createButton.setEnabled(!isEmpty(nameField.getText()));
            }
        });

        JLabel nameLabel = new JLabel("Set name: ");
        nameField = new JTextField(14);

        nameField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent keyEvent) {}

            @Override
            public void keyPressed(KeyEvent keyEvent) {}

            @Override
            public void keyReleased(KeyEvent keyEvent) {
                createButton.setEnabled(personList.getSelectedValue() != null && !isEmpty(nameField.getText()));
            }
        });

        createButton = new JButton("Create");
        createButton.setEnabled(false);
//        createButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent actionEvent) {
//                PersonRace race = personList.getSelectedValue();
//                String name = nameField.getText();
//                if (race != null && !isEmpty(name)) {
//                    hero = PersonFactory.newPerson(race, name.trim());
//                    System.out.println(hero);
//                }
//            }
//        });

        JPanel p1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        p1.add(nameLabel);
        p1.add(nameField);

        JPanel p2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        p2.add(createButton);

        // Создание горизонтальной группы
        layout.setHorizontalGroup(layout.createParallelGroup(LEADING)
                .addGroup(layout.createSequentialGroup()
                        .addComponent(iconLabel)
                        .addComponent(statPane))
                .addComponent(personList)
                .addGroup(layout.createSequentialGroup()
                        .addComponent(p1)
                        .addComponent(p2))
        );

        // Создание вертикальной группы
        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(BASELINE)
                        .addComponent(iconLabel)
                        .addComponent(statPane))
                .addComponent(personList)
                .addGroup(layout.createParallelGroup(BASELINE)
                        .addComponent(p1)
                        .addComponent(p2))
        );

        mainFrame.pack();
        mainFrame.setVisible(true);
    }

    public boolean isEmpty(String s) {
        return s == null || s.trim().isEmpty();
    }

    public JFrame getMainFrame() {
        return mainFrame;
    }

    public JButton getCreateButton() {
        return createButton;
    }

    public JList<PersonRace> getPersonList() {
        return personList;
    }

    public JTextField getNameField() {
        return nameField;
    }

    public Person getHero() {
        return hero;
    }

    public void setHero(Person hero) {
        this.hero = hero;
    }
}
