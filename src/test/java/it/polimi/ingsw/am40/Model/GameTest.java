package it.polimi.ingsw.am40.Model;

import org.junit.jupiter.api.Test;

public class GameTest {

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
    }
}
