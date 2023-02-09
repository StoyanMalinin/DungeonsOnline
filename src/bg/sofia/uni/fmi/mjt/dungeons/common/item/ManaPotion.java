package bg.sofia.uni.fmi.mjt.dungeons.common.item;

import bg.sofia.uni.fmi.mjt.dungeons.common.Stats;
import bg.sofia.uni.fmi.mjt.dungeons.server.entity.ItemConsumingEntity;
import bg.sofia.uni.fmi.mjt.dungeons.server.entity.WeaponCarryingEntity;

public class ManaPotion implements Spell {

    private int manaCast;
    private int points;
    private String name;
    private int level;

    public ManaPotion(int level, int manaCast, int points) {
        if (level <= 0) {
            throw new IllegalArgumentException("Level must be positive");
        }
        if (manaCast < 0) {
            throw new IllegalArgumentException("Manacast cannot be negative");
        }

        this.level = level;
        this.points = points;
        this.manaCast = manaCast;
        this.name = "ManaPotion potion: " + points + "mana, level: " + level + ", manaCast: " + manaCast;
    }

    public ManaPotion(int level, int manaCast, int points, String name) {
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
        return s.changedMana(s.getMana() + points);
    }

    @Override
    public boolean canBeSetAsAWeapon(WeaponCarryingEntity entity) {
        return false;
    }

    @Override
    public void setAsAWeapon(WeaponCarryingEntity entity) {

    }

    @Override
    public boolean canBeConsumed(ItemConsumingEntity entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Entity cannot be null");
        }
        return entity.getStats().getMana() >= manaCast && entity.getLevel() >= level;
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

        if (obj instanceof ManaPotion other) {
            final double eps = 0.001;
            return (Math.abs(manaCast - other.manaCast) < eps && Math.abs(points - other.points) < eps
                    && name.equals(other.getName()) == true);
        }

        return false;
    }
}
