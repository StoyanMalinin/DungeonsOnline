package bg.sofia.uni.fmi.mjt.dungeons.server.observer;

import bg.sofia.uni.fmi.mjt.dungeons.server.entity.MovableEntity;
import bg.sofia.uni.fmi.mjt.dungeons.server.map.Position;

public interface EntityMovedListener {
    void onEntityMoved(MovableEntity entity, Position oldPosition, Position newPosition);
}
