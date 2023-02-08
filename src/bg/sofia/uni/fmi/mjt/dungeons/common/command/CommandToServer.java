package bg.sofia.uni.fmi.mjt.dungeons.common.command;

import bg.sofia.uni.fmi.mjt.dungeons.server.GameMaster;
import bg.sofia.uni.fmi.mjt.dungeons.server.exception.ServerLogicException;
import bg.sofia.uni.fmi.mjt.dungeons.server.exception.UnexpectedServerLogicException;

import java.io.Serializable;

public interface CommandToServer extends Serializable {
    void execute(GameMaster gameMaster) throws ServerLogicException, UnexpectedServerLogicException;
}
