package bg.sofia.uni.fmi.mjt.dungeons.server;

import bg.sofia.uni.fmi.mjt.dungeons.client.DefaultLogger;
import bg.sofia.uni.fmi.mjt.dungeons.common.PlayerId;
import bg.sofia.uni.fmi.mjt.dungeons.common.command.CommandToServer;
import bg.sofia.uni.fmi.mjt.dungeons.common.command.ProducerCommandToServer;
import bg.sofia.uni.fmi.mjt.dungeons.server.entity.player.Player;
import bg.sofia.uni.fmi.mjt.dungeons.server.exception.NoAvailableMapPositionsException;
import bg.sofia.uni.fmi.mjt.dungeons.server.exception.NoAvailablePlayersException;
import bg.sofia.uni.fmi.mjt.dungeons.server.exception.ServerLogicException;
import bg.sofia.uni.fmi.mjt.dungeons.server.exception.UnexpectedServerLogicException;

public class CommandExecutor {
    private GameMaster gameMaster;

    public CommandExecutor(GameMaster gameMaster) {
        if (gameMaster == null) {
            throw new IllegalArgumentException("GameMaster cannot be null");
        }

        this.gameMaster = gameMaster;
    }

    public synchronized void executeCommand(CommandToServer command) {
        if (command == null) {
            throw new IllegalArgumentException("Command cannot be null");
        }

        try {
            command.execute(gameMaster);
        } catch (ServerLogicException e) {
            DefaultLogger.logException("A safe error has occurred on the server", e);
        } catch (UnexpectedServerLogicException e) {
            DefaultLogger.logException("An unsafe error has occurred on the server", e);
        } catch (Exception e) {
            DefaultLogger.logException("An unexpected error has occurred on the server", e);
        }
    }

    public synchronized Player registerPlayer() throws NoAvailableMapPositionsException, NoAvailablePlayersException {
        return gameMaster.registerPlayer();
    }

    public synchronized void refreshGameMaster() {
        gameMaster.refresh();
    }
}
