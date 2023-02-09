package bg.sofia.uni.fmi.mjt.dungeons.server.entity.player;

import bg.sofia.uni.fmi.mjt.dungeons.common.PlayerId;
import bg.sofia.uni.fmi.mjt.dungeons.common.Stats;
import bg.sofia.uni.fmi.mjt.dungeons.common.item.Item;
import bg.sofia.uni.fmi.mjt.dungeons.common.item.WeaponItem;
import bg.sofia.uni.fmi.mjt.dungeons.server.entity.FightableEntity;
import bg.sofia.uni.fmi.mjt.dungeons.server.entity.ItemGivingEntity;
import bg.sofia.uni.fmi.mjt.dungeons.server.entity.controller.MapMoveController;
import bg.sofia.uni.fmi.mjt.dungeons.server.entity.controller.MoveController;
import bg.sofia.uni.fmi.mjt.dungeons.server.map.Position;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PlayerTest {
    @Test
    void testGetLevel() {
        MoveController moveController = mock(MoveController.class);
        Player player = new Player(new PlayerId(1), new Position(1, 1), moveController, 1, new Stats(100, 100, 50, 50));

        assertEquals(1, player.getLevel());
    }

    @Test
    void testGetId() {
        MoveController moveController = mock(MoveController.class);
        Player player = new Player(new PlayerId(1), new Position(1, 1), moveController, 1, new Stats(100, 100, 50, 50));

        assertEquals(new PlayerId(1), player.getId());
    }

    @Test
    void testGetStatsNoWeapon() {
        MoveController moveController = mock(MoveController.class);
        Player player = new Player(new PlayerId(1), new Position(1, 1), moveController, 1, new Stats(100, 100, 50, 50));

        assertEquals(100, player.getStats().getHealth(), 0.001);
        assertEquals(100, player.getStats().getMana(), 0.001);
        assertEquals(50, player.getStats().getAttack(), 0.001);
        assertEquals(50, player.getStats().getDefense(), 0.001);
    }

    @Test
    void testGetStatsWithWeapon() {
        MoveController moveController = mock(MoveController.class);
        Player player = new Player(new PlayerId(1), new Position(1, 1), moveController, 1, new Stats(100, 100, 50, 50));

        WeaponItem weaponItem = mock(WeaponItem.class);
        when(weaponItem.affectStats(any())).thenReturn(new Stats(100, 100, 60, 50));
        player.setWeapon(weaponItem);

        assertEquals(100, player.getStats().getHealth(), 0.001);
        assertEquals(100, player.getStats().getMana(), 0.001);
        assertEquals(60, player.getStats().getAttack(), 0.001);
        assertEquals(50, player.getStats().getDefense(), 0.001);
    }

    @Test
    void testCanEnter() {
        MoveController moveController = mock(MoveController.class);
        Player player = new Player(new PlayerId(1), new Position(1, 1), moveController, 1, new Stats(100, 100, 50, 50));

        assertTrue(player.canEnter());
    }

    @Test
    void testIsFree() {
        MoveController moveController = mock(MoveController.class);
        Player player = new Player(new PlayerId(1), new Position(1, 1), moveController, 1, new Stats(100, 100, 50, 50));

        assertFalse(player.isFree());
    }

    @Test
    void testGetPosition() {
        MoveController moveController = mock(MoveController.class);
        Player player = new Player(new PlayerId(1), new Position(1, 1), moveController, 1, new Stats(100, 100, 50, 50));

        assertEquals(new Position(1, 1), player.getPosition());
    }

    @Test
    void testSetPosition() {
        MoveController moveController = mock(MoveController.class);
        Player player = new Player(new PlayerId(1), new Position(1, 1), moveController, 1, new Stats(100, 100, 50, 50));

        player.setPosition(new Position(1, 2));
        assertEquals(new Position(1, 2), player.getPosition());
    }

    @Test
    void testTakeDamageNonLethal() {
        MoveController moveController = mock(MoveController.class);
        Player player = new Player(new PlayerId(1), new Position(1, 1), moveController, 1, new Stats(100, 100, 50, 50));

        FightableEntity fightableEntity = mock(FightableEntity.class);
        assertFalse(player.takeDamage(60, fightableEntity), "The player should not die when taking small damage");

        assertEquals(90, player.getStats().getHealth(), 0.001, "Health should change after being attacked");
    }

    @Test
    void testTakeDamageLethal() {
        MoveController moveController = mock(MoveController.class);
        Player player = new Player(new PlayerId(1), new Position(1, 1), moveController, 1, new Stats(100, 100, 50, 50));

        FightableEntity fightableEntity = mock(FightableEntity.class);
        assertTrue(player.takeDamage(150, fightableEntity), "The player should not die when taking small damage");

        assertFalse(player.isAlive());
    }

    @Test
    void testIsAlive() {
        MoveController moveController = mock(MoveController.class);
        Player player = new Player(new PlayerId(1), new Position(1, 1), moveController, 1, new Stats(100, 100, 50, 50));

        assertTrue(player.isAlive(), "Player is alive by default");
    }

    @Test
    void testGetXPForKilling() {
        MoveController moveController = mock(MoveController.class);
        Player player = new Player(new PlayerId(1), new Position(1, 1), moveController, 1, new Stats(100, 100, 50, 50));

        assertEquals(10, player.getXPForKilling());
    }

    @Test
    void testOnVictimDied() {
        MoveController moveController = mock(MoveController.class);
        Player player = new Player(new PlayerId(1), new Position(1, 1), moveController, 1, new Stats(100, 100, 50, 50));

        FightableEntity victim = mock(FightableEntity.class);
        when(victim.getXPForKilling()).thenReturn(10);

        player.onVictimDied(victim);
        assertEquals(10, player.getXP());
        assertEquals(1, player.getLevel());
    }

    @Test
    void testOnVictimDiedNull() {
        MoveController moveController = mock(MoveController.class);
        Player player = new Player(new PlayerId(1), new Position(1, 1), moveController, 1, new Stats(100, 100, 50, 50));

        assertThrows(IllegalArgumentException.class, () -> {
            player.onVictimDied(null);
        });
    }


    @Test
    void testOnVictimDiedLevelUp() {
        MoveController moveController = mock(MoveController.class);
        Player player = new Player(new PlayerId(1), new Position(1, 1), moveController, 1, new Stats(100, 100, 50, 50));

        FightableEntity victim = mock(FightableEntity.class);
        when(victim.getXPForKilling()).thenReturn(20);

        player.onVictimDied(victim);
        assertEquals(0, player.getXP());
        assertEquals(2, player.getLevel());
    }

    @Test
    void testReceiveItem() {
        MoveController moveController = mock(MoveController.class);
        Player player = new Player(new PlayerId(1), new Position(1, 1), moveController, 1, new Stats(100, 100, 50, 50));

        Item item = mock(Item.class);
        ItemGivingEntity giver = mock(ItemGivingEntity.class);
        player.receiveItem(item, giver);

        assertEquals(1, player.getBackpack().getItems().size());
    }

    @Test
    void testRessurect() {
        MoveController moveController = mock(MoveController.class);
        Player player = new Player(new PlayerId(1), new Position(1, 1), moveController, 1, new Stats(100, 100, 50, 50));

        Item item = mock(Item.class);
        ItemGivingEntity giver = mock(ItemGivingEntity.class);
        player.receiveItem(item, giver);

        FightableEntity fightableEntity = mock(FightableEntity.class);
        assertTrue(player.takeDamage(150, fightableEntity), "The player should not die when taking small damage");

        player.resurrect(new Random());
        assertEquals(0, player.getBackpack().getItems().size(), "One random item should drop after ressurrection");
    }
}
