package bg.sofia.uni.fmi.mjt.dungeons.server.interaction;

import bg.sofia.uni.fmi.mjt.dungeons.common.item.ManaPotion;
import bg.sofia.uni.fmi.mjt.dungeons.server.entity.player.Player;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.mockito.Mockito.mock;

public class PlayerInteractionChoiceTest {
    @Test
    void testAddOption() {
        Player player = mock(Player.class);
        InteractionChoice interactionChoice = new PlayerInteractionChoice(player);

        Interaction interaction = new PlayerConsumeItemInteraction(player, new ManaPotion(1, 1, 1));
        interactionChoice.addOption(interaction);

        assertIterableEquals(List.of(interaction), interactionChoice.getOptions());
    }

    @Test
    void testAddOptionTwice() {
        Player player = mock(Player.class);
        InteractionChoice interactionChoice = new PlayerInteractionChoice(player);

        Interaction interaction = new PlayerConsumeItemInteraction(player, new ManaPotion(1, 1, 1));
        interactionChoice.addOption(interaction);
        interactionChoice.addOption(interaction);

        assertIterableEquals(List.of(interaction), interactionChoice.getOptions());
    }
}
