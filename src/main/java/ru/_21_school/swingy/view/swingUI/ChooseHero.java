package ru._21_school.swingy.view.swingUI;

import ru._21_school.swingy.model.person.Person;

import javax.swing.*;
import java.awt.*;

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

    public ChooseHero() {
        prepareWindow();
    }

    private void prepareWindow() {
        mainFrame = new JFrame("Choose Hero");
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

        statPane = new JTextPane();

        personListModel = new DefaultListModel<>();
        personList = new JList<>(personListModel);
        personList.setPreferredSize(new Dimension(width, height / 2));
        personList.setMinimumSize(new Dimension(width, height / 2));

        personList.setLayoutOrientation(JList.VERTICAL);

        Font defaultListFont = personList.getFont();
        personList.setFont(new Font("monospaced", defaultListFont.getStyle(), defaultListFont.getSize()));
        personList.setFixedCellWidth(60);

        JScrollPane personScroll = new JScrollPane(personList);

        addButton = new JButton("add");
        deleteButton = new JButton("delete");
        deleteButton.setEnabled(false);
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
                        .addComponent(personScroll)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(p1)
                                .addComponent(p2))
        );

        // Создание вертикальной группы
        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(BASELINE)
                        .addComponent(iconLabel)
                        .addComponent(statPane))
                .addComponent(personScroll)
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

}