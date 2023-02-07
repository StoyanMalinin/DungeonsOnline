package bg.sofia.uni.fmi.mjt.dungeons.common.item;

import bg.sofia.uni.fmi.mjt.dungeons.common.Stats;

public interface Item {
    int getLevel();
    String getName();

    Stats affectStats(Stats s);
}
