package bg.sofia.uni.fmi.mjt.dungeons.server.entity;

import bg.sofia.uni.fmi.mjt.dungeons.server.interaction.InteractionChoice;
import bg.sofia.uni.fmi.mjt.dungeons.server.interaction.InteractionNegotiator;

public interface GameEntity {

    String getName();

    InteractionChoice getInteractionChoice(GameEntity other);
    void negotiateForInteractions(InteractionNegotiator negotiator);
}
