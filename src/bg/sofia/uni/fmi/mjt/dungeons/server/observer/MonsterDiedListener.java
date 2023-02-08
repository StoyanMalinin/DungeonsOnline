package bg.sofia.uni.fmi.mjt.dungeons.server.observer;

import bg.sofia.uni.fmi.mjt.dungeons.server.entity.Monster;

public interface MonsterDiedListener {
    void onMonsterDied(Monster monster);
}
