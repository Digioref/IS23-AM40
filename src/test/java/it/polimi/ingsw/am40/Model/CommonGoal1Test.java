package it.polimi.ingsw.am40.Model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*; // what ???

public class CommonGoal1Test {

    @Test
    public void Test() { // GREEN, WHITE, YELLOW, BLUE, CYAN, VIOLET

        Bookshelf bookshelf = new Bookshelf();
        Tile g = new Tile(TileColor.GREEN, TileType.CATS); // colors: GREEN, WHITE, YELLOW, BLUE, CYAN, VIOLET, NOCOLOR;
        Tile w = new Tile(TileColor.WHITE, TileType.CATS);
        Tile y = new Tile(TileColor.YELLOW, TileType.CATS);
        Tile b = new Tile(TileColor.BLUE, TileType.CATS);
        Tile c = new Tile(TileColor.CYAN, TileType.CATS);
        Tile v = new Tile(TileColor.VIOLET, TileType.CATS);

        ArrayList<Tile> row5 = new ArrayList<>(List.of(g,g,g,g,g));
        ArrayList<Tile> row4 = new ArrayList<>(List.of(g,g,g,g,g));
        ArrayList<Tile> row3 = new ArrayList<>(List.of(g,g,g,g,g));
        ArrayList<Tile> row2 = new ArrayList<>(List.of(g,g,g,g,g));
        ArrayList<Tile> row1 = new ArrayList<>(List.of(g,g,g,g,g));
        ArrayList<Tile> row0 = new ArrayList<>(List.of(g,g,g,g,g));

        ArrayList<ArrayList<Tile>> structure = new ArrayList<>(List.of(row0, row1, row2, row3, row4, row5));

        for (int i = 0; i < structure.size(); i++) {
            for (int j = 0; j < structure.get(i).size(); j++) {
                bookshelf.addTile(structure.get(i).get(j),j);
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