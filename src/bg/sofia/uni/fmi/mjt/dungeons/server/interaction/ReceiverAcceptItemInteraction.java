package bg.sofia.uni.fmi.mjt.dungeons.server.interaction;

import bg.sofia.uni.fmi.mjt.dungeons.server.GameMaster;
import bg.sofia.uni.fmi.mjt.dungeons.server.entity.GameEntity;
import bg.sofia.uni.fmi.mjt.dungeons.server.entity.ItemGivingEntity;
import bg.sofia.uni.fmi.mjt.dungeons.server.entity.ItemReceivingEntity;
import bg.sofia.uni.fmi.mjt.dungeons.server.exception.ClientCommandException;

public class ReceiverAcceptItemInteraction implements InteractionWithOne {
    private ItemReceivingEntity receiver;
    private ItemGivingEntity giver;

    public ReceiverAcceptItemInteraction() {
        this.receiver = null;
        this.giver = null;
    }

    @Override
    public GameEntity getInitiator() {
        return receiver;
    }

    @Override
    public GameEntity getSubject() {
        return giver;
    }

    public void setInitiator(ItemReceivingEntity receiver) {
        this.receiver = receiver;
    }

    public void setSubject(ItemGivingEntity giver) {
        this.giver = giver;
    }

    @Override
    public boolean isComplete() {
        return receiver != null && giver != null;
    }

    @Override
    public void execute(GameMaster gameMaster) throws ClientCommandException {
        gameMaster.receiverAcceptItem(receiver, giver);
    }

    public boolean isValid() {
        return isComplete() == true
                && giver.canGiveItem(receiver) == true
                && receiver.canReceiveItem(giver.getItemToGive(), giver) == true;
    }

    @Override
    public String toString() {
        return "Take " + giver.getItemToGive().getName()
                + " from " + giver.getName();
    }
}
