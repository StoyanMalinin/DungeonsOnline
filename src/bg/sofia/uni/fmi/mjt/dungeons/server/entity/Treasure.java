package bg.sofia.uni.fmi.mjt.dungeons.server.entity;

import bg.sofia.uni.fmi.mjt.dungeons.common.item.Item;
import bg.sofia.uni.fmi.mjt.dungeons.server.interaction.EmptyInteractionChoice;
import bg.sofia.uni.fmi.mjt.dungeons.server.interaction.InteractionChoice;
import bg.sofia.uni.fmi.mjt.dungeons.server.interaction.InteractionNegotiator;
import bg.sofia.uni.fmi.mjt.dungeons.server.map.Position;
import bg.sofia.uni.fmi.mjt.dungeons.server.observer.TreasureTakenObserver;

public class Treasure implements GridEntity, ItemGivingEntity {

    private int id;
    private Item item;
    private Position position;
    private TreasureTakenObserver treasureTakenObserver;


    public Treasure(int id, Item item, Position position) {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null");
        }
        if (position == null) {
            throw new IllegalArgumentException("Position cannot be null");
        }

        this.id = id;
        this.item = item;
        this.position = position;
        this.treasureTakenObserver = new TreasureTakenObserver();
    }

    @Override
    public String getName() {
        return "Treasure(" + position.row() + ", " + position.col() + ")";
    }

    @Override
    public InteractionChoice getInteractionChoice(GameEntity other) {
        return new EmptyInteractionChoice();
    }

    @Override
    public void negotiateForInteractions(InteractionNegotiator negotiator) {
        if (negotiator.getReceiverAcceptItemInteraction() != null) {
            negotiator.getReceiverAcceptItemInteraction().setSubject(this);
        }
    }

    @Override
    public Position getPosition() {
        return position;
    }

    @Override
    public boolean canEnter() {
        return true;
    }

    @Override
    public char consoleSymbol() {
        return 'T';
    }

    @Override
    public boolean isFree() {
        return false;
    }

    @Override
    public Item getItemToGive() {
        return item;
    }

    @Override
    public boolean canGiveItem(ItemReceivingEntity receiver) {
        return receiver != null;
    }

    @Override
    public void giveItem(ItemReceivingEntity receiver) {
        if (canGiveItem(receiver) == true) {
            receiver.receiveItem(item, this);
            treasureTakenObserver.notifyListeners(this);
        }
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj instanceof Treasure other) {
            return (id == other.id);
        }

        return false;
    }

    public void setPosition(Position position) {
        if (position == null) {
            throw new IllegalArgumentException("Position cannot be null");
        }

        this.position = position;
    }

    public TreasureTakenObserver getTreasureTakenObserver() {
        return treasureTakenObserver;
    }
}
