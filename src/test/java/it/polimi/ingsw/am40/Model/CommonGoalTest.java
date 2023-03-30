package it.polimi.ingsw.am40.Model;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class CommonGoalTest {

    @Test
    public void Test() { // GREEN, WHITE, YELLOW, BLUE, CYAN, VIOLET

        JSONParser jsonParser = new JSONParser();
        FileReader reader;

        try {
            reader = new FileReader("CommonGoals.json");
            JSONObject commonGoals = (JSONObject) jsonParser.parse(reader);

            JSONArray array = (JSONArray) commonGoals.get("CommonGoals");

            for (int i = 0; i < array.size(); i++) {

                ArrayList<Tile> row;
                ArrayList<ArrayList<Tile>> book = new ArrayList<>(6);
                ArrayList<Integer> asserts = new ArrayList<>(7);

                JSONObject o = (JSONObject) array.get(i);

                JSONArray obj1 = (JSONArray) o.get("bookshelf");
                JSONArray obj2 = (JSONArray) o.get("asserts");

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

                for (Object value : obj2) {
                    asserts.add(Integer.parseInt(value.toString()));
                }

                ArrayList<CommonGoal> cg = new ArrayList<>(12);
                for (int j = 0; j < 12; j++) {
                    cg.add(new CommonGoal(j+1, 2));
                    //System.out.println("cg: " + (j+1));
                    assertEquals((int) asserts.get(j), cg.get(j).check(bookshelf));
                }

                System.out.println("i: " + i);

            }

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void TestEmpty() {

        JSONParser jsonParser = new JSONParser();
        FileReader reader;

        try {
            reader = new FileReader("CommonGoalsEmpty.json");
            JSONObject commonGoals = (JSONObject) jsonParser.parse(reader);

            JSONArray array = (JSONArray) commonGoals.get("CommonGoals");

            for (int i = 0; i < array.size(); i++) {

                ArrayList<Tile> row;
                ArrayList<ArrayList<Tile>> book = new ArrayList<>(6);
                ArrayList<Integer> asserts = new ArrayList<>(7);

                JSONObject o = (JSONObject) array.get(i);

                JSONArray obj1 = (JSONArray) o.get("bookshelf");
                JSONArray obj2 = (JSONArray) o.get("asserts");

                Bookshelf bookshelf = new Bookshelf();

                for (int k = 0; k < obj1.size(); k++) {
                    JSONObject col = (JSONObject) obj1.get(k);
                    JSONArray column = (JSONArray) col.get("col");

                    for (Object value : column) {
                        switch (value.toString()) {
                            case "Y" -> {
                                bookshelf.addTile(new Tile(TileColor.YELLOW, TileType.CATS), k);
                            }
                            case "W" -> {
                                bookshelf.addTile(new Tile(TileColor.WHITE, TileType.CATS), k);
                            }
                            case "B" -> {
                                bookshelf.addTile(new Tile(TileColor.BLUE, TileType.CATS), k);
                            }
                            case "G" -> {
                                bookshelf.addTile(new Tile(TileColor.GREEN, TileType.CATS), k);
                            }
                            case "C" -> {
                                bookshelf.addTile(new Tile(TileColor.CYAN, TileType.CATS), k);
                            }
                            case "V" -> {
                                bookshelf.addTile(new Tile(TileColor.VIOLET, TileType.CATS), k);
                            }
                        }
                    }
                }

                System.out.println("i: " + i);

                for (Object value : obj2) {
                    asserts.add(Integer.parseInt(value.toString()));
                }

                ArrayList<CommonGoal> cg = new ArrayList<>(12);
                for (int j = 0; j < 12; j++) {
                    cg.add(new CommonGoal(j+1, 2));
                    //System.out.println("cg: " + (j+1));
                    assertEquals((int) asserts.get(j), cg.get(j).check(bookshelf));
                }

            }

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }
}