package bg.sofia.uni.fmi.mjt.dungeons.server.entity;

import bg.sofia.uni.fmi.mjt.dungeons.server.map.Position;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class WallCellTest {
    @Test
    void testGetPosition() {
        WallCell cell = new WallCell(new Position(2, 3));
        assertEquals(new Position(2, 3), cell.getPosition());
    }

    @Test
    void testCanEnter() {
        WallCell cell = new WallCell(new Position(2, 3));
        assertFalse(cell.canEnter());
    }

    @Test
    void testIsFree() {
        WallCell cell = new WallCell(new Position(2, 3));
        assertFalse(cell.isFree());
    }

    @Test
    void testGetName() {
        WallCell cell = new WallCell(new Position(2, 3));
        assertEquals("WallCell(2, 3)", cell.getName());
    }

    @Test
    void testEqualsIdentical() {
        WallCell cell1 = new WallCell(new Position(2, 3));
        WallCell cell2 = new WallCell(new Position(2, 3));

        assertEquals(cell1, cell2);
    }

    @Test
    void testEqualsNull() {
        WallCell cell = new WallCell(new Position(2, 3));
        assertFalse(cell.equals(null));
    }

    @Test
    void testEqualsOtherObject() {
        WallCell cell = new WallCell(new Position(2, 3));
        assertFalse(cell.equals(2));
    }
}
