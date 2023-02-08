package bg.sofia.uni.fmi.mjt.dungeons.server.entity;

public interface FightableEntity extends GameEntity {
    double attack();
    void takeDamage(double damage, FightableEntity attacker);
    boolean isAlive();
}
