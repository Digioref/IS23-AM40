package it.polimi.ingsw.am40.Controller;
import it.polimi.ingsw.am40.Model.*;


public class Controller {
    Game game;
    GameController gameController;
    Lobby lobby;
    public Controller(Game game, Lobby lobby) {
        this.game = game;
        gameController = new GameController(game, this);
        this.lobby = lobby;
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

    public Lobby getLobby() {
        return lobby;
    }


}