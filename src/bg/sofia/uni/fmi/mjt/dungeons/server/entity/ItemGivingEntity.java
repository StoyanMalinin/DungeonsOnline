package bg.sofia.uni.fmi.mjt.dungeons.server.entity;

import bg.sofia.uni.fmi.mjt.dungeons.common.item.Item;

public interface ItemGivingEntity extends GridEntity {
    Item getItemToGive();
    boolean canGiveItem(ItemReceivingEntity receiver);
    void giveItem(ItemReceivingEntity receiver);
}
