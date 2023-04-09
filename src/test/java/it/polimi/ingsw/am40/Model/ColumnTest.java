package it.polimi.ingsw.am40.Model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ColumnTest {

    Tile tile1 = new Tile(TileColor.BLUE,TileType.CATS);
    Column column = new Column();

    /**
     * Testing if the tiles are added in the column
     * Testing also limit cases like no color tile or full column
     */
    @Test
    void addTile() {
        assertEquals(column.addTile(tile1), true);

        Tile no_color_tile = new Tile(TileColor.NOCOLOR, TileType.CATS);
        assertEquals(column.addTile(no_color_tile), false);

        column.addTile(tile1);
        column.addTile(tile1);
        column.addTile(tile1);
        column.addTile(tile1);
        column.addTile(tile1);
        assertEquals(column.addTile(tile1), false);

    }

    /**
     * Tests isFull method filling a column
     */
    @Test
    void isFull() {
        assertEquals(column.isFull(), false);
        column.addTile(tile1);
        column.addTile(tile1);
        column.addTile(tile1);
        column.addTile(tile1);
        column.addTile(tile1);
        column.addTile(tile1);
        assertEquals(column.isFull(),true);

    }

    /**
     * Test of getMark and setMark
     */
    @Test
    void setMark() {
        Position p1 = new Position(1,1);
        assertEquals(column.getMark(p1.getY()), 0);
        column.setMark(p1.getY();
        assertEquals(column.getMark(p1.getY()), 1);
    }


    @Test
    void getColor() {
        column.addTile(tile1);
        assertEquals(column.getColor(1), TileColor.BLUE);
    }

    @Test
    void getSize() {
        column.addTile(tile1);
        assertEquals(column.getSize(), 1);
    }

    /**
     * Testing getTile method
     */
    @Test
    void getTile() {
        column.addTile(tile1);
        assertEquals(column.getTile(1), tile1);
    }

    /*
    @Test

    void testToString() {
    }

     */

    /**
     * Testing is empty method
     */
    @Test
    void testIsEmpty() {
        assertEquals(column.isEmpty(), true);
        column.addTile(tile1);
        assertEquals(column.isEmpty(), false);
    }
}