package bg.sofia.uni.fmi.mjt.dungeons.server.interaction;

import bg.sofia.uni.fmi.mjt.dungeons.common.item.Item;
import bg.sofia.uni.fmi.mjt.dungeons.server.GameMaster;
import bg.sofia.uni.fmi.mjt.dungeons.server.entity.GameEntity;
import bg.sofia.uni.fmi.mjt.dungeons.server.entity.ItemGivingEntity;
import bg.sofia.uni.fmi.mjt.dungeons.server.entity.ItemReceivingEntity;
import bg.sofia.uni.fmi.mjt.dungeons.server.entity.player.Player;
import bg.sofia.uni.fmi.mjt.dungeons.server.exception.ClientCommandException;

public class PlayerOffersItemInteraction implements OfferItemInteraction {

    Player giver;
    ItemReceivingEntity receiver;
    Item item;

    public PlayerOffersItemInteraction(Player giver, Item item) {
        if (giver == null) {
            throw new IllegalArgumentException("Giver cannot be null");
        }
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null");
        }

        this.giver = giver;
        this.item = item;
    }

    @Override
    public GameEntity getInitiator() {
        return giver;
    }

    @Override
    public GameEntity getSubject() {
        return receiver;
    }

    @Override
    public boolean isComplete() {
        return giver != null && receiver != null;
    }

    @Override
    public void execute(GameMaster gameMaster) throws ClientCommandException {
        gameMaster.playerOffersItem(giver, item, receiver);
    }

    @Override
    public void setSubject(ItemReceivingEntity receiver) {
        this.receiver = receiver;
    }

    @Override
    public String toString() {
        return "Offer " + item.getName()
                + " to " + receiver.getName();
    }
}
