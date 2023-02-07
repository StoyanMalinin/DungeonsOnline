package bg.sofia.uni.fmi.mjt.dungeons.server;

import bg.sofia.uni.fmi.mjt.dungeons.common.PlayerId;
import bg.sofia.uni.fmi.mjt.dungeons.server.entity.GridEntity;
import bg.sofia.uni.fmi.mjt.dungeons.server.entity.MovableEntity;
import bg.sofia.uni.fmi.mjt.dungeons.server.entity.controller.MapMoveController;
import bg.sofia.uni.fmi.mjt.dungeons.server.entity.player.Player;
import bg.sofia.uni.fmi.mjt.dungeons.server.entity.player.PlayerState;
import bg.sofia.uni.fmi.mjt.dungeons.server.exception.NoAvailableMapPositionsException;
import bg.sofia.uni.fmi.mjt.dungeons.server.exception.NoAvailablePlayersException;
import bg.sofia.uni.fmi.mjt.dungeons.server.interaction.Interaction;
import bg.sofia.uni.fmi.mjt.dungeons.server.map.GameMap;
import bg.sofia.uni.fmi.mjt.dungeons.server.map.GameMapView;
import bg.sofia.uni.fmi.mjt.dungeons.server.map.Position;
import bg.sofia.uni.fmi.mjt.dungeons.server.observer.EntityMovedListener;

import java.util.*;

public class GameMaster implements EntityMovedListener {

    private Map<Player, Interaction> playerInteraction;
    private Map<PlayerId, Player> id2Player;
    private Set<Player> activePlayers;
    private Stack<PlayerId> availablePlayerIds;
    private GameMap gameMap;

    public GameMaster() {
        final int mapSize = 7;

        this.availablePlayerIds = new Stack<>();
        this.gameMap = new GameMap(mapSize, mapSize);
        this.activePlayers = new HashSet<>();
        this.id2Player = new HashMap<>();
        this.playerInteraction = new HashMap<>();
    }

    @Override
    public void onEntityMoved(MovableEntity entity, Position oldPosition, Position newPosition) {
        //todo
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

                if (entitiesAtPosition.stream().allMatch(e -> e.canEnter() == true) == true) {
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

        //todo: interaction shit
        id2Player.put(id, player);
        activePlayers.add(player);

        onRegisteredPlayer();
        return null;
    }

    public Collection<GridEntity> getAt(int r, int c) {
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

    public Collection<GridEntity> getAt(Position position) {
        if (position == null) {
            throw new IllegalArgumentException("Position cannot be null");
        }

        return getAt(position.row(), position.col());
    }

    public void onRegisteredPlayer() {
        notifyAllActivePlayersToUpdate();
    }

    private void notifyAllActivePlayersToUpdate() {

    }

    private PlayerState getPlayerState(PlayerId id) {
        Player player = id2Player.get(id);
        PlayerState.PlayerStateBuilder builder = PlayerState.builder(player).setGameMapView(new GameMapView(gameMap));

        if (playerInteraction.containsKey(player) == true) {
            builder.setInteraction(playerInteraction.get(player));
        }

        return builder.build();
    }
}
