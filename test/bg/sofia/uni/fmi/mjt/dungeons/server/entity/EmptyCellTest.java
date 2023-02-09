package bg.sofia.uni.fmi.mjt.dungeons.server.entity;

import bg.sofia.uni.fmi.mjt.dungeons.server.entity.EmptyCell;
import bg.sofia.uni.fmi.mjt.dungeons.server.entity.WallCell;
import bg.sofia.uni.fmi.mjt.dungeons.server.map.Position;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class EmptyCellTest {
    @Test
    void testGetPosition() {
        EmptyCell cell = new EmptyCell(new Position(2, 3));
        assertEquals(new Position(2, 3), cell.getPosition());
    }

    @Test
    void testCanEnter() {
        EmptyCell cell = new EmptyCell(new Position(2, 3));
        assertTrue(cell.canEnter());
    }

    @Test
    void testIsFree() {
        EmptyCell cell = new EmptyCell(new Position(2, 3));
        assertTrue(cell.isFree());
    }

    @Test
    void testGetName() {
        EmptyCell cell = new EmptyCell(new Position(2, 3));
        assertEquals("EmptyCell(2, 3)", cell.getName());
    }

    @Test
    void testEqualsIdentical() {
        EmptyCell cell1 = new EmptyCell(new Position(2, 3));
        EmptyCell cell2 = new EmptyCell(new Position(2, 3));

        assertEquals(cell1, cell2);
    }

    @Test
    void testEqualsNull() {
        EmptyCell cell = new EmptyCell(new Position(2, 3));
        assertFalse(cell.equals(2));
    }

    @Test
    void testEqualsOtherObject() {
        EmptyCell cell = new EmptyCell(new Position(2, 3));
        assertFalse(cell.equals(2));
    }
}
