package bg.sofia.uni.fmi.mjt.dungeons.server.interaction;

import java.util.ArrayList;
import java.util.List;

public class EmptyInteractionChoice implements InteractionChoice {
    @Override
    public List<InteractionWithOne> getOptions() {
        return new ArrayList<>();
    }

    @Override
    public void addOption(InteractionWithOne interaction) {

    }
}
