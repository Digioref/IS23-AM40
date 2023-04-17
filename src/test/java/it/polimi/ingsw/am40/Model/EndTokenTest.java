package it.polimi.ingsw.am40.Model;

import static org.junit.jupiter.api.Assertions.*;

class EndTokenTest {

    Player player = new Player("pippo");
    Bookshelf bookshelf = new Bookshelf();
    EndToken endToken = new EndToken();

    // riempire una bookshelf per testare check

    //uso getCurrent o getFinalScore qui sotto?

    void testUpdateScore(){
        endToken.updateScore(player);
        assertEquals(player.getCurrentScore(), 1);

    }

    void testIsEnd(){
        Boolean end = true;
        assertEquals(endToken.isEnd(), false);
        endToken.setEnd(end);
        assertEquals(endToken.isEnd(), true);
    }

    void testSetPlayer(){
        assertEquals(endToken.getPlayer(), "DEFAULT");
        endToken.setPlayer(player);
        assertEquals(endToken.getPlayer(), player);
    }


}