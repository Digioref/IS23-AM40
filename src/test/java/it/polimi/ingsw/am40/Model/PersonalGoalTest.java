package it.polimi.ingsw.am40.Model;

import it.polimi.ingsw.am40.CLI.Colors;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

class PersonalGoalTest {

    PersonalGoal pers;

    @BeforeEach
    public void setup() {
        Player p = new Player("fra");
        p.setPersonalGoal(1);
        pers = p.getPersonalGoal();
    }

    @Test
    void calcScore() {
        Bookshelf bookshelf = new Bookshelf();
        Tile tile = new Tile(TileColor.CYAN, TileType.CATS);

        for(int i=0;i<5;i++){
            for(int j=0;j<6;j++){
                bookshelf.addTile(tile,i);
            }
            assertTrue(bookshelf.isFull(i));
        }
        assertTrue(bookshelf.isFull());

        Assertions.assertEquals(1, pers.calcScore(bookshelf));

    }

    @Test
    void setPos() {
        ArrayList<Position> p = new ArrayList<>();
        p.add(new Position(0,0));
        pers.setPos(p);
        Assertions.assertEquals(p,pers.getPos());

    }

    @Test
    void setColor() {
        ArrayList<TileColor> c = new ArrayList<>();
        c.add(TileColor.WHITE);
        pers.setColor(c);
        Assertions.assertEquals(c,pers.getColor());

    }

    @Test
    void testToString() {
        PersonalGoal p = new PersonalGoal(0);
        Assertions.assertEquals("PersonalGoal", p.toString());
    }

    @Test
    void getKey() {
        PersonalGoal p = new PersonalGoal(0);
        Assertions.assertEquals(0,p.getKey());
    }
}