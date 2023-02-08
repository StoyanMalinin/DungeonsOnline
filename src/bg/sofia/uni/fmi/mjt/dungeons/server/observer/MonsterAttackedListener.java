package bg.sofia.uni.fmi.mjt.dungeons.server.observer;

import bg.sofia.uni.fmi.mjt.dungeons.server.entity.FightableEntity;
import bg.sofia.uni.fmi.mjt.dungeons.server.entity.Monster;

public interface MonsterAttackedListener {
    void onMonsterAttacked(FightableEntity attacker, Monster monster);
}
