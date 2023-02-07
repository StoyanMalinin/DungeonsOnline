package bg.sofia.uni.fmi.mjt.dungeons.server;

import bg.sofia.uni.fmi.mjt.dungeons.server.entity.MovableEntity;
import bg.sofia.uni.fmi.mjt.dungeons.server.map.Position;
import bg.sofia.uni.fmi.mjt.dungeons.server.observer.EntityMovedListener;

public class GameMaster implements EntityMovedListener {
    @Override
    public synchronized void onEntityMoved(MovableEntity entity, Position oldPosition, Position newPosition) {
        //todo
    }
}
