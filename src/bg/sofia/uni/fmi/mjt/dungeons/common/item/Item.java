package bg.sofia.uni.fmi.mjt.dungeons.common.item;

import bg.sofia.uni.fmi.mjt.dungeons.common.Stats;
import bg.sofia.uni.fmi.mjt.dungeons.server.entity.ItemConsumingEntity;
import bg.sofia.uni.fmi.mjt.dungeons.server.entity.WeaponCarryingEntity;

import java.io.Serializable;

public interface Item extends Serializable {
    int getLevel();
    String getName();
    Stats affectStats(Stats s);

    boolean canBeSetAsAWeapon(WeaponCarryingEntity entity);
    void setAsAWeapon(WeaponCarryingEntity entity);

    boolean canBeConsumed(ItemConsumingEntity entity);
}
