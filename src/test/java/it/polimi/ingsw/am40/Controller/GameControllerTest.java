package it.polimi.ingsw.am40.Controller;

import it.polimi.ingsw.am40.Model.EndToken;
import it.polimi.ingsw.am40.Model.Game;
import it.polimi.ingsw.am40.Model.Player;
import it.polimi.ingsw.am40.Network.*;
import it.polimi.ingsw.am40.Model.Position;
import it.polimi.ingsw.am40.Network.ClientHandler;
import it.polimi.ingsw.am40.Network.GameServer;
import it.polimi.ingsw.am40.Network.RMI.RMIServer;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class GameControllerTest {

    @Test
    void selectTile() {
        Controller controller = new Controller(null, null);
        VirtualView virtualView = new VirtualView("marco", null, controller);
        Position p = new Position(1,1);
        GameController gameController = new GameController(null, controller);
        Game game = new Game(2);
        Player player = new Player("marco");
        gameController.setGame(game);
        game.setCurrentPlayer(player);


        gameController.selectTile(virtualView, p);

        Player player2 = new Player("filippo");
        game.setCurrentPlayer(player2);
        gameController.selectTile(virtualView, p);
    }

    @Test
    void pickTiles() {
        Controller controller = new Controller(null, null);
        VirtualView virtualView = new VirtualView("marco", null, controller);
        GameController gameController = new GameController(null, controller);
        Game game = new Game(2);
        Player player = new Player("marco");
        gameController.setGame(game);
        game.setCurrentPlayer(player);


        gameController.pickTiles(virtualView);

        Player player2 = new Player("filippo");
        game.setCurrentPlayer(player2);
        gameController.pickTiles(virtualView);
    }

    @Test
    void notConfirmSelection() {
        Controller controller = new Controller(null, null);
        VirtualView virtualView = new VirtualView("marco", null, controller);
        GameController gameController = new GameController(null, controller);
        Game game = new Game(2);
        Player player = new Player("marco");
        gameController.setGame(game);
        game.setCurrentPlayer(player);


        gameController.notConfirmSelection(virtualView);

        Player player2 = new Player("filippo");
        game.setCurrentPlayer(player2);
        gameController.notConfirmSelection(virtualView);
    }

    @Test
    void order() {
        Controller controller = new Controller(null, null);
        VirtualView virtualView = new VirtualView("marco", null, controller);
        GameController gameController = new GameController(null, controller);
        Game game = new Game(2);
        Player player = new Player("marco");
        gameController.setGame(game);
        game.setCurrentPlayer(player);
        ArrayList<Integer> lista_posizioni = new ArrayList<>();
        lista_posizioni.add(1);
        lista_posizioni.add(2);


        gameController.order(virtualView,lista_posizioni);

        Player player2 = new Player("filippo");
        game.setCurrentPlayer(player2);
        gameController.order(virtualView,lista_posizioni);
    }

    @Test
    void insert() {
        Controller controller = new Controller(null, null);
        VirtualView virtualView = new VirtualView("marco", null, controller);
        GameController gameController = new GameController(null, controller);
        Game game = new Game(2);
        Player player = new Player("marco");
        gameController.setGame(game);
        game.setCurrentPlayer(player);

        gameController.insert(virtualView,3);

        EndToken endToken = new EndToken();

        gameController.getGame().setEnd();
        gameController.getGame().setEndToken(endToken);
        gameController.insert(virtualView,3);

        Controller controller2 = new Controller(null, null);
        VirtualView virtualView2 = new VirtualView("pietro", null, controller);
        GameController gameController2 = new GameController(null, controller);
        Game game2 = new Game(2);
        Player player2 = new Player("marco");
        gameController2.setGame(game2);
        gameController2.getGame().setCurrentPlayer(player2);

        gameController2.insert(virtualView2,3);


    }

    @Test
    void chat() {
        Controller controller = new Controller(null, null);
        VirtualView virtualView = new VirtualView("marco", null, controller);
        GameController gameController = new GameController(null, controller);
        Game game = new Game(3);
        Player player = new Player("marco");
        gameController.setGame(game);
        game.setCurrentPlayer(player);
        game.getObservers().add(virtualView);

        ClientHandler clientHandler = new ClientHandler();
        Writer out = new Writer() {
            @Override
            public void write(char[] cbuf, int off, int len) throws IOException {}
            @Override
            public void flush() throws IOException {}
            @Override
            public void close() throws IOException {}
        };
        PrintWriter out1 = new PrintWriter(out);
        clientHandler.setOut(out1);
        virtualView.setClientHandler(clientHandler);
        Player player1 = new Player("gianni");
        Player player2 = new Player("filippo");
        Player player3 = new Player("marco");
        gameController.getGame().addPlayer(player1);
        gameController.getGame().addPlayer(player2);
        gameController.getGame().addPlayer(player3);

        gameController.chat("marco", "ciao", "filippo");
        gameController.chat("all", "ciao", "filippo");


        Controller controller2 = new Controller(null, null);
        VirtualView virtualView2 = new VirtualView("maria", null, controller);
        GameController gameController2 = new GameController(null, controller2);
        Game game2 = new Game(3);
        gameController2.setGame(game2);
        game2.setCurrentPlayer(player2);
        game2.getObservers().add(virtualView2);

        ClientHandler clientHandler2 = new ClientHandler();
        Writer out2 = new Writer() {
            @Override
            public void write(char[] cbuf, int off, int len) throws IOException {}
            @Override
            public void flush() throws IOException {}
            @Override
            public void close() throws IOException {}
        };
        PrintWriter out3 = new PrintWriter(out2);
        clientHandler2.setOut(out3);
        virtualView2.setClientHandler(clientHandler2);
        Player player4 = new Player("antonio");
        Player player5 = new Player("giuseppe");
        Player player6 = new Player("maria");
        gameController2.getGame().addPlayer(player4);
        gameController2.getGame().addPlayer(player5);
        gameController2.getGame().addPlayer(player6);


        gameController2.chat("maria", "ciao", "maria");
        gameController2.chat("all", "ciao", "maria");


        gameController.chat("alberto", "ciao", "alberto");
        gameController.chat("alberto", "ciao", "maria");


    }

    @Test
    void getChat() {
        Controller controller = new Controller(null, null);
        VirtualView virtualView = new VirtualView("marco", null, controller);
        Game game = new Game(2);
        Player player = new Player("marco");
        game.addPlayer(player);
        GameController gameController = new GameController(game, controller);
        gameController.getGame().getObservers().add(virtualView);
        ClientHandler clientHandler = new ClientHandler();
        Writer out = new Writer() {
            @Override
            public void write(char[] cbuf, int off, int len) throws IOException {}
            @Override
            public void flush() throws IOException {}
            @Override
            public void close() throws IOException {}
        };
        PrintWriter out2 = new PrintWriter(out);
        virtualView.setClientHandler(clientHandler);
        clientHandler.setOut(out2);

        gameController.getChat("marco");
    }


    @Test
    void disconnectPlayer() {
        Game game = new Game(3);
        Player luca = new Player("Luca");
        Player francesco = new Player("Francesco");
        Player giovanni = new Player("Giovanni");

        game.addPlayer(luca);
        game.addPlayer(francesco);
        game.addPlayer(giovanni);

        Lobby lobby = new Lobby();
        Controller controller = new Controller(game, lobby);
        GameController gameController = new GameController(game, controller);

        game.setCurrentPlayer(luca);
        EndToken endToken = new EndToken();
        game.setEndToken(endToken);
        gameController.disconnectPlayer("Luca");
        gameController.reconnect("Luca");
        gameController.reconnect("Giacomo");

        VirtualView v_luca = new VirtualView("luca", null, controller);
        game.getObservers().add(v_luca);

        gameController.disconnectPlayer("Luca");
        gameController.disconnectPlayer("Francesco");

    }


    @Test
    void getGame() {
        Game game = new Game(2);
        GameController gameController = new GameController(game, null);
        assertEquals(gameController.getGame(), game);
    }

    @Test
    void getController() {
       Controller controller = new Controller(null, null);
        GameController gameController = new GameController(null, controller);
        assertEquals(gameController.getController(), controller);
    }


    @Test
    void reconnect() {
    Game game = new Game(3);
    Player luca = new Player("Luca");
    Player francesco = new Player("Francesco");
    Player giovanni = new Player("Giovanni");

    game.addPlayer(luca);
    game.addPlayer(francesco);
    game.addPlayer(giovanni);

    Lobby lobby = new Lobby();
    Controller controller = new Controller(game, lobby);
    GameController gameController = new GameController(game, controller);

    game.setCurrentPlayer(luca);
    EndToken endToken = new EndToken();
    game.setEndToken(endToken);
    gameController.disconnectPlayer("Luca");
    gameController.reconnect("Luca");
    gameController.reconnect("Giacomo");


        gameController.disconnectPlayer("Luca");
    gameController.disconnectPlayer("Francesco");
    gameController.reconnect("Luca");

    }

    @Test
    void setGame() {
        Game game = new Game(2);
        GameController gameController = new GameController(game, null);
        Game game2 = new Game(4);
        gameController.setGame(game2);
        assertEquals(gameController.getGame(), game2);
    }


}