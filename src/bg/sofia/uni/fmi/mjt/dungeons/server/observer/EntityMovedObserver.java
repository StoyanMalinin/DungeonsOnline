package bg.sofia.uni.fmi.mjt.dungeons.server.observer;

import bg.sofia.uni.fmi.mjt.dungeons.server.entity.MovableEntity;
import bg.sofia.uni.fmi.mjt.dungeons.server.map.Position;

import java.util.LinkedList;
import java.util.List;

public class EntityMovedObserver {

    List<EntityMovedListener> listeners;

    public EntityMovedObserver() {
        this.listeners = new LinkedList<>();
    }

    public void subscribe(EntityMovedListener listener) {
        if (listener == null) {
            throw new IllegalArgumentException("Listener cannot be null");
        }

        listeners.add(listener);
    }

    public void notifyListeners(MovableEntity entity, Position oldPosition, Position newPosition) {
        if (entity == null) {
            throw new IllegalArgumentException("Entity cannot be null");
        }
        if (oldPosition == null) {
            throw new IllegalArgumentException("OldPosition cannot be null");
        }
        if (newPosition == null) {
            throw new IllegalArgumentException("NewPosition cannot be null");
        }

        for (EntityMovedListener listener : listeners) {
            listener.onEntityMoved(entity, oldPosition, newPosition);
        }
    }
}
