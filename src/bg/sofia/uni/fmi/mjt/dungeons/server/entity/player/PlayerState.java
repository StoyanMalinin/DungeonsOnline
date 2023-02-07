package bg.sofia.uni.fmi.mjt.dungeons.server.entity.player;

import bg.sofia.uni.fmi.mjt.dungeons.server.interaction.Interaction;
import bg.sofia.uni.fmi.mjt.dungeons.server.interaction.InteractionChoice;
import bg.sofia.uni.fmi.mjt.dungeons.server.interaction.PlayerInteractionChoice;
import bg.sofia.uni.fmi.mjt.dungeons.server.map.GameMapView;

public class PlayerState {
    private Player player;
    private GameMapView gameMapView;
    private Interaction interaction;
    private String errorMessage;

    private PlayerState(PlayerStateBuilder builder) {
        this.player = builder.player;
        this.gameMapView = builder.gameMapView;
        this.interaction = builder.interaction;
        this.errorMessage = builder.errorMessage;
    }

    public static class PlayerStateBuilder {
        private Player player;
        private GameMapView gameMapView;
        private Interaction interaction;
        private String errorMessage;

        public PlayerStateBuilder(Player player) {
            if (player == null) {
                throw new IllegalArgumentException("Player cannot be null");
            }

            this.player = player;
            this.gameMapView = null;
            this.interaction = null;
            this.errorMessage = null;
        }

        public PlayerStateBuilder setGameMapView(GameMapView gameMapView) {
            if (gameMapView == null) {
                throw new IllegalArgumentException("GameMapView cannot be null");
            }

            this.gameMapView = gameMapView;
            return this;
        }

        public PlayerStateBuilder setInteraction(Interaction interaction) {
            if (interaction == null) {
                throw new IllegalArgumentException("Interaction cannot be null");
            }

            this.interaction = interaction;
            return this;
        }

        public PlayerStateBuilder setErrorMessage(String errorMessage) {
            if (errorMessage == null) {
                throw new IllegalArgumentException("Error message cannot be null");
            }

            this.errorMessage = errorMessage;
            return this;
        }

        public PlayerState build() {
            return new PlayerState(this);
        }
    }

    public static PlayerStateBuilder builder(Player player) {
        return new PlayerStateBuilder(player);
    }

    public Interaction getInteraction() {
        return interaction;
    }

    public Player getPlayer() {
        return player;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public GameMapView getGameMapView() {
        return gameMapView;
    }
}
