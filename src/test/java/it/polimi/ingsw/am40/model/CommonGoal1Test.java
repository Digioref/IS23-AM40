package it.polimi.ingsw.am40.model;

import it.polimi.ingsw.am40.model.*;
import org.junit.jupiter.api.Test;

public class CommonGoal1Test {

    @Test
    public void Test() {

        Bookshelf b = new Bookshelf();
        Tile t1 = new Tile(TileColor.WHITE, TileType.CATS); // colors: GREEN, WHITE, YELLOW, BLUE, CYAN, VIOLET, NOCOLOR;
        b.addTile(t1,0);
        b.addTile(t1,0);
        b.addTile(t1,1);
        b.addTile(t1,1);
        b.addTile(t1,0);
        b.addTile(t1,0);
        b.addTile(t1,1);
        //b.addTile(t1,1);


        System.out.println(b.getBookshelf().get(0).getColumn().get(0).getColor().toString());
        System.out.println(b.getBookshelf());




        //CommonGoal1 c1 = new CommonGoal1(2); // errors 10, 11, 12
        CommonGoal3 c1 = new CommonGoal3(2); // errors 10
        System.out.println(c1.check(b));
        System.out.println(c1.check(b));


    }
}