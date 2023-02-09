package bg.sofia.uni.fmi.mjt.dungeons.server.interaction;

import bg.sofia.uni.fmi.mjt.dungeons.common.item.Item;
import bg.sofia.uni.fmi.mjt.dungeons.server.entity.player.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PlayerOffersItemInteractionTest {
    @Test
    void testGetInitiator() {
        Player player1 = mock(Player.class);
        Player player2 = mock(Player.class);
        Item item = mock(Item.class);

        PlayerOffersItemInteraction interaction = new PlayerOffersItemInteraction(player1, item);
        assertEquals(player1, interaction.getInitiator());
    }

    @Test
    void testGetSubject() {
        Player player1 = mock(Player.class);
        Player player2 = mock(Player.class);
        Item item = mock(Item.class);

        PlayerOffersItemInteraction interaction = new PlayerOffersItemInteraction(player1, item);
        assertEquals(null, interaction.getSubject());
    }

    @Test
    void testSetSubject() {
        Player player1 = mock(Player.class);
        Player player2 = mock(Player.class);
        Item item = mock(Item.class);

        PlayerOffersItemInteraction interaction = new PlayerOffersItemInteraction(player1, item);

        interaction.setSubject(player2);
        assertEquals(player2, interaction.getSubject());
    }

    @Test
    void testIsCompleteNotComplete() {
        Player player1 = mock(Player.class);
        Player player2 = mock(Player.class);
        Item item = mock(Item.class);

        PlayerOffersItemInteraction interaction = new PlayerOffersItemInteraction(player1, item);
        assertFalse(interaction.isComplete());
    }

    @Test
    void testIsCompleteComplete() {
        Player player1 = mock(Player.class);
        Player player2 = mock(Player.class);
        Item item = mock(Item.class);

        PlayerOffersItemInteraction interaction = new PlayerOffersItemInteraction(player1, item);
        interaction.setSubject(player2);

        assertTrue(interaction.isComplete());
    }

    @Test
    void testToString() {
        Player player1 = mock(Player.class);

        Player player2 = mock(Player.class);
        when(player2.getName()).thenReturn("player2");

        Item item = mock(Item.class);
        when(item.getName()).thenReturn("item");

        PlayerOffersItemInteraction interaction = new PlayerOffersItemInteraction(player1, item);
        interaction.setSubject(player2);

        assertEquals("Offer item to player2", interaction.toString());
    }

    @Test
    void testConstructorNullPlayer() {
        Player player1 = mock(Player.class);
        Item item = mock(Item.class);

        assertThrows(IllegalArgumentException.class, () -> {
            new PlayerOffersItemInteraction(null, item);
        });
    }

    @Test
    void testConstructorNullItem() {
        Player player1 = mock(Player.class);
        Item item = mock(Item.class);

        assertThrows(IllegalArgumentException.class, () -> {
            new PlayerOffersItemInteraction(player1, null);
        });
    }
}
