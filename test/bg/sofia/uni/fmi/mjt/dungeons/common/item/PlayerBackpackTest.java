package bg.sofia.uni.fmi.mjt.dungeons.common.item;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PlayerBackpackTest {
    @Test
    void testGetItemsEmptyBackpack() {
        PlayerBackpack backpack = new PlayerBackpack();
        assertEquals(0, backpack.getItems().size(), "The player backpack should be empty by default");
    }

    @Test
    void testAddItemNullItem() {
        PlayerBackpack backpack = new PlayerBackpack();

        assertThrows(IllegalArgumentException.class, () -> {
            backpack.addItem(null);
        });
    }

    @Test
    void testAddEnoughSpace() {
        PlayerBackpack backpack = new PlayerBackpack();

        Item item = mock(Item.class);
        assertTrue(backpack.addItem(item));
    }

    @Test
    void testAddNotEnoughSpace() {
        PlayerBackpack backpack = new PlayerBackpack();

        for (int i = 0; i < 10; i++) {
            Item item = mock(Item.class);
            assertTrue(backpack.addItem(item));
        }

        Item item = mock(Item.class);
        assertFalse(backpack.addItem(item));
    }

    @Test
    void testToString() {
        PlayerBackpack backpack = new PlayerBackpack();

        Item item = mock(Item.class);
        when(item.getName()).thenReturn("item");
        backpack.addItem(item);

        String expected = "Player backpack: " + System.lineSeparator() +
                "item" + System.lineSeparator() +
                "End of backpack" + System.lineSeparator();

        assertEquals(expected, backpack.toString());
    }
}
