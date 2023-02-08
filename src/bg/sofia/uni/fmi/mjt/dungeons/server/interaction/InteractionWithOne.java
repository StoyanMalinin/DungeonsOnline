package bg.sofia.uni.fmi.mjt.dungeons.server.interaction;

import bg.sofia.uni.fmi.mjt.dungeons.server.GameMaster;
import bg.sofia.uni.fmi.mjt.dungeons.server.entity.GameEntity;

public interface InteractionWithOne {
    GameEntity getInitiator();
    GameEntity getSubject();

    boolean isComplete();
    void execute(GameMaster gameMaster);
}
