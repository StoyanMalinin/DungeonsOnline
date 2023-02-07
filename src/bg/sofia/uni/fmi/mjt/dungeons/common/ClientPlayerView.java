package bg.sofia.uni.fmi.mjt.dungeons.common;

import bg.sofia.uni.fmi.mjt.dungeons.server.entity.player.PlayerState;
import bg.sofia.uni.fmi.mjt.dungeons.server.interaction.Interaction;

public class ClientPlayerView {
    private PlayerId id;
    private String serverMessage;
    private String errorMessage;


    private ClientPlayerView(PlayerState state) {
        if (state == null) {
            throw new IllegalArgumentException("State cannot be null");
        }

        this.id = state.getPlayer().getId();
        this.serverMessage = generateServerMessage(state.getInteraction());

        if (state.getErrorMessage() != null) {
            this.errorMessage = state.getErrorMessage();
        }
    }

    private String generateServerMessage(Interaction interaction) {
        StringBuilder builder = new StringBuilder();

        builder.append("You can decide to move by typing u/d/l/r" + System.lineSeparator());
        if (interaction != null) {
            builder.append(interaction.toString());
        }

        return builder.toString();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append("Player state of player with id ");
        builder.append(id.toString());
        builder.append(System.lineSeparator());

        builder.append("---------- Map State ----------");
        builder.append(System.lineSeparator());

        return builder.toString();
    }
}
