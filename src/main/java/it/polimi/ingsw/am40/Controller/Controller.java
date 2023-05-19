package it.polimi.ingsw.am40.Controller;
import it.polimi.ingsw.am40.Model.*;


public class Controller {
    Game game;
    GameController gameController;
    public Controller(Game game) {
        this.game = game;
        gameController = new GameController(game, this);
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