package it.polimi.ingsw.am40.Model;

import org.junit.jupiter.api.Test;

public class CliTest {


    @Test
    public void Test() {

        Game game1 = new Game(2);

        Player p1 = new Player("pippo");
        Player p2 = new Player("pluto");

        game1.addPlayer(p1);
        game1.addPlayer(p2);

        game1.configureGame();
        game1.createTiles();
        game1.startGame();

        Bookshelf b = game1.getCurrentPlayer().getBookshelf();


        for (int row = 5; row >= 0; row--) {
            for (int col = 0; col < 5; col++) {
                if (b.getTile(col, row) != null) {
                    System.out.printf(b.getTile(col, row).print());
                } else {
                    System.out.printf("\u001B[30m0\u001B[0m ");
                }
            }
            System.out.printf("\n");
        }

        System.out.printf("\n");

        game1.getCurrentPlayer().getBookshelf().addTile(new Tile(TileColor.GREEN, TileType.CATS), 0);
        game1.getCurrentPlayer().getBookshelf().addTile(new Tile(TileColor.GREEN, TileType.CATS), 0);
        game1.getCurrentPlayer().getBookshelf().addTile(new Tile(TileColor.GREEN, TileType.CATS), 0);

        game1.getCurrentPlayer().getBookshelf().addTile(new Tile(TileColor.WHITE, TileType.CATS), 1);
        game1.getCurrentPlayer().getBookshelf().addTile(new Tile(TileColor.GREEN, TileType.CATS), 2);
        game1.getCurrentPlayer().getBookshelf().addTile(new Tile(TileColor.BLUE, TileType.CATS), 3);

        game1.getCurrentPlayer().getBookshelf().addTile(new Tile(TileColor.YELLOW, TileType.CATS), 3);
        game1.getCurrentPlayer().getBookshelf().addTile(new Tile(TileColor.CYAN, TileType.CATS), 3);
        game1.getCurrentPlayer().getBookshelf().addTile(new Tile(TileColor.GREEN, TileType.CATS), 3);

        for (int row = 5; row >= 0; row--) {
            for (int col = 0; col < 5; col++) {
                if (b.getTile(col, row) != null) {
                    System.out.printf(b.getTile(col, row).print());
                } else {
                    System.out.printf("\u001B[30m0\u001B[0m ");
                }
            }
            System.out.printf("\n");
        }



    }
}
