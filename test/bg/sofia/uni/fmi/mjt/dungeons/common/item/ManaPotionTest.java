package bg.sofia.uni.fmi.mjt.dungeons.common.item;

import bg.sofia.uni.fmi.mjt.dungeons.common.Stats;
import bg.sofia.uni.fmi.mjt.dungeons.server.entity.ItemConsumingEntity;
import bg.sofia.uni.fmi.mjt.dungeons.server.entity.WeaponCarryingEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ManaPotionTest {
    @Test
    void testGetNameAutoName() {
        ManaPotion potion = new ManaPotion(1, 100, 100);
        assertEquals("ManaPotion: 100mana, level: 1, manaCast: 100", potion.getName());
    }

    @Test
    void testGetNameCustomName() {
        ManaPotion potion = new ManaPotion(1, 100, 100, "manaPotion");
        assertEquals("manaPotion", potion.getName());
    }

    @Test
    void testGetLevel() {
        ManaPotion potion = new ManaPotion(1, 100, 100);
        assertEquals(1, potion.getLevel());
    }

    @Test
    void testManaCast() {
        ManaPotion potion = new ManaPotion(1, 100, 100);
        assertEquals(100, potion.getManaCast(), 0.001);
    }

    @Test
    void testAffectStats() {
        ManaPotion potion = new ManaPotion(1, 50, 100);
        Stats stats = new Stats(0, 0, 0, 0);

        Stats affected = potion.affectStats(stats);
        assertEquals(0, affected.getHealth(), 0.001);
        assertEquals(100, affected.getMana(), 0.001);
        assertEquals(0, affected.getAttack(), 0.001);
        assertEquals(0, affected.getDefense(), 0.001);
    }

    @Test
    void testCanBeSetAsWeapon() {
        ManaPotion potion = new ManaPotion(1, 50, 100);
        WeaponCarryingEntity weaponCarryingEntity = mock(WeaponCarryingEntity.class);

        assertFalse(potion.canBeSetAsAWeapon(weaponCarryingEntity));
    }

    @Test
    void testCanBeConsumed() {
        ManaPotion potion = new ManaPotion(1, 50, 100);
        ItemConsumingEntity itemConsumingEntity = mock(ItemConsumingEntity.class);

        when(itemConsumingEntity.getLevel()).thenReturn(1);
        when(itemConsumingEntity.getStats()).thenReturn(new Stats(0, 51, 0, 0));

        assertTrue(potion.canBeConsumed(itemConsumingEntity));
    }

    @Test
    void testEqualsIdentical() {
        ManaPotion potion1 = new ManaPotion(1, 50, 100);
        ManaPotion potion2 = new ManaPotion(1, 50, 100);

        assertEquals(potion1, potion2);
    }

    @Test
    void testEqualsNUll() {
        ManaPotion potion1 = new ManaPotion(1, 50, 100);
        assertNotEquals(potion1, null);
    }

    @Test
    void testEqualsDifferentClass() {
        ManaPotion potion1 = new ManaPotion(1, 50, 100);
        assertNotEquals(potion1,2);
    }
}
