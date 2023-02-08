package bg.sofia.uni.fmi.mjt.dungeons.server.observer;

import bg.sofia.uni.fmi.mjt.dungeons.server.entity.Monster;
import bg.sofia.uni.fmi.mjt.dungeons.server.entity.player.Player;

import java.util.LinkedList;
import java.util.List;

public class MonsterDiedObserver {
    List<MonsterDiedListener> listeners;

    public MonsterDiedObserver() {
        this.listeners = new LinkedList<>();
    }

    public void subscribe(MonsterDiedListener listener) {
        if (listener == null) {
            throw new IllegalArgumentException("Listener cannot be null");
        }

        listeners.add(listener);
    }

    public void notifyListeners(Monster monster) {
        if (monster == null) {
            throw new IllegalArgumentException("Monster cannot be null");
        }

        for (MonsterDiedListener listener : listeners) {
            listener.onMonsterDied(monster);
        }
    }
}
