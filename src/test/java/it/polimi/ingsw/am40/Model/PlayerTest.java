package it.polimi.ingsw.am40.Model;

import it.polimi.ingsw.am40.Controller.Lobby;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Tests the Player class
 */
public class PlayerTest {
    /**
     Tests the creation of more players
     */
    @Test
    public void addPlayer() {
        Player p1 = new Player("Giuseppe");
        assertEquals("Giuseppe", p1.getNickname());
        Player p2 = new Player("Francesco");
        assertNotEquals("Giuseppe", p2.getNickname());

        assertEquals(0, p1.getCurrentScore());
        assertEquals(0, p1.getFinalScore());
        assertEquals(0, p2.getCurrentScore());
        assertEquals(0, p2.getFinalScore());

    }
    /**
     Tests the feature next;
     */
    @Test
    public void assignNext() {
        Player p1 = new Player("Giuseppe");
        Player p2 = new Player("Giovanna");
        p1.setNext(p2);
        Player p3 = new Player("Francesco");
        p2.setNext(p3);
        Player p4 = new Player("Marta");
        p3.setNext(p4);
        p4.setNext(p1);

        Player p = p2.getNext();
        assertEquals("Francesco", p.getNickname());
        p = p4.getNext();
        assertEquals("Giuseppe", p.getNickname());
        p = p1.getNext();
        assertEquals("Giovanna", p.getNickname());
        p = p3.getNext();
        assertEquals("Marta", p.getNickname());
    }


    @Test
    void pickTile() {
        Player player = new Player("marco");
        Position position = new Position(1,1);

        Board board = new Board(2);
        Bag bag = new Bag();
        board.config(bag);

        player.setBoard(board);

        player.pickTile(position);
    }

    @Test
    void clearTilesPicked() {

        /**
         * TODO
         * si può migliorare
         */

        Player player = new Player("gigi");
        Position position = new Position(1,1);

        Board board = new Board(2);
        Bag bag = new Bag();
        board.config(bag);

        player.setBoard(board);
        player.pickTile(position);

        assertEquals(player.getTilesPicked().size(), 1);

        player.clearTilesPicked();
    }

    @Test
    void createBookshelf() {
        Player player = new Player("gigi");
        player.createBookshelf();
        assertTrue(player.getBookshelf() != null);
    }

    @Test
    void placeInBookshelf() {
        Player player = new Player("gigi");
        player.createBookshelf();

        Tile tile = new Tile(TileColor.BLUE, TileType.CATS);
        player.getTilesPicked().add(tile);

        player.placeInBookshelf(5);
    }

    @Test
    void selectOrder() {
        Player player = new Player("gigi");
        player.createBookshelf();

        Tile tile1 = new Tile(TileColor.BLUE, TileType.CATS);
        Tile tile2 = new Tile(TileColor.BLUE, TileType.CATS);

        player.getTilesPicked().add(tile1);
        player.getTilesPicked().add(tile2);

        ArrayList<Integer> posizioni = new ArrayList<>();
        posizioni.add(2);
        posizioni.add(1);

        player.selectOrder(posizioni);

        assertEquals(player.getTilesPicked().get(0), tile2);
        assertEquals(player.getTilesPicked().get(1), tile1);

    }

    @Test
    void calculateScore() {
        Player player = new Player("pippo");
        player.createBookshelf();


    }

    @Test
    void updateCurrScore() {
    }

    @Test
    void updateHiddenScore() {
    }

    @Test
    void getNickname() {
        Player player = new Player("mirko");
        assertEquals(player.getNickname(), "mirko");
    }

    @Test
    void getNext() {
        Player player1 = new Player("pino");
        Player player2 = new Player("chiara");

        player1.setNext(player2);
        assertEquals(player1.getNext(), player2);

    }

    @Test
    void setNext() {
        Player player1 = new Player("pino");
        Player player2 = new Player("chiara");

        player1.setNext(player2);
        assertEquals(player1.getNext(), player2);

    }

    @Test
    void addCurrentScore() {
        Player player = new Player("marco");
        player.setCurrentScore(0);

        player.addCurrentScore(4);

        assertEquals(player.getCurrentScore(), 5);

    }

    @Test
    void getTilesPicked() {
        Player player = new Player("pietro");
        ArrayList<Tile> picked = new ArrayList<>();
        Tile tile1 = new Tile(TileColor.BLUE, TileType.CATS);
        Tile tile2 = new Tile(TileColor.BLUE, TileType.CATS);

        picked.add(tile1);
        picked.add(tile2);
        player.setTilesPicked(picked);

        assertTrue(player.getTilesPicked().contains(tile1));
        assertTrue(player.getTilesPicked().contains(tile2));
    }

