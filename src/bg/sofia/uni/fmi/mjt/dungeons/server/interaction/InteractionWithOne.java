package bg.sofia.uni.fmi.mjt.dungeons.server.interaction;

import bg.sofia.uni.fmi.mjt.dungeons.server.entity.GameEntity;

public interface InteractionWithOne extends Interaction {
    GameEntity getSubject();
}
