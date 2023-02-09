package bg.sofia.uni.fmi.mjt.dungeons.server.interaction;

import java.util.Collection;
import java.util.List;

public interface InteractionChoice {
    List<Interaction> getOptions();
    void addOption(Interaction interaction);
}
