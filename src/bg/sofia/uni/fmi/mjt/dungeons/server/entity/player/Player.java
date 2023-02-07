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
        this.position = position;
        this.moveController = moveController;
        this.moveObserver = new EntityMovedObserver();
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
    public boolean canMove(Direction direction) {
        if (direction == null) {
            throw new IllegalArgumentException("Direction cannot be null");
        }

        return moveController.canMove(direction) && moveController.isActive();
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
        Position newPosition = moveController.move(direction);
        position = newPosition;

        moveObserver.notifyListeners(this, oldPosition, newPosition);
        return position;
    }

    public boolean isAllowedToMove() {
        return moveController.isActive();
    }
}
