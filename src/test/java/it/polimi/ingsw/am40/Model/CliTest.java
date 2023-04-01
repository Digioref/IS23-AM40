package it.polimi.ingsw.am40.Model;

import it.polimi.ingsw.am40.CLI.CliView;
import org.junit.jupiter.api.Test;

public class CliTest {


    @Test
    public void Test() {

        CliView cli = new CliView();
        Game game1 = new Game(2);

        Player p1 = new Player("pippo");
        Player p2 = new Player("pluto");

        game1.addPlayer(p1);
        game1.addPlayer(p2);

        game1.configureGame();
        game1.createTiles();
        game1.startGame();

        cli.showBoard(game1);

        game1.getCurrentPlayer().getBookshelf().addTile(new Tile(TileColor.GREEN, TileType.CATS), 0);
        game1.getCurrentPlayer().getBookshelf().addTile(new Tile(TileColor.GREEN, TileType.CATS), 0);
        game1.getCurrentPlayer().getBookshelf().addTile(new Tile(TileColor.GREEN, TileType.CATS), 0);

        game1.getCurrentPlayer().getBookshelf().addTile(new Tile(TileColor.WHITE, TileType.CATS), 1);
        game1.getCurrentPlayer().getBookshelf().addTile(new Tile(TileColor.GREEN, TileType.CATS), 2);
        game1.getCurrentPlayer().getBookshelf().addTile(new Tile(TileColor.BLUE, TileType.CATS), 3);

        game1.getCurrentPlayer().getBookshelf().addTile(new Tile(TileColor.YELLOW, TileType.CATS), 3);
        game1.getCurrentPlayer().getBookshelf().addTile(new Tile(TileColor.CYAN, TileType.CATS), 3);
        game1.getCurrentPlayer().getBookshelf().addTile(new Tile(TileColor.GREEN, TileType.CATS), 3);

        cli.showCurrentBookshelf(game1);

        cli.showBoardPickable(game1);

    }
}
