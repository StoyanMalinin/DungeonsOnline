package bg.sofia.uni.fmi.mjt.dungeons.server.entity.controller;

import bg.sofia.uni.fmi.mjt.dungeons.server.exception.NotAllowedToMoveException;
import bg.sofia.uni.fmi.mjt.dungeons.server.map.Direction;
import bg.sofia.uni.fmi.mjt.dungeons.server.map.Position;

public interface MoveController extends Controller {
    boolean canMove(Position position, Direction direction);
    Position move(Position position, Direction direction) throws NotAllowedToMoveException;
}
