package bg.sofia.uni.fmi.mjt.dungeons.server.interaction;

import bg.sofia.uni.fmi.mjt.dungeons.common.item.Item;
import bg.sofia.uni.fmi.mjt.dungeons.common.item.WeaponItem;
import bg.sofia.uni.fmi.mjt.dungeons.server.GameMaster;
import bg.sofia.uni.fmi.mjt.dungeons.server.entity.GameEntity;
import bg.sofia.uni.fmi.mjt.dungeons.server.entity.WeaponCarryingEntity;
import bg.sofia.uni.fmi.mjt.dungeons.server.entity.player.Player;
import bg.sofia.uni.fmi.mjt.dungeons.server.exception.ClientCommandException;

public class PlayerEquipWeaponInteraction implements InteractionWithZero {

    private Player player;
    private Item item;

    public PlayerEquipWeaponInteraction(Player player, Item item) {
        if (player == null) {
            throw new IllegalArgumentException("Player cannot be null");
        }
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null");
        }

        this.player = player;
        this.item = item;
    }

    @Override
    public GameEntity getInitiator() {
        return player;
    }

    @Override
    public void execute(GameMaster gameMaster) throws ClientCommandException {
        gameMaster.playerEquipItem(player, item);
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public String toString() {
        return "Equip " + item.getName();
    }

    @Override
    public int hashCode() {
        return player.hashCode() ^ item.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj instanceof PlayerEquipWeaponInteraction other) {
            return player.equals(other.player) == true
                    && item.equals(other.item);
        }

        return false;
    }
}
