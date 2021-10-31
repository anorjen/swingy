package ru._21_school.swingy.model.equipment;

import ru._21_school.swingy.view.swingUI.GameImages;

import static java.lang.Math.random;

public class AidFactory {
    public static Aid newAid() {

        String[] prefixs = new String[] {"aid", "help", "leg-up", "assistance", "support"};

        Aid aid = new Aid(prefixs[(int) (random() * prefixs.length)], (int)(random() * 20), (int)(random() * 2), (int)(random() * 2), (int)(random() * 2), (int) (random() * 1000));
        aid.setIcon(GameImages.getInstance().getAid());
        return aid;
    }
}
