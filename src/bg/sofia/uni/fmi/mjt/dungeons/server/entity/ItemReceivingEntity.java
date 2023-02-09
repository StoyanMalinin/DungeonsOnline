package bg.sofia.uni.fmi.mjt.dungeons.server.entity;

import bg.sofia.uni.fmi.mjt.dungeons.common.item.Item;

public interface ItemReceivingEntity extends GridEntity {
    boolean canReceiveItem(Item item, ItemGivingEntity giver);
    void receiveItem(Item item, ItemGivingEntity giver);
}
