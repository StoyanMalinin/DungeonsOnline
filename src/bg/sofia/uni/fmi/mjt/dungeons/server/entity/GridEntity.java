package bg.sofia.uni.fmi.mjt.dungeons.server.entity;

import bg.sofia.uni.fmi.mjt.dungeons.server.map.Direction;
import bg.sofia.uni.fmi.mjt.dungeons.server.map.Position;

public interface GridEntity extends GameEntity {
    Position getPosition();
    boolean canEnter();
    char consoleSymbol();

    boolean isFree();
}
