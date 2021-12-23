package ru._21_school.swingy.view.swingUI;

import ru._21_school.swingy.model.person.PersonRace;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import static javax.swing.GroupLayout.Alignment.BASELINE;
import static javax.swing.GroupLayout.Alignment.LEADING;

public class CreateHero {
    private JFrame mainFrame;
    private JLabel iconLabel;
    private JTextPane statPane;
    private DefaultListModel<PersonRace> personListModel;
    private JList<PersonRace> personList;
    private JButton createButton;
    private JTextField nameField;
    private int width = 400;
    private int height = 400;

    public CreateHero() {
        prepareWindow();
    }

    private void prepareWindow() {
        mainFrame = new JFrame("Create Hero");
        mainFrame.setSize(width, height);
        mainFrame.setPreferredSize(new Dimension(width, height));
        mainFrame.setResizable(false);

        GroupLayout layout = new GroupLayout(mainFrame.getContentPane());
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        mainFrame.setLayout(layout);

        //Компоненты
        iconLabel = new JLabel();
        iconLabel.setMinimumSize(new Dimension(128, 128));
        iconLabel.setBackground(Color.BLACK);


        statPane = new JTextPane();
        statPane.setEditable(false);

        personListModel = new DefaultListModel<>();
        personList = new JList<>(personListModel);
        personList.setPreferredSize(new Dimension(width, height / 2));
        personList.setMinimumSize(new Dimension(width, height / 2));
        personList.setListData(PersonRace.values());


        JLabel nameLabel = new JLabel("Set name: ");
        nameField = new JTextField(14);

        createButton = new JButton("Create");
        createButton.setEnabled(false);

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
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
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

    public DefaultListModel<PersonRace> getPersonListModel() {
        return personListModel;
    }

    public JLabel getIconLabel() {
        return iconLabel;
    }

    public JTextPane getStatPane() {
        return statPane;
    }
}
