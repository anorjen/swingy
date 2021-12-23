package ru._21_school.swingy.view.swingUI;

import ru._21_school.swingy.model.Area;
import ru._21_school.swingy.model.equipment.Aid;
import ru._21_school.swingy.model.equipment.Block;
import ru._21_school.swingy.model.person.Person;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.io.File;
import java.io.IOException;

import static javax.swing.GroupLayout.Alignment.*;

public class GameArea {

    private JFrame mainFrame;

    private JLabel enemyIconLabel;
    private JLabel enemyNameLabel;
    private JTextPane enemyStatPane;
    private JProgressBar enemyHpBar;
    private JProgressBar enemyExpBar;

    private JLabel heroIconLabel;
    private JLabel heroNameLabel;
    private JTextPane heroStatPane;
    private JScrollPane logScrollPane;
    private JProgressBar heroHpBar;
    private JProgressBar heroExpBar;

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


    public GameArea(Area area, Person hero) {
        prepareWindow();
        updateMap(area, hero);
    }

    private void prepareWindow() {
        mainFrame = new JFrame("Game Area");
        int frameWidth = (VIEW_SIZE * ICON_SIZE) + (SIDE_PANE_WIDTH * 2);
        int frameHeight = VIEW_SIZE * ICON_SIZE + INFO_PANEL_HEIGHT;
        mainFrame.setSize(new Dimension(frameWidth, frameHeight));

        GroupLayout layout = new GroupLayout(mainFrame.getContentPane());
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        mainFrame.setLayout(layout);

        //Компоненты
        //Enemy
        enemyIconLabel = new JLabel();
        enemyIconLabel.setPreferredSize(new Dimension(SIDE_PANE_WIDTH, SIDE_PANE_WIDTH));
        enemyIconLabel.setBackground(Color.BLACK);
        enemyNameLabel = new JLabel();
        enemyNameLabel.setVisible(false);
        enemyStatPane = new JTextPane();
        enemyStatPane.setEditable(false);
        enemyHpBar = new JProgressBar();
        enemyHpBar.setStringPainted(true);
        enemyHpBar.setVisible(false);
        enemyExpBar = new JProgressBar();
        enemyExpBar.setStringPainted(true);
        enemyExpBar.setVisible(false);

        //Hero
        heroIconLabel = new JLabel();
        heroIconLabel.setPreferredSize(new Dimension(SIDE_PANE_WIDTH, SIDE_PANE_WIDTH));
        heroIconLabel.setBackground(Color.BLACK);
        heroNameLabel = new JLabel();
        heroStatPane = new JTextPane();
        heroStatPane.setEditable(false);
        heroHpBar = new JProgressBar();
        heroHpBar.setStringPainted(true);
        heroHpBar.setVisible(true);
        heroExpBar = new JProgressBar();
        heroExpBar.setStringPainted(true);
        heroExpBar.setVisible(true);

        //buttons
        fleeButton = new JButton("Flee");
        fleeButton.setActionCommand("flee");
        fleeButton.setEnabled(false);

        fightButton = new JButton("Fight");
        fightButton.setActionCommand("fight");
        fightButton.setEnabled(false);

        switchButton = new JButton("Switch mode");
        switchButton.setActionCommand("switch");

        upButton = new JButton("Up");
        upButton.setActionCommand("up");

        downButton = new JButton("Down");
        downButton.setActionCommand("down");

        leftButton = new JButton("Left");
        leftButton.setActionCommand("left");

        rightButton = new JButton("Right");
        rightButton.setActionCommand("right");

        layout.linkSize(upButton, downButton, leftButton, rightButton);


        logTextArea = new JTextArea(10, 20);
        logTextArea.setEditable(false);
        logTextArea.setAutoscrolls(true);
        Font defaultListFont = logTextArea.getFont();
        logTextArea.setFont(new Font("monospaced", defaultListFont.getStyle(), defaultListFont.getSize()));

        logScrollPane = new JScrollPane(logTextArea);
        logScrollPane.setPreferredSize(new Dimension(VIEW_SIZE * ICON_SIZE, VIEW_SIZE * ICON_SIZE / 2));
        logScrollPane.setAutoscrolls(true);
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

        //		create array of labels that will contain game icons
        mapCells = new JLabel[VIEW_SIZE][VIEW_SIZE];
        for (int y = 0; y < VIEW_SIZE; y++) {
            for (int x = 0; x < VIEW_SIZE; x++) {
                mapCells[x][y] = new JLabel();
                mapCells[x][y].setHorizontalAlignment(SwingConstants.CENTER);
                mapCells[x][y].setVerticalAlignment(SwingConstants.CENTER);
                mapCells[x][y].setPreferredSize(new Dimension(ICON_SIZE, ICON_SIZE));
                mapPanel.add(mapCells[x][y]);
            }
        }

        // Создание горизонтальной группы
        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(CENTER)
                        .addComponent(enemyIconLabel)
                        .addComponent(enemyNameLabel)
                        .addComponent(enemyHpBar)
                        .addComponent(enemyExpBar)
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
                        .addComponent(heroNameLabel)
                        .addComponent(heroHpBar)
                        .addComponent(heroExpBar)
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
                        .addComponent(enemyNameLabel)
                        .addComponent(enemyHpBar)
                        .addComponent(enemyExpBar)
                        .addComponent(enemyStatPane)
                        .addGroup(layout.createParallelGroup(TRAILING)
                            .addComponent(fleeButton)
                            .addComponent(fightButton))
                        .addComponent(switchButton))
                .addGroup(layout.createSequentialGroup()
                        .addComponent(mapPanel)
                        .addComponent(logScrollPane))
                .addGroup(layout.createSequentialGroup()
                        .addComponent(heroIconLabel)
                        .addComponent(heroNameLabel)
                        .addComponent(heroHpBar)
                        .addComponent(heroExpBar)
                        .addComponent(heroStatPane)
                        .addComponent(upButton)
                        .addGroup(layout.createParallelGroup(LEADING)
                                .addComponent(leftButton)
                                .addComponent(rightButton))
                        .addComponent(downButton))
        );

        mainFrame.pack();
        mainFrame.setResizable(false);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }


    public void updateMap(Area area, Person hero) {

        Object[][] map = area.getArea();

        int y = hero.getPosition().getY() - VIEW_DISTANCE;
        for (int j = 0; j < VIEW_SIZE; j++, y++) {
            int x = hero.getPosition().getX() - VIEW_DISTANCE;
            for (int i = 0; i < VIEW_SIZE; i++, x++) {

                if (x < 0 || y < 0 || x >= area.getSize() || y >= area.getSize()) {
                    ImageIcon imageIcon = GameImages.getInstance().getSpace();
                    mapCells[i][j].setIcon(imageIcon);
                    mapCells[i][j].setBorder(blackBorder);
                }
                else if (hero.getPosition().getX() == x && hero.getPosition().getY() == y) {
                    ImageIcon imageIcon = hero.getIcon();
                    mapCells[i][j].setOpaque(true);
                    mapCells[i][j].setIcon(imageIcon);
                    mapCells[i][j].setBackground(Color.BLUE);
                    mapCells[i][j].setBorder(blueBorder);
                }
                else if (map[x][y] != null) {
                    if (map[x][y] instanceof Block) {
                        ImageIcon imageIcon = ((Block) map[x][y]).getIcon();
                        mapCells[i][j].setIcon(imageIcon);
                        mapCells[i][j].setBorder(grayBorder);
                    }
                    else if (map[x][y] instanceof Aid) {
                        ImageIcon imageIcon = ((Aid) map[x][y]).getIcon();
                        mapCells[i][j].setIcon(imageIcon);
                        mapCells[i][j].setBorder(greenBorder);
                    }
                    else if (map[x][y] instanceof Person) {
                        Person enemy = (Person) map[x][y];
                        ImageIcon imageIcon = enemy.getIcon();
                        mapCells[i][j].setIcon(imageIcon);
                        mapCells[i][j].setBorder(redBorder);
                    }
                }
                else {
                    mapCells[i][j].setIcon(null);
                    mapCells[i][j].setBorder(whiteBorder);
                }
            }
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

    public JProgressBar getEnemyHpBar() {
        return enemyHpBar;
    }

    public JProgressBar getEnemyExpBar() {
        return enemyExpBar;
    }

    public JProgressBar getHeroHpBar() {
        return heroHpBar;
    }

    public JProgressBar getHeroExpBar() {
        return heroExpBar;
    }

    public JLabel getEnemyNameLabel() {
        return enemyNameLabel;
    }

    public JLabel getHeroNameLabel() {
        return heroNameLabel;
    }

    public JScrollPane getLogScrollPane() {
        return logScrollPane;
    }
}

