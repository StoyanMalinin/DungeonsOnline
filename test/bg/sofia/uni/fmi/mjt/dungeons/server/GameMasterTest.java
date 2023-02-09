package bg.sofia.uni.fmi.mjt.dungeons.server;

import bg.sofia.uni.fmi.mjt.dungeons.common.PlayerId;
import bg.sofia.uni.fmi.mjt.dungeons.server.entity.player.Player;
import bg.sofia.uni.fmi.mjt.dungeons.server.exception.NoAvailableMapPositionsException;
import bg.sofia.uni.fmi.mjt.dungeons.server.exception.NoAvailablePlayersException;
import bg.sofia.uni.fmi.mjt.dungeons.server.exception.PlayerNotActiveException;
import bg.sofia.uni.fmi.mjt.dungeons.server.map.Direction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameMasterTest {
    @Test
    void testRegisterPlayerSuccessful() {
        GameMaster gameMaster = new GameMaster(5, 5, 1, 1);

        assertDoesNotThrow(() -> {
            gameMaster.registerPlayer();
        }, "Registering player with enough space should not throw exceptions");
    }

    @Test
    void testRegisterPlayerTooManyAddedPlayers() {
        GameMaster gameMaster = new GameMaster(5, 5, 1, 1);

        for (int i = 0; i < 9; i++) {
            assertDoesNotThrow(() -> {
                gameMaster.registerPlayer();
            }, "Registering player with enough space should not throw exceptions");
        }

        assertThrows(NoAvailablePlayersException.class, () -> {
            gameMaster.registerPlayer();
        }, "Trying to register 10th player must throw an exception");
    }

    @Test
    void testRegisterPlayerTooNoFreeSpace() {
        GameMaster gameMaster = new GameMaster(1, 0, 1, 0);

        assertThrows(NoAvailableMapPositionsException.class, () -> {
            gameMaster.registerPlayer();
        }, "Trying to register player at a full map must throw an exception");
    }

    @Test
    void testUnregisterPlayerSuccessful() {
        GameMaster gameMaster = new GameMaster(5, 1, 1, 1);

        Player player = null;
        try {
            player = gameMaster.registerPlayer();
        } catch (Exception e) {
            fail();
        }

        PlayerId id = player.getId();
        assertDoesNotThrow(() -> {
            gameMaster.unregisterPlayer(id);
        }, "Unregistering a registerd player should not throw an exception");
    }

    @Test
    void testUnregisterPlayerUnSuccessfulNotRegistered() {
        GameMaster gameMaster = new GameMaster(5, 1, 1, 1);

        Player player = null;
        try {
            player = gameMaster.registerPlayer();
        } catch (Exception e) {
            fail();
        }

        PlayerId id = player.getId();
        final PlayerId fakeId = id.toChar() == '1' ? new PlayerId(2) : new PlayerId(1);

        assertDoesNotThrow(() -> {
            gameMaster.unregisterPlayer(fakeId);
        }, "Unregistering a player that is not registered should not throw");
    }

    @Test
    void testUnregisterPlayerUnSuccessfulNull() {
        GameMaster gameMaster = new GameMaster(5, 1, 1, 1);

        assertThrows(IllegalArgumentException.class, () -> {
            gameMaster.unregisterPlayer(null);
        }, "Unregistering a player that is not registered should not throw");
    }

    @Test
    void testMovePlayerLeft() {
        GameMaster gameMaster = new GameMaster(5, 0, 0, 0);

        Player player = null;
        try {
            player = gameMaster.registerPlayer();
        } catch (Exception e) {
            fail();
        }

        PlayerId id = player.getId();
        assertDoesNotThrow(() -> {
            gameMaster.movePlayer(id, Direction.LEFT);
        }, "Moving an existing player should not throw an exception");
    }

    @Test
    void testMovePlayerRight() {
        GameMaster gameMaster = new GameMaster(5, 0, 0, 0);

        Player player = null;
        try {
            player = gameMaster.registerPlayer();
        } catch (Exception e) {
            fail();
        }

        PlayerId id = player.getId();
        assertDoesNotThrow(() -> {
            gameMaster.movePlayer(id, Direction.RIGHT);
        }, "Moving an existing player should not throw an exception");
    }

    @Test
    void testMovePlayerUnregistered() {
        GameMaster gameMaster = new GameMaster(5, 1, 1, 1);

        Player player = null;
        try {
            player = gameMaster.registerPlayer();
        } catch (Exception e) {
            fail();
        }

        PlayerId id = player.getId();
        PlayerId fakeId = id.toChar() == '1' ? new PlayerId(2) : new PlayerId(1);

        assertThrows(PlayerNotActiveException.class, () -> {
            gameMaster.movePlayer(fakeId, Direction.LEFT);
        }, "Moving an unregistered player must throw an exception");
    }

    @Test
    void testMovePlayerNullId() {
        GameMaster gameMaster = new GameMaster(5, 1, 1, 1);

        assertThrows(IllegalArgumentException.class, () -> {
            gameMaster.movePlayer(null, Direction.LEFT);
        });
    }

    @Test
    void testMovePlayerNullDirection() {
        GameMaster gameMaster = new GameMaster(5, 1, 1, 1);

        assertThrows(IllegalArgumentException.class, () -> {
            gameMaster.movePlayer(new PlayerId(1), null);
        });
    }
}
