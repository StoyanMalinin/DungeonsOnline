package bg.sofia.uni.fmi.mjt.dungeons.server.observer;

import bg.sofia.uni.fmi.mjt.dungeons.server.entity.Treasure;

public interface TreasureTakenListener {
    void onTreasureTaken(Treasure treasure);
}
