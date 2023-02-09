package bg.sofia.uni.fmi.mjt.dungeons.common;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class PlayerIdTest {
    @Test
    void testToChar() {
        PlayerId id = new PlayerId(2);
        assertEquals('2', id.toChar());
    }

    @Test
    void testEqualsTwoDifferent() {
        PlayerId id1 = new PlayerId(2);
        PlayerId id2 = new PlayerId(3);
        assertNotEquals(id1, id2);
    }

    @Test
    void testEqualsIdentical() {
        PlayerId id1 = new PlayerId(2);
        PlayerId id2 = new PlayerId(2);
        assertEquals(id1, id2);
    }

    @Test
    void testEqualsNull() {
        PlayerId id1 = new PlayerId(2);
        assertNotEquals(id1, null);
    }
    @Test
    void testEqualsOtherObject() {
        PlayerId id1 = new PlayerId(2);
        assertNotEquals(id1, new Integer(2));
    }
}
