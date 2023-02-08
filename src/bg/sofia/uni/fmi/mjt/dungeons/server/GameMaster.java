package bg.sofia.uni.fmi.mjt.dungeons.server;

import bg.sofia.uni.fmi.mjt.dungeons.common.PlayerId;
import bg.sofia.uni.fmi.mjt.dungeons.server.entity.GridEntity;
import bg.sofia.uni.fmi.mjt.dungeons.server.entity.controller.MapMoveController;
import bg.sofia.uni.fmi.mjt.dungeons.server.entity.player.Player;
import bg.sofia.uni.fmi.mjt.dungeons.server.entity.player.PlayerState;
import bg.sofia.uni.fmi.mjt.dungeons.server.exception.*;
import bg.sofia.uni.fmi.mjt.dungeons.server.interaction.Interaction;
import bg.sofia.uni.fmi.mjt.dungeons.server.map.Direction;
import bg.sofia.uni.fmi.mjt.dungeons.server.map.GameMap;
import bg.sofia.uni.fmi.mjt.dungeons.server.map.GameMapView;
import bg.sofia.uni.fmi.mjt.dungeons.server.map.Position;

import java.util.*;

public class GameMaster {

    private Map<Player, Interaction> playerInteraction;
    private Map<PlayerId, Player> id2Player;
    private Set<Player> activePlayers;
    private Stack<PlayerId> availablePlayerIds;
    private GameMap gameMap;

    public GameMaster() {
        final int mapSize = 7;
        final int playerCnt = 9;

        this.availablePlayerIds = new Stack<>();
        this.gameMap = new GameMap(mapSize, mapSize);
        this.activePlayers = new HashSet<>();
        this.id2Player = new HashMap<>();
        this.playerInteraction = new HashMap<>();

        for (int p = playerCnt; p >= 1; p--) {
            availablePlayerIds.push(new PlayerId(p));
        }
    }

    public void movePlayer(PlayerId id, Direction direction) throws PlayerNotActiveException, UnexpectedServerLogicException {
        if (id == null) {
            throw new IllegalArgumentException("Id cannot be null");
        }
        if (direction == null) {
            throw new IllegalArgumentException("Direction cannot be null");
        }

        Player player = id2Player.get(id);
        if (player == null) {
            throw new PlayerNotActiveException("Player with this id is not active, probably a client or network error");
        }

        if (player.canMove(direction) == false) {
            player.setState(getPlayerState(id, "You cannot move in this direction"));
            return;
        }

        try {
            player.move(direction);
        } catch (NotAllowedToMoveException e) {
            throw new UnexpectedServerLogicException("Internal logic error");
        }

        notifyAllActivePlayersToUpdateState();
    }

    public Player registerPlayer() throws NoAvailablePlayersException, NoAvailableMapPositionsException {
        if (availablePlayerIds.empty() == true) {
            throw new NoAvailablePlayersException("No available players");
        }

        Position playerPosition = null;
        PlayerId id = availablePlayerIds.pop();

        for (int r = 0; r < gameMap.getRowCnt(); r++) {
            for (int c = 0; c < gameMap.getColCnt(); c++) {
                Collection<GridEntity> entitiesAtPosition = getAt(r, c);

                if (entitiesAtPosition.stream().allMatch(e -> e.isFree() == true && e.canEnter() == true) == true) {
                    playerPosition = new Position(r, c);
                    break;
                }
            }

            if (playerPosition != null) {
                break;
            }
        }

        if (playerPosition == null) {
            throw new NoAvailableMapPositionsException("There is no available position to locate the player currently");
        }

        Player player = new Player(id, playerPosition, new MapMoveController(gameMap));

        id2Player.put(id, player);
        activePlayers.add(player);
        player.setState(getPlayerState(player.getId()));

        return player;
    }

    public List<GridEntity> getAt(int r, int c) {
        if (gameMap.isInside(r, c) == false) {
            throw new IllegalArgumentException("The requested position must be inside the grid");
        }

        List<GridEntity> entitiesOnPosition = new LinkedList<>();

        entitiesOnPosition.add(gameMap.getAt(r, c));
        for (Player player : activePlayers) {
            if (player.getPosition().equals(new Position(r, c)) == true) {
                entitiesOnPosition.add(player);
            }
        }

        return entitiesOnPosition;
    }

    public List<GridEntity> getAt(Position position) {
        if (position == null) {
            throw new IllegalArgumentException("Position cannot be null");
        }

        return getAt(position.row(), position.col());
    }

    private void notifyAllActivePlayersToUpdateState() {
        for (Player player : activePlayers) {
            player.setState(getPlayerState(player.getId()));
        }
    }

    private PlayerState getPlayerState(PlayerId id) {
        return getPlayerState(id, null);
    }

    private PlayerState getPlayerState(PlayerId id, String errorMessage) {
        Player player = id2Player.get(id);
        PlayerState.PlayerStateBuilder builder = PlayerState.builder(player).setGameMapView(new GameMapView(this));

        if (playerInteraction.containsKey(player) == true) {
            builder.setInteraction(playerInteraction.get(player));
        }

        if (errorMessage != null) {
            builder.setErrorMessage(errorMessage);
        }

        return builder.build();
    }

    public void unregisterPlayer(PlayerId id) {
        if (id == null) {
            throw new IllegalArgumentException("Id cannot be null");
        }

        if (id2Player.containsKey(id) == false) {
            return;
        }

        Player player = id2Player.get(id);

        id2Player.remove(id);
        activePlayers.remove(player);
        playerInteraction.remove(player);
        availablePlayerIds.push(id);

        notifyAllActivePlayersToUpdateState();
    }

    public void refresh() {
        notifyAllActivePlayersToUpdateState();
    }

    public GameMap getGameMap() {
        return gameMap;
    }
}
