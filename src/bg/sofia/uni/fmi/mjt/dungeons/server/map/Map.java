package bg.sofia.uni.fmi.mjt.dungeons.server.map;

import bg.sofia.uni.fmi.mjt.dungeons.server.entity.EmptyCell;
import bg.sofia.uni.fmi.mjt.dungeons.server.entity.GridEntity;

public class Map {
    private int n;
    private int m;
    private GridEntity[][] grid;

    public Map(int n, int m) {
        this.n = n;
        this.m = m;
        this.grid = new GridEntity[n][m];

        fillGrid();
    }

    private void fillGrid() {
        for (int r = 0; r < n; r++) {
            for (int c = 0; c < m; c++) {
                grid[r][c] = new EmptyCell(new Position(r, c));
            }
        }
    }

    public GridEntity getAt(int r, int c) {
        if (r < 0 || c < 0) {
            throw new IllegalArgumentException("Row and column must be non-negative");
        }
        if (isInside(r, c) == false) {
            throw new IllegalArgumentException("The requested position must be inside the grid");
        }

        return grid[r][c];
    }

    public GridEntity getAt(Position position) {
        return getAt(position.row(), position.col());
    }

    public boolean isInside(Position position) {
        return isInside(position.row(), position.col());
    }

    public boolean isInside(int r, int c) {
        return (0 <= r && r < n && 0 <= c && c < m);
    }
}
