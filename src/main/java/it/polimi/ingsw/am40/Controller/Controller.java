package it.polimi.ingsw.am40.Controller;
import it.polimi.ingsw.am40.Model.*;

/**
 * <p>It represents the Controller of the game. Each match of My Shelfie has its own controller.</p>
 * <p>It has only one task: to create the Game Controller, which will act on the game</p>
 */
public class Controller {
    private Game game;
    private GameController gameController;
    private final Lobby lobby;

    /**
     * Constructor which creates the Controller from the associated game and the lobby
     * @param game the game the Controller must control
     * @param lobby the lobby of the game
     */
    public Controller(Game game, Lobby lobby) {
        this.game = game;
        gameController = new GameController(game, this);
        this.lobby = lobby;
    }

    /**
     * It returns the game controlled by the Controller
     * @return the attribute game
     */
    public Game getGame() {
        return game;
    }

    /**
     * Sets the attribute game to the parameter passed
     * @param game a game
     */
    public void setGame(Game game) {
        this.game = game;
    }

    /**
     * It returns the Game Controller related to this Controller
     * @return the attribute gameController
     */
    public GameController getGameController() {
        return gameController;
    }

    /**
     * It returns the lobby of the game
     *@return the attribute lobby
     */
    public Lobby getLobby() {
        return lobby;
    }

    /**
     * Sets the attribute gameController to the parameter passed
     * @param gameController Controller of the game
     */
    public void setGameController(GameController gameController){
        this.gameController = gameController;
    }
}