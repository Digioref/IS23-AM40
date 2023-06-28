package it.polimi.ingsw.am40.Model;

import it.polimi.ingsw.am40.Controller.Controller;
import it.polimi.ingsw.am40.Controller.Lobby;
import it.polimi.ingsw.am40.Network.ClientHandler;
import it.polimi.ingsw.am40.Network.GameServer;
import it.polimi.ingsw.am40.Network.Handlers;
import it.polimi.ingsw.am40.Network.VirtualView;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.Socket;
import java.util.ArrayList;

public class GameTest {

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
        game.createTiles();
        game.startGame();
        game.assignPersonalGoal();
        game.nextPlayer();
        Assertions.assertEquals(2, game.checkDisconnection());
    }

    @Test
    void startTimer() {
        game.startTimer();
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
        Assertions.assertEquals(false,game.checkEndGame());
    }

    @Test
    void updatePickableTiles() {
        game.updatePickableTiles(new Position(0,0));
        game.setTurn(TurnPhase.SELECTION);
        game.updatePickableTiles(new Position(0,0));
    }

    @Test
    void removeSelectedTiles() {
        game.startGame();
        game.removeSelectedTiles();
        game.setTurn(TurnPhase.SELECTION);
        game.removeSelectedTiles();
    }

    @Test
    void pickTiles() {
        game.pickTiles();
    }

    @Test
    void setOrder() {
        Player p1 = new Player("marco");
        game.setCurrentPlayer(p1);
        game.setTurn(TurnPhase.ORDER);

        ArrayList<Integer> pos = new ArrayList<>();
        pos.add(1);
        pos.add(2);
        game.setOrder(pos);

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
        game.setCurrentPlayer(p1);
        //game.setTurn(TurnPhase.INSERT);
        game.insertInBookshelf(1);
    }

    @Test
    void endGame() {
        ArrayList<Player> players = game.getPlayers();
        players.get(0).setDisconnected(true);
        game.setTurn(TurnPhase.ENDGAME);
        game.endGame();
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
    }

    @Test
    void setWinner() {
        game.startGame();
        game.setCurrentPlayer(game.getPlayers().get(0));
        game.setWinner();
    }

    @Test
    void hasStarted() {
        game.setHasStarted(true);
        Assertions.assertEquals(true,game.HasStarted());
    }

    @Test
    void hasEnded() {
        game.setHasEnded(true);
        Assertions.assertEquals(true,game.HasEnded());
    }

    @Test
    void getBoard() {
        Assertions.assertNotEquals(null, game.getBoard());
    }

    @Test
    void getBag() {
        Bag b = new Bag();
        game.setBag(b);
        Assertions.assertEquals(b, game.getBag());
    }

    @Test
    void register() {
    }

    @Test
    void unregister() {
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
    }

    @Test
    void notifyCommongoal() {
        game.notifyCommongoal("fuil", 0,0);
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
}
