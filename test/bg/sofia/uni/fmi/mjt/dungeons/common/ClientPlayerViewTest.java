package bg.sofia.uni.fmi.mjt.dungeons.common;

import bg.sofia.uni.fmi.mjt.dungeons.common.item.PlayerBackpack;
import bg.sofia.uni.fmi.mjt.dungeons.server.entity.player.Player;
import bg.sofia.uni.fmi.mjt.dungeons.server.entity.player.PlayerState;
import bg.sofia.uni.fmi.mjt.dungeons.server.map.GameMapView;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ClientPlayerViewTest  {
    @Test
    void testClientPlayerViewToString() {
        PlayerState state = mock(PlayerState.class);
        Player player = mock(Player.class);
        GameMapView gameMapView = mock(GameMapView.class);

        when(state.getPlayer()).thenReturn(player);
        when(player.getId()).thenReturn(new PlayerId(1));
        when(player.getBackpack()).thenReturn(new PlayerBackpack());
        when(state.getGameMapView()).thenReturn(gameMapView);
        when(gameMapView.toString()).thenReturn(".");

        ClientPlayerView view = new ClientPlayerView(state);

        assertDoesNotThrow(() -> {
            view.toString();
        });
    }
}
