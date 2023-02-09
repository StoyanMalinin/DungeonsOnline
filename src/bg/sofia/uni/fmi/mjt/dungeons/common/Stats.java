package bg.sofia.uni.fmi.mjt.dungeons.common;

import java.io.Serializable;

public class Stats implements Serializable {
    private double health;
    private double mana;
    private double attack;
    private double defense;

    public Stats(double health, double mana, double attack, double defense) {
        this.health = health;
        this.mana = mana;
        this.attack = attack;
        this.defense = defense;
    }

    public double getAttack() {
        return attack;
    }

    public double getDefense() {
        return defense;
    }

    public double getHealth() {
        return health;
    }

    public double getMana() {
        return mana;
    }

    public Stats changedHealth(double health) {
        return new Stats(health, mana, attack, defense);
    }

    public Stats changedAttack(double attack) {
        return new Stats(health, mana, attack, defense);
    }

    public Stats changedMana(double mana) {
        return new Stats(health, mana, attack, defense);
    }

    public Stats changedDefense(double defense) {
        return new Stats(health, mana, attack, defense);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append("Health: ");
        builder.append(health);
        builder.append(System.lineSeparator());

        builder.append("Mana: ");
        builder.append(mana);
        builder.append(System.lineSeparator());

        builder.append("Attack: ");
        builder.append(attack);
        builder.append(System.lineSeparator());

        builder.append("Defense: ");
        builder.append(defense);
        builder.append(System.lineSeparator());

        return builder.toString();
    }
}
