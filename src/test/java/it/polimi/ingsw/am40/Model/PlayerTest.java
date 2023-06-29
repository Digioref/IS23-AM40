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

        Board board = new Board();
        Bag bag = new Bag();
        board.config(bag);

        player.setBoard(board);

        player.pickTile(position);
    }

    @Test
    void clearTilesPicked() {
        Player player = new Player("gigi");
        Position position = new Position(1,1);

        Board board = new Board();
        Bag bag = new Bag();
        board.config(bag);
        ParsingJSONManager pJSONm = new ParsingJSONManager();
        pJSONm.createBoard(board.getGrid(), 2);

        player.setBoard(board);
        player.pickTile(position);

        assertEquals(1, player.getTilesPicked().size());
        player.clearTilesPicked();

        assertEquals(0, player.getTilesPicked().size());
    }

    @Test
    void createBookshelf() {
        Player player = new Player("gigi");
        player.createBookshelf();
        assertNotNull(player.getBookshelf());
    }

    @Test
    void placeInBookshelf() {
        Player player = new Player("gigi");
        player.createBookshelf();

        Tile tile = new Tile(TileColor.BLUE, TileType.CATS);
        player.getTilesPicked().add(tile);

        player.placeInBookshelf(4);
        assertEquals(tile, player.getBookshelf().getTile(4, 0));
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

        assertEquals(tile2, player.getTilesPicked().get(0));
        assertEquals(tile1, player.getTilesPicked().get(1));

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
        assertEquals(player2, player1.getNext());

    }

    @Test
    void setNext() {
        Player player1 = new Player("pino");
        Player player2 = new Player("chiara");

        player1.setNext(player2);
        assertEquals(player2, player1.getNext());

    }

    @Test
    void addCurrentScore() {
        Player player = new Player("marco");

        player.addCurrentScore(4);

        assertNotEquals(5, player.getCurrentScore());

    }

    @Test
    void getTilesPicked() {
        Player player = new Player("pietro");
        ArrayList<Tile> picked = new ArrayList<>();
        Tile tile1 = new Tile(TileColor.BLUE, TileType.CATS);
        Tile tile2 = new Tile(TileColor.BLUE, TileType.CATS);

        picked.add(tile1);
        picked.add(tile2);
        player.getTilesPicked().addAll(picked);

        assertTrue(player.getTilesPicked().contains(tile1));
        assertTrue(player.getTilesPicked().contains(tile2));
    }



    @Test
    void getCurrentScore() {
        Player player = new Player("marco");
        player.setCurrentScore(5);
        assertEquals(5, player.getCurrentScore());
    }

    @Test
    void setCurrentScore() {
        Player player = new Player("marco");
        player.setCurrentScore(5);
        assertEquals(5, player.getCurrentScore());
    }

    @Test
    void getFinalScore() {
        Player player = new Player("filippo");
        player.setBookshelf(new Bookshelf());
        player.setPersonalGoal(7);
        assertEquals(0, player.getFinalScore());

    }

    @Test
    void getBookshelf() {
        Player player = new Player("marta");
        Bookshelf bookshelf = new Bookshelf();
        player.setBookshelf(bookshelf);
        assertEquals(bookshelf, player.getBookshelf());
    }

    @Test
    void setBookshelf() {
        Player player = new Player("marta");
        Bookshelf bookshelf = new Bookshelf();
        player.setBookshelf(bookshelf);
        assertEquals(bookshelf, player.getBookshelf());
    }

    @Test
    void getBoard() {
        Player player = new Player("alice");
        Board board = new Board();
        player.setBoard(board);
        assertEquals(board, player.getBoard());
    }

    @Test
    void setBoard() {
        Player player = new Player("alice");
        Board board = new Board();
        player.setBoard(board);
        assertEquals(board, player.getBoard());
    }

    @Test
    void getPersonalGoal() {
        Player player = new Player("alberto");
        player.setPersonalGoal(3);
        assertEquals(3, player.getPersonalGoal().getKey());
    }

    @Test
    void setPersonalGoal() {
        Player player = new Player("alberto");
        player.setPersonalGoal(3);
        assertEquals(3, player.getPersonalGoal().getKey());
    }

    @Test
    void getSelectedPositions() {
        Player player = new Player("mario");

        Position posizione1 = new Position(1,1);
        Position posizione2 = new Position(2,2);

        player.getSelectedPositions().add(posizione1);
        player.getSelectedPositions().add(posizione2);

        assertEquals(posizione1, player.getSelectedPositions().get(0));
        assertEquals(posizione2, player.getSelectedPositions().get(1));
        assertNotEquals(posizione1, player.getSelectedPositions().get(1));
        assertNotEquals(posizione2, player.getSelectedPositions().get(0));
    }

    @Test
    void setSelectedPositions() {
        Player player = new Player("mario");

        Position posizione1 = new Position(1,1);
        Position posizione2 = new Position(2,2);

        player.getSelectedPositions().add(posizione1);
        player.getSelectedPositions().add(posizione2);

        assertEquals(posizione1, player.getSelectedPositions().get(0));
        assertEquals(posizione2, player.getSelectedPositions().get(1));
        assertNotEquals(posizione1, player.getSelectedPositions().get(1));
        assertNotEquals(posizione2, player.getSelectedPositions().get(0));
    }

    @Test
    void clearSelected() {
        Player player = new Player("mario");

        Position posizione1 = new Position(1,1);
        Position posizione2 = new Position(2,2);

        player.getSelectedPositions().add(posizione1);
        player.getSelectedPositions().add(posizione2);
        player.setBoard(new Board());

        player.clearSelected();

        assertTrue(player.getSelectedPositions().isEmpty());
    }

    @Test
    void getHiddenScore() {
        Player player = new Player("giovanni");
        player.setHiddenScore(5);
        assertEquals(5, player.getHiddenScore());
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
        assertEquals(game, player.getGame());
    }
}