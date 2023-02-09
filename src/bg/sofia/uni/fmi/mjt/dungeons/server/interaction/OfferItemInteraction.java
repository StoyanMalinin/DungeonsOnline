package bg.sofia.uni.fmi.mjt.dungeons.server.interaction;

import bg.sofia.uni.fmi.mjt.dungeons.server.entity.ItemReceivingEntity;

public interface OfferItemInteraction extends InteractionWithOne {
    void setSubject(ItemReceivingEntity receiver);
}
