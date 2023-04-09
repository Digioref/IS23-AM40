package it.polimi.ingsw.am40.Model;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.Assert.*;

class BookshelfTest {
    @Test
    public void Test(){
        JSONParser jsonParser = new JSONParser();
        FileReader reader;

        try {
            reader = new FileReader("Bookshelf.json");
            JSONObject commonGoals = (JSONObject) jsonParser.parse(reader);

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
    public void Test2(){

    }



}