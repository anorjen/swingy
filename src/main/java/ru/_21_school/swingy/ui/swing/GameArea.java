package ru._21_school.swingy.ui.swing;

import ru._21_school.swingy.model.Area;
import ru._21_school.swingy.model.Coordinate;
import ru._21_school.swingy.model.equipment.Aid;
import ru._21_school.swingy.model.equipment.Block;
import ru._21_school.swingy.model.person.Person;
import ru._21_school.swingy.model.person.PersonFactory;
import ru._21_school.swingy.model.person.PersonRace;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

import static javax.swing.GroupLayout.Alignment.*;

public class GameArea {

    private JFrame mainFrame;
    private JLabel enemyIconLabel;
    private JTextPane enemyStatPane;

    private JLabel heroIconLabel;
    private JTextPane heroStatPane;

    private JButton fleeButton;
    private JButton fightButton;
    private JButton switchButton;

    private JButton upButton;
    private JButton downButton;
    private JButton leftButton;
    private JButton rightButton;

    private JPanel mapPanel;
    private JLabel[][] mapCells;

    private JTextArea logTextArea;

    private int width = 800;
    private int height = 800;

    private Person hero;
    private Area area;

    private final int ICON_SIZE = 64;
    private final int SIDE_PANE_WIDTH = 290;
    private final int VIEW_SIZE = 9;
    private final int VIEW_DISTANCE = VIEW_SIZE / 2;
    private final int INFO_PANEL_HEIGHT = 200;

    private Border whiteBorder = BorderFactory.createLineBorder(Color.white);
    private Border blueBorder = BorderFactory.createLineBorder(Color.blue);
    private Border blackBorder = BorderFactory.createLineBorder(Color.black);
    private Border redBorder = BorderFactory.createLineBorder(Color.red);
    private Border greenBorder = BorderFactory.createLineBorder(Color.green);
    private Border grayBorder = BorderFactory.createLineBorder(Color.darkGray);

    private String action;

    {
        hero = PersonFactory.newPerson(PersonRace.DWARF, "ddd");
        int level = 3;
        int size = 5 * level + 4;
        hero.setPosition(new Coordinate(size / 2, size / 2));
        area = new Area(level, hero);
    }

    public GameArea() {
        prepareWindow();
    }

    public static void main(String[] args) {
        GameArea gameArea = new GameArea();
    }

    private void prepareWindow() {
        mainFrame = new JFrame("Game Area");
//        mainFrame.setSize(width, height);
//        mainFrame.setMinimumSize(new Dimension(width, height));
        int winWidth = (VIEW_SIZE * ICON_SIZE) + (SIDE_PANE_WIDTH * 2);
        int winHeight = VIEW_SIZE * ICON_SIZE + INFO_PANEL_HEIGHT;
//        mainFrame.setPreferredSize(new Dimension(winWidth, winHeight));
        mainFrame.setSize(new Dimension(winWidth, winHeight));

//        mainFrame.setMaximumSize(new Dimension(1000, 1000));
//        mainFrame.setResizable(false);

        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
//                saveHero();
                System.exit(0);
            }
        });

        Container mainPanel = mainFrame.getContentPane();
//        mainPanel.setSize(new Dimension(winWidth, winHeight));
        GroupLayout layout = new GroupLayout(mainPanel);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        mainFrame.setLayout(layout);

        //Компоненты
        //Enemy
        enemyIconLabel = new JLabel();
        enemyIconLabel.setPreferredSize(new Dimension(SIDE_PANE_WIDTH, SIDE_PANE_WIDTH));
        enemyIconLabel.setBackground(Color.BLACK);
        enemyStatPane = new JTextPane();
        enemyStatPane.setEditable(false);

        enemyIconLabel.setIcon(new ImageIcon("src/main/resources/img/personLabels/dwarf.gif"));
        enemyStatPane.setText(PersonFactory.newPerson(PersonRace.DWARF, "enemy").toStat());


        //Hero
        heroIconLabel = new JLabel();
        heroIconLabel.setPreferredSize(new Dimension(SIDE_PANE_WIDTH, SIDE_PANE_WIDTH));
        heroIconLabel.setBackground(Color.BLACK);
        heroStatPane = new JTextPane();
        heroStatPane.setEditable(false);

        heroIconLabel.setIcon(new ImageIcon("src/main/resources/img/personLabels/ork.gif"));
        heroStatPane.setText(PersonFactory.newPerson(PersonRace.ORK, "hero").toStat());

        //buttons
        fleeButton = new JButton("Flee");
        fleeButton.setActionCommand("flee");
        fleeButton.setEnabled(false);
        fleeButton.addActionListener(new FightOrFleeClickListener());

        fightButton = new JButton("Fight");
        fightButton.setActionCommand("fight");
        fightButton.setEnabled(false);
        fightButton.addActionListener(new FightOrFleeClickListener());

//        fleeButton.setMinimumSize(new Dimension((SIDE_PANE_WIDTH - 20) / 2, 0));
//        fightButton.setMinimumSize(new Dimension((SIDE_PANE_WIDTH - 20) / 2, 0));

        switchButton = new JButton("Switch mode");
        switchButton.setActionCommand("switch");
//        switchButton.setEnabled(false);
        switchButton.addActionListener(new FightOrFleeClickListener());

        upButton = new JButton("Up");
        upButton.setActionCommand("up");
        upButton.addActionListener(new MoveClickListener());

        downButton = new JButton("Down");
        downButton.setActionCommand("down");
        downButton.addActionListener(new MoveClickListener());

        leftButton = new JButton("Left");
        leftButton.setActionCommand("left");
        leftButton.addActionListener(new MoveClickListener());

        rightButton = new JButton("Right");
        rightButton.setActionCommand("right");
        rightButton.addActionListener(new MoveClickListener());

        layout.linkSize(upButton, downButton, leftButton, rightButton);


        logTextArea = new JTextArea(10, 20);
        logTextArea.setEditable(false);
        logTextArea.setAutoscrolls(true);
        JScrollPane logScrollPane = new JScrollPane(logTextArea);
//        logScrollPane.setMinimumSize(new Dimension(0, 200));
//        logScrollPane.setMaximumSize(new Dimension(600, 400));
        logScrollPane.setPreferredSize(new Dimension(VIEW_SIZE * ICON_SIZE, VIEW_SIZE * ICON_SIZE / 2));
