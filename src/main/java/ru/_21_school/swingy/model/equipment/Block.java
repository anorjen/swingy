package ru._21_school.swingy.model.equipment;

import javax.swing.*;

public class Block {
    private String name;
    private ImageIcon icon;

    Block(String name) {
        this.name = name;
    }

    public ImageIcon getIcon() {
        return icon;
    }

    public void setIcon(ImageIcon icon) {
        this.icon = icon;
    }
}
