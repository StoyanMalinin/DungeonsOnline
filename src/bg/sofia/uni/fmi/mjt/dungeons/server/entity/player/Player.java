package bg.sofia.uni.fmi.mjt.dungeons.server.entity.player;

import bg.sofia.uni.fmi.mjt.dungeons.common.PlayerId;
import bg.sofia.uni.fmi.mjt.dungeons.common.Stats;
import bg.sofia.uni.fmi.mjt.dungeons.common.item.Item;
import bg.sofia.uni.fmi.mjt.dungeons.common.item.PlayerBackpack;
import bg.sofia.uni.fmi.mjt.dungeons.common.item.WeaponItem;
import bg.sofia.uni.fmi.mjt.dungeons.server.entity.*;
import bg.sofia.uni.fmi.mjt.dungeons.server.entity.controller.MoveController;
import bg.sofia.uni.fmi.mjt.dungeons.server.exception.NotAllowedToMoveException;
import bg.sofia.uni.fmi.mjt.dungeons.server.interaction.*;
import bg.sofia.uni.fmi.mjt.dungeons.server.map.Direction;
import bg.sofia.uni.fmi.mjt.dungeons.server.map.Position;
import bg.sofia.uni.fmi.mjt.dungeons.server.observer.EntityMovedObserver;
import bg.sofia.uni.fmi.mjt.dungeons.server.observer.PlayerDiedObserver;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Player implements GridEntity, MovableEntity,
        FightableEntity, WeaponCarryingEntity, ItemConsumingEntity,
        ItemGivingEntity, ItemReceivingEntity {

    private PlayerId id;
    private Position position;
    private MoveController moveController;
    private PlayerState state;
    private PlayerStateMonitor stateMonitor;
    private int level;
    private Stats baseStats;
    private PlayerBackpack backpack;
    private PlayerDiedObserver playerDiedObserver;
    private EntityMovedObserver entityMovedObserver;
    private WeaponItem weaponItem;
    private Item itemToGive;
    private ItemReceivingEntity choosenReceiver;
    private int xp;

    private static final double EPS = 0.0001;
    private static final int RESSURRECTION_HEALTH = 100;

    private static final int XP_KILL_BONUS = 10;
    private static final int XP_LEVEL_LIMIT = 20;
    private static final int LEVEL_HEALTH_BONUS = 10;
    private static final int LEVEL_MANA_BONUS = 10;
    private static final int LEVEL_DEFENSE_BONUS = 5;
    private static final int LEVEL_ATTACK_BONUS = 5;
    private static final int ITEM_RECEIVING_HP_BONUS = 10;

    public Player(PlayerId id, Position position, MoveController moveController, int level, Stats stats) {
        if (id == null) {
            throw new IllegalArgumentException("Id cannot be null");
        }
        if (position == null) {
            throw new IllegalArgumentException("Position cannot be null");
        }
        if (position == null) {
            throw new IllegalArgumentException("MoveController cannot be null");
        }
        if (stats == null) {
            throw new IllegalArgumentException("Stats cannot be null");
        }
        if (level <= 0) {
            throw new IllegalArgumentException("Level must be positive");
        }

        this.id = id;
        this.state = null;
        this.position = position;
        this.moveController = moveController;
        this.level = level;
        this.baseStats = stats;
        this.backpack = new PlayerBackpack();
        this.playerDiedObserver = new PlayerDiedObserver();
        this.entityMovedObserver = new EntityMovedObserver();
        this.weaponItem = null;
        this.itemToGive = null;
        this.choosenReceiver = null;
        this.xp = 0;

        this.stateMonitor = new PlayerStateMonitor();
    }

    public int getLevel() {
        return level;
    }

    public int getXP() {
        return xp;
    }

    public PlayerDiedObserver getPlayerDiedObserver() {
        return playerDiedObserver;
    }

    public EntityMovedObserver getEntityMovedObserver() {
        return entityMovedObserver;
    }

    public Stats getStats() {
        if (weaponItem != null) {
            return weaponItem.affectStats(baseStats);
        }

        return baseStats;
    }

    public PlayerId getId() {
        return id;
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
        return id.toChar();
    }

    @Override
    public boolean isFree() {
        return false;
    }

    @Override
    public boolean canMove(Direction direction) {
        if (direction == null) {
            throw new IllegalArgumentException("Direction cannot be null");
        }

        return moveController.canMove(position, direction) && moveController.isActive();
    }

    @Override
    public Position move(Direction direction) throws NotAllowedToMoveException {
        if (direction == null) {
            throw new IllegalArgumentException("Direction cannot be null");
        }
        if (isAllowedToMove() == false) {
            throw new NotAllowedToMoveException("Player is not allowed to move (this action is locked)");
        }
        if (canMove(direction) == false) {
            throw new NotAllowedToMoveException("Player is not allowed to move (the move is not valid)");
        }

        Position oldPosition = position;
        Position newPosition = moveController.move(position, direction);
        position = newPosition;

        resetGiveState();

        entityMovedObserver.notifyListeners(this, oldPosition, newPosition);
        return position;
    }

    public boolean isAllowedToMove() {
        return moveController.isActive();
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj instanceof Player other) {
            return id.equals(other.id);
        }
        return false;
    }

    public void setState(PlayerState state) {
        if (state == null) {
            throw new IllegalArgumentException("State cannot be null");
        }

        this.state = state;
        onStateUpdate();
    }

    private void onStateUpdate() {
        synchronized (stateMonitor) {
            stateMonitor.notifyAll();
        }
    }

    public PlayerStateMonitor getStateMonitor() {
        return stateMonitor;
    }

    public PlayerState getState() {
        return state;
    }

    public void setPosition(Position position) {
        if (position == null) {
            throw new IllegalArgumentException("Position cannot be null");
        }

        Position oldPosition = this.position;
        Position newPosition = position;
        this.position = position;

        resetGiveState();

        entityMovedObserver.notifyListeners(this, oldPosition, newPosition);
    }

    public void resetGiveState() {
        choosenReceiver = null;
        itemToGive = null;
    }

    @Override
    public double attack() {
        return getStats().getAttack();
    }

    @Override
    public boolean takeDamage(double damage, FightableEntity attacker) {
        boolean fatal = false;
        baseStats = baseStats.changedHealth(baseStats.getHealth() - Math.max(damage - baseStats.getDefense(), 0));

        if (isAlive() == false) {
            fatal = true;
            playerDiedObserver.notifyListeners(this);
        }

        resetGiveState();
        return fatal;
    }

    @Override
    public boolean isAlive() {
        return getStats().getHealth() > EPS;
    }

    @Override
    public void onVictimDied(FightableEntity victim) {
        if (victim == null) {
            throw new IllegalArgumentException("Victim cannot be null");
        }

        getHpBonus(victim.getXPForKilling());
    }

    @Override
    public int getXPForKilling() {
        final int xpPerLevel = 10;
        return level * xpPerLevel;
    }

    private void getHpBonus(int bonus) {
        xp += bonus;
        while (xp >= XP_LEVEL_LIMIT) {
            levelUp();
            xp -= XP_LEVEL_LIMIT;
        }
    }

    private void levelUp() {
        level++;
        baseStats = baseStats.changedHealth(baseStats.getHealth() + LEVEL_HEALTH_BONUS);
        baseStats = baseStats.changedHealth(baseStats.getMana() + LEVEL_MANA_BONUS);
        baseStats = baseStats.changedHealth(baseStats.getAttack() + LEVEL_ATTACK_BONUS);
        baseStats = baseStats.changedHealth(baseStats.getDefense() + LEVEL_DEFENSE_BONUS);
    }

    @Override
    public String getName() {
        return "Player" + id.toChar();
    }

    @Override
    public InteractionChoice getInteractionChoice(GameEntity other) {

        List<OfferItemInteraction> offerItemInteractions =
                backpack.getItems().stream()
                        .map(item -> (OfferItemInteraction) new PlayerOffersItemInteraction(this, item)).toList();

        List<InteractionWithZero> interactionsWithZero = new LinkedList<>();
        for (Item item : backpack.getItems()) {
            if (item.canBeConsumed(this) == true) {
                interactionsWithZero.add(new PlayerConsumeItemInteraction(this, item));
            }
            if (item.canBeSetAsAWeapon(this) == true) {
                interactionsWithZero.add(new PlayerEquipWeaponInteraction(this, item));
            }
        }

        InteractionNegotiator negotiator = InteractionNegotiator.builder()
                .setAttackInteraction(new AttackInteraction())
                .setOfferItemInteraction(offerItemInteractions)
                .setReceiverAcceptItemInteraction(new ReceiverAcceptItemInteraction())
                .setInteractionsWithZero(interactionsWithZero).build();
        negotiator.getAttackInteraction().setInitiator(this);
        negotiator.getReceiverAcceptItemInteraction().setInitiator(this);

        other.negotiateForInteractions(negotiator);

        InteractionChoice interactionChoice = new PlayerInteractionChoice(this);
        for (Interaction interaction : negotiator.getCompleteInteractions()) {
            interactionChoice.addOption(interaction);
        }

        return interactionChoice;
    }

    @Override
    public void negotiateForInteractions(InteractionNegotiator negotiator) {
        if (negotiator.getAttackInteraction() != null) {
            negotiator.getAttackInteraction().setSubject(this);
        }

        for (OfferItemInteraction interaction : negotiator.getOfferItemInteractions()) {
            interaction.setSubject(this);
        }

        if (negotiator.getReceiverAcceptItemInteraction() != null) {
            negotiator.getReceiverAcceptItemInteraction().setSubject(this);
        }
    }

    public void resurrect(Random rnd) {
        if (rnd == null) {
            throw new IllegalArgumentException("Rnd cannot be null");
        }

        if (backpack.getItems().isEmpty() == false) {
            backpack.removeItem(backpack.getItems().get(rnd.nextInt(backpack.getItems().size())));
        }
        baseStats = baseStats.changedHealth(RESSURRECTION_HEALTH);

        xp = 0;
        level = 1;
    }

    @Override
    public void setWeapon(WeaponItem weapon) {
        this.weaponItem = weapon;

        resetGiveState();
    }

    @Override
    public WeaponItem getWeapon() {
        return weaponItem;
    }

    @Override
    public Item getItemToGive() {
        return itemToGive;
    }

    @Override
    public boolean canGiveItem(ItemReceivingEntity receiver) {
        return itemToGive != null && choosenReceiver != null && choosenReceiver.equals(receiver);
    }

    @Override
    public void giveItem(ItemReceivingEntity receiver) {
        if (canGiveItem(receiver) == false) {
            return;
        }

        backpack.removeItem(itemToGive);
        if (weaponItem != null && weaponItem.equals(itemToGive) == true) {
            weaponItem = null;
        }

        receiver.receiveItem(itemToGive, this);
        resetGiveState();
    }

    @Override
    public boolean canReceiveItem(Item item, ItemGivingEntity giver) {
        return (backpack.isFull() == false) && giver != null && item != null;
    }

    @Override
    public void receiveItem(Item item, ItemGivingEntity giver) {
        if (canReceiveItem(item, giver) == false) {
            return;
        }

        getHpBonus(ITEM_RECEIVING_HP_BONUS);
        backpack.addItem(item);
    }

    public void setItemToGive(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null");
        }
        if (backpack.containsItem(item) == false) {
            return;
        }

        itemToGive = item;
    }

    public PlayerBackpack getBackpack() {
        return backpack;
    }

    public void setChoosenReceiver(ItemReceivingEntity choosenReceiver) {
        if (choosenReceiver == null) {
            throw new IllegalArgumentException("Choosen receiver cannot be null");
        }

        this.choosenReceiver = choosenReceiver;
    }

    public ItemReceivingEntity getChoosenReceiver() {
        return choosenReceiver;
    }

    public void consumeItem(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null");
        }
        if (backpack.containsItem(item) == false) {
            return;
        }

        baseStats = item.affectStats(baseStats);
        backpack.removeItem(item);
    }
}
