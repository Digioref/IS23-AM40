package it.polimi.ingsw.am40.Model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
public class BoardTest {
    /**
     * Tests the constructor
     */
    @Test
    void Test1() {
        Board b1 = new Board();
        for (String s: b1.getGrid().keySet()) {
            assertEquals(TileColor.NOCOLOR, b1.getGrid().get(s).getColor());
            assertEquals(TileType.EMPTY, b1.getGrid().get(s).getType());
        }
        for (int i = 2; i < 5; i++) {
            Board b2 = new Board();
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
        Game g = new Game(4);
        g.configureGame();
        Board b1 = g.getBoard();
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
        Player pl = new Player("pippo");
        Game g = new Game(4);
        g.configureGame();
        Board b1 = g.getBoard();
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
        b1.updatePickable(t.getPos(), pl);
        /*
        System.out.println(t.toString());
        assertNotEquals(TileColor.NOCOLOR, t.getColor());
        assertNotEquals(TileType.EMPTY, t.getType());

        assertEquals(TileColor.NOCOLOR, b1.getGrid().get(p.getKey()).getColor());
        assertEquals(TileType.EMPTY, b1.getGrid().get(p.getKey()).getType());
        */
        Tile t1 = b1.pick(p.getKey());
        assertNull(t1);
    }

    /**
     * Tests the remove method
     */
    @Test
    void Test4() {
        Game g = new Game(4);
        g.configureGame();
        Board b1 = g.getBoard();
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
        int oldsize = b.getAvailableTiles().size();
        b1.remove(b);
        for (String s : b1.getGrid().keySet()) {
            assertEquals(TileColor.NOCOLOR, b1.getGrid().get(s).getColor());
            assertEquals(TileType.EMPTY, b1.getGrid().get(s).getType());
        }
        assertNotEquals(oldsize, b.getAvailableTiles().size());
        assertEquals(oldsize + 2, b.getAvailableTiles().size());
    }

    /**
     * Tests the methods related to pickable tiles
     */
    @Test
    void Test5() {
        Game g = new Game(4);
        Player pippo = new Player("pippo");
        g.configureGame();
        Board b1 = g.getBoard();
        Bag b = g.getBag();
        b1.config(b);
        pippo.setBoard(b1);
        for (String s: b1.getGrid().keySet()) {
            System.out.println(b1.getGrid().get(s).toString());
        }
        b1.setSideFreeTile();
        for (Position p: b1.getPickableTiles()) {
            System.out.println(p.getKey());
        }
        b1.updatePickable(new Position(0,0),pippo);
        b1.getPickableTiles().remove(new Position(2,2));
        b1.updatePickable(new Position(1, 1), pippo);
        pippo.getSelectedPositions().add(new Position(0,0));
        pippo.getSelectedPositions().add(new Position(0,1));
        pippo.getSelectedPositions().add(new Position(0,2));
        b1.updatePickable(new Position(-1, 1), pippo);
        pippo.clearSelected();
        pippo.getSelectedPositions().add(new Position(0,0));
        pippo.getSelectedPositions().add(new Position(0,1));
        b1.getPickableTiles().remove(new Position(-2,2));
        b1.updatePickable(new Position(-1, 1), pippo);
        pippo.clearSelected();
        pippo.getSelectedPositions().add(new Position(3,0));
        pippo.getSelectedPositions().add(new Position(3,1));
        b1.getPickableTiles().remove(new Position(-2,1));
        b1.updatePickable(new Position(3, 1), pippo);
        pippo.clearSelected();
        pippo.getSelectedPositions().add(new Position(0,3));
        pippo.getSelectedPositions().add(new Position(-1,3));
        b1.getPickableTiles().remove(new Position(-1,-3));
        b1.updatePickable(new Position(3, 1), pippo);

        b1.getStartingPickable().clear();
        assertTrue(b1.getStartingPickable().isEmpty());

        b1.config(b);
        assertFalse(b1.needRefill());
        b1.getGrid().clear();
        assertTrue(b1.needRefill());

    }
}
