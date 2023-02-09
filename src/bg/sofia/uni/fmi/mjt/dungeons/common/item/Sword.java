package bg.sofia.uni.fmi.mjt.dungeons.common.item;

import bg.sofia.uni.fmi.mjt.dungeons.common.Stats;
import bg.sofia.uni.fmi.mjt.dungeons.server.entity.ItemConsumingEntity;
import bg.sofia.uni.fmi.mjt.dungeons.server.entity.WeaponCarryingEntity;

public class Sword implements WeaponItem {

    private int level;
    private int points;
    private String name;

    public Sword(int level, int points) {
        if (level <= 0) {
            throw new IllegalArgumentException("Level must be positive");
        }

        this.level = level;
        this.points = points;
        this.name = "Sword: " + points + "attack, level: " + level;
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
        if (s == null) {
            throw new IllegalArgumentException("Stats cannot be null");
        }
        return s.changedAttack(s.getAttack() + points);
    }

    @Override
    public boolean canBeSetAsAWeapon(WeaponCarryingEntity entity) {
        return entity.getLevel() >= level;
    }

    @Override
    public void setAsAWeapon(WeaponCarryingEntity entity) {
        entity.setWeapon(this);
    }

    @Override
    public boolean canBeConsumed(ItemConsumingEntity entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Entity cannot be null");
        }
        return false;
    }

    @Override
    public double getAttack() {
        return points;
    }
}
