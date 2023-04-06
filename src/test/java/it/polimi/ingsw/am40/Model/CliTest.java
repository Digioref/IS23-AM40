package it.polimi.ingsw.am40.Model;
import it.polimi.ingsw.am40.CLI.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class CliTest {
    @Test
    public void Test() {


        Game game1 = new Game(2);
        CliView cli = new CliView(game1);

        Player p1 = new Player("pippo");
        Player p2 = new Player("pluto");

        game1.addPlayer(p1);
        game1.addPlayer(p2);

        game1.configureGame();
        game1.createTiles();
        game1.startGame();

        cli.showCurrentPlayer();

        cli.showCurrentScore();
        cli.showHiddenScore();
        cli.showFinalScore();

        cli.showCommonGoals();

        cli.showBoard();

        cli.showCurrentBookshelf();

        cli.showBoardPickable();

        cli.showSelectedTiles();

        game1.updatePickableTiles(new Position(-3,-1));

        cli.showSelectedTiles();
        cli.showBoardPickable();

        game1.updatePickableTiles(new Position(-3,0));
        cli.showSelectedTiles();
        cli.showPickedTiles();
        cli.showBoardPickable();

        /*
        System.out.print("\033[H\033[2J");
        System.out.flush();
        */

        game1.pickTiles();
        cli.showPickedTiles();

        ArrayList<Integer> ord = new ArrayList<Integer>(List.of(2,1));
        game1.setOrder(ord);

        cli.showSelectedTiles();
        cli.showPickedTiles();

        cli.showBoardPickable();

/*
        game1.removeSelectedTiles();  // can't unselect the tiles picked

        cli.showSelectedTiles(game1);

        cli.showBoardPickable(game1);
*/
        game1.getCurrentPlayer().placeInBookshelf(0);
        cli.showCurrentBookshelf();
        cli.showPersonalGoal();

        cli.showAllBookshelfs();

    }
}
