package bg.sofia.uni.fmi.mjt.dungeons.server.entity;

import bg.sofia.uni.fmi.mjt.dungeons.server.interaction.EmptyInteractionChoice;
import bg.sofia.uni.fmi.mjt.dungeons.server.interaction.InteractionChoice;
import bg.sofia.uni.fmi.mjt.dungeons.server.interaction.InteractionNegotiator;
import bg.sofia.uni.fmi.mjt.dungeons.server.map.Position;

public class WallCell implements GridEntity {
    private Position position;

    public WallCell(Position position) {
        this.position = position;
    }

    @Override
    public Position getPosition() {
        return null;
    }

    @Override
    public boolean canEnter() {
        return false;
    }

    @Override
    public char consoleSymbol() {
        return '#';
    }

    @Override
    public boolean isFree() {
        return false;
    }

    @Override
    public String getName() {
        return "WallCell(" + position.row() + ", " + position.col()+ ")";
    }

    @Override
    public InteractionChoice getInteractionChoice(GameEntity other) {
        return new EmptyInteractionChoice();
    }

    @Override
    public void negotiateForInteractions(InteractionNegotiator negotiator) {

    }
}
