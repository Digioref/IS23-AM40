package it.polimi.ingsw.am40.model;

import it.polimi.ingsw.am40.model.*;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.*; // what ???

public class CommonGoal1Test {

    @Test
    public void Test() {

        Bookshelf b = new Bookshelf();
        Tile t1 = new Tile(TileColor.WHITE, TileType.CATS); // colors: GREEN, WHITE, YELLOW, BLUE, CYAN, VIOLET, NOCOLOR;
        Tile t2 = new Tile(TileColor.GREEN, TileType.CATS);

        b.addTile(t2,0);
        b.addTile(t1,1);
        b.addTile(t2,2);
        b.addTile(t1,3);
        b.addTile(t2,4);

        b.addTile(t1,0);
        b.addTile(t1,1);
        b.addTile(t1,2);
        b.addTile(t1,3);
        b.addTile(t1,4);

        b.addTile(t2,0);
        b.addTile(t2,1);
        b.addTile(t1,2);
        b.addTile(t2,3);
        b.addTile(t2,4);

        b.addTile(t2,0);
        b.addTile(t2,1);
        b.addTile(t1,2);
        b.addTile(t2,3);
        b.addTile(t2,4);

        b.addTile(t2,0);
        b.addTile(t2,1);
        b.addTile(t1,2);
        b.addTile(t2,3);
        b.addTile(t2,4);

        b.addTile(t1,2);
/*
        b.addTile(t1,0);
        b.addTile(t1,1);
        b.addTile(t1,2);
        b.addTile(t1,3);
        b.addTile(t1,4);
*/




        /*
        b.addTile(t1,0);
        b.addTile(t1,0);
        b.addTile(t1,1);
        b.addTile(t1,1);
        b.addTile(t1,0);
        b.addTile(t1,0);
        b.addTile(t1,1);
        b.addTile(t1,1);

         */


        //System.out.println(b.getBookshelf().get(0).getColumn().get(0).getColor().toString());
        //System.out.println(b.getBookshelf());




        //CommonGoal1 c1 = new CommonGoal1(2);
        CommonGoal3 c1 = new CommonGoal3(2);
        assertEquals(8, c1.check(b));
        assertEquals(4, c1.check(b));



    }
}