package bg.sofia.uni.fmi.mjt.dungeons.server.observer;

import bg.sofia.uni.fmi.mjt.dungeons.server.entity.player.Player;

import java.util.LinkedList;
import java.util.List;

public class PlayerDiedObserver {
    List<PlayerDiedListener> listeners;

    public PlayerDiedObserver() {
        this.listeners = new LinkedList<>();
    }

    public void subscribe(PlayerDiedListener listener) {
        if (listener == null) {
            throw new IllegalArgumentException("Listener cannot be null");
        }

        listeners.add(listener);
    }

    public void notifyListeners(Player player) {
        if (player == null) {
            throw new IllegalArgumentException("Player cannot be null");
        }

        for (PlayerDiedListener listener : listeners) {
            listener.onPlayerDied(player);
        }
    }
}
