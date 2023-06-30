package it.polimi.ingsw.am40.Model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class EndTokenTest {

    Player player = new Player("pippo");
    Bookshelf bookshelf = new Bookshelf();
    EndToken endToken = new EndToken();

    @Test
    void testUpdateScore(){
        Player p = new Player("DEFAULT");
        p.createBookshelf();

        endToken.updateScore(p);
        assertEquals(p.getCurrentScore(), 0);

        Tile t = new Tile(TileColor.CYAN,TileType.CATS);
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 6; j++) {
                p.getBookshelf().addTile(t, i);
            }
        }
        System.out.println(endToken.check(p));
        endToken.updateScore(p);


    }

    @Test
    void testIsEnd(){
        Boolean end = true;
        assertEquals(false, endToken.isEnd());
        endToken.setEnd(end);
        assertEquals(true, endToken.isEnd());
    }

    @Test
    void testSetPlayer(){
        assertEquals("DEFAULT", endToken.getPlayer().getNickname());
        endToken.setPlayer(player);
        assertEquals(endToken.getPlayer(), player);
    }


}