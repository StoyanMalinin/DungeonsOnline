package bg.sofia.uni.fmi.mjt.dungeons.server.map;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PositionTest {
    @Test
    void testMoveByDirectionLeft() {
        Position position = new Position(1, 1);
        Position newPos = position.moveByDirection(Direction.LEFT);

        assertEquals(new Position(1, 0), newPos);
    }

    @Test
    void testMoveByDirectionRight() {
        Position position = new Position(1, 1);
        Position newPos = position.moveByDirection(Direction.RIGHT);

        assertEquals(new Position(1, 2), newPos);
    }

    @Test
    void testMoveByDirectionUp() {
        Position position = new Position(1, 1);
        Position newPos = position.moveByDirection(Direction.UP);

        assertEquals(new Position(0, 1), newPos);
    }

    @Test
    void testMoveByDirectionDown() {
        Position position = new Position(1, 1);
        Position newPos = position.moveByDirection(Direction.DOWN);

        assertEquals(new Position(2, 1), newPos);
    }
}