    @Test
    void setTilesPicked() {
        Player player = new Player("pietro");
        ArrayList<Tile> picked = new ArrayList<>();
        Tile tile1 = new Tile(TileColor.BLUE, TileType.CATS);
        Tile tile2 = new Tile(TileColor.BLUE, TileType.CATS);

        picked.add(tile1);
        picked.add(tile2);
        player.setTilesPicked(picked);

        assertTrue(player.getTilesPicked().contains(tile1));
        assertTrue(player.getTilesPicked().contains(tile2));
    }

    @Test
    void getCurrentScore() {
        Player player = new Player("marco");
        player.setCurrentScore(5);
        assertEquals(player.getCurrentScore(), 5);
    }

    @Test
    void setCurrentScore() {
        Player player = new Player("marco");
        player.setCurrentScore(5);
        assertEquals(player.getCurrentScore(), 5);
    }

    @Test
    void getFinalScore() {
        Player player = new Player("filippo");
        player.setFinalScore(5);
        assertEquals(player.getFinalScore(), 5);

    }

    @Test
    void setFinalScore() {
        Player player = new Player("filippo");
        player.setFinalScore(5);
        assertEquals(player.getFinalScore(), 5);
    }

    @Test
    void getBookshelf() {
        Player player = new Player("marta");
        Bookshelf bookshelf = new Bookshelf();
        player.setBookshelf(bookshelf);
        assertEquals(player.getBookshelf(), bookshelf);
    }

    @Test
    void setBookshelf() {
        Player player = new Player("marta");
        Bookshelf bookshelf = new Bookshelf();
        player.setBookshelf(bookshelf);
        assertEquals(player.getBookshelf(), bookshelf);
    }

    @Test
    void getBoard() {
        Player player = new Player("alice");
        Board board = new Board(2);
        player.setBoard(board);
        assertEquals(player.getBoard(), board);
    }

    @Test
    void setBoard() {
        Player player = new Player("alice");
        Board board = new Board(2);
        player.setBoard(board);
        assertEquals(player.getBoard(), board);
    }

    @Test
    void getPersonalGoal() {
        Player player = new Player("alberto");
        player.setPersonalGoal(3);
        assertEquals(player.getPersonalGoal(),3);
    }

    @Test
    void setPersonalGoal() {
        Player player = new Player("alberto");
        player.setPersonalGoal(3);
        assertEquals(player.getPersonalGoal(),3);
    }

    @Test
    void getSelectedPositions() {
        Player player = new Player("mario");
        ArrayList<Position> posizioni = new ArrayList<>();

        Position posizione1 = new Position(1,1);
        Position posizione2 = new Position(2,2);

        posizioni.add(posizione1);
        posizioni.add(posizione2);
        player.setSelectedPositions(posizioni);

        assertEquals(player.getSelectedPositions(), posizioni);
    }

    @Test
    void setSelectedPositions() {
        Player player = new Player("mario");
        ArrayList<Position> posizioni = new ArrayList<>();

        Position posizione1 = new Position(1,1);
        Position posizione2 = new Position(2,2);

        posizioni.add(posizione1);
        posizioni.add(posizione2);
        player.setSelectedPositions(posizioni);

        assertEquals(player.getSelectedPositions(), posizioni);
    }

    @Test
    void clearSelected() {
        Player player = new Player("mario");
        ArrayList<Position> posizioni = new ArrayList<>();

        Position posizione1 = new Position(1,1);
        Position posizione2 = new Position(2,2);

        posizioni.add(posizione1);
        posizioni.add(posizione2);
        player.setSelectedPositions(posizioni);

        player.clearSelected();

        assertTrue(player.getSelectedPositions().isEmpty());
    }

    @Test
    void getHiddenScore() {
        Player player = new Player("giovanni");
        player.setHiddenScore(5);
        assertEquals(player.getHiddenScore(), 5);
    }

    @Test
    void isDisconnected() {
        Player player = new Player("gigi");
        player.setDisconnected(true);

        assertEquals(player.isDisconnected(), true);
    }

    @Test
    void setDisconnected() {
        Player player = new Player("gigi");
        player.setDisconnected(true);

        assertEquals(player.isDisconnected(), true);
    }

    @Test
    void setGame() {
        Player player = new Player("francesca");
        Game game = new Game(2);

        player.setGame(game);
        assertEquals(player.getGame(),game);
    }
}