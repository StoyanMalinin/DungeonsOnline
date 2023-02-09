package bg.sofia.uni.fmi.mjt.dungeons.server.entity;

public interface FightableEntity extends GameEntity {
    double attack();
    boolean takeDamage(double damage, FightableEntity attacker);
    boolean isAlive();
    void onVictimDied(FightableEntity victim);
    int getXPForKilling();
}
