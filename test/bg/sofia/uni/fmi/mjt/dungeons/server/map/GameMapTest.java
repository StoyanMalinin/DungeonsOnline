package bg.sofia.uni.fmi.mjt.dungeons.server.map;

import bg.sofia.uni.fmi.mjt.dungeons.server.entity.EmptyCell;
import bg.sofia.uni.fmi.mjt.dungeons.server.entity.WallCell;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameMapTest {
    @Test
    void testGetAtOnlyEmptyCell() {
        GameMap gameMap = new GameMap(2, 2, 0);
        assertEquals(gameMap.getAt(0, 0), new EmptyCell(new Position(0,0)));
    }

    @Test
    void testGetAtOnlyWallCell() {
        GameMap gameMap = new GameMap(2, 2, 4);
        assertEquals(gameMap.getAt(0, 0), new WallCell(new Position(0,0)));
    }
}
