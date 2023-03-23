package it.polimi.ingsw.am40.model;

import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
public class BoardTest {
    /**
     * Tests the constructor
     */
    @Test
    void Test1() {
        Board b1 = new Board(4);
        for (String s: b1.getGrid().keySet()) {
            assertEquals(TileColor.NOCOLOR, b1.getGrid().get(s).getColor());
            assertEquals(TileType.EMPTY, b1.getGrid().get(s).getType());
        }
        for (int i = 2; i < 5; i++) {
            Board b2 = new Board(i);
            for (String s: b2.getGrid().keySet()) {
                System.out.println(s);
            }
            System.out.println("--------------");
        }
    }

    /**
     * Tests the config method
     */
    @Test
    void Test2() {
        Board b1 = new Board(4);
        Game g = new Game(4);
        g.configureGame();
        Bag b = g.getBag();
        b1.config(b);
        for (String s: b1.getGrid().keySet()) {
            System.out.println(s + " : " + b1.getGrid().get(s).toString());
            assertEquals(s, b1.getGrid().get(s).getPos().getKey());
            assertNotEquals(TileColor.NOCOLOR, b1.getGrid().get(s).getColor());
            assertNotEquals(TileType.EMPTY, b1.getGrid().get(s).getType());
        }
    }

    /**
     * Tests the pick method
     */
    @Test
    void Test3() {
        Board b1 = new Board(4);
        Game g = new Game(4);
        g.configureGame();
        Bag b = g.getBag();
        b1.config(b);
        for (String s: b1.getGrid().keySet()) {
            System.out.println(s + " : " + b1.getGrid().get(s).toString());
            assertEquals(s, b1.getGrid().get(s).getPos().getKey());
            assertNotEquals(TileColor.NOCOLOR, b1.getGrid().get(s).getColor());
            assertNotEquals(TileType.EMPTY, b1.getGrid().get(s).getType());
        }
        Position p = new Position(1,3);
        Tile t = b1.pick(p.getKey());
        System.out.println(t.toString());
        assertNotEquals(TileColor.NOCOLOR, t.getColor());
        assertNotEquals(TileType.EMPTY, t.getType());
        assertEquals(TileColor.NOCOLOR, b1.getGrid().get(p.getKey()).getColor());
        assertEquals(TileType.EMPTY, b1.getGrid().get(p.getKey()).getType());
        Tile t1 = b1.pick(p.getKey());
        assertEquals(null, t1);
    }

    /**
     * Tests the remove method
     */
    @Test
    void Test4() {
        Board b1 = new Board(4);
        Game g = new Game(4);
        g.configureGame();
        Bag b = g.getBag();
        Tile t1 = b.pick();
        Tile t2 = b.pick();
        System.out.println(t1.toString());
        System.out.println(t2.toString());
        Position p1 = new Position(2,1);
        Position p2 = new Position(-3,0);
        b1.getGrid().put(p1.getKey(), t1);
        b1.getGrid().put(p2.getKey(), t2);
        assertEquals(t1, b1.getGrid().get(p1.getKey()));
        assertEquals(t2, b1.getGrid().get(p2.getKey()));
        assertNotEquals(t2, b1.getGrid().get(p1.getKey()));
        assertNotEquals(t1, b1.getGrid().get(p2.getKey()));
        int oldsize = b.getAvailableTiles().size();
        b1.remove(b);
        for (String s : b1.getGrid().keySet()) {
            assertEquals(TileColor.NOCOLOR, b1.getGrid().get(s).getColor());
            assertEquals(TileType.EMPTY, b1.getGrid().get(s).getType());
        }
        assertNotEquals(oldsize, b.getAvailableTiles().size());
        assertEquals(oldsize + 2, b.getAvailableTiles().size());
    }
}
