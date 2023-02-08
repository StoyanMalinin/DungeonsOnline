package bg.sofia.uni.fmi.mjt.dungeons.server.interaction;

import bg.sofia.uni.fmi.mjt.dungeons.server.entity.FightableEntity;

import java.awt.image.AreaAveragingScaleFilter;
import java.util.ArrayList;
import java.util.List;

public class InteractionNegotiator {
    private AttackInteraction attackInteraction;

    public InteractionNegotiator() {
        this.attackInteraction = new AttackInteraction();
    }

    public void setInitiatorOfAttackInteraction(FightableEntity entity) {
        attackInteraction.setInitiator(entity);
    }

    public void setSubjectOfAttackInteraction(FightableEntity entity) {
        attackInteraction.setSubject(entity);
    }

    public List<InteractionWithOne> getCompleteInteractions() {
        List<InteractionWithOne> res = new ArrayList<>();

        if (attackInteraction.isComplete() == true) {
            res.add(attackInteraction);
        }

        return res;
    }
}
