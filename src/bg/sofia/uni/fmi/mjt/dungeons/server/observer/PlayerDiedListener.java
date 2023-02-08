package bg.sofia.uni.fmi.mjt.dungeons.server.observer;

import bg.sofia.uni.fmi.mjt.dungeons.server.entity.GameEntity;
import bg.sofia.uni.fmi.mjt.dungeons.server.entity.player.Player;

public interface PlayerDiedListener {
    void onPlayerDied(Player entity);
}
