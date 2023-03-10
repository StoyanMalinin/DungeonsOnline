package bg.sofia.uni.fmi.mjt.dungeons.server.interaction;

import bg.sofia.uni.fmi.mjt.dungeons.server.GameMaster;
import bg.sofia.uni.fmi.mjt.dungeons.server.entity.GameEntity;
import bg.sofia.uni.fmi.mjt.dungeons.server.exception.ClientCommandException;

public interface InteractionWithOne extends Interaction {
    GameEntity getSubject();

    boolean isComplete();
}