//        logScrollPane.setVisible(true);
        logScrollPane.setAutoscrolls(true);


        String text = "eee(ELF)[267/267]:            does damage 1 hp.\n" +
                "Enemy(GHOST)[134/135]:        does damage 1 hp.\n" +
                "eee(ELF)[266/267]:            does damage 1 hp.\n" +
                "Enemy(GHOST)[133/135]:        does damage 1 hp.\n" +
                "eee(ELF)[265/267]:            does damage 17 hp.\n" +
                "Enemy(GHOST)[116/135]:        does damage 1 hp.\n" +
                "eee(ELF)[264/267]:            does damage 1 hp.\n" +
                "Enemy(GHOST)[115/135]:        does damage 1 hp.\n" +
                "eee(ELF)[263/267]:            does damage 8 hp.\n" +
                "Enemy(GHOST)[107/135]:        does damage 1 hp.\n" +
                "eee(ELF)[262/267]:            does damage 1 hp.\n" +
                "Enemy(GHOST)[106/135]:        does damage 1 hp.\n" +
                "eee(ELF)[261/267]:            does damage 1 hp.\n" +
                "Enemy(GHOST)[105/135]:        does damage 1 hp.\n" +
                "eee(ELF)[260/267]:            does damage 4 hp.\n" +
                "Enemy(GHOST)[101/135]:        does damage 1 hp.\n" +
                "eee(ELF)[259/267]:            does damage 1 hp.\n" +
                "Enemy(GHOST)[100/135]:        does damage 1 hp.\n" +
                "eee(ELF)[258/267]:            does damage 1 hp.\n" +
                "Enemy(GHOST)[99/135]:         does damage 1 hp.\n" +
                "eee(ELF)[257/267]:            does damage 1 hp.\n" +
                "Enemy(GHOST)[98/135]:         does damage 1 hp.\n" +
                "eee(ELF)[256/267]:            does damage 2 hp.\n" +
                "Enemy(GHOST)[96/135]:         does damage 1 hp.\n" +
                "eee(ELF)[255/267]:            does damage 1 hp.\n" +
                "Enemy(GHOST)[95/135]:         does damage 1 hp.\n" +
                "eee(ELF)[254/267]:            attack and miss.\n" +
                "Enemy(GHOST)[95/135]:         does damage 1 hp.\n" +
                "eee(ELF)[253/267]:            does damage 16 hp.\n" +
                "Enemy(GHOST)[79/135]:         does damage 1 hp.\n" +
                "eee(ELF)[252/267]:            does damage 1 hp.\n" +
                "Enemy(GHOST)[78/135]:         does damage 1 hp.\n" +
                "eee(ELF)[251/267]:            does damage 1 hp.\n" +
                "Enemy(GHOST)[77/135]:         does damage 1 hp.\n" +
                "eee(ELF)[250/267]:            does damage 1 hp.\n" +
                "Enemy(GHOST)[76/135]:         does damage 1 hp.\n" +
                "eee(ELF)[249/267]:            does damage 20 hp.\n" +
                "Enemy(GHOST)[56/135]:         does damage 1 hp.\n" +
                "eee(ELF)[248/267]:            does damage 1 hp.\n" +
                "Enemy(GHOST)[55/135]:         does damage 1 hp.\n" +
                "eee(ELF)[247/267]:            does damage 1 hp.\n" +
                "Enemy(GHOST)[54/135]:         does damage 1 hp.\n" +
                "eee(ELF)[246/267]:            does damage 1 hp.\n" +
                "Enemy(GHOST)[53/135]:         does damage 1 hp.\n" +
                "eee(ELF)[245/267]:            does damage 1 hp.\n" +
                "Enemy(GHOST)[52/135]:         does damage 1 hp.\n" +
                "eee(ELF)[244/267]:            does damage 1 hp.\n" +
                "Enemy(GHOST)[51/135]:         does damage 1 hp.\n" +
                "eee(ELF)[243/267]:            does damage 1 hp.\n" +
                "Enemy(GHOST)[50/135]:         does damage 1 hp.\n" +
                "eee(ELF)[242/267]:            does damage 1 hp.\n" +
                "Enemy(GHOST)[49/135]:         does damage 1 hp.\n" +
                "eee(ELF)[241/267]:            does damage 1 hp.\n" +
                "Enemy(GHOST)[48/135]:         does damage 1 hp.\n" +
                "eee(ELF)[240/267]:            does damage 1 hp.\n" +
                "Enemy(GHOST)[47/135]:         does damage 1 hp.\n" +
                "eee(ELF)[239/267]:            does damage 10 hp.\n" +
                "Enemy(GHOST)[37/135]:         does damage 1 hp.\n" +
                "eee(ELF)[238/267]:            does damage 1 hp.\n" +
                "Enemy(GHOST)[36/135]:         does damage 1 hp.\n" +
                "eee(ELF)[237/267]:            does damage 3 hp.\n" +
                "Enemy(GHOST)[33/135]:         does damage 1 hp.\n" +
                "eee(ELF)[236/267]:            does damage 11 hp.\n" +
                "Enemy(GHOST)[22/135]:         does damage 1 hp.\n" +
                "eee(ELF)[235/267]:            does damage 1 hp.\n" +
                "Enemy(GHOST)[21/135]:         does damage 1 hp.\n" +
                "eee(ELF)[234/267]:            does damage 1 hp.\n" +
                "Enemy(GHOST)[20/135]:         does damage 1 hp.\n" +
                "eee(ELF)[233/267]:            does damage 10 hp.\n" +
                "Enemy(GHOST)[10/135]:         does damage 1 hp.\n" +
                "eee(ELF)[232/267]:            does damage 1 hp.\n" +
                "Enemy(GHOST)[9/135]:          does damage 1 hp.\n" +
                "eee(ELF)[231/267]:            does damage 1 hp.\n" +
                "Enemy(GHOST)[8/135]:          does damage 1 hp.\n" +
                "eee(ELF)[230/267]:            does damage 19 hp.\n";

        logTextArea.setText(text);
//        logScrollPane.getVerticalScrollBar().setValue(logScrollPane.getVerticalScrollBar().getMaximum());
        logScrollPane.getVerticalScrollBar().setValue(logScrollPane.getVerticalScrollBar().getValue());

        mapPanel = new JPanel() {
            Image img;

            @Override
            public void paintComponent(Graphics g) {
                if (img == null) {
                    try {
                        img = ImageIO.read(new File("src/main/resources/img/map/grass.jpg"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                Graphics2D g2 = (Graphics2D) g;
                g2.drawImage(img, 0, 0, this);
            }
        };
        mapPanel.setLayout(new GridLayout(VIEW_SIZE, VIEW_SIZE, 0, 0));
        mapPanel.setBorder(null);
//        mapPanel.setPreferredSize(new Dimension(VIEW_SIZE * ICON_SIZE, VIEW_SIZE * ICON_SIZE));


        //		create array of labels that will contain game icons
        mapCells = new JLabel[VIEW_SIZE][VIEW_SIZE];
        for (int y = 0; y < VIEW_SIZE; y++) {
            for (int x = 0; x < VIEW_SIZE; x++) {
                mapCells[y][x] = new JLabel();
                mapCells[y][x].setHorizontalAlignment(SwingConstants.CENTER);
                mapCells[y][x].setVerticalAlignment(SwingConstants.CENTER);
                mapCells[y][x].setPreferredSize(new Dimension(ICON_SIZE, ICON_SIZE));
//                mapCells[y][x].setBorder(null);
                mapPanel.add(mapCells[y][x]);
            }
        }

        // Создание горизонтальной группы
        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(CENTER)
                        .addComponent(enemyIconLabel)
                        .addComponent(enemyStatPane)
                        .addGroup(CENTER, layout.createSequentialGroup()
                            .addComponent(fleeButton)
                            .addComponent(fightButton))
                        .addComponent(switchButton, CENTER))
                .addGroup(layout.createParallelGroup(LEADING)
                        .addComponent(mapPanel)
                        .addComponent(logScrollPane))
                .addGroup(layout.createParallelGroup(CENTER)
                        .addComponent(heroIconLabel)
                        .addComponent(heroStatPane)
                        .addComponent(upButton)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(leftButton)
                            .addComponent(rightButton))
                        .addComponent(downButton))
        );

        // Создание вертикальной группы
        layout.setVerticalGroup(layout.createParallelGroup(LEADING)
                .addGroup(layout.createSequentialGroup()
                        .addComponent(enemyIconLabel)
                        .addComponent(enemyStatPane)
                        .addGroup(layout.createParallelGroup(LEADING)
                            .addComponent(fleeButton)
                            .addComponent(fightButton))
                        .addComponent(switchButton))
                .addGroup(layout.createSequentialGroup()
                        .addComponent(mapPanel)
                        .addComponent(logScrollPane))
                .addGroup(layout.createSequentialGroup()
                        .addComponent(heroIconLabel)
                        .addComponent(heroStatPane)
                        .addComponent(upButton)
                        .addGroup(layout.createParallelGroup(LEADING)
                                .addComponent(leftButton)
                                .addComponent(rightButton))
                        .addComponent(downButton))
        );

        updateMap();

        mainFrame.pack();
        mainFrame.setResizable(false);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }


    public void updateMap() {

//		update references if the map has changed
//        hero = PersonFactory.newPerson(PersonRace.DWARF, "ddd");
//        int level = 3;
//        int size = 5 * level + 4;
//        hero.setPosition(new Coordinate(size / 2, size / 2));
//        Area area = new Area(level, hero);
        Object[][] map = area.getArea();

//		draw only part of the map around the hero
        int y = hero.getPosition().getY() - VIEW_DISTANCE;
        for (int i = 0; i < VIEW_SIZE; i++, y++) {
            int x = hero.getPosition().getX() - VIEW_DISTANCE;
            for (int j = 0; j < VIEW_SIZE; j++, x++) {

                if (x < 0 || y < 0 || x >= area.getSize() || y >= area.getSize()) {
                    ImageIcon imageIcon = new ImageIcon(new ImageIcon("src/main/resources/img/map/space.png").getImage().getScaledInstance(ICON_SIZE, ICON_SIZE, Image.SCALE_DEFAULT));
                    mapCells[i][j].setIcon(imageIcon);
                    mapCells[i][j].setBorder(blackBorder);
//					if explored set corresponding icon
                }
                else if (hero.getPosition().getX() == x && hero.getPosition().getY() == y) {
                    ImageIcon imageIcon = new ImageIcon(new ImageIcon("src/main/resources/img/personIcons/" + hero.getRace().toLowerCase() + ".png").getImage().getScaledInstance(ICON_SIZE, ICON_SIZE, Image.SCALE_DEFAULT));
                    mapCells[i][j].setOpaque(true);
                    mapCells[i][j].setIcon(imageIcon);
                    mapCells[i][j].setBackground(Color.BLUE);
                    mapCells[i][j].setBorder(blueBorder);
                }
                else if (map[y][x] != null) {
                    if (map[y][x] instanceof Block) {
                        ImageIcon imageIcon = new ImageIcon(new ImageIcon("src/main/resources/img/blocks/guildhall.png").getImage().getScaledInstance(ICON_SIZE, ICON_SIZE, Image.SCALE_DEFAULT));
                        mapCells[i][j].setIcon(imageIcon);
//                        mapCells[i][j].setIcon(new ImageIcon("src/main/resources/img/obstacles/mountains.png"));
                        mapCells[i][j].setBorder(grayBorder);
                    }
                    else if (map[y][x] instanceof Aid) {
                        ImageIcon imageIcon = new ImageIcon(new ImageIcon("src/main/resources/img/aids/aid.png").getImage().getScaledInstance(ICON_SIZE, ICON_SIZE, Image.SCALE_DEFAULT));
                        mapCells[i][j].setIcon(imageIcon);
//                        mapCells[i][j].setIcon(new ImageIcon("src/main/resources/img/personLabels/" + hero.getType().toLowerCase() + ".gif"));
                        mapCells[i][j].setBorder(greenBorder);
                    }
                    else if (map[y][x] instanceof Person) {
                        Person enemy = (Person) map[y][x];
                        ImageIcon imageIcon = new ImageIcon(new ImageIcon("src/main/resources/img/personIcons/" + enemy.getRace().toLowerCase() + ".png").getImage().getScaledInstance(ICON_SIZE, ICON_SIZE, Image.SCALE_DEFAULT));
                        mapCells[i][j].setIcon(imageIcon);
//                        mapCells[i][j].setIcon(new ImageIcon("src/main/resources/img/personLabels/" + enemy.getType().toLowerCase() + ".gif"));
                        mapCells[i][j].setBorder(redBorder);
                    }
                }
                else {
                    mapCells[i][j].setIcon(null);
                    mapCells[i][j].setBorder(whiteBorder);
                }
////				if not explored, set fog
//                } else {
//                    ImageIcon imageIcon = new ImageIcon(new ImageIcon("src/main/resources/img/personLabels/fog.gif").getImage().getScaledInstance(ICON_SIZE, ICON_SIZE, Image.SCALE_DEFAULT));
//                    mapCells[i][j].setIcon(imageIcon);
////                    mapCells[i][j].setIcon(new ImageIcon("src/main/resources/img/obstacles/fog.png"));
//                    mapCells[i][j].setBorder(null);
//                }
            }
        }
    }

    public boolean isEmpty(String s) {
        return s == null || s.trim().isEmpty();
    }

    private class FightOrFleeClickListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();

            switch (command) {
                case "fight":
                    System.out.println("Fight Button clicked.");
                    break;
                case "flee":
                    System.out.println("Flee Button clicked.");
                    break;
                case "switch":
                    System.out.println("Switch Button clicked.");
                    break;
            }
        }
    }

    private class MoveClickListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();

            if( command.equals( "up" )) {
                System.out.println("up Button clicked.");
                hero.getPosition().setY(hero.getPosition().getY() - 1);
            } else if( command.equals( "down" ) )  {
                System.out.println("down Button clicked.");
                hero.getPosition().setY(hero.getPosition().getY() + 1);
            } else if( command.equals( "left" ) )  {
                System.out.println("left Button clicked.");
                hero.getPosition().setX(hero.getPosition().getX() - 1);
            } else if( command.equals( "right" ) )  {
                System.out.println("right Button clicked.");
                hero.getPosition().setX(hero.getPosition().getX() + 1);
            }
            updateMap();
        }
    }

    public JFrame getMainFrame() {
        return mainFrame;
    }

    public JLabel getEnemyIconLabel() {
        return enemyIconLabel;
    }

    public JTextPane getEnemyStatPane() {
        return enemyStatPane;
    }

    public JLabel getHeroIconLabel() {
        return heroIconLabel;
    }

    public JTextPane getHeroStatPane() {
        return heroStatPane;
    }

    public JButton getFleeButton() {
        return fleeButton;
    }

    public JButton getFightButton() {
        return fightButton;
    }

    public JButton getSwitchButton() {
        return switchButton;
    }

    public JButton getUpButton() {
        return upButton;
    }

    public JButton getDownButton() {
        return downButton;
    }

    public JButton getLeftButton() {
        return leftButton;
    }

    public JButton getRightButton() {
        return rightButton;
    }

    public JTextArea getLogTextArea() {
        return logTextArea;
    }

    public String getAction() {
        return action;
    }
}

