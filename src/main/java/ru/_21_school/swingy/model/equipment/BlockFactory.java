package ru._21_school.swingy.model.equipment;

import static java.lang.Math.random;

public class BlockFactory {

    public static Block newBlock() {

        String[] name = new String[] {"wall", "barrier", "fence", "clump", "water"};

        return new Block(name[(int)(random() * name.length)]);
    }
}
