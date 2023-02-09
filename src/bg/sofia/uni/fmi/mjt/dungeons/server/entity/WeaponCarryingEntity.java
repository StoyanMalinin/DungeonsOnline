package bg.sofia.uni.fmi.mjt.dungeons.server.entity;

import bg.sofia.uni.fmi.mjt.dungeons.common.item.WeaponItem;

public interface WeaponCarryingEntity {
    void setWeapon(WeaponItem weapon);
    WeaponItem getWeapon();
    int getLevel();
}
