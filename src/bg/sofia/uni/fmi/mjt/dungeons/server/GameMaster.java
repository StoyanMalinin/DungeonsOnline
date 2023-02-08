package bg.sofia.uni.fmi.mjt.dungeons.server;

import bg.sofia.uni.fmi.mjt.dungeons.common.PlayerId;
import bg.sofia.uni.fmi.mjt.dungeons.common.Stats;
import bg.sofia.uni.fmi.mjt.dungeons.server.entity.*;
import bg.sofia.uni.fmi.mjt.dungeons.server.entity.controller.MapMoveController;
import bg.sofia.uni.fmi.mjt.dungeons.server.entity.player.Player;
import bg.sofia.uni.fmi.mjt.dungeons.server.entity.player.PlayerState;
import bg.sofia.uni.fmi.mjt.dungeons.server.exception.*;
import bg.sofia.uni.fmi.mjt.dungeons.server.interaction.InteractionChoice;
import bg.sofia.uni.fmi.mjt.dungeons.server.interaction.InteractionWithOne;
import bg.sofia.uni.fmi.mjt.dungeons.server.interaction.PlayerInteractionChoice;
import bg.sofia.uni.fmi.mjt.dungeons.server.map.Direction;
import bg.sofia.uni.fmi.mjt.dungeons.server.map.GameMap;
import bg.sofia.uni.fmi.mjt.dungeons.server.map.GameMapView;
import bg.sofia.uni.fmi.mjt.dungeons.server.map.Position;
import bg.sofia.uni.fmi.mjt.dungeons.server.observer.EntityMovedListener;
import bg.sofia.uni.fmi.mjt.dungeons.server.observer.MonsterAttackedListener;
import bg.sofia.uni.fmi.mjt.dungeons.server.observer.MonsterDiedListener;
import bg.sofia.uni.fmi.mjt.dungeons.server.observer.PlayerDiedListener;

import java.util.*;

public class GameMaster implements PlayerDiedListener, EntityMovedListener, MonsterDiedListener, MonsterAttackedListener {

    private Map<Player, InteractionChoice> playerInteraction;
    private Map<PlayerId, Player> id2Player;
    private Set<Player> alivePlayers;
    private Set<Monster> aliveMonsters;
    private Stack<PlayerId> availablePlayerIds;
    private GameMap gameMap;
    private Random rnd;


    public GameMaster(int mapSize, int monsterCount) {

        if (monsterCount < 0) {
            throw new IllegalArgumentException("MonsterCount cannot be negative");
        }
        if (mapSize <= 0) {
            throw new IllegalArgumentException("MapSize must be positive");
        }

        final int playerCnt = 9;
        final int seed = 22;

        this.availablePlayerIds = new Stack<>();
        this.aliveMonsters = new HashSet<>();
        this.gameMap = new GameMap(mapSize, mapSize);
        this.alivePlayers = new HashSet<>();
        this.id2Player = new HashMap<>();
        this.playerInteraction = new HashMap<>();
        this.rnd = new Random(seed);

        for (int p = playerCnt; p >= 1; p--) {
            availablePlayerIds.push(new PlayerId(p));
        }

        for (int m = 0; m < monsterCount; m++) {
            Position position = getRandomFreePosition();
            if (position == null) {
                break;
            }

            Monster monster = new Monster(m, m + 1, position);
            monster.getEntityMovedObserver().subscribe(this);
            monster.getMonsterDiedObserver().subscribe(this);
            monster.getMonsterAttackedObserver().subscribe(this);

            this.aliveMonsters.add(monster);
        }
    }

    private void respawnMonster(Monster monster) {
        Position position = getRandomFreePosition();
        monster.ressurrect();
        monster.setPosition(position);
    }

    public void movePlayer(PlayerId id, Direction direction)
            throws PlayerNotActiveException, UnexpectedServerLogicException {
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
    }

    public Player registerPlayer() throws NoAvailablePlayersException, NoAvailableMapPositionsException {
        if (availablePlayerIds.empty() == true) {
            throw new NoAvailablePlayersException("No available players");
        }

        PlayerId id = availablePlayerIds.pop();

        Position playerPosition = getRandomFreePosition();
        if (playerPosition == null) {
            throw new NoAvailableMapPositionsException("There is no available position to locate the player currently");
        }

        final int defaultLevel = 1;
        final int defaultHealth = 100;
        final int defaultMana = 100;
        final int defaultAttack = 50;
        final int defaultDefense = 40;

        Player player = new Player(id, playerPosition, new MapMoveController(gameMap),
                defaultLevel, new Stats(defaultHealth, defaultMana, defaultAttack, defaultDefense));
        player.getPlayerDiedObserver().subscribe(this);
        player.getEntityMovedObserver().subscribe(this);

        id2Player.put(id, player);

        alivePlayers.add(player);
        player.setState(getPlayerState(player.getId()));

        return player;
    }

