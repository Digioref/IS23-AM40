package it.polimi.ingsw.am40.Model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the Position class
 */
public class PositionTest {
    /**
     Tests class constructor, set and get methods, and overrided equals method
     */
    @Test
    public void Test1 () {
        Position p1 = new Position(1,3);
        assertEquals(1, p1.getX());
        assertEquals(3, p1.getY());
        Position p2 = new Position(0,0);
        p2.setX(-1);
        p2.setY(4);
        assertEquals(-1, p2.getX());
        assertEquals(4, p2.getY());
        assertNotEquals(p1.getX(), p2.getX());
        assertNotEquals(p1.getY(), p2.getY());
        assertNotEquals(true, p1.equals(p2));
        assertFalse(p2.equals(p1));
    }

    /**
     * Tests the method getKey
     */
    @Test
    public void Test2 () {
        Position p1 = new Position(2,4);
        String k = p1.getKey();
        assertEquals("(2,4)", k);
        assertNotEquals("(4,2)", k);
    }

    /**
     * Tests the convertKey method
     */
    @Test
    void Test3() {
        Position p = new Position(3,4);
        assertEquals("(3,4)", p.getKey());
        Position p1 = new Position(0,1);
        p1.convertKey(p.getKey());
        assertEquals(p.getX(), p1.getX());
        assertEquals(p.getY(), p1.getY());
    }
}