package it.polimi.ingsw.am40.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the Tile class
 */
public class TileTest {
    /**
     Tests class constructor, set and get methods, and overrided equals method
     */
    @Test
    public void Test () {
        Tile p1 = new Tile(TileColor.WHITE,TileType.BOOKS);
        assertEquals(TileColor.WHITE, p1.getColor());
        assertEquals(TileType.BOOKS, p1.getType());
        Tile p2 = new Tile(TileColor.YELLOW,TileType.GAMES);
        p2.setColor(TileColor.GREEN);
        p2.setType(TileType.CATS);
        assertEquals(TileColor.GREEN, p2.getColor());
        assertEquals(TileType.CATS, p2.getType());
        assertNotEquals(p1.getColor(), p2.getColor());
        assertNotEquals(p1.getType(), p2.getType());
        assertNotEquals(true, p1.equals(p2));
        assertFalse(p2.equals(p1));
    }

    /**
     * Tests get and set position, and the toString
     */
    @Test
    public void Test2() {
        Tile t1 = new Tile(TileColor.GREEN, TileType.CATS);
        Position p = new Position(1,3);
        t1.setPos(p);
        String s = t1.toString();
        assertEquals(1, t1.getPos().getX());
        assertEquals(3, t1.getPos().getY());
        assertNotEquals(1, t1.getPos().getY());
        assertNotEquals(3, t1.getPos().getX());
        assertEquals("Tile { color = GREEN, type = CATS, pos = (1,3) }", t1.toString());
    }
}
