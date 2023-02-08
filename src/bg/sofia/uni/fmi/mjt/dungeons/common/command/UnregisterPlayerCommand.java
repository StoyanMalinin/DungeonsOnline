package bg.sofia.uni.fmi.mjt.dungeons.common.command;

import bg.sofia.uni.fmi.mjt.dungeons.common.PlayerId;
import bg.sofia.uni.fmi.mjt.dungeons.server.GameMaster;
import bg.sofia.uni.fmi.mjt.dungeons.server.exception.ServerLogicException;
import bg.sofia.uni.fmi.mjt.dungeons.server.exception.UnexpectedServerLogicException;

public class UnregisterPlayerCommand implements CommandToServer {

    private PlayerId id;

    public UnregisterPlayerCommand(PlayerId id) {
        if (id == null) {
            throw new IllegalArgumentException("Id cannot be null");
        }

        this.id = id;
    }

    @Override
    public void execute(GameMaster gameMaster) throws ServerLogicException, UnexpectedServerLogicException {
        gameMaster.unregisterPlayer(id);
    }
}