    private Position getRandomFreePosition() {
        List<Position> availablePositions = new ArrayList<>();
        for (int r = 0; r < gameMap.getRowCnt(); r++) {
            for (int c = 0; c < gameMap.getColCnt(); c++) {
                List<GridEntity> entitiesAtPosition = getAt(r, c);
                if (entitiesAtPosition.stream().allMatch(e -> e.isFree() == true && e.canEnter() == true) == true) {
                    availablePositions.add(new Position(r, c));
                }
            }
        }

        if (availablePositions.isEmpty() == true) {
            return null;
        }

        return availablePositions.get(rnd.nextInt(availablePositions.size()));
    }

    public List<GridEntity> getAt(int r, int c) {
        if (gameMap.isInside(r, c) == false) {
            throw new IllegalArgumentException("The requested position must be inside the grid");
        }

        List<GridEntity> entitiesOnPosition = new LinkedList<>();
        entitiesOnPosition.add(gameMap.getAt(r, c));

        for (Player player : alivePlayers) {
            if (player.getPosition().equals(new Position(r, c)) == true) {
                entitiesOnPosition.add(player);
            }
        }

        for (Monster monster : aliveMonsters) {
            if (monster.getPosition().equals(new Position(r, c)) == true) {
                entitiesOnPosition.add(monster);
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
        for (Player player : alivePlayers) {
            player.setState(getPlayerState(player.getId()));
        }
    }

    private PlayerState getPlayerState(PlayerId id) {
        return getPlayerState(id, null);
    }

    private PlayerState getPlayerState(PlayerId id, String errorMessage) {
        Player player = id2Player.get(id);
        PlayerState.PlayerStateBuilder builder = PlayerState.builder(player).setGameMapView(new GameMapView(this));

        updatePlayerInteractions(player);
        if (playerInteraction.containsKey(player) == true) {
            builder.setInteractionChoice(playerInteraction.get(player));
        }

        if (errorMessage != null) {
            builder.setErrorMessage(errorMessage);
        }

        return builder.build();
    }

    private void updatePlayerInteractions(Player player) {
        List<GridEntity> entities = getAt(player.getPosition());

        InteractionChoice res = new PlayerInteractionChoice(player);
        for (GridEntity entity : entities) {
            if (entity.equals(player) == false) {
                InteractionChoice choice = player.getInteractionChoice(entity);

                for (InteractionWithOne interaction : choice.getOptions()) {
                    res.addOption(interaction);
                }
            }
        }

        playerInteraction.put(player, res);
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
        alivePlayers.remove(player);
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

    public void respawnPlayer(Player player) throws NoAvailableMapPositionsException {
        player.resurrect();

        Position position = getRandomFreePosition();
        if (position == null) {
            throw new NoAvailableMapPositionsException("There is no available position to locate the player currently");
        }

        player.setPosition(position);
    }

    public void attack(FightableEntity initiator, FightableEntity subject) {
        if (initiator == null) {
            throw new IllegalArgumentException("Initiator cannot be null");
        }
        if (subject == null) {
            throw new IllegalArgumentException("Subject cannot be null");
        }

        subject.takeDamage(initiator.attack(), initiator);
        refresh();
    }

    private void sendErrorMessageToPlayer(Player player, String errorMessage) {
        player.setState(getPlayerState(player.getId(), errorMessage));
    }

    public void performChoiceInd(PlayerId playerId, int choiceInd) {
        if (playerId == null) {
            throw new IllegalArgumentException("Id cannot be null");
        }

        if (id2Player.containsKey(playerId) == false) {
            return;
        }
        Player player = id2Player.get(playerId);

        InteractionChoice interactionChoice = playerInteraction.get(player);
        if (interactionChoice == null) {
            sendErrorMessageToPlayer(player, "The player has no interaction options");
            return;
        }

        List<InteractionWithOne> options = interactionChoice.getOptions();
        if (!(0 <= choiceInd && choiceInd < options.size())) {
            sendErrorMessageToPlayer(player, "The choice index is out of bounds");
            return;
        }

        options.get(choiceInd).execute(this);
    }

    @Override
    public void onPlayerDied(Player player) {
        try {
            respawnPlayer(player);
        } catch (NoAvailableMapPositionsException e) {
            sendErrorMessageToPlayer(player, "The map is full and you cannot respawn, so you are dead forever");
            unregisterPlayer(player.getId());
        }
    }

    @Override
    public void onEntityMoved(MovableEntity entity, Position oldPosition, Position newPosition) {
        refresh();
    }

    @Override
    public void onMonsterDied(Monster monster) {
        respawnMonster(monster);
    }

    @Override
    public void onMonsterAttacked(FightableEntity attacker, Monster monster) {
        if (monster.isAlive() == true) {
            attack(monster, attacker);
        }
    }
}
