package it.polimi.ingsw.am40.Model;

import it.polimi.ingsw.am40.Controller.Controller;
import it.polimi.ingsw.am40.Controller.Lobby;
import it.polimi.ingsw.am40.Network.ClientHandler;
import it.polimi.ingsw.am40.Network.GameServer;
import it.polimi.ingsw.am40.Network.VirtualView;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class GameTest{

    private Game game;

    @BeforeEach
    public void setup() {
        game = new Game(2);
        Player player1 = new Player("pippo");
        Player player2 = new Player("dani");
        game.addPlayer(player1);
        game.addPlayer(player2);
        game.configureGame();
        /*
        Lobby l = new Lobby();
        ClientHandler h = new ClientHandler(new Socket(),new GameServer());
        Controller c = new Controller(game, l);
        VirtualView v = new VirtualView("pippo", h, c);
        game.register(v);

         */

        ClientHandler clientHandler = new ClientHandler();
        Controller controller = new Controller(game, null);
        VirtualView virtualView = new VirtualView("marco", clientHandler);
        game.getObservers().add(virtualView);

        Writer out = new Writer() {
            @Override
            public void write(char[] cbuf, int off, int len) throws IOException {}
            @Override
            public void flush() throws IOException {}
            @Override
            public void close() throws IOException {}
        };
        PrintWriter out1 = new PrintWriter(out);
        virtualView.setClientHandler(clientHandler);
        clientHandler.setOut(out1);
    }

    @Test
    void addPlayer() {
        ArrayList<Player> players = game.getPlayers();
        Assertions.assertEquals(2, players.size());
        Assertions.assertEquals("pippo", players.get(0).getNickname());
        Assertions.assertEquals("dani", players.get(1).getNickname());
    }


    @Test
    void configureGame() {
        ArrayList<CommonGoal> commonGoals = game.getCommonGoals();
        Assertions.assertEquals(12, commonGoals.size());

        ArrayList<CommonGoal> currentComGoals = game.getCurrentComGoals();
        Assertions.assertEquals(0, currentComGoals.size());

        Bag bag = game.getBag();
        Assertions.assertNotNull(bag);

        Board board = game.getBoard();
        Assertions.assertNotNull(board);

        EndToken endToken = game.getEndToken();
        Assertions.assertNotNull(endToken);

        ArrayList<Player> players = game.getPlayers();
        for (Player player : players) {
            Assertions.assertNotNull(player.getBookshelf());
        }
    }

    @Test
    void startGame() {
        game.startGame();

        Player currentPlayer = game.getCurrentPlayer();
        Assertions.assertNotNull(currentPlayer);
        Assertions.assertNotNull(currentPlayer.getNext());

        game.getObservers().clear();
        ArrayList<VirtualView> observers = game.getObservers();
        Assertions.assertEquals(0, observers.size());

        ArrayList<Player> players = game.getPlayers();
        for (Player player : players) {
            Assertions.assertNotNull(player.getBoard());
            Assertions.assertNotNull(player.getPersonalGoal());
        }

        Assertions.assertNotNull(game.getTurn());
    }

    @Test
    void createTiles() {
        game.createTiles();
        game.startGame();
    }

    @Test
    void assignPersonalGoal() {
        game.createTiles();
        game.startGame();
        game.assignPersonalGoal();
        Assertions.assertNotEquals(game.getPlayers().get(0).getPersonalGoal(),game.getPlayers().get(1).getPersonalGoal());
    }

    @Test
    void nextPlayer() {
        game.createTiles();
        game.startGame();
        game.assignPersonalGoal();
        String prev = game.getCurrentPlayer().getNickname();
        game.nextPlayer();
        Assertions.assertEquals(prev, game.getCurrentPlayer().getNickname());
        game.setTurn(TurnPhase.ENDTURN);
        game.nextPlayer();
    }

    @Test
    void checkDisconnection() {
        Player player = new Player("marco");
        game.addPlayer(player);
        player.setDisconnected(true);
        Assertions.assertEquals(1, game.checkDisconnection());

    }

    @Test
    void startTimer() {
        Player player1 = new Player("gianni");
        Player player2 = new Player("gigi");


        ClientHandler clientHandler1 = new ClientHandler();
        ClientHandler clientHandler2 = new ClientHandler();
        Game game = new Game(2);
        Lobby lobby = new Lobby();
        Controller controller = new Controller(game, lobby);
        VirtualView virtualView1 = new VirtualView("gianni", clientHandler1);
        VirtualView virtualView2 = new VirtualView("gigi", clientHandler2);

        game.getObservers().add(virtualView1);
        game.getObservers().add(virtualView2);

        game.addPlayer(player1);
        game.addPlayer(player2);

        Writer out = new Writer() {
            @Override
            public void write(char[] cbuf, int off, int len) throws IOException {}
            @Override
            public void flush() throws IOException {}
            @Override
            public void close() throws IOException {}
        };
        PrintWriter out1 = new PrintWriter(out);
        virtualView1.setClientHandler(clientHandler1);
        clientHandler1.setOut(out1);
        PrintWriter out2 = new PrintWriter(out);
        virtualView2.setClientHandler(clientHandler2);
        clientHandler2.setOut(out2);


        game.startTimer();

        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    void startTurn(){
        Player player = new Player("marco");
        game.setTurn(TurnPhase.START);
        Board board = new Board();
        game.setBoard(board);
        game.setCurrentPlayer(player);

        game.setFirstPlayer(player);

        CommonGoal c1 = new CommonGoal(1, 1);
        CommonGoal c2 = new CommonGoal(2,1);
        game.getCurrentComGoals().add(c1);
        game.getCurrentComGoals().add(c2);
        game.startTurn();

    }

    @Test
    void stopTimer() {
        game.startTimer();
        Assertions.assertEquals(true, game.isTimerStarted());
        game.stopTimer();
        Assertions.assertEquals(false, game.isTimerStarted());
    }

    @Test
    void checkEndGame() {
    Game game = new Game(2);
    game.setEnd();
    EndToken endToken = new EndToken();
    game.setEndToken(endToken);
    game.getEndToken().setEnd(true);

    Player player1 = new Player("gigi");
    Player player2 = new Player("mario");

    game.setTurn(TurnPhase.ENDTURN);


    game.setCurrentPlayer(player1);
    player1.setNext(player2);
    game.setFirstPlayer(player2);

    game.checkEndGame();

    game.getEndToken().setEnd(false);

    game.checkEndGame();

    }

    @Test
    void updatePickableTiles() {
        Game game = new Game(2);
        Board board = new Board();
        game.setBoard(board);

        Position position1 = new Position(1,1);
        board.getPickableTiles().add(position1);

        Player player = new Player("luigi");
        game.setCurrentPlayer(player);

        game.setTurn(TurnPhase.SELECTION);
        game.updatePickableTiles(new Position(1,1));

        ClientHandler clientHandler = new ClientHandler();
        Controller controller = new Controller(game, null);
        VirtualView virtualView = new VirtualView("luigi", clientHandler);


        Writer out = new Writer() {
            @Override
            public void write(char[] cbuf, int off, int len) throws IOException {}
            @Override
            public void flush() throws IOException {}
            @Override
            public void close() throws IOException {}
        };
        PrintWriter out1 = new PrintWriter(out);
        virtualView.setClientHandler(clientHandler);
        clientHandler.setOut(out1);

        game.getObservers().add(virtualView);

        game.setTurn(TurnPhase.SELECTION);

        game.updatePickableTiles(new Position(0,0));

        game.setTurn(TurnPhase.PICK);

        game.updatePickableTiles(new Position(1,1));


    }

    @Test
    void removeSelectedTiles() {
        game.startGame();

        game.setTurn(TurnPhase.SELECTION);
        game.removeSelectedTiles();

        Player marco = new Player("marco");

        game.setCurrentPlayer(marco);
        ClientHandler clientHandler = new ClientHandler();
        Controller controller = new Controller(game, null);
        VirtualView virtualView = new VirtualView("marco", clientHandler);
        game.getObservers().add(virtualView);

        Writer out = new Writer() {
            @Override
            public void write(char[] cbuf, int off, int len) throws IOException {}
            @Override
            public void flush() throws IOException {}
            @Override
            public void close() throws IOException {}
        };
        PrintWriter out1 = new PrintWriter(out);
        virtualView.setClientHandler(clientHandler);
        clientHandler.setOut(out1);

        game.setTurn(TurnPhase.PICK);
        game.removeSelectedTiles();


    }

    @Test
    void pickTiles() {
        Game game = new Game(2);
        game.setTurn(TurnPhase.PICK);

        Player player = new Player("marco");
        game.setCurrentPlayer(player);
        Bookshelf bookshelf = new Bookshelf();
        player.setBookshelf(bookshelf);

        Board board = new Board();
        player.setBoard(board);

        Position position = new Position(1,1);
        player.getSelectedPositions().add(position);

        game.pickTiles();


        Game game2 = new Game(2);
        game2.setTurn(TurnPhase.PICK);

        Player player2 = new Player("marco");
        game.setCurrentPlayer(player2);
        Bookshelf bookshelf2 = new Bookshelf();
        player2.setBookshelf(bookshelf2);

        Board board2 = new Board();
        player.setBoard(board2);

        game2.setCurrentPlayer(player2);

        // riempio il tile delle posizioni con più di 6 elementi per non farlo inserire nella bookshelf
        player2.getSelectedPositions().add(position);
        player2.getSelectedPositions().add(position);
        player2.getSelectedPositions().add(position);
        player2.getSelectedPositions().add(position);
        player2.getSelectedPositions().add(position);
        player2.getSelectedPositions().add(position);
        player2.getSelectedPositions().add(position);
        player2.getSelectedPositions().add(position);
        player2.getSelectedPositions().add(position);

        ClientHandler clientHandler = new ClientHandler();
        Controller controller = new Controller(game, null);
        VirtualView virtualView = new VirtualView("marco", clientHandler);

        Writer out = new Writer() {
            @Override
            public void write(char[] cbuf, int off, int len) throws IOException {}
            @Override
            public void flush() throws IOException {}
            @Override
            public void close() throws IOException {}
        };
        PrintWriter out1 = new PrintWriter(out);
        virtualView.setClientHandler(clientHandler);
        clientHandler.setOut(out1);
        game2.getObservers().add(virtualView);

        game2.pickTiles();

        game2.setTurn(TurnPhase.SELECTION);
        game2.pickTiles();
    }

    @Test
    void setOrder() {
        Player p1 = new Player("marco");
        game.setCurrentPlayer(p1);
        game.setTurn(TurnPhase.ORDER);

        ArrayList<Integer> pos = new ArrayList<>();
        pos.add(1);
        pos.add(2);

        Tile tile = new Tile(TileColor.BLUE, TileType.CATS);
        p1.getTilesPicked().add(tile);
        p1.getTilesPicked().add(tile);
        game.setOrder(pos);



        ClientHandler clientHandler = new ClientHandler();
        Controller controller = new Controller(game, null);
        VirtualView virtualView = new VirtualView("marco", clientHandler);
        game.getObservers().add(virtualView);

        Writer out = new Writer() {
            @Override
            public void write(char[] cbuf, int off, int len) throws IOException {}
            @Override
            public void flush() throws IOException {}
            @Override
            public void close() throws IOException {}
        };
        PrintWriter out1 = new PrintWriter(out);
        virtualView.setClientHandler(clientHandler);
        clientHandler.setOut(out1);

        game.setOrder(pos);

        game.setTurn(TurnPhase.ORDER);

        ArrayList<Integer> pos1 = new ArrayList<>();
        pos1.add(3);
        pos1.add(1);
        pos1.add(2);
        game.setOrder(pos1);
    }

    @Test
    void insertInBookshelf() {
        Player p1 = new Player("marco");
        p1.createBookshelf();
        p1.setPersonalGoal(1);

        CommonGoal c1 = new CommonGoal(1,1);
        CommonGoal c2 = new CommonGoal(2,1);
        game.getCurrentComGoals().add(c1);
        game.getCurrentComGoals().add(c2);

        game.setCurrentPlayer(p1);
        game.setTurn(TurnPhase.INSERT);

        ClientHandler clientHandler = new ClientHandler();
        Controller controller = new Controller(game, null);
        VirtualView virtualView = new VirtualView("marco", clientHandler);
        game.getObservers().add(virtualView);

        Writer out = new Writer() {
            @Override
            public void write(char[] cbuf, int off, int len) throws IOException {}
            @Override
            public void flush() throws IOException {}
            @Override
            public void close() throws IOException {}
        };
        PrintWriter out1 = new PrintWriter(out);
        virtualView.setClientHandler(clientHandler);
        clientHandler.setOut(out1);

        // filling the first column
        Tile tile = new Tile (TileColor.BLUE, TileType.CATS);
        p1.getBookshelf().addTile(tile,1);
        p1.getBookshelf().addTile(tile, 1);
        p1.getBookshelf().addTile(tile, 1);
        p1.getBookshelf().addTile(tile, 1);
        p1.getBookshelf().addTile(tile,1);
        p1.getBookshelf().addTile(tile,1);


        game.insertInBookshelf(1);
        game.insertInBookshelf(2);

        game.setTurn(TurnPhase.PICK);
        game.insertInBookshelf(3);

    }

    @Test
    void endGame() {
        Player player = new Player("marco");
        Player player2 = new Player("filippo");

        Game game = new Game(2);

        game.setFirstPlayer(player2);
        game.setEnd();
        EndToken endToken = new EndToken();
        game.setEndToken(endToken);
        game.getEndToken().setEnd(true);

        game.setCurrentPlayer(player);
        player.setNext(player2);



        game.getPlayers().add(player);
        game.getPlayers().add(player2);

        game.configureGame();
        game.startGame();
        game.setTurn(TurnPhase.ENDGAME);

        game.getPlayers().get(0).setDisconnected(true);

        game.endGame();

        game.setTurn(TurnPhase.ENDTURN);
        game.setFirstPlayer(game.getCurrentPlayer().getNext());
        System.out.println(game.checkEndGame());


    }

    @Test
    void controlRefill() {
        Assertions.assertEquals(true,game.controlRefill());
    }

    @Test
    void endTurn() {


        game.setTurn(TurnPhase.ENDTURN);
        game.setCurrentPlayer(game.getPlayers().get(0));
        System.out.println(game.getCurrentPlayer());
        game.endTurn();

        Player player = new Player("maria");
        Player player2 = new Player("mario");
        game.setFirstPlayer(player2);
        game.setEnd();
        EndToken endToken = new EndToken();
        game.setEndToken(endToken);
        game.getEndToken().setEnd(true);

        game.setCurrentPlayer(player);
        player.setNext(player2);

        game.setTurn(TurnPhase.ENDGAME);

        game.endTurn();
    }

    @Test
    void setWinner() {
        Game game = new Game(4);

        Player player1 = new Player("marco");
        Player player2 = new Player("francesco");
        Player player3 = new Player("daniele");
        Player player4 = new Player("filippo");

        player1.setPersonalGoal(1);
        player1.setBookshelf(new Bookshelf());

        game.addPlayer(player1);
        game.setCurrentPlayer(game.getPlayers().get(0));
        game.setWinner();


        game.setFirstPlayer(player1);

        player2.setPersonalGoal(1);
        player2.setBookshelf(new Bookshelf());

        game.addPlayer(player2);

        game.setWinner();



        player3.setPersonalGoal(1);
        player3.setBookshelf(new Bookshelf());
        game.setFirstPlayer(player2);

        game.addPlayer(player3);

        game.setWinner();

        player4.setPersonalGoal(1);
        player4.setBookshelf(new Bookshelf());
        game.setFirstPlayer(player3);

        game.addPlayer(player4);

        game.setWinner();


        game.setFirstPlayer(player4);
        game.setWinner();





    }

    @Test
    void getBoard() {
        Assertions.assertNotEquals(null, game.getBoard());
    }

    @Test
    void register() {
        ClientHandler clientHandler = new ClientHandler();
        Game game = new Game(2);
        Lobby lobby = new Lobby();
        Controller controller = new Controller(game, lobby);
        VirtualView virtualView = new VirtualView("marco", clientHandler);
        game.register(virtualView);
        Assertions.assertFalse(game.getObservers().isEmpty());
    }

    @Test
    void unregister() {
        ClientHandler clientHandler = new ClientHandler();
        Game game = new Game(2);
        Lobby lobby = new Lobby();
        Controller controller = new Controller(game, lobby);
        VirtualView virtualView = new VirtualView("marco", clientHandler);
        game.register(virtualView);
        game.unregister(virtualView);
        Assertions.assertTrue(game.getObservers().isEmpty());
    }

    @Test
    void getGroupChat() {
        System.out.println(game.getGroupChat());
    }

    @Test
    void getDiscPlayers() {
        Assertions.assertEquals(0, game.getDiscPlayers().size());
    }

    @Test
    void getNumPlayers() {
        Assertions.assertEquals(2,game.getNumPlayers());
    }

    @Test
    void isTimerStarted() {
        game.startTimer();
        Assertions.assertEquals(true, game.isTimerStarted());
    }

    @Test
    void notifyReconnection() {

        Controller controller = new Controller(null, null);
        VirtualView virtualView = new VirtualView("marco", null);
        Game game = new Game(2);
        game.configureGame();
        Player player = new Player("marco");
        game.addPlayer(player);
        game.setFirstPlayer(player);

        ClientHandler clientHandler = new ClientHandler();

        player.setPersonalGoal(1);
        Bookshelf bookshelf = new Bookshelf();
        player.setBookshelf(bookshelf);

        Board board = new Board();
        game.setBoard(board);
        game.setCurrentPlayer(player);
        Writer out = new Writer() {
            @Override
            public void write(char[] cbuf, int off, int len) throws IOException {}
            @Override
            public void flush() throws IOException {}
            @Override
            public void close() throws IOException {}
        };
        PrintWriter out1 = new PrintWriter(out);
        virtualView.setClientHandler(clientHandler);
        clientHandler.setOut(out1);

        CommonGoal c1 = new CommonGoal(1, 1);
        CommonGoal c2 = new CommonGoal(2, 1);


        game.getCurrentComGoals().add(c1);
        game.getCurrentComGoals().add(c2);

        game.register(virtualView);



        player.setDoneCG1(true);
        player.setDoneCG2(true);

        game.notifyReconnection("marco");

    }

    @Test
    void notifyCommongoal() {
        game.notifyCommongoal("fuil", 0,0);
    }

    @Test
    void notifyObservers(){
        Game game = new Game(2);

        Player player = new Player("marco");

        game.setCurrentPlayer(player);
        game.setFirstPlayer(player);

        CommonGoal c1 = new CommonGoal(1, 1);
        CommonGoal c2 = new CommonGoal(2, 1);


        game.getCurrentComGoals().add(c1);
        game.getCurrentComGoals().add(c2);

        Board board = new Board();
        game.setBoard(board);
        game.getPlayers().add(player);

        player.setPersonalGoal(2);
        Bookshelf bookshelf = new Bookshelf();
        player.setBookshelf(bookshelf);

        ClientHandler clientHandler = new ClientHandler();
        ScheduledExecutorService waitPing = new ScheduledThreadPoolExecutor(1);
        clientHandler.setWaitPing(waitPing);

        ScheduledExecutorService sendPing = new ScheduledThreadPoolExecutor(1);
        clientHandler.setSendPing(sendPing);

        Lobby lobby = new Lobby();
        clientHandler.setLobby(lobby);

        GameServer gameServer = new GameServer();
        clientHandler.setGameServer(gameServer);

        Controller controller = new Controller(game, null);
        VirtualView virtualView = new VirtualView("marco", clientHandler);

        Writer out = new Writer() {
            @Override
            public void write(char[] cbuf, int off, int len) throws IOException {}
            @Override
            public void flush() throws IOException {}
            @Override
            public void close() throws IOException {}
        };
        PrintWriter out1 = new PrintWriter(out);
        virtualView.setClientHandler(clientHandler);
        clientHandler.setOut(out1);

        game.getObservers().add(virtualView);

        game.notifyObservers(TurnPhase.ENDGAME);
        game.notifyObservers(TurnPhase.PICK);
        game.notifyObservers(TurnPhase.START);
        game.notifyObservers(TurnPhase.SELECTION);
        game.notifyObservers(TurnPhase.INSERT);
        game.notifyObservers(TurnPhase.ORDER);

    }
    @Test
    void setCurrentPlayer() {
        Player p1 = new Player("fra");
        game.setCurrentPlayer(p1);
        Assertions.assertEquals(p1, game.getCurrentPlayer());
    }

    @Test
    void setEnd() {
        game.setEnd();
        Assertions.assertEquals("ENDGAME", game.getTurn().toString());
    }

    @Test
    void setEndToken() {
        EndToken end = new EndToken();
        game.setEndToken(end);
        Assertions.assertEquals(end, game.getEndToken());
    }

    @Test
    void resetPickedDisc(){
        Game game = new Game(2);
        Player player = new Player("marco");
        game.addPlayer(player);
        game.setCurrentPlayer(player);

        Tile tile = new Tile(TileColor.BLUE, TileType.CATS);
        player.getTilesPicked().add(tile);

        Board board = new Board();
        game.setBoard(board);

        game.resetPickedDisc();
    }
}
