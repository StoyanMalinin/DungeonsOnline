package bg.sofia.uni.fmi.mjt.dungeons.common.command;

import bg.sofia.uni.fmi.mjt.dungeons.common.PlayerId;
import bg.sofia.uni.fmi.mjt.dungeons.server.GameMaster;
import bg.sofia.uni.fmi.mjt.dungeons.server.entity.player.Player;
import bg.sofia.uni.fmi.mjt.dungeons.server.exception.NotAllowedToMoveException;
import bg.sofia.uni.fmi.mjt.dungeons.server.exception.PlayerNotActiveException;
import bg.sofia.uni.fmi.mjt.dungeons.server.exception.ServerLogicException;
import bg.sofia.uni.fmi.mjt.dungeons.server.exception.UnexpectedServerLogicException;
import bg.sofia.uni.fmi.mjt.dungeons.server.map.Direction;

public class MoveCommand implements CommandToServer {
    private PlayerId id;
    private Direction direction;

    public MoveCommand(PlayerId id, Direction direction) {
        if (direction == null) {
            throw new IllegalArgumentException("Direction cannot be null");
        }
        if (id == null) {
            throw new IllegalArgumentException("Id cannot be null");
        }

        this.id = id;
        this.direction = direction;
    }

    @Override
    public void execute(GameMaster gameMaster) throws ServerLogicException, UnexpectedServerLogicException {

        try {
            gameMaster.movePlayer(id, direction);
        } catch (PlayerNotActiveException e) {
            throw new ServerLogicException("The player with the id given is not active. " +
                    "Probably a client error. More info: " + e.getMessage());
        }
    }
}
