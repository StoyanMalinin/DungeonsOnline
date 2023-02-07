package bg.sofia.uni.fmi.mjt.dungeons.server.map;

public record Position(int row, int col) {
    public Position {
        if (row < 0) {
            throw new IllegalArgumentException("Row cannot be negative");
        }
        if (col < 0) {
            throw new IllegalArgumentException("Row cannot be negative");
        }
    }
}
