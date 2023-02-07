package bg.sofia.uni.fmi.mjt.dungeons.common.command;

import bg.sofia.uni.fmi.mjt.dungeons.server.GameMaster;

public interface CommandToServer {
    void execute(GameMaster gameMaster);
}
