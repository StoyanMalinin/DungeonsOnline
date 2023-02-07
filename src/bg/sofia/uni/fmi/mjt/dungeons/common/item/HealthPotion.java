package bg.sofia.uni.fmi.mjt.dungeons.common.item;

import bg.sofia.uni.fmi.mjt.dungeons.common.Stats;

public class HealthPotion implements Spell {

    private double manaCast;
    private double points;
    private String name;
    private int level;

    public HealthPotion(int level, double manaCast, double points) {
        if (level <= 0) {
            throw new IllegalArgumentException("Level must be positive");
        }
        if (manaCast < 0) {
            throw new IllegalArgumentException("Manacast cannot be negative");
        }

        this.level = level;
        this.points = points;
        this.manaCast = manaCast;
        this.name = "Mana potion: " + manaCast + "hp";
    }

    public HealthPotion(int level, double manaCast, double points, String name) {
        this(level, manaCast, points);

        if (name == null) {
            throw new IllegalArgumentException("Name cannot be null");
        }

        this.name = name;
    }

    @Override
    public int getLevel() {
        return level;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Stats affectStats(Stats s) {
        return new Stats(s.health() + points, s.mana(), s.attack(), s.defense());
    }

    @Override
    public double getManaCast() {
        return manaCast;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj instanceof HealthPotion other) {
            final double eps = 0.001;
            return (Math.abs(manaCast - other.manaCast) < eps && Math.abs(points - other.points) < eps
                    && name.equals(other.getName()) == true);
        }

        return false;
    }
}
