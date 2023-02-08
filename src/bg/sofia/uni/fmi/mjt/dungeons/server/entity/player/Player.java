package bg.sofia.uni.fmi.mjt.dungeons.server.entity.player;

import bg.sofia.uni.fmi.mjt.dungeons.common.PlayerId;
import bg.sofia.uni.fmi.mjt.dungeons.common.Stats;
import bg.sofia.uni.fmi.mjt.dungeons.common.item.Backpack;
import bg.sofia.uni.fmi.mjt.dungeons.common.item.PlayerBackpack;
import bg.sofia.uni.fmi.mjt.dungeons.server.entity.FightableEntity;
import bg.sofia.uni.fmi.mjt.dungeons.server.entity.GameEntity;
import bg.sofia.uni.fmi.mjt.dungeons.server.entity.GridEntity;
import bg.sofia.uni.fmi.mjt.dungeons.server.entity.MovableEntity;
import bg.sofia.uni.fmi.mjt.dungeons.server.entity.controller.MoveController;
import bg.sofia.uni.fmi.mjt.dungeons.server.exception.NotAllowedToMoveException;
import bg.sofia.uni.fmi.mjt.dungeons.server.interaction.InteractionChoice;
import bg.sofia.uni.fmi.mjt.dungeons.server.interaction.InteractionNegotiator;
import bg.sofia.uni.fmi.mjt.dungeons.server.interaction.InteractionWithOne;
import bg.sofia.uni.fmi.mjt.dungeons.server.interaction.PlayerInteractionChoice;
import bg.sofia.uni.fmi.mjt.dungeons.server.map.Direction;
import bg.sofia.uni.fmi.mjt.dungeons.server.map.Position;
import bg.sofia.uni.fmi.mjt.dungeons.server.observer.EntityMovedObserver;
import bg.sofia.uni.fmi.mjt.dungeons.server.observer.PlayerDiedObserver;

import java.util.ArrayList;
import java.util.List;

public class Player implements GridEntity, MovableEntity, FightableEntity {

    private PlayerId id;
    private Position position;
    private MoveController moveController;
    private PlayerState state;
    private PlayerStateMonitor stateMonitor;
    private int level;
    private Stats baseStats;
    private Backpack backpack;
    private PlayerDiedObserver playerDiedObserver;
    private EntityMovedObserver entityMovedObserver;

    private static final double EPS = 0.0001;
    private static final int RESSURRECTION_HEALTH = 100;

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

        this.stateMonitor = new PlayerStateMonitor();
    }

    public PlayerDiedObserver getPlayerDiedObserver() {
        return playerDiedObserver;
    }

    public EntityMovedObserver getEntityMovedObserver() {
        return entityMovedObserver;
    }

    public Stats getStats() {
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
        entityMovedObserver.notifyListeners(this, oldPosition, newPosition);
    }

    @Override
    public double attack() {
        return getStats().getAttack();
    }

    @Override
    public void takeDamage(double damage, FightableEntity attacker) {
        baseStats = baseStats.changedHealth(baseStats.getHealth() - Math.max(damage - baseStats.getDefense(), 0));

        if (isAlive() == false) {
            playerDiedObserver.notifyListeners(this);
        }
    }

    @Override
    public boolean isAlive() {
        return getStats().getHealth() > EPS;
    }

    @Override
    public String getName() {
        return "Player" + id.toChar();
    }

    @Override
    public InteractionChoice getInteractionChoice(GameEntity other) {
        InteractionNegotiator negotiator = new InteractionNegotiator();
        negotiator.setInitiatorOfAttackInteraction(this);

        other.negotiateForInteractions(negotiator);

        InteractionChoice interactionChoice = new PlayerInteractionChoice(this);
        for (InteractionWithOne interaction : negotiator.getCompleteInteractions()) {
            interactionChoice.addOption(interaction);
        }

        return interactionChoice;
    }

    @Override
    public void negotiateForInteractions(InteractionNegotiator negotiator) {
        negotiator.setSubjectOfAttackInteraction(this);
    }

    public void resurrect() {
        baseStats = baseStats.changedHealth(RESSURRECTION_HEALTH);
    }
}
