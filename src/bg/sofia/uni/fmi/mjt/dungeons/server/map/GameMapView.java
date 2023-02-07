package bg.sofia.uni.fmi.mjt.dungeons.server.map;

import bg.sofia.uni.fmi.mjt.dungeons.server.GameMaster;
import bg.sofia.uni.fmi.mjt.dungeons.server.entity.GridEntity;

public class GameMapView {
    private GameMap gameMap;

    public GameMapView(GameMap gameMap) {
        if (gameMap == null) {
            throw new IllegalArgumentException("GameMap cannot be null");
        }

        this.gameMap = gameMap;
    }

    GridEntity getAt(int r, int c) {
        return gameMap.getAt(r, c);
    }

    GridEntity getAt(Position position) {
        return gameMap.getAt(position);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        for (int r = 0; r < gameMap.getRowCnt(); r++) {
            for (int c = 0; c < gameMap.getColCnt(); c++ ) {
                builder.append(gameMap.getAt(r, c).consoleSymbol());
            }
            builder.append(System.lineSeparator());
        }

        return builder.toString();
    }
}
