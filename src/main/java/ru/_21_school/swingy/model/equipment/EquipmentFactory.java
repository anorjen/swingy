package ru._21_school.swingy.model.equipment;


import ru._21_school.swingy.exception.SwingyException;

import static java.lang.Math.random;

public class EquipmentFactory {
    public static Equipment newEquip() {

        String[] prefixs = new String[] {"shiny", "legendary", "scary", "indestructible", "ancient", "dragon", "light", "elven", "orc"};
        EquipmentType[] types = EquipmentType.values();

        Equipment equip;

        EquipmentType type = types[(int) (random() * types.length)];
        switch (type) {
            case HELM:
                equip = new Equipment(prefixs[(int) (random() * prefixs.length)] + " " + type.name().toLowerCase(), type.name(), 1, 1,  random() / 4 + 1);
                break ;
            case ARMOR:
                equip = new Equipment(prefixs[(int) (random() * prefixs.length)] + " " + type.name().toLowerCase(), type.name(), 1, random() / 4 + 1, 1);
                break;
            case WEAPON:
                equip = new Equipment(prefixs[(int) (random() * prefixs.length)] + " " + type.name().toLowerCase(), type.name(), random() / 4 + 1, 1, 1);
                break ;
            default:
                throw new SwingyException("Wrong equip type!");
        }
        return equip;
    }
}
