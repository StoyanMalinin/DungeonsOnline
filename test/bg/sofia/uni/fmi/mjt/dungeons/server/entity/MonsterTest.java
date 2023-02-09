package bg.sofia.uni.fmi.mjt.dungeons.server.entity;

import bg.sofia.uni.fmi.mjt.dungeons.server.interaction.AttackInteraction;
import bg.sofia.uni.fmi.mjt.dungeons.server.interaction.InteractionNegotiator;
import bg.sofia.uni.fmi.mjt.dungeons.server.map.Position;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MonsterTest {
    @Test
    void testGetPositon() {
        Monster monster = new Monster(1, 1, new Position(2, 3));
        assertEquals(monster.getPosition(), new Position(2, 3));
    }

    @Test
    void testCanEnter() {
        Monster monster = new Monster(1, 1, new Position(2, 3));
        assertTrue(monster.canEnter());
    }

    @Test
    void testIsFree() {
        Monster monster = new Monster(1, 1, new Position(2, 3));
        assertFalse(monster.isFree());
    }

    @Test
    void testEqualsIdentical() {
        Monster monster1 = new Monster(1, 1, new Position(2, 3));
        Monster monster2 = new Monster(1, 1, new Position(2, 3));

        assertEquals(monster1, monster2);
    }

    @Test
    void testEqualsDifferentId() {
        Monster monster1 = new Monster(1, 1, new Position(2, 3));
        Monster monster2 = new Monster(2, 1, new Position(2, 3));

        assertNotEquals(monster1, monster2);
    }

    @Test
    void testEqualsDifferenObject() {
        Monster monster1 = new Monster(1, 1, new Position(2, 3));
        assertNotEquals(monster1, new Integer(2));
    }

    @Test
    void testEqualsNull() {
        Monster monster1 = new Monster(1, 1, new Position(2, 3));
        assertNotEquals(monster1, new Integer(2));
    }

    @Test
    void testRessurect() {
        Monster monster = new Monster(1, 1, new Position(2, 2));
        monster.ressurrect();

        assertTrue(monster.isAlive());
    }

    @Test
    void testGetXpForKilling() {
        Monster monster = new Monster(1, 1, new Position(2, 2));
        assertEquals(10, monster.getXPForKilling());
    }

    @Test
    void testAttack() {
        Monster monster1 = new Monster(1, 1, new Position(2, 2));

        double damage = monster1.attack();
        assertEquals(50, damage, 0.001);
    }

    @Test
    void testTakeDamageNonLethal() {
        Monster monster1 = new Monster(1, 1, new Position(2, 2));
        FightableEntity attacker = mock(FightableEntity.class);

        assertFalse(monster1.takeDamage(10, attacker), "The monster should not die after taking small damage");
    }

    @Test
    void testTakeDamageLethal() {
        Monster monster1 = new Monster(1, 1, new Position(2, 2));
        FightableEntity attacker = mock(FightableEntity.class);

        assertTrue(monster1.takeDamage(500, attacker), "The monster should die after taking small damage");
    }

    @Test
    void testNegotiateForInteractionsWithAttackInteraction() {
        Monster monster = new Monster(1, 1, new Position(1, 1));

        AttackInteraction attackInteraction = new AttackInteraction();
        InteractionNegotiator negotiator = mock(InteractionNegotiator.class);
        when(negotiator.getAttackInteraction()).thenReturn(attackInteraction);

        monster.negotiateForInteractions(negotiator);

        assertEquals(monster, attackInteraction.getSubject());
    }
}
