package bg.sofia.uni.fmi.mjt.dungeons.server.interaction;

import bg.sofia.uni.fmi.mjt.dungeons.common.item.Item;
import bg.sofia.uni.fmi.mjt.dungeons.server.entity.player.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PlayerConsumeItemInteractionTest {
    @Test
    void testGetInitiator() {
        Player player1 = mock(Player.class);
        Player player2 = mock(Player.class);
        Item item = mock(Item.class);

        PlayerConsumeItemInteraction interaction = new PlayerConsumeItemInteraction(player1, item);
        assertEquals(player1, interaction.getInitiator());
    }

    @Test
    void testToString() {
        Player player1 = mock(Player.class);

        Item item = mock(Item.class);
        when(item.getName()).thenReturn("item");

        PlayerConsumeItemInteraction interaction = new PlayerConsumeItemInteraction(player1, item);

        assertEquals("Consume item", interaction.toString());
    }

    @Test
    void testConstructorNullPlayer() {
        Player player1 = mock(Player.class);
        Item item = mock(Item.class);

        assertThrows(IllegalArgumentException.class, () -> {
            new PlayerConsumeItemInteraction(null, item);
        });
    }

    @Test
    void testConstructorNullItem() {
        Player player1 = mock(Player.class);
        Item item = mock(Item.class);

        assertThrows(IllegalArgumentException.class, () -> {
            new PlayerConsumeItemInteraction(player1, null);
        });
    }

    @Test
    void testEqualsIdentical() {
        Player player1 = mock(Player.class);

        Item item = mock(Item.class);
        when(item.getName()).thenReturn("item");

        PlayerConsumeItemInteraction interaction1 = new PlayerConsumeItemInteraction(player1, item);
        PlayerConsumeItemInteraction interaction2 = new PlayerConsumeItemInteraction(player1, item);

        assertTrue(interaction1.equals(interaction2));
    }

    @Test
    void testEqualsNull() {
        Player player1 = mock(Player.class);

        Item item = mock(Item.class);
        when(item.getName()).thenReturn("item");

        PlayerConsumeItemInteraction interaction1 = new PlayerConsumeItemInteraction(player1, item);

        assertFalse(interaction1.equals(null));
    }

    @Test
    void testEqualsOtherObject() {
        Player player1 = mock(Player.class);

        Item item = mock(Item.class);
        when(item.getName()).thenReturn("item");

        PlayerConsumeItemInteraction interaction1 = new PlayerConsumeItemInteraction(player1, item);

        assertFalse(interaction1.equals(2));
    }
}
