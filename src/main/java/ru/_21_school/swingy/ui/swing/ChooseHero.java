package ru._21_school.swingy.ui.swing;

import ru._21_school.swingy.model.person.Person;
import ru._21_school.swingy.model.person.PersonFactory;
import ru._21_school.swingy.model.person.PersonRace;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static javax.swing.GroupLayout.Alignment.*;

public class ChooseHero {
    private JFrame mainFrame;
    private JLabel iconLabel;
    private JTextPane statPane;
    private DefaultListModel<Person> personListModel;
    private JList<Person> personList;
    private JButton addButton;
    private JButton deleteButton;
    private JButton playButton;
    private int width = 400;
    private int height = 400;

    private Person hero;

    public ChooseHero() {
        prepareWindow();
    }

    public static void main(String[] args) {
        ChooseHero chooseHero = new ChooseHero();
    }

    private void prepareWindow() {
        mainFrame = new JFrame("Choose Hero");
        mainFrame.setSize(width, height);
//        mainFrame.setMinimumSize(new Dimension(width, height));
        mainFrame.setPreferredSize(new Dimension(width, height));
        mainFrame.setResizable(false);

        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent){
                System.exit(0);
            }
        });

        GroupLayout layout = new GroupLayout(mainFrame.getContentPane());
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        mainFrame.setLayout(layout);

        //Компоненты
        iconLabel = new JLabel(new ImageIcon("src/main/resources/img/personLabels/human.gif"));
//        iconLabel.setPreferredSize(new Dimension(width / 2, height / 2));
//        iconLabel.setMaximumSize(new Dimension(width / 2, height / 2));
//        iconLabel.setMinimumSize(new Dimension(120, 120));

        statPane = new JTextPane();
//        statPane.setPreferredSize(new Dimension(width / 2, height / 2));
//        statPane.setMaximumSize(new Dimension(width / 2, height / 2));
//        statPane.setMinimumSize(new Dimension(150, 120));
        statPane.setText(PersonFactory.newPerson(PersonRace.values()[(int) (Math.random() * PersonRace.values().length)], "name").toStat());

        personListModel = new DefaultListModel<>();
        personList = new JList<>(personListModel);
        personList.setPreferredSize(new Dimension(width, height / 2));
        personList.setMinimumSize(new Dimension(width, height / 2));
        for (int i = 0; i < 10; i++) {
            personListModel.addElement(PersonFactory.newPerson(PersonRace.values()[(int) (Math.random() * PersonRace.values().length)], "name" + i));
        }

        addButton = new JButton("add");
        deleteButton = new JButton("delete");
        playButton = new JButton("Play");
        playButton.setEnabled(false);

        JPanel p1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        p1.add(addButton);
        p1.add(deleteButton);

        JPanel p2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        p2.add(new JSeparator());
        p2.add(playButton);

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

//    private void showEventDemo() {
//        headerLabel.setText("Control in action: Button");
//
//        JButton okButton = new JButton("OK");
//        JButton submitButton = new JButton("Submit");
//        JButton cancelButton = new JButton("Cancel");
//
//        okButton.setActionCommand("OK");
//        submitButton.setActionCommand("Submit");
//        cancelButton.setActionCommand("Cancel");
//
//        okButton.addActionListener(new ButtonClickListener());
//        submitButton.addActionListener(new ButtonClickListener());
//        cancelButton.addActionListener(new ButtonClickListener());
//
//        controlPanel.add(okButton);
//        controlPanel.add(submitButton);
//        controlPanel.add(cancelButton);
//
//        mainFrame.setVisible(true);
//    }
//
//    private class ButtonClickListener implements ActionListener {
//        public void actionPerformed(ActionEvent e) {
//            String command = e.getActionCommand();
//
//            if( command.equals( "OK" ))  {
//                statusLabel.setText("Ok Button clicked.");
//            } else if( command.equals( "Submit" ) )  {
//                statusLabel.setText("Submit Button clicked.");
//            } else {
//                statusLabel.setText("Cancel Button clicked.");
//            }
//        }
//    }


    public JFrame getMainFrame() {
        return mainFrame;
    }

    public JLabel getIconLabel() {
        return iconLabel;
    }

    public JTextPane getStatPane() {
        return statPane;
    }

    public JList<Person> getPersonList() {
        return personList;
    }

    public void setPersonList(JList<Person> personList) {
        this.personList = personList;
    }

    public JButton getAddButton() {
        return addButton;
    }

    public JButton getDeleteButton() {
        return deleteButton;
    }

    public JButton getPlayButton() {
        return playButton;
    }

    public DefaultListModel<Person> getPersonListModel() {
        return personListModel;
    }

    public Person getHero() {
        return hero;
    }

    public void setHero(Person hero) {
        this.hero = hero;
    }
}