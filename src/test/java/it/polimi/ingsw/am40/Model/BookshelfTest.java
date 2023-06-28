package it.polimi.ingsw.am40.Model;

import it.polimi.ingsw.am40.JSONConversion.ServerArgs;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.awt.print.Book;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.Assert.*;

class BookshelfTest {

    @Test
    public void AddTile(){
        Bookshelf bookshelf=new Bookshelf();
        Tile tile = null;
        //test with null tile for each column
        for(int i=0; i<5;i++){
            assertFalse(bookshelf.addTile(null,i));
        }
        //test with null tile and non existing column
        assertFalse(bookshelf.addTile(null,-1));

        tile  = new Tile(TileColor.GREEN,TileType.CATS);

        //test with one tile for each column
        for(int i=0; i<5;i++){
            assertTrue(bookshelf.addTile(tile,i));
        }
        //test out of bound with correct tile
        assertFalse(bookshelf.addTile(tile,7));
        //bookshelf.print();
        System.out.println("\n");

        //test filling each column and also exceed column limit
        int count=0;
        for(int j=0; j<20; j++){
            for(int k=0;k<5;k++){
                if(count<5){
                    assertTrue(bookshelf.addTile(tile,k));
                }
                else {
                    assertFalse(bookshelf.addTile(tile,k));
                }
            }
            count++;
        }
        //bookshelf.print();

        //test adding null tile in a filled column
        assertFalse(bookshelf.addTile(null,0));

    }

    @Test
    public void TestAddingCalcScore(){
        JSONParser jsonParser = new JSONParser();

        try {
            InputStream is = ServerArgs.class.getClassLoader().getResourceAsStream("Bookshelf.json");
            JSONObject commonGoals = (JSONObject) jsonParser.parse(new InputStreamReader(is, StandardCharsets.UTF_8));

            JSONArray array = (JSONArray) commonGoals.get("Bookshelfs");

            for (int i = 0; i < array.size(); i++) {

                ArrayList<Tile> row;
                ArrayList<ArrayList<Tile>> book = new ArrayList<>(6);
                Integer score = null;

                JSONObject o = (JSONObject) array.get(i);

                JSONArray obj1 = (JSONArray) o.get("bookshelf");
                JSONArray obj2 = (JSONArray) o.get("assert");

                for (int j = 5; j >= 0; j--) {
                    row = new ArrayList<>(5);
                    for (int col = 0; col < 5; col++) {
                        String tile = obj1.get(5*j+col).toString();
                        switch (tile) {
                            case "Y" -> {
                                row.add(new Tile(TileColor.YELLOW, TileType.CATS));
                            }
                            case "W" -> {
                                row.add(new Tile(TileColor.WHITE, TileType.CATS));
                            }
                            case "B" -> {
                                row.add(new Tile(TileColor.BLUE, TileType.CATS));
                            }
                            case "G" -> {
                                row.add(new Tile(TileColor.GREEN, TileType.CATS));
                            }
                            case "C" -> {
                                row.add(new Tile(TileColor.CYAN, TileType.CATS));
                            }
                            case "V" -> {
                                row.add(new Tile(TileColor.VIOLET, TileType.CATS));
                            }
                            case "-" -> {
                                row.add(new Tile(TileColor.NOCOLOR, TileType.EMPTY));
                            }
                        }
                    }
                    book.add(5-j, row);
                }

                Bookshelf bookshelf = new Bookshelf();

                for (ArrayList<Tile> tiles : book) {
                    for (int j = 0; j < tiles.size(); j++) {
                        bookshelf.addTile(tiles.get(j), j);
                    }
                }
                bookshelf.print();


                for (Object value : obj2) {
                    score=(Integer.parseInt(value.toString()));
                }

                assertEquals((int) score, bookshelf.calcScore());

                System.out.println("i: " + i);

            }

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void isFull(){
        Bookshelf bookshelf=new Bookshelf();
        //test isFull() metod with empty bookshelf
        assertFalse(bookshelf.isFull());
        //test isFulll(int) metod with empty bookshelf
        for(int i=0; i<5;i++){
            assertFalse(bookshelf.isFull(i));
        }
        Tile tile =new Tile(TileColor.GREEN,TileType.CATS);
        //test with column filled with some tile but not full
        for(int i=0;i<5;i++){
            for(int j=0;j<3;j++){
                bookshelf.addTile(tile,i);
            }
            assertFalse(bookshelf.isFull(i));
        }
        assertFalse(bookshelf.isFull());

        //one column full and the others empty
        bookshelf=new Bookshelf();
        for(int i=0;i<6;i++){
            bookshelf.addTile(tile,0);
        }
        assertTrue(bookshelf.isFull(0));
        assertFalse(bookshelf.isFull());

        //fill the others with some tiles but not full
        for(int i=1;i<5;i++){
            for(int j=0;j<4;j++){
                bookshelf.addTile(tile,i);
            }
            assertFalse(bookshelf.isFull(i));
        }
        assertTrue(bookshelf.isFull(0));
        assertFalse(bookshelf.isFull());

        //test out of limit column
        assertFalse(bookshelf.isFull(-1));
        assertFalse(bookshelf.isFull(7));

        //full bookshelf
        bookshelf = new Bookshelf();
        for(int i=0;i<5;i++){
            for(int j=0;j<6;j++){
                bookshelf.addTile(tile,i);
            }
            assertTrue(bookshelf.isFull(i));
        }
        assertTrue(bookshelf.isFull());

        Player p = new Player("fra");
        p.setPersonalGoal(1);
        Assertions.assertEquals(1, p.getPersonalGoal().calcScore(bookshelf));
    }

    @Test
    public void getBookshelf(){
        Bookshelf bookshelf=new Bookshelf();
        assertNotNull(bookshelf.getBookshelf());
        Tile tile=new Tile(TileColor.GREEN,TileType.CATS);
        for(int i=0;i<5;i++){
            for(int j=0;j<6;j++) {
                bookshelf.addTile(tile, i);
            }
        }
        assertNotNull(bookshelf.getBookshelf());
        bookshelf=new Bookshelf();
        for(int j=0;j<6;j++) {
            bookshelf.addTile(tile, 0);
        }
        assertNotNull(bookshelf.getBookshelf());
    }

    @Test
    public void getTile(){
        Bookshelf bookshelf = new Bookshelf();
        assertNull(bookshelf.getTile(0,1));
        Tile tile=new Tile(TileColor.GREEN,TileType.CATS);
        for(int j=0;j<5;j++) {
            bookshelf.addTile(tile, 0);
        }
        assertNull(bookshelf.getTile(0,5));
        assertNull(bookshelf.getTile(0,6));
        bookshelf=new Bookshelf();
        for(int i=0;i<5;i++){
            for(int j=0;j<6;j++) {
                bookshelf.addTile(tile, i);
                assertNotNull(bookshelf.getTile(i,j));
            }
        }
    }
}