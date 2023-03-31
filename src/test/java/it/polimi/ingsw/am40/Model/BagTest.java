package it.polimi.ingsw.am40.Model;

import org.junit.Before;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class BagTest {
    /**
     * Tests the constructor
     */
    @Test
    public void Test1() {
        Bag b = new Bag();
        assertEquals(0, b.getAvailableTiles().size());
        assertTrue(b.getAvailableTiles().isEmpty());
    }

    /**
     * Tests the get and set methods
     */
    @Test
    void Test2() {
        Bag b = new Bag();
        ArrayList<Tile> arr = new ArrayList<>();
        Tile t1 = new Tile(TileColor.GREEN, TileType.CATS);
        Tile t2 = new Tile(TileColor.CYAN, TileType.TROPHIES);
        arr.add(t1);
        arr.add(t2);
        b.setAvailableTiles(arr);
        assertEquals(t1, b.getAvailableTiles().get(0));
        assertEquals(t2, b.getAvailableTiles().get(1));
        assertNotEquals(t2, b.getAvailableTiles().get(0));
        assertNotEquals(t1, b.getAvailableTiles().get(1));
    }

    /**
     * Tests the pick method
     */
    @Test
    void Test3() {
        Bag b = new Bag();
        assertEquals(null, b.pick());
        ArrayList<Tile> arr = new ArrayList<>();
        Tile t1 = new Tile(TileColor.GREEN, TileType.CATS);
        Tile t2 = new Tile(TileColor.CYAN, TileType.TROPHIES);
        arr.add(t1);
        b.setAvailableTiles(arr);
        assertNotEquals(null, b.pick());
        arr.add(t2);
        assertEquals(t2, b.pick());
    }

    /**
     * Tests the insert method
     */
    @Test
    void Test4() {
        Bag b = new Bag();
        assertEquals(null, b.pick());
        Tile t1 = new Tile(TileColor.GREEN, TileType.CATS);
        Tile t2 = new Tile(TileColor.CYAN, TileType.TROPHIES);
        b.insert(t1);
        b.insert(t2);
        ArrayList<Tile> arr = new ArrayList<>();
        arr.add(t1);
        arr.add(t2);
        for (int i = 0; i < arr.size(); i++) {
            assertEquals(arr.get(i), b.getAvailableTiles().get(i));
        }
    }
}
