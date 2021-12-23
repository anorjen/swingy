package ru._21_school.swingy.model.person;

import jakarta.validation.constraints.*;
import ru._21_school.swingy.model.Coordinate;
import ru._21_school.swingy.model.equipment.Aid;
import ru._21_school.swingy.model.equipment.Equipment;


import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.sqrt;

public class Person {

    @PositiveOrZero
    private Integer id;
    @Size(min=1, max = 20, message = "Name must be 1-20 symbols")
    @NotBlank(message = "Name can't be empty")
    private String name;
    @NotNull
    private String race;
    @PositiveOrZero
    private int level;
    @PositiveOrZero
    private int experience;
    @PositiveOrZero
    private int attack;
    @PositiveOrZero
    private int defense;
    @PositiveOrZero
    private int hitPoints;
    @Positive
    private int fullHitPoints;
    @PositiveOrZero
    private int agility;
    private List<Equipment> equipments;
    private Coordinate position;
    private ImageIcon icon;
    private ImageIcon label;


    Person() {
    }

    Person(String name, String race, int hitPoints, int attack, int defense, int agility) {
        this.name = name;
        this.race = race;
        this.hitPoints = hitPoints;
        this.fullHitPoints = hitPoints;
        this.attack = attack;
        this.defense = defense;
        this.agility = agility;
        this.experience = 0;
        this.level = 0;
        this.equipments = new ArrayList<>();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    public void levelUp() {
        this.fullHitPoints = (int) (this.fullHitPoints * 1.25);
        this.hitPoints = this.fullHitPoints;
        this.attack = (int) (this.attack * 1.25) + 1;
        this.defense = (int) (this.defense * 1.25) + 1;
        this.agility = (int) (this.agility * 1.25) + 1;
        ++this.level;
    }

    public void levelDown() {
        this.fullHitPoints = (int) (this.fullHitPoints / 1.25);
        this.hitPoints = this.fullHitPoints;
        this.attack = (int) ((this.attack - 1) / 1.25);
        this.defense = (int) ((this.defense - 1) / 1.25);
        this.agility = (int) ((this.agility - 1) / 1.25);
        --this.level;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public int getExperience() {
        return experience;
    }

    public int getNextLevelExperience() {
        return (level + 1) * 1000 + (int)(Math.pow(level, 2)) * 450;
    }

    public void addExperience(int experience) {
        this.experience += experience;
        if (this.experience >= getNextLevelExperience()) {
            int level = (int) (sqrt(this.experience * 9 / 50 - 80) - 1) / 9;
            while (level - this.level > 0) {
                levelUp();
            }
        }
    }

    public void resumeExperience(int experience) {
        this.experience -= experience;
        if (this.experience < 0) {
            this.experience = 0;
        }
        int level = 0;
        if (this.experience > 1000) {
            level = (int) (sqrt(this.experience * 9 / 50 - 80) - 1) / 9;
        }
        while (this.level - level > 0) {
            levelDown();
        }
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public void setHitPoints(int hitPoints) {
        this.hitPoints = hitPoints;
    }

    public int getHitPoints() {
        return hitPoints;
    }

    public void damageHitPoints(int hitPoints) {
        if (hitPoints > this.hitPoints) {
            this.hitPoints = 0;
        }
        else {
            this.hitPoints -= hitPoints;
        }
    }

    public int getFullHitPoints() {
        return fullHitPoints;
    }

    public void setFullHitPoints(int fullHitPoints) {
        this.fullHitPoints = fullHitPoints;
    }

    public int getAgility() {
        return agility;
    }

    public void setAgility(int agility) {
        this.agility = agility;
    }

    public void setEquipments(List<Equipment> equipments) {
        this.equipments = equipments;
    }

    public List<Equipment> getEquipments() {
        return equipments;
    }

    private void useEquip(Equipment equip) {
        equipments.add(equip);
        setAttack((int) (this.attack * equip.getBonusAttack()));
        setDefense((int) (this.defense * equip.getBonusDefense()));
        setHitPoints((int) (this.hitPoints * equip.getBonusHitPoints()));
        setFullHitPoints((int) (this.fullHitPoints * equip.getBonusHitPoints()));
    }

    private void throwEquip(Equipment equip) {
        setAttack((int) (this.attack / equip.getBonusAttack()));
        setDefense((int) (this.defense / equip.getBonusDefense()));
        setHitPoints((int) (this.hitPoints / equip.getBonusHitPoints()));
        setFullHitPoints((int) (this.fullHitPoints / equip.getBonusHitPoints()));
        equipments.remove(equip);
    }

    public boolean setEquip(Equipment equip) {

        Equipment heroEquip = null;
        for (Equipment e : equipments) {
            if (e.getType().equals(equip.getType())) {
                heroEquip = e;
                break ;
            }
        }

        if (heroEquip == null) {
            useEquip(equip);
            return true;
        }
        if (heroEquip.getBonusAttack() <= equip.getBonusAttack()
            && heroEquip.getBonusDefense() <= equip.getBonusDefense()
            && heroEquip.getBonusHitPoints() <= equip.getBonusHitPoints()) {

            throwEquip(heroEquip);
            useEquip(equip);
            return true;
        }
        return false;
    }

    public Coordinate getPosition() {
        return position;
    }

    public void setPosition(Coordinate position) {
        this.position = position;
    }

    public int attack(Person enemy) {
        double dodgeChance = (double) enemy.getAgility() / (100 * (getAgility() + 1));
        if (isDead() || Math.random() < dodgeChance) {
            return 0;
        }
        int damage = (int) (Math.random() * getAttack() - enemy.getDefense()) + 1;
        if (damage <= 0) {
            damage = 1;
        }
        enemy.damageHitPoints(damage);
        return damage;
    }

    public boolean isDead() {
        return hitPoints <= 0;
    }

    public void applyAid(Aid aid) {
        this.hitPoints += aid.getBonusHitPoints();
        if (this.hitPoints > this.fullHitPoints) {
            this.hitPoints = this.fullHitPoints;
        }
        this.attack += aid.getBonusAttack();
        this.defense += aid.getBonusDefense();
        this.agility += aid.getBonusAgility();
        addExperience(aid.getBonusExperience());
    }

    public String toStat() {
        String heroStat = String.format("LEVEL: %4d\nATTACK: %4d\nDEFENCE: %4d\nAGILITY: %4d\n\nEquipment:\n",
                        level,
                        attack,
                        defense,
                        agility);

        StringBuilder equips = new StringBuilder();
        for (int i = 0; i < equipments.size(); i++) {
            equips.append(i + 1)
                    .append(". ")
                    .append(equipments.get(i).toStat())
                    .append("\n");
        }
        return heroStat + equips.toString();
    }

    @Override
    public String toString() {
        return String.format("%-20s | RACE: %-8s | LEVEL: %-3d", name, race, level);
    }

    public ImageIcon getIcon() {
        return icon;
    }

    public void setIcon(ImageIcon icon) {
        this.icon = icon;
    }

    public ImageIcon getLabel() {
        return label;
    }

    public void setLabel(ImageIcon label) {
        this.label = label;
    }
}
