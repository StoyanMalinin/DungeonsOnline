package bg.sofia.uni.fmi.mjt.dungeons.server.map;

import bg.sofia.uni.fmi.mjt.dungeons.server.entity.EmptyCell;
import bg.sofia.uni.fmi.mjt.dungeons.server.entity.GridEntity;
import bg.sofia.uni.fmi.mjt.dungeons.server.entity.WallCell;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameMap {
    private int n;
    private int m;
    private GridEntity[][] grid;

    public GameMap(int n, int m, int wallCellCnt) {
        if (n <= 0) {
            throw new IllegalArgumentException("GameMap dimension must be positive");
        }
        if (m <= 0) {
            throw new IllegalArgumentException("GameMap dimension must be positive");
        }
        if (wallCellCnt < 0) {
            throw new IllegalArgumentException("WallCellCnt cannot be negative");
        }

        this.n = n;
        this.m = m;
        this.grid = new GridEntity[n][m];

        this.fillGrid(wallCellCnt);
    }

    private void fillGrid(int wallCellCnt) {
        List<Position> allCells = new ArrayList<>();
        for (int r = 0; r < n; r++) {
            for (int c = 0; c < m; c++) {
                allCells.add(new Position(r, c));
            }
        }

        Collections.shuffle(allCells);
        for (int i = 0; i < wallCellCnt; i++) {
            grid[allCells.get(i).row()][allCells.get(i).col()] = new WallCell(allCells.get(i));
        }
        for (int i = wallCellCnt; i < allCells.size(); i++) {
            grid[allCells.get(i).row()][allCells.get(i).col()] = new EmptyCell(allCells.get(i));
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

    public int getRowCnt() {
        return n;
    }

    public int getColCnt() {
        return m;
    }
}
