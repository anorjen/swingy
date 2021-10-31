package ru._21_school.swingy.model.equipment;

import ru._21_school.swingy.view.swingUI.GameImages;

import static java.lang.Math.random;

public class BlockFactory {

    public static Block newBlock() {

        String[] name = new String[] {"wall", "barrier", "fence", "clump", "water"};

        Block block = new Block(name[(int)(random() * name.length)]);
        block.setIcon(GameImages.getInstance().getBlock());
        return block;
    }
}
