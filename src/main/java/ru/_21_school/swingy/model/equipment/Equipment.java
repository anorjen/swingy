package ru._21_school.swingy.model.equipment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public class Equipment {
    @NotBlank
    private String name;
    @NotNull
    private String type;
    @PositiveOrZero
    private double bonusAttack;
    @PositiveOrZero
    private double bonusDefense;
    @PositiveOrZero
    private double bonusHitPoints;

    Equipment() {
    }

    Equipment(String name, String type, double bonusAttack, double bonusDefense, double bonusHitPoints) {
        this.name = name;
        this.type = type;
        this.bonusAttack = bonusAttack;
        this.bonusDefense = bonusDefense;
        this.bonusHitPoints = bonusHitPoints;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getBonusAttack() {
        return bonusAttack;
    }

    public void setBonusAttack(double bonusAttack) {
        this.bonusAttack = bonusAttack;
    }

    public double getBonusDefense() {
        return bonusDefense;
    }

    public void setBonusDefense(double bonusDefense) {
        this.bonusDefense = bonusDefense;
    }

    public double getBonusHitPoints() {
        return bonusHitPoints;
    }

    public void setBonusHitPoints(double bonusHitPoints) {
        this.bonusHitPoints = bonusHitPoints;
    }

    @Override
    public String toString() {
        return "Equipment{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", bonusAttack=" + bonusAttack +
                ", bonusDefense=" + bonusDefense +
                ", bonusHitPoints=" + bonusHitPoints +
                '}';
    }

    public String toStat() {
        return name.toUpperCase();
    }
}
