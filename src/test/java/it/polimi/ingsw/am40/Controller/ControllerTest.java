package it.polimi.ingsw.am40.Controller;

import it.polimi.ingsw.am40.Model.Game;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {

    /**
     * Testing the getter of the attribute game
     */
    @Test
    void getGame() {
        Lobby lobby = new Lobby();
        Game game = new Game(4);
        Controller controller = new Controller(game, lobby);
        assertEquals(controller.getGame(), game);
    }

    /**
     * Testing the setter of the attribute game
     */
    @Test
    void setGame() {
        Lobby lobby = new Lobby();
        Game game1 = new Game(4);
        Game game2 = new Game(4);
        Controller controller = new Controller(game1, lobby);
        assertEquals(controller.getGame(), game1);
        controller.setGame(game2);
        assertEquals(controller.getGame(), game2);
    }

    /**
     * Testing the getter of the attribute GameController
     */
    @Test
    void getGameController() {
        Lobby lobby = new Lobby();
        Game game = new Game(4);
        Controller controller = new Controller(game, lobby);
        GameController gameController = new GameController(null, null);
        controller.setGameController(gameController);
        assertEquals(controller.getGameController(), gameController);
    }


    /**
     * Testing the getter of the attribute lobby
     */
    @Test
    void getLobby() {
        Lobby lobby = new Lobby();
        Game game = new Game(4);
        Controller controller = new Controller(game, lobby);
        assertEquals(controller.getLobby(), lobby);
    }
}