package ru._21_school.swingy.view.swingUI;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameImages {

    private static GameImages instance;

    private Map<String, ImageIcon> personLabels = new HashMap<>();
    private Map<String, ImageIcon> personIcons = new HashMap<>();
    private List<ImageIcon> aidIcons = new ArrayList<>();
    private List<ImageIcon> blockIcons = new ArrayList<>();
    private ImageIcon space = new ImageIcon(new ImageIcon("src/main/resources/img/map/space.png").getImage().getScaledInstance(64, 64, Image.SCALE_DEFAULT));

    private GameImages() {
        personLabels.put("dwarf", new ImageIcon(new ImageIcon("src/main/resources/img/personLabels/dwarf.gif").getImage().getScaledInstance(128, 128, Image.SCALE_DEFAULT)));
        personLabels.put("elf", new ImageIcon(new ImageIcon("src/main/resources/img/personLabels/elf.gif").getImage().getScaledInstance(128, 128, Image.SCALE_DEFAULT)));
        personLabels.put("ghost", new ImageIcon(new ImageIcon("src/main/resources/img/personLabels/ghost.gif").getImage().getScaledInstance(128, 128, Image.SCALE_DEFAULT)));
        personLabels.put("human", new ImageIcon(new ImageIcon("src/main/resources/img/personLabels/human.gif").getImage().getScaledInstance(128, 128, Image.SCALE_DEFAULT)));
        personLabels.put("ork", new ImageIcon(new ImageIcon("src/main/resources/img/personLabels/ork.gif").getImage().getScaledInstance(128, 128, Image.SCALE_DEFAULT)));

        personIcons.put("dwarf", new ImageIcon(new ImageIcon("src/main/resources/img/personIcons/dwarf.png").getImage().getScaledInstance(64, 64, Image.SCALE_DEFAULT)));
        personIcons.put("elf", new ImageIcon(new ImageIcon("src/main/resources/img/personIcons/elf.png").getImage().getScaledInstance(64, 64, Image.SCALE_DEFAULT)));
        personIcons.put("ghost", new ImageIcon(new ImageIcon("src/main/resources/img/personIcons/ghost.png").getImage().getScaledInstance(64, 64, Image.SCALE_DEFAULT)));
        personIcons.put("human", new ImageIcon(new ImageIcon("src/main/resources/img/personIcons/human.png").getImage().getScaledInstance(64, 64, Image.SCALE_DEFAULT)));
        personIcons.put("ork", new ImageIcon(new ImageIcon("src/main/resources/img/personIcons/ork.png").getImage().getScaledInstance(64, 64, Image.SCALE_DEFAULT)));

        aidIcons.add(new ImageIcon(new ImageIcon("src/main/resources/img/aids/aid.png").getImage().getScaledInstance(64, 64, Image.SCALE_DEFAULT)));
        aidIcons.add(new ImageIcon(new ImageIcon("src/main/resources/img/aids/aid1.png").getImage().getScaledInstance(64, 64, Image.SCALE_DEFAULT)));
        aidIcons.add(new ImageIcon(new ImageIcon("src/main/resources/img/aids/aid3.png").getImage().getScaledInstance(64, 64, Image.SCALE_DEFAULT)));

        blockIcons.add(new ImageIcon(new ImageIcon("src/main/resources/img/blocks/forge.png").getImage().getScaledInstance(64, 64, Image.SCALE_DEFAULT)));
        blockIcons.add(new ImageIcon(new ImageIcon("src/main/resources/img/blocks/guildhall.png").getImage().getScaledInstance(64, 64, Image.SCALE_DEFAULT)));
        blockIcons.add(new ImageIcon(new ImageIcon("src/main/resources/img/blocks/mountains.png").getImage().getScaledInstance(64, 64, Image.SCALE_DEFAULT)));
        blockIcons.add(new ImageIcon(new ImageIcon("src/main/resources/img/blocks/playerhouse.png").getImage().getScaledInstance(64, 64, Image.SCALE_DEFAULT)));
        blockIcons.add(new ImageIcon(new ImageIcon("src/main/resources/img/blocks/stonemasonry.png").getImage().getScaledInstance(64, 64, Image.SCALE_DEFAULT)));
        blockIcons.add(new ImageIcon(new ImageIcon("src/main/resources/img/blocks/wall.png").getImage().getScaledInstance(64, 64, Image.SCALE_DEFAULT)));
        blockIcons.add(new ImageIcon(new ImageIcon("src/main/resources/img/blocks/water.png").getImage().getScaledInstance(64, 64, Image.SCALE_DEFAULT)));
    }

    public static GameImages getInstance() {
        if (instance == null) {
            instance = new GameImages();
        }
        return instance;
    }

    public ImageIcon getPersonLabel(String type) {
        return personLabels.get(type.toLowerCase());
    }

    public ImageIcon getPersonIcon(String type) {
        return personIcons.get(type.toLowerCase());
    }

    public ImageIcon getAid() {
        return aidIcons.get((int) (Math.random() * aidIcons.size()));
    }

    public ImageIcon getBlock() {
        return blockIcons.get((int) (Math.random() * blockIcons.size()));
    }

    public ImageIcon getSpace() {
        return space;
    }
}
