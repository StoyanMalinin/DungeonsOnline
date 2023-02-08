package bg.sofia.uni.fmi.mjt.dungeons.server.map;

import bg.sofia.uni.fmi.mjt.dungeons.server.GameMaster;
import bg.sofia.uni.fmi.mjt.dungeons.server.entity.GridEntity;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

public class GameMapView implements Serializable {
    private String view;

    public GameMapView(GameMaster gameMaster) {
        if (gameMaster == null) {
            throw new IllegalArgumentException("GameMaster cannot be null");
        }

        this.view = makeString(gameMaster);
    }

    private String makeString(GameMaster gameMaster) {
        StringBuilder builder = new StringBuilder();

        for (int r = 0; r < gameMaster.getGameMap().getRowCnt(); r++) {
            for (int c = 0; c < gameMaster.getGameMap().getColCnt(); c++ ) {
                List<GridEntity> entites = gameMaster.getAt(r, c);
                builder.append(entites.get(entites.size() - 1).consoleSymbol());
            }
            builder.append(System.lineSeparator());
        }

        return builder.toString();
    }

    @Override
    public String toString() {
        return view;
    }
}
