package bg.sofia.uni.fmi.mjt.dungeons.server.observer;

import bg.sofia.uni.fmi.mjt.dungeons.server.entity.Treasure;

import java.util.LinkedList;
import java.util.List;

public class TreasureTakenObserver {
    List<TreasureTakenListener> listeners;

    public TreasureTakenObserver() {
        this.listeners = new LinkedList<>();
    }

    public void subscribe(TreasureTakenListener listener) {
        if (listener == null) {
            throw new IllegalArgumentException("Listener cannot be null");
        }

        listeners.add(listener);
    }

    public void notifyListeners(Treasure treasure) {
        if (treasure == null) {
            throw new IllegalArgumentException("Treasure cannot be null");
        }

        for (TreasureTakenListener listener : listeners) {
            listener.onTreasureTaken(treasure);
        }
    }
}
