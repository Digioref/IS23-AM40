package it.polimi.ingsw.am40.Model;

import it.polimi.ingsw.am40.JSONConversion.ServerArgs;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class CommonGoalTest {

    @Test
    public void Test() { // GREEN, WHITE, YELLOW, BLUE, CYAN, VIOLET

        JSONParser jsonParser = new JSONParser();

        try {
            InputStream is = ServerArgs.class.getClassLoader().getResourceAsStream("CommonGoals.json");
            JSONObject commonGoals = (JSONObject) jsonParser.parse(new InputStreamReader(is, StandardCharsets.UTF_8));
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

                for (Object value : obj2) {
                    asserts.add(Integer.parseInt(value.toString()));
                }

                ArrayList<CommonGoal> cg = new ArrayList<>(12);
                for (int j = 0; j < 12; j++) {
                    cg.add(new CommonGoal(j+1, 2));
                    //System.out.println("cg: " + (j+1));
                    assertEquals((int) asserts.get(j), cg.get(j).check(bookshelf));
                }

                CommonGoal tmp = new CommonGoal(13, 2); // test if I go out of the range
                assertEquals(0,tmp.check(bookshelf));

                tmp.getCommgoaltok();
                tmp.getNum();

                System.out.println("i: " + i);

            }

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

    }

}