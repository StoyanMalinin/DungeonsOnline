package bg.sofia.uni.fmi.mjt.dungeons.server.interaction;

import bg.sofia.uni.fmi.mjt.dungeons.common.item.Item;
import bg.sofia.uni.fmi.mjt.dungeons.server.entity.player.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PlayerEquipWeaponInteractionTest {
    @Test
    void testGetInitiator() {
        Player player1 = mock(Player.class);
        Player player2 = mock(Player.class);
        Item item = mock(Item.class);

        PlayerEquipWeaponInteraction interaction = new PlayerEquipWeaponInteraction(player1, item);
        assertEquals(player1, interaction.getInitiator());
    }

    @Test
    void testToString() {
        Player player1 = mock(Player.class);

        Item item = mock(Item.class);
        when(item.getName()).thenReturn("item");

        PlayerEquipWeaponInteraction interaction = new PlayerEquipWeaponInteraction(player1, item);
        assertEquals("Equip item", interaction.toString());
    }

    @Test
    void testConstructorNullPlayer() {
        Player player1 = mock(Player.class);
        Item item = mock(Item.class);

        assertThrows(IllegalArgumentException.class, () -> {
            new PlayerEquipWeaponInteraction(null, item);
        });
    }

    @Test
    void testConstructorNullItem() {
        Player player1 = mock(Player.class);
        Item item = mock(Item.class);

        assertThrows(IllegalArgumentException.class, () -> {
            new PlayerEquipWeaponInteraction(player1, null);
        });
    }

    @Test
    void testEqualsIdentical() {
        Player player1 = mock(Player.class);

        Item item = mock(Item.class);
        when(item.getName()).thenReturn("item");

        PlayerEquipWeaponInteraction interaction1 = new PlayerEquipWeaponInteraction(player1, item);
        PlayerEquipWeaponInteraction interaction2 = new PlayerEquipWeaponInteraction(player1, item);

        assertTrue(interaction1.equals(interaction2));
    }

    @Test
    void testEqualsNull() {
        Player player1 = mock(Player.class);

        Item item = mock(Item.class);
        when(item.getName()).thenReturn("item");

        PlayerEquipWeaponInteraction interaction1 = new PlayerEquipWeaponInteraction(player1, item);

        assertFalse(interaction1.equals(null));
    }

    @Test
    void testEqualsOtherObject() {
        Player player1 = mock(Player.class);

        Item item = mock(Item.class);
        when(item.getName()).thenReturn("item");

        PlayerEquipWeaponInteraction interaction1 = new PlayerEquipWeaponInteraction(player1, item);

        assertFalse(interaction1.equals(2));
    }
}
