package it.polimi.ingsw.am40.model;

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
    public void Test () {
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
}