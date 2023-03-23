package it.polimi.ingsw.am40.controller;
import it.polimi.ingsw.am40.model.*;


public class Controller {
    Game game;
    GameController gameController;
    public Controller(Game game) {
        this.game = game;
    }

    public void game() {
        gameController = new GameController(game);
        gameController.setController(this);
        game.configureGame();
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public GameController getGameController() {
        return gameController;
    }

    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }

    //    public void pick (Position p) {
//        game.selectTiles(game.getCurrentPlayer(), p);
//    }
//    public void changePlayer() {
//        game.nextPlayer();
//    }

}