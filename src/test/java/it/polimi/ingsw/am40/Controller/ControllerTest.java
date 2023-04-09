package it.polimi.ingsw.am40.Controller;

import it.polimi.ingsw.am40.Model.Game;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {
    /**
     * Tests the constructor, getGame, setGame and getGameController
     */
    @Test
    void Test () {
        Game game1 = new Game(2);
        Controller c1 = new Controller(game1);

        assertEquals(game1, c1.getGame());

        Game game2 = new Game(4);
        c1.setGame(game2);

        assertEquals(game2, c1.getGame());

        assertNotEquals(c1.getGameController(), null);
    }

}