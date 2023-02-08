package bg.sofia.uni.fmi.mjt.dungeons.server.observer;

import bg.sofia.uni.fmi.mjt.dungeons.server.entity.FightableEntity;
import bg.sofia.uni.fmi.mjt.dungeons.server.entity.Monster;

import java.util.LinkedList;
import java.util.List;

public class MonsterAttackedObserver {
    List<MonsterAttackedListener> listeners;

    public MonsterAttackedObserver() {
        this.listeners = new LinkedList<>();
    }

    public void subscribe(MonsterAttackedListener listener) {
        if (listener == null) {
            throw new IllegalArgumentException("Listener cannot be null");
        }

        listeners.add(listener);
    }

    public void notifyListeners(FightableEntity attacker, Monster monster) {
        if (monster == null) {
            throw new IllegalArgumentException("Monster cannot be null");
        }

        for (MonsterAttackedListener listener : listeners) {
            listener.onMonsterAttacked(attacker, monster);
        }
    }
}
