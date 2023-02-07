package bg.sofia.uni.fmi.mjt.dungeons.server.entity;

import bg.sofia.uni.fmi.mjt.dungeons.server.map.Position;

public class WallCell implements GridEntity {
    private Position position;

    public WallCell(Position position) {
        this.position = position;
    }

    @Override
    public Position getPosition() {
        return null;
    }

    @Override
    public boolean canEnter() {
        return false;
    }

    @Override
    public char consoleSymbol() {
        return '#';
    }
}
