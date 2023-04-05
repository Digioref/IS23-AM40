package it.polimi.ingsw.am40.Controller;

import it.polimi.ingsw.am40.Model.Game;
import it.polimi.ingsw.am40.Model.Player;
import it.polimi.ingsw.am40.Model.Position;
import it.polimi.ingsw.am40.Network.ClientHandler;
import it.polimi.ingsw.am40.Network.VirtualView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.Socket;

import static org.junit.jupiter.api.Assertions.*;

class GameControllerTest {
        Game game1;
        Controller controller;
        Socket socket;
        ClientHandler clientHandler;
        VirtualView virtualView;
        Position position;
        Player player;
        GameController gameController;
    @BeforeEach
    void setup(){
        game1 = new Game(2);
        controller = new Controller(game1);
        socket = new Socket();
        clientHandler = new ClientHandler(socket)
        virtualView = new VirtualView("pippo", clientHandler, controller);
        position = new Position(1,1);
        player = new Player("pippo");
        gameController = new GameController(game1);


    }
    @Test
    void testSelectTile() {
        game1.addPlayer(player);
        gameController.selectTile(virtualView, position);

        // assertEquals(game1., true);

    }

    @Test
    void testPickTiles() {
    }

    @Test
    void testNotConfirmSelection() {
    }

    @Test
    void testOrder() {
    }

    @Test
    void testInsert() {
    }

    @Test
    void testSetController() {
    }
}