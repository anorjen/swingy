package ru._21_school.swingy.model.equipment;

public class Aid {

    private String name;
    private int bonusAttack;
    private int bonusDefense;
    private int bonusHitPoints;
    private int bonusAgility;
    private int bonusExperience;

    Aid(String name, int bonusHitPoints, int bonusAttack, int bonusDefense, int bonusAgility, int experience) {
        this.name = name;
        this.bonusAttack = bonusAttack;
        this.bonusDefense = bonusDefense;
        this.bonusHitPoints = bonusHitPoints;
        this.bonusAgility = bonusAgility;
        this.bonusExperience = experience;
    }

    public String getName() {
        return name;
    }

    public int getBonusAttack() {
        return bonusAttack;
    }

    public int getBonusDefense() {
        return bonusDefense;
    }

    public int getBonusHitPoints() {
        return bonusHitPoints;
    }

    public int getBonusAgility() {
        return bonusAgility;
    }

    public int getBonusExperience() {
        return bonusExperience;
    }
}
