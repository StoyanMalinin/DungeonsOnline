package bg.sofia.uni.fmi.mjt.dungeons.common.command;

import bg.sofia.uni.fmi.mjt.dungeons.common.PlayerId;
import bg.sofia.uni.fmi.mjt.dungeons.server.GameMaster;
import bg.sofia.uni.fmi.mjt.dungeons.server.exception.ServerLogicException;
import bg.sofia.uni.fmi.mjt.dungeons.server.exception.UnexpectedServerLogicException;

public class PerformInteractionFromChoiceCommand implements CommandToServer {
    private PlayerId playerId;
    private int choiceInd;

    public PerformInteractionFromChoiceCommand(PlayerId playerId, int choiceInd) {
        if (playerId == null) {
            throw new IllegalArgumentException("PlayerId cannot be null");
        }

        this.playerId = playerId;
        this.choiceInd = choiceInd;
    }

    @Override
    public void execute(GameMaster gameMaster) throws ServerLogicException, UnexpectedServerLogicException {
        gameMaster.performChoiceInd(playerId, choiceInd);
    }
}
