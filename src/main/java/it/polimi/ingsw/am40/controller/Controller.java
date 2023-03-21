package it.polimi.ingsw.am40.controller;
import it.polimi.ingsw.am40.model.*;


public class Controller {
    Game game;
    public Controller(Game game) {
        this.game = game;
    }

    public void pick (Position p) {
        game.selectTiles(game.getCurrentPlayer(), p);
    }
    public void changePlayer() {
        game.nextPlayer();
    }
}