package bg.sofia.uni.fmi.mjt.dungeons.server.entity;

import bg.sofia.uni.fmi.mjt.dungeons.server.exception.NotAllowedToMoveException;
import bg.sofia.uni.fmi.mjt.dungeons.server.map.Direction;
import bg.sofia.uni.fmi.mjt.dungeons.server.map.Position;

public interface MovableEntity extends GameEntity {
    boolean canMove(Direction direction);
    Position move(Direction direction) throws NotAllowedToMoveException;
}
