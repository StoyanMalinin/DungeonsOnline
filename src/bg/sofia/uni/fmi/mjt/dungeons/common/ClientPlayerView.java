package bg.sofia.uni.fmi.mjt.dungeons.common;

import bg.sofia.uni.fmi.mjt.dungeons.common.item.PlayerBackpack;
import bg.sofia.uni.fmi.mjt.dungeons.server.entity.player.PlayerState;
import bg.sofia.uni.fmi.mjt.dungeons.server.interaction.InteractionChoice;
import bg.sofia.uni.fmi.mjt.dungeons.server.map.GameMapView;
import org.mockito.internal.creation.SuspendMethod;

import java.io.Serializable;

public class ClientPlayerView implements Serializable {
    private PlayerId id;
    private int playerLevel;
    private int playerXP;
    private String serverMessage;
    private String errorMessage;
    private GameMapView gameMapView;
    private Stats playerStats;
    private String backpackString;


    public ClientPlayerView(PlayerState state) {
        if (state == null) {
            throw new IllegalArgumentException("State cannot be null");
        }

        this.id = state.getPlayer().getId();
        this.playerStats = state.getPlayer().getStats();
        this.backpackString = state.getPlayer().getBackpack().toString();
        this.playerLevel = state.getPlayer().getLevel();
        this.playerXP = state.getPlayer().getXP();
        this.serverMessage = generateServerMessage(state.getInteractionChoice());

        if (state.getErrorMessage() != null) {
            this.errorMessage = state.getErrorMessage();
        }
        if (state.getGameMapView() != null) {
            this.gameMapView = state.getGameMapView();
        }
    }

    private String generateServerMessage(InteractionChoice interactionChoice) {
        StringBuilder builder = new StringBuilder();

        builder.append("You can decide to move by typing u/d/l/r" + System.lineSeparator());
        if (interactionChoice != null && interactionChoice.getOptions().isEmpty() == false) {
            builder.append("You can chose interaction by typing its index" + System.lineSeparator());
            builder.append(interactionChoice);
        }

        return builder.toString();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append("Player state of player with id ");
        builder.append(id.toString());
        builder.append(System.lineSeparator());
        builder.append("Stats: " + System.lineSeparator());
        builder.append(playerStats);
        builder.append(System.lineSeparator());
        builder.append(backpackString);
        builder.append(System.lineSeparator());
        builder.append("Level: " + playerLevel + System.lineSeparator());
        builder.append("XP: " + playerXP + System.lineSeparator());


        builder.append("---------- Map State ----------");
        builder.append(System.lineSeparator());
        builder.append(gameMapView.toString());

        if (serverMessage != null) {
            builder.append("------- Server message ----- ");
            builder.append(System.lineSeparator());
            builder.append(serverMessage);
            builder.append(System.lineSeparator());
        }

        if (errorMessage != null) {
            builder.append("------- Error message ----- ");
            builder.append(System.lineSeparator());
            builder.append(errorMessage);
            builder.append(System.lineSeparator());
        }

        return builder.toString();
    }
}
