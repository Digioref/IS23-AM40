package it.polimi.ingsw.am40.Controller;
import it.polimi.ingsw.am40.Model.*;

/**
 * todo
 */
public class Controller {
    Game game;
    GameController gameController;
    Lobby lobby;

    /**
     * todo
     * @param game
     * @param lobby
     */
    public Controller(Game game, Lobby lobby) {
        this.game = game;
        gameController = new GameController(game, this);
        this.lobby = lobby;
    }

    /**
     * @return the attribute game
     */
    public Game getGame() {
        return game;
    }

    /**
     * Sets the attribute game to the parameter passed
     * @param game
     */
    public void setGame(Game game) {
        this.game = game;
    }

    /**
     * @return the attribute gameController
     */
    public GameController getGameController() {
        return gameController;
    }

    /**
     *@return the attribute lobby
     */
    public Lobby getLobby() {
        return lobby;
    }

    /**
     * Sets the attribute gameController to the parameter passed
     * @param gameController
     */
    public void setGameController(GameController gameController){
        this.gameController = gameController;
    }
}