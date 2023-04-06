package it.polimi.ingsw.am40.Model;
import it.polimi.ingsw.am40.CLI.*;
import it.polimi.ingsw.am40.Controller.Controller;
import it.polimi.ingsw.am40.Controller.GameController;
import it.polimi.ingsw.am40.Network.VirtualView;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class CliTest {
    @Test
    public void Test() {


        Game game1 = new Game(2);
        GameController gc = new GameController(game1);
        Controller c = new Controller(game1);
        CliView cli = new CliView(game1);

        Player p1 = new Player("pippo");
        Player p2 = new Player("pluto");
        VirtualView v1 = new VirtualView(p1.getNickname(), null, c);
        VirtualView v2 = new VirtualView(p2.getNickname(), null, c);

        game1.addPlayer(p1);
        game1.addPlayer(p2);

        game1.configureGame();
        game1.createTiles();
        game1.startGame();
        VirtualView v = new VirtualView("cammello", null, c);
        for (Player p: game1.getPlayers()) {
            if (game1.getCurrentPlayer().equals(p)) {
                v = new VirtualView(p.getNickname(), null, c);
            }
        }

        cli.showCurrentPlayer();

        cli.showCurrentScore();
        cli.showHiddenScore();
        cli.showFinalScore();

        cli.showCommonGoals();

        cli.showBoard();

        cli.showCurrentBookshelf();

        cli.showBoardPickable();

        cli.showSelectedTiles();

        gc.selectTile(v,new Position(-3,-1));

        cli.showSelectedTiles();
        cli.showBoardPickable();

        gc.selectTile(v, new Position(-3,0));
        cli.showSelectedTiles();
        cli.showPickedTiles();
        cli.showBoardPickable();

        /*
        System.out.print("\033[H\033[2J");
        System.out.flush();
        */

        gc.pickTiles(v);
        cli.showPickedTiles();

        ArrayList<Integer> ord = new ArrayList<Integer>(List.of(2,1));
        gc.order(v, ord);

        cli.showSelectedTiles();
        cli.showPickedTiles();

        cli.showBoardPickable();

/*
        game1.removeSelectedTiles();  // can't unselect the tiles picked

        cli.showSelectedTiles(game1);

        cli.showBoardPickable(game1);
*/
        gc.insert(v, 0);
        cli.showCurrentBookshelf();
        cli.showPersonalGoal();

        cli.showAllBookshelfs();

    }
}
