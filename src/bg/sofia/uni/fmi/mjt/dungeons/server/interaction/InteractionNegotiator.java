package bg.sofia.uni.fmi.mjt.dungeons.server.interaction;

import bg.sofia.uni.fmi.mjt.dungeons.server.entity.FightableEntity;
import bg.sofia.uni.fmi.mjt.dungeons.server.entity.ItemGivingEntity;

import java.awt.image.AreaAveragingScaleFilter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class InteractionNegotiator {
    private AttackInteraction attackInteraction;
    private List<OfferItemInteraction> offerItemInteractions;
    private ReceiverAcceptItemInteraction receiverAcceptItemInteraction;
    private List<InteractionWithZero> interactionsWithZero;


    private InteractionNegotiator(InteractionNegotiatorBuilder builder) {
        this.attackInteraction = builder.attackInteraction;
        this.offerItemInteractions = builder.offerItemInteractions;
        this.receiverAcceptItemInteraction = builder.receiverAcceptItemInteraction;
        this.interactionsWithZero = builder.interactionsWithZero;
    }

    public static class InteractionNegotiatorBuilder {
        private AttackInteraction attackInteraction;
        private List<OfferItemInteraction> offerItemInteractions;
        private ReceiverAcceptItemInteraction receiverAcceptItemInteraction;
        private List<InteractionWithZero> interactionsWithZero;


        private InteractionNegotiatorBuilder() {
            this.attackInteraction = null;
            this.offerItemInteractions = new LinkedList<>();
            this.receiverAcceptItemInteraction = null;
            this.interactionsWithZero = new LinkedList<>();
        }

        public InteractionNegotiatorBuilder setAttackInteraction(AttackInteraction attackInteraction) {
            this.attackInteraction = attackInteraction;
            return this;
        }

        public InteractionNegotiatorBuilder setOfferItemInteraction(List<OfferItemInteraction> offerItemInteractions) {
            if (offerItemInteractions == null) {
                throw new IllegalArgumentException("OfferItemInteractions cannot be null");
            }

            this.offerItemInteractions = offerItemInteractions;
            return this;
        }

        public InteractionNegotiatorBuilder setReceiverAcceptItemInteraction(
                ReceiverAcceptItemInteraction receiverAcceptItemInteraction) {
            this.receiverAcceptItemInteraction = receiverAcceptItemInteraction;
            return this;
        }

        public InteractionNegotiatorBuilder setInteractionsWithZero(List<InteractionWithZero> interactionsWithZero) {
            if (interactionsWithZero == null) {
                throw new IllegalArgumentException("Interctions with zero cannot be null");
            }

            this.interactionsWithZero = interactionsWithZero;
            return this;
        }

        public InteractionNegotiator build() {
            return new InteractionNegotiator(this);
        }
    }

    public static InteractionNegotiatorBuilder builder() {
        return new InteractionNegotiatorBuilder();
    }

    public List<Interaction> getCompleteInteractions() {
        List<Interaction> res = new ArrayList<>();

        if (attackInteraction != null && attackInteraction.isComplete() == true) {
            res.add(attackInteraction);
        }

        for (OfferItemInteraction interaction : offerItemInteractions) {
            if (interaction.isComplete() == true) {
                res.add(interaction);
            }
        }

        if (receiverAcceptItemInteraction != null
                && receiverAcceptItemInteraction.isComplete() == true
                && receiverAcceptItemInteraction.isValid() == true) {
            res.add(receiverAcceptItemInteraction);
        }

        for (InteractionWithZero interaction : interactionsWithZero) {
            if (interaction != null && interaction.isValid() == true) {
                res.add(interaction);
            }
        }

        return res;
    }

    public AttackInteraction getAttackInteraction() {
        return attackInteraction;
    }

    public List<OfferItemInteraction> getOfferItemInteractions() {
        return offerItemInteractions;
    }

    public ReceiverAcceptItemInteraction getReceiverAcceptItemInteraction() {
        return receiverAcceptItemInteraction;
    }

    public List<InteractionWithZero> getInteractionsWithZero() {
        return interactionsWithZero;
    }
}
