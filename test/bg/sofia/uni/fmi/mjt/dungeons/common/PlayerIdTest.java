package bg.sofia.uni.fmi.mjt.dungeons.common;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlayerIdTest {
    @Test
    void testToChar() {
        PlayerId id = new PlayerId(2);

        assertEquals('2', id.toChar());
    }
}
