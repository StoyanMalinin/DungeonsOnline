package bg.sofia.uni.fmi.mjt.dungeons.server.entity;

import bg.sofia.uni.fmi.mjt.dungeons.server.interaction.EmptyInteractionChoice;
import bg.sofia.uni.fmi.mjt.dungeons.server.interaction.InteractionChoice;
import bg.sofia.uni.fmi.mjt.dungeons.server.interaction.InteractionNegotiator;
import bg.sofia.uni.fmi.mjt.dungeons.server.map.Position;
import bg.sofia.uni.fmi.mjt.dungeons.server.observer.*;

import javax.swing.plaf.basic.BasicSplitPaneUI;

public class Monster implements GridEntity, FightableEntity {

    private int level;
    private int id;
    private double health;
    private Position position;

    private EntityMovedObserver entityMovedObserver;
    private MonsterDiedObserver monsterDiedObserver;
    private MonsterAttackedObserver monsterAttackedObserver;


    private static final int BASE_HEALTH = 100;
    private static final int HEALTH_LEVEL_MULTIPLIER = 10;
    private static final int BASE_ATTACK = 50;
    private static final int ATTACK_LEVEL_MULTIPLIER = 10;
    private static final int LEVEL_OFFSET = 1;
    private static final int DEFENSE = 10;
    private static final double EPS = 0.0001;


    public Monster(int id, int level, Position position) {
        if (level <= 0) {
            throw new IllegalArgumentException("Level must be positive");
        }
        if (position == null) {
            throw new IllegalArgumentException("Position cannot be null");
        }

        this.id = id;
        this.level = level;
        this.position = position;
        this.health = BASE_HEALTH + (level - 1) * HEALTH_LEVEL_MULTIPLIER;

        this.entityMovedObserver = new EntityMovedObserver();
        this.monsterDiedObserver = new MonsterDiedObserver();
        this.monsterAttackedObserver = new MonsterAttackedObserver();
    }

    public EntityMovedObserver getEntityMovedObserver() {
        return entityMovedObserver;
    }

    public MonsterDiedObserver getMonsterDiedObserver() {
        return monsterDiedObserver;
    }

    public MonsterAttackedObserver getMonsterAttackedObserver() {
        return monsterAttackedObserver;
    }

    @Override
    public double attack() {
        return BASE_ATTACK + (level - LEVEL_OFFSET) * ATTACK_LEVEL_MULTIPLIER;
    }

    @Override
    public void takeDamage(double damage, FightableEntity attacker) {
        health = health - Math.max(0, damage - DEFENSE);

        if (isAlive() == false) {
            monsterDiedObserver.notifyListeners(this);
        }

        monsterAttackedObserver.notifyListeners(attacker, this);
    }

    @Override
    public boolean isAlive() {
        return health > EPS;
    }

    @Override
    public String getName() {
        return "Monster(" + id + ")";
    }

    @Override
    public InteractionChoice getInteractionChoice(GameEntity other) {
        return new EmptyInteractionChoice();
    }

    @Override
    public void negotiateForInteractions(InteractionNegotiator negotiator) {
        negotiator.setSubjectOfAttackInteraction(this);
    }

    @Override
    public Position getPosition() {
        return position;
    }

    @Override
    public boolean canEnter() {
        return true;
    }

    @Override
    public char consoleSymbol() {
        return 'M';
    }

    @Override
    public boolean isFree() {
        return false;
    }

    public void setPosition(Position position) {
        if (position == null) {
            throw new IllegalArgumentException("Position cannot be null");
        }

        this.position = position;
    }

    public void ressurrect() {
        this.health = BASE_HEALTH + (level - 1) * HEALTH_LEVEL_MULTIPLIER;
    }
}
