package bg.sofia.uni.fmi.mjt.dungeons.common.item;

import bg.sofia.uni.fmi.mjt.dungeons.common.Stats;
import bg.sofia.uni.fmi.mjt.dungeons.server.entity.ItemConsumingEntity;
import bg.sofia.uni.fmi.mjt.dungeons.server.entity.WeaponCarryingEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class HealthPotionTest {
    @Test
    void testGetNameAutoName() {
        HealthPotion potion = new HealthPotion(1, 100, 100);
        assertEquals("HealthPotion: 100hp, level: 1, manaCast: 100", potion.getName());
    }

    @Test
    void testGetNameCustomName() {
        HealthPotion potion = new HealthPotion(1, 100, 100, "healthPotion");
        assertEquals("healthPotion", potion.getName());
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
        HealthPotion potion = new HealthPotion(1, 50, 100);
        Stats stats = new Stats(0, 0, 0, 0);

        Stats affected = potion.affectStats(stats);
        assertEquals(100, affected.getHealth(), 0.001);
        assertEquals(0, affected.getMana(), 0.001);
        assertEquals(0, affected.getAttack(), 0.001);
        assertEquals(0, affected.getDefense(), 0.001);
    }

    @Test
    void testCanBeSetAsWeapon() {
        HealthPotion potion = new HealthPotion(1, 50, 100);
        WeaponCarryingEntity weaponCarryingEntity = mock(WeaponCarryingEntity.class);

        assertFalse(potion.canBeSetAsAWeapon(weaponCarryingEntity));
    }

    @Test
    void testCanBeConsumed() {
        HealthPotion potion = new HealthPotion(1, 50, 100);
        ItemConsumingEntity itemConsumingEntity = mock(ItemConsumingEntity.class);

        when(itemConsumingEntity.getLevel()).thenReturn(1);
        when(itemConsumingEntity.getStats()).thenReturn(new Stats(0, 51, 0, 0));

        assertTrue(potion.canBeConsumed(itemConsumingEntity));
    }

    @Test
    void testEqualsIdentical() {
        HealthPotion potion1 = new HealthPotion(1, 50, 100);
        HealthPotion potion2 = new HealthPotion(1, 50, 100);

        assertEquals(potion1, potion2);
    }

    @Test
    void testEqualsNUll() {
        HealthPotion potion1 = new HealthPotion(1, 50, 100);
        assertNotEquals(potion1, null);
    }

    @Test
    void testEqualsDifferentClass() {
        HealthPotion potion1 = new HealthPotion(1, 50, 100);
        assertNotEquals(potion1,2);
    }
}
