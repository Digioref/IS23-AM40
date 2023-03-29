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

import static org.junit.Assert.*;

public class CommonGoalTest2 {

    @Test
    public void Test() { // GREEN, WHITE, YELLOW, BLUE, CYAN, VIOLET

        JSONParser jsonParser = new JSONParser();
        FileReader reader;

        try {
            reader = new FileReader("CommonGoals2.json");
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

                CommonGoal1 c1 = new CommonGoal1(2);
                CommonGoal2 c2 = new CommonGoal2(2);
                CommonGoal3 c3 = new CommonGoal3(2);
                CommonGoal4 c4 = new CommonGoal4(2);
                CommonGoal5 c5 = new CommonGoal5(2);
                CommonGoal6 c6 = new CommonGoal6(2);
                CommonGoal7 c7 = new CommonGoal7(2);
                CommonGoal8 c8 = new CommonGoal8(2);
                CommonGoal9 c9 = new CommonGoal9(2);
                CommonGoal10 c10 = new CommonGoal10(2);
                CommonGoal11 c11 = new CommonGoal11(2);
                CommonGoal12 c12 = new CommonGoal12(2);

                System.out.println("i: " + i);
                System.out.println(obj2.size());

                for (Object value : obj2) {
                    asserts.add(Integer.parseInt(value.toString()));
                }

                assertEquals((int) asserts.get(0), c1.check(bookshelf));
                assertEquals((int) asserts.get(1), c2.check(bookshelf));
                assertEquals((int) asserts.get(2), c3.check(bookshelf));
                assertEquals((int) asserts.get(3), c4.check(bookshelf));
                assertEquals((int) asserts.get(4), c5.check(bookshelf));
                assertEquals((int) asserts.get(5), c6.check(bookshelf));
                assertEquals((int) asserts.get(6), c7.check(bookshelf));
                assertEquals((int) asserts.get(7), c8.check(bookshelf));
                assertEquals((int) asserts.get(8), c9.check(bookshelf));
                assertEquals((int) asserts.get(9), c10.check(bookshelf));
                assertEquals((int) asserts.get(10), c11.check(bookshelf));
                assertEquals((int) asserts.get(11), c12.check(bookshelf));

            }

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void Test2() { // we have to do some test without the json to test the empty cells

        Bookshelf bookshelf = new Bookshelf();
        Tile g = new Tile(TileColor.GREEN, TileType.CATS); // colors: GREEN, WHITE, YELLOW, BLUE, CYAN, VIOLET, NOCOLOR;
        Tile w = new Tile(TileColor.WHITE, TileType.CATS);
        Tile y = new Tile(TileColor.YELLOW, TileType.CATS);
        Tile b = new Tile(TileColor.BLUE, TileType.CATS);
        Tile c = new Tile(TileColor.CYAN, TileType.CATS);
        Tile v = new Tile(TileColor.VIOLET, TileType.CATS);

        ArrayList<Tile> row5 = new ArrayList<>(List.of(g, g, g, g, g));
        ArrayList<Tile> row4 = new ArrayList<>(List.of(g, g, g, g, g));
        ArrayList<Tile> row3 = new ArrayList<>(List.of(g, g, g, g, g));
        ArrayList<Tile> row2 = new ArrayList<>(List.of(g, g, g, g, g));
        ArrayList<Tile> row1 = new ArrayList<>(List.of(g, g, g, g, g));
        ArrayList<Tile> row0 = new ArrayList<>(List.of(g, g, g, g, g));

        ArrayList<ArrayList<Tile>> structure = new ArrayList<>(List.of(row0, row1, row2, row3, row4, row5));

        for (ArrayList<Tile> tiles : structure) {
            for (int j = 0; j < tiles.size(); j++) {
                bookshelf.addTile(tiles.get(j), j);
            }
        }

        CommonGoal1 c1 = new CommonGoal1(2);
        CommonGoal2 c2 = new CommonGoal2(2);
        CommonGoal3 c3 = new CommonGoal3(2);
        CommonGoal4 c4 = new CommonGoal4(2);
        CommonGoal5 c5 = new CommonGoal5(2);
        CommonGoal6 c6 = new CommonGoal6(2);

        assertEquals(8, c1.check(bookshelf));
        assertEquals(0, c2.check(bookshelf));
        assertEquals(8, c3.check(bookshelf));
        assertEquals(8, c4.check(bookshelf));
        assertEquals(8, c5.check(bookshelf));
        assertEquals(0, c6.check(bookshelf));


    }
}