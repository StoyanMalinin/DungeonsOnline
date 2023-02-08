package bg.sofia.uni.fmi.mjt.dungeons.server.map;

public record Position(int row, int col) {
    public Position moveByDirection(Direction direction) {
        if (direction == Direction.UP) {
            return new Position(row() - 1, col());
        }
        if (direction == Direction.DOWN) {
            return new Position(row() + 1, col());
        }
        if (direction == Direction.LEFT) {
            return new Position(row(), col() - 1);
        }
        if (direction == Direction.RIGHT) {
            return new Position(row() , col() + 1);
        }

        throw new IllegalArgumentException("Unsupported movement direction");
    }
}
