package bg.sofia.uni.fmi.mjt.dungeons.server.interaction;

import bg.sofia.uni.fmi.mjt.dungeons.server.entity.player.Player;
import jdk.jshell.spi.ExecutionControl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class PlayerInteractionChoice implements InteractionChoice {

    private Player player;
    private List<Interaction> options;

    public PlayerInteractionChoice(Player player) {
        this.player = player;
        this.options = new ArrayList<>();
    }

    @Override
    public void addOption(Interaction interaction) {
        if (interaction == null) {
            throw new IllegalArgumentException("Interaction cannot be null");
        }
        if (interaction.getInitiator().equals(player) == false) {
            throw new IllegalArgumentException("The interaction's initiator must be the player");
        }

        options.add(interaction);
    }

    @Override
    public List<Interaction> getOptions() {
        return List.copyOf(options);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append("You can chose one of the following options" + System.lineSeparator());
        for (int i = 0; i < options.size(); i++) {
            builder.append(i);
            builder.append(" -> ");
            builder.append(options.get(i).toString());
            builder.append(System.lineSeparator());
        }

        return builder.toString();
    }
}
