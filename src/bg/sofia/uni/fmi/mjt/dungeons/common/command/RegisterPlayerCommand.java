package bg.sofia.uni.fmi.mjt.dungeons.common.command;

import bg.sofia.uni.fmi.mjt.dungeons.server.GameMaster;
import bg.sofia.uni.fmi.mjt.dungeons.server.entity.player.Player;

public class RegisterPlayerCommand implements ProducerCommandToServer<Player> {
    @Override
    public Player execute(GameMaster gameMaster) {
        return null;
    }
}
