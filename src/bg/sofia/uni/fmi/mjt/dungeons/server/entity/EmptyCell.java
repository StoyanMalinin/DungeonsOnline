package bg.sofia.uni.fmi.mjt.dungeons.server.entity;

import bg.sofia.uni.fmi.mjt.dungeons.server.interaction.EmptyInteractionChoice;
import bg.sofia.uni.fmi.mjt.dungeons.server.interaction.InteractionChoice;
import bg.sofia.uni.fmi.mjt.dungeons.server.interaction.InteractionNegotiator;
import bg.sofia.uni.fmi.mjt.dungeons.server.map.Position;

public class EmptyCell implements GridEntity {
    private Position position;

    public EmptyCell(Position position) {
        this.position = position;
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
        return '.';
    }

    @Override
    public boolean isFree() {
        return true;
    }

    @Override
    public InteractionChoice getInteractionChoice(GameEntity other) {
        return new EmptyInteractionChoice();
    }

    @Override
    public void negotiateForInteractions(InteractionNegotiator negotiator) {

    }

    @Override
    public String getName() {
        return "EmptyCell(" + position.row() + ", " + position.col()+ ")";
    }
}
