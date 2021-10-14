package ru._21_school.swingy.model.equipment;

import static java.lang.Math.random;

public class AidFactory {
    public static Aid newAid() {

        String[] prefixs = new String[] {"aid", "help", "leg-up", "assistance", "support"};

        return new Aid(prefixs[(int) (random() * prefixs.length)], (int)(random() * 20), (int)(random() * 5), (int)(random() * 5), (int)(random() * 5), (int) (random() * 1000));
    }
}
