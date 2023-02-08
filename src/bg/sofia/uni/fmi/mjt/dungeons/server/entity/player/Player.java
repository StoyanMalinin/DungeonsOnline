package bg.sofia.uni.fmi.mjt.dungeons.server.entity.player;

import bg.sofia.uni.fmi.mjt.dungeons.common.PlayerId;
import bg.sofia.uni.fmi.mjt.dungeons.server.entity.GridEntity;
import bg.sofia.uni.fmi.mjt.dungeons.server.entity.MovableEntity;
import bg.sofia.uni.fmi.mjt.dungeons.server.entity.controller.MoveController;
import bg.sofia.uni.fmi.mjt.dungeons.server.exception.NotAllowedToMoveException;
import bg.sofia.uni.fmi.mjt.dungeons.server.map.Direction;
import bg.sofia.uni.fmi.mjt.dungeons.server.map.Position;
import bg.sofia.uni.fmi.mjt.dungeons.server.observer.EntityMovedObserver;

public class Player implements GridEntity, MovableEntity {

    private PlayerId id;
    private Position position;

    private MoveController moveController;

    private EntityMovedObserver moveObserver;
    private PlayerState state;
    private PlayerStateMonitor stateMonitor;


    public Player(PlayerId id, Position position, MoveController moveController) {
        if (id == null) {
            throw new IllegalArgumentException("Id cannot be null");
        }
        if (position == null) {
            throw new IllegalArgumentException("Position cannot be null");
        }
        if (position == null) {
            throw new IllegalArgumentException("MoveController cannot be null");
        }

        this.id = id;
        this.state = null;
        this.position = position;
        this.moveController = moveController;
        this.moveObserver = new EntityMovedObserver();

        this.stateMonitor = new PlayerStateMonitor();
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

        moveObserver.notifyListeners(this, oldPosition, newPosition);
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
}
