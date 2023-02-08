package bg.sofia.uni.fmi.mjt.dungeons.server.interaction;

import bg.sofia.uni.fmi.mjt.dungeons.server.GameMaster;
import bg.sofia.uni.fmi.mjt.dungeons.server.entity.FightableEntity;
import bg.sofia.uni.fmi.mjt.dungeons.server.entity.GameEntity;

public class AttackInteraction implements InteractionWithOne {
    private GameMaster gameMaster;
    private FightableEntity initiator;
    private FightableEntity subject;

    public AttackInteraction() {
        this.initiator = null;
        this.subject = null;
    }

    @Override
    public GameEntity getInitiator() {
        return initiator;
    }

    @Override
    public GameEntity getSubject() {
        return subject;
    }

    @Override
    public boolean isComplete() {
        return initiator != null && subject != null;
    }

    @Override
    public void execute(GameMaster gameMaster) {
        if (isComplete() == false) {
            return;
        }

        gameMaster.attack(initiator, subject);
    }

    public void setInitiator(FightableEntity initiator) {
        if (initiator == null) {
            throw new IllegalArgumentException("Initiator cannot be null");
        }

        this.initiator = initiator;
    }

    public void setSubject(FightableEntity subject) {
        if (subject == null) {
            throw new IllegalArgumentException("Subject cannot be null");
        }

        this.subject = subject;
    }

    @Override
    public String toString() {
        return "Attack " + subject.getName();
    }
}
