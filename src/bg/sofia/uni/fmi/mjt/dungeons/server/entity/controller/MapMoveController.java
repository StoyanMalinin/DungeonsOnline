package bg.sofia.uni.fmi.mjt.dungeons.server.entity.controller;

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
    public boolean canMove(Direction direction) {
        return false;
    }

    @Override
    public Position move(Direction direction) {
        return null;
    }
}
