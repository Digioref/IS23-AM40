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

        for (int row = 4; row > -5; row--) {
            for (int col = -4; col < 5; col++) {
                Position pos = new Position(col, row);
                if (game1.getBoard().getGrid().get(pos.getKey()) != null) {
                    System.out.printf(game1.getBoard().getGrid().get(pos.getKey()).print());
                } else {
                    System.out.printf("\u001B[40m  \u001B[0m");
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
                System.out.printf("\u001B[40m \u001B[0m");
                if (b.getTile(col, row) != null) {
                    System.out.printf(b.getTile(col, row).print());
                } else {
                    System.out.printf("\u001B[40m  \u001B[0m");
                }
            }
            System.out.printf("\n");
        }

        System.out.println();
        Tile tile;
        game1.getBoard().setSideFreeTile();
        System.out.println("pickable size: " + game1.getBoard().getPickableTiles().size());

        for (int row = 4; row > -5; row--) {
            for (int col = -4; col < 5; col++) {
                Position pos = new Position(col, row);
                tile = game1.getBoard().getGrid().get(pos.getKey());
                if (tile != null) {
                    if (game1.getBoard().getPickableTiles().contains(tile.getPos()) ) {
                        System.out.printf("\u001B[41m");
                    } else {
                        System.out.printf("\u001B[40m");
                    }
                    System.out.printf(game1.getBoard().getGrid().get(pos.getKey()).print());
                } else {
                    System.out.printf("\u001B[40m  \u001B[0m");
                }
            }
            System.out.printf("\n");
        }

    }
}
