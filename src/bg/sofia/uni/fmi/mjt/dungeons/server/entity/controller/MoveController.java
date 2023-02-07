package bg.sofia.uni.fmi.mjt.dungeons.server.entity.controller;

import bg.sofia.uni.fmi.mjt.dungeons.server.map.Direction;
import bg.sofia.uni.fmi.mjt.dungeons.server.map.Position;

public interface MoveController extends Controller {
    Position getPosition();

    boolean canMove(Direction direction);
    Position move(Direction direction);
}
