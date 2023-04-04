package it.polimi.ingsw.am40.Model;
import it.polimi.ingsw.am40.CLI.*;
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

        cli.showCurrentPlayer(game1);


        cli.showBoard(game1);

        cli.showCurrentBookshelf(game1);

        cli.showBoardPickable(game1);

        cli.showSelectedTiles(game1);

        game1.getCurrentPlayer().pickTile(new Position(-3,-1));
        game1.updatePickableTiles(new Position(-3,-1));

        cli.showSelectedTiles(game1);

        cli.showBoardPickable(game1);

        game1.getCurrentPlayer().pickTile(new Position(-3,0));
        game1.updatePickableTiles(new Position(-3,0));

        /*
        System.out.print("\033[H\033[2J");
        System.out.flush();
        */

        cli.showSelectedTiles(game1);

        cli.showBoardPickable(game1);
/*
        game1.removeSelectedTiles();  // can't unselect the tiles picked

        cli.showSelectedTiles(game1);

        cli.showBoardPickable(game1);
*/
        game1.getCurrentPlayer().placeInBookshelf(0);

        cli.showCurrentBookshelf(game1);

        cli.showPersonalGoal(game1);




    }
}
