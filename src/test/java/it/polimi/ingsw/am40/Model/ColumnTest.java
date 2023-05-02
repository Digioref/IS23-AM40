package it.polimi.ingsw.am40.Model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ColumnTest {
    private final static int DIM = 6;


    @Test
    void addTile() {
        Column col = new Column();
        assertNotEquals(null,col);
        assertEquals(0,col.getSize());
        Tile tile = null;
        assertFalse(col.addTile(tile));
        tile=new Tile(TileColor.NOCOLOR,TileType.EMPTY);
        assertFalse(col.addTile(tile));
        tile=new Tile(TileColor.GREEN,TileType.CATS);
        for(int i=0;i<DIM;i++){
            assertTrue(col.addTile(tile));
        }
        //add tile exced col size limit
        assertFalse(col.addTile(tile));
    }

    @Test
    void getColumn() {
        //test with full column
        Column col = new Column();
        Tile tile1= new Tile(TileColor.GREEN,TileType.CATS);
        Tile tile2= new Tile(TileColor.YELLOW,TileType.GAMES);
        Tile tile3= new Tile(TileColor.CYAN,TileType.TROPHIES);
        Tile tile4= new Tile(TileColor.VIOLET,TileType.PLANTS);
        Tile tile5= new Tile(TileColor.BLUE,TileType.FRAMES);
        Tile tile6= new Tile(TileColor.GREEN,TileType.CATS);
        col.addTile(tile1);
        col.addTile(tile2);
        col.addTile(tile3);
        col.addTile(tile4);
        col.addTile(tile5);
        col.addTile(tile6);
        ArrayList<Tile> result = new ArrayList<>();
        result=col.getColumn();
        assertEquals(TileColor.GREEN,result.get(0).getColor());
        assertEquals(TileColor.YELLOW,result.get(1).getColor());
        assertEquals(TileColor.CYAN,result.get(2).getColor());
        assertEquals(TileColor.VIOLET,result.get(3).getColor());
        assertEquals(TileColor.BLUE,result.get(4).getColor());
        assertEquals(TileColor.GREEN,result.get(5).getColor());

        //test with empty column
        Column col2 = new Column();
        ArrayList<Tile> result2 = new ArrayList<>();
        result2=col2.getColumn();
        assertEquals(0,result2.size());
    }

    @Test
    void isFull() {
        Column col = new Column();

        //test with full column
        Tile tile1= new Tile(TileColor.GREEN,TileType.CATS);
        Tile tile2= new Tile(TileColor.YELLOW,TileType.GAMES);
        Tile tile3= new Tile(TileColor.CYAN,TileType.TROPHIES);
        Tile tile4= new Tile(TileColor.VIOLET,TileType.PLANTS);
        Tile tile5= new Tile(TileColor.BLUE,TileType.FRAMES);
        Tile tile6= new Tile(TileColor.GREEN,TileType.CATS);
        col.addTile(tile1);
        col.addTile(tile2);
        col.addTile(tile3);
        col.addTile(tile4);
        col.addTile(tile5);
        col.addTile(tile6);
        assertTrue(col.isFull());

        //test with empty column
        col = new Column();
        assertFalse(col.isFull());
        //test with random number of tiles
        col.addTile(tile1);
        col.addTile(tile2);
        assertFalse(col.isFull());

    }

    @Test
    void setMark() {
        Column col=new Column();
        Tile tile= new Tile(TileColor.GREEN,TileType.CATS);
        for(int i=0;i<DIM;i++){
            col.addTile(tile);
        }
        col.addTile(tile);
        assertFalse(col.setMark(6));
        assertTrue(col.setMark(1));

        //set MArk on empty column
        col=new Column();
        assertFalse(col.setMark(0));
        col.addTile(tile);
        assertFalse(col.setMark(2));
        assertTrue(col.setMark(0));
    }

    @Test
    void getMark() {
        //column full
        Column col=new Column();
        Tile tile= new Tile(TileColor.GREEN,TileType.CATS);
        for(int i=0;i<DIM;i++){
            col.addTile(tile);
        }
        col.addTile(tile);
        assertEquals(0,col.getMark(1));

        //check outside column limit
        assertEquals(-1,col.getMark(6));

        //column empty
        col = new Column();
        assertEquals(-1,col.getMark(0));

        //column half full and access outside limit
        col=new Column();
        for(int i=0;i<3;i++){
            col.addTile(tile);
        }
        assertEquals(-1,col.getMark(4));
        assertEquals(0,col.getMark(2));
    }

    @Test
    void getColor() {
        Column col = new Column();

        //test with full column
        Tile tile1= new Tile(TileColor.GREEN,TileType.CATS);
        Tile tile2= new Tile(TileColor.YELLOW,TileType.GAMES);
        Tile tile3= new Tile(TileColor.CYAN,TileType.TROPHIES);
        Tile tile4= new Tile(TileColor.VIOLET,TileType.PLANTS);
        Tile tile5= new Tile(TileColor.BLUE,TileType.FRAMES);
        Tile tile6= new Tile(TileColor.GREEN,TileType.CATS);
        col.addTile(tile1);
        col.addTile(tile2);
        col.addTile(tile3);
        col.addTile(tile4);
        col.addTile(tile5);
        col.addTile(tile6);

        assertEquals(TileColor.GREEN,col.getColor(5));
        assertNotEquals(TileColor.YELLOW,col.getColor(4));
        assertNull(col.getColor(7));

        col=new Column();
        assertNull(col.getColor(0));
    }

    @Test
    void getSize() {
        Column col= new Column();
        Tile tile= new Tile(TileColor.GREEN,TileType.CATS);
        assertEquals(0,col.getSize());
        col.addTile(tile);
        assertEquals(1,col.getSize());
        for(int i=0;i<5;i++){
            col.addTile(tile);
        }
        assertEquals(DIM,col.getSize());
    }

    @Test
    void getTile() {
        Column col= new Column();
        Tile tile= new Tile(TileColor.GREEN,TileType.CATS);
        for(int i=0;i<DIM;i++){
            col.addTile(tile);
        }
        assertEquals(tile,col.getTile(5));
        assertEquals(tile,col.getTile(1));
        assertNull(col.getTile(7));

        col=new Column();
        assertNull(col.getTile(0));
        col.addTile(tile);
        assertNull(col.getTile(1));
        assertEquals(tile,col.getTile(0));

    }

    @Test
    void isEmpty() {
        Column col= new Column();
        assertTrue(col.isEmpty());
        Tile tile= new Tile(TileColor.GREEN,TileType.CATS);
        col.addTile(tile);
        assertFalse(col.isEmpty());
    }
}