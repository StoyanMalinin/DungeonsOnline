package bg.sofia.uni.fmi.mjt.dungeons.server.entity.controller;

import bg.sofia.uni.fmi.mjt.dungeons.server.exception.NotAllowedToMoveException;
import bg.sofia.uni.fmi.mjt.dungeons.server.map.Direction;
import bg.sofia.uni.fmi.mjt.dungeons.server.map.GameMap;
import bg.sofia.uni.fmi.mjt.dungeons.server.map.Position;

public class MapMoveController implements MoveController {

    private GameMap gameMap;
    private boolean isActive;

    public MapMoveController(GameMap gameMap) {
        if (gameMap == null) {
            throw new IllegalArgumentException("Map cannot be null");
        }

        this.gameMap = gameMap;
        this.isActive = true;
    }

    @Override
    public boolean isActive() {
        return isActive;
    }

    @Override
    public void activate() {
        isActive = true;
    }

    @Override
    public void deactivate() {
        isActive = false;
    }

    @Override
    public boolean canMove(Position position, Direction direction) {
        if (position == null) {
            throw new IllegalArgumentException("Position cannot be null");
        }
        if (direction == null) {
            throw new IllegalArgumentException("Direction cannot be null");
        }

        return gameMap.isInside(position.moveByDirection(direction));
    }

    @Override
    public Position move(Position position, Direction direction) throws NotAllowedToMoveException {
        if (canMove(position, direction) == false) {
            throw new NotAllowedToMoveException();
        }

        return position.moveByDirection(direction);
    }
}
