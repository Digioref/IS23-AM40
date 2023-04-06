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

    @Test
    void setMark() {

    }

    @Test
    void getMark() {
    }

    @Test
    void getColor() {
    }

    @Test
    void getSize() {
    }

    @Test
    void getTile() {
    }

    @Test
    void testToString() {
    }

    @Test
    void isEmpty() {
    }
}