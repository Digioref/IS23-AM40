package it.polimi.ingsw.am40.Model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class EndTokenTest {

    Player player = new Player("pippo");
    Bookshelf bookshelf = new Bookshelf();
    EndToken endToken = new EndToken();

    // riempire una bookshelf per testare check

    //uso getCurrent o getFinalScore qui sotto?

    @Test
    void testUpdateScore(){
        player.createBookshelf();
        endToken.updateScore(player);
        assertEquals(player.getCurrentScore(), 0);

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