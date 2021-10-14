package ru._21_school.swingy.model.person;

public enum PersonRace {
    HUMAN(100, 10, 10, 5),
    ELF(90, 17, 8, 20),
    DWARF(150, 15, 15, 2),
    ORK(120, 12, 7, 5),
    GHOST(70, 8, 20, 120);

    private Integer hitPoints;
    private Integer attack;
    private Integer defense;
    private Integer agility;

    private PersonRace(Integer hitPoints, Integer attack, Integer defense, Integer agility) {
        this.hitPoints = hitPoints;
        this.attack = attack;
        this.defense = defense;
        this.agility = agility;
    }

    public Integer getHitPoints() {
        return hitPoints;
    }

    public Integer getAttack() {
        return attack;
    }

    public Integer getDefense() {
        return defense;
    }

    public Integer getAgility() {
        return agility;
    }
}
