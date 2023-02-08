package bg.sofia.uni.fmi.mjt.dungeons.server.interaction;

import java.util.Collection;
import java.util.List;

public interface InteractionChoice {
    List<InteractionWithOne> getOptions();
    void addOption(InteractionWithOne interaction);
}
