package it.polimi.ingsw.am40.Controller;

import it.polimi.ingsw.am40.Model.Game;
import it.polimi.ingsw.am40.Model.Player;
import it.polimi.ingsw.am40.Model.Position;
import it.polimi.ingsw.am40.Model.TurnPhase;
import it.polimi.ingsw.am40.Network.VirtualView;

import java.util.ArrayList;

/**
 * <p>This class is the Controller which acts directly on the game, calling specific methods of the game to update the game state</p>
 */
public class GameController {
    private Game game;
    private final Controller controller;

    /**
     * Constructor of the class, initializes the attributes game and controller to the parameters passed
     * @param game game controlled
     * @param controller controller
     */
    public GameController(Game game, Controller controller) {
        this.game = game;
        this.controller = controller;
    }

    /**
     * Checks if the name of the player in the virtualView passed is the active player;
     * if so, updates the pickable tiles, if not sends an error to the virtualView
     * @param virtualView the virtual view of the player that has done the corresponding action
     * @param position position of the tile selected
     */
    public void selectTile(VirtualView virtualView, Position position) {
        if (game.getCurrentPlayer().getNickname().equals(virtualView.getNickname())) {
            game.updatePickableTiles(position);
        } else {
            virtualView.turnError();
        }
    }

    /**
     * This method allows the player to pick the tiles selected by the player from the board
     * @param v the virtual view of the player
     */
    public void pickTiles(VirtualView v) {
        if (game.getCurrentPlayer().getNickname().equals(v.getNickname())) {
            game.setTurn(TurnPhase.PICK);
            game.pickTiles();
        } else {
            v.turnError();
        }
    }

    /**
     * This method allows the player to remove the tiles selected
     * @param v the virtual view of the player
     */
    public void notConfirmSelection(VirtualView v) {
        if (game.getCurrentPlayer().getNickname().equals(v.getNickname())) {
            game.removeSelectedTiles();
        } else {
            v.turnError();
        }
    }

    /**
     * This method sets the order of the picked tiles according to the order specified by the parameters
     * @param v the virtual view of the player
     * @param arr the desired order of the tiles
     */
    public void order(VirtualView v, ArrayList<Integer> arr) {
        if (game.getCurrentPlayer().getNickname().equals(v.getNickname())) {
            game.setOrder(arr);
        } else {
            v.turnError();
        }
    }


    /**
     * This method allows the player to insert the tiles picked and ordered in his bookshelf
     * @param v the virtual view of the player
     * @param c the column chosen by the player in which he wants to insert the tiles
     */
    public void insert(VirtualView v, int c) {
        if (game.getCurrentPlayer().getNickname().equals(v.getNickname())) {
            game.insertInBookshelf(c-1);
            game.endTurn();
            if (game.getTurn() == TurnPhase.ENDGAME) {
                game.endGame();
            }
            else {
                game.startTurn();
            }
        } else {
            v.turnError();
        }

    }

    /**
     * This method adds to the chat of the game the message in the parameters
     * @param name the receiver of the message
     * @param message the message
     * @param from the sender of the message
     */
    public void chat(String name, String message, String from) {
        if (name.equals("all")) {
            game.getGroupChat().addMessage(name, message, from);
            for (VirtualView v: game.getObservers()) {
                v.receiveChat(game.getGroupChat());

            }
        } else {
            for (Player p: game.getPlayers()) {
                if(p.getNickname().equals(name)) {
                    game.getGroupChat().addMessage(name, message, from);
                    for (VirtualView v: game.getObservers()) {
                        if (name.equals(v.getNickname())) {
                            v.receiveChat(game.getGroupChat());
                        }
                    }
                return;
                }
            }
            for (VirtualView v: game.getObservers()) {
                if (from.equals(v.getNickname())) {
                    v.chatError();
                }
            }
        }
        getChat(name);
    }

    /**
     * This method returns the chat of the game
     * @param name the name of the player who requested the chat
     */
    public void getChat(String name) {
        for (VirtualView v : game.getObservers()) {
            if (name.equals(v.getNickname())) {
                v.receiveChat(game.getGroupChat());
            }
        }
    }

    /**
     * This method disconnects the player whose name is the one in the parameters
     * @param s the name of the player to be disconnected
     */
    public void disconnectPlayer(String s) {
        for (Player p: game.getPlayers()) {
            if (p.getNickname().equals(s)) {
                p.setDisconnected(true);
                game.getDiscPlayers().add(p.getNickname());
                break;
            }
        }
        if (game.getCurrentPlayer().getNickname().equals(s)) {
            if (!game.getCurrentPlayer().getSelectedPositions().isEmpty()) {
                game.getCurrentPlayer().getSelectedPositions().clear();
            }
            game.resetPickedDisc();
            game.setTurn(TurnPhase.ENDTURN);
            game.nextPlayer();
            game.setTurn(TurnPhase.START);
            game.startTurn();
        }
        if (game.checkDisconnection() == 1) {
            game.startTimer();
        }
        for (VirtualView v: game.getObservers()) {
            v.receiveDisconnection(s);
        }
    }

    /**
     * This method returns the game controlled by this controller
     * @return a game
     */
    public Game getGame() {
        return game;
    }

    /**
     * This method returns the Controller which has created this Game controller
     * @return the controller
     */
    public Controller getController() {
        return controller;
    }

    /**
     * This method reconnects the player whose name is the one specified in the parameters
     * @param s the name of the reconnecting player
     */
    public void reconnect(String s) {
        game.getDiscPlayers().remove(s);
        for (Player p: game.getPlayers()) {
            if(p.getNickname().equals(s)) {
                p.setDisconnected(false);
                break;
            }
        }
        if (game.isTimerStarted()) {
            game.stopTimer();
        }
        game.notifyReconnection(s);

    }

    /**
     * This method sets the game the controller has to control
     * @param game a game
     */
    public void setGame(Game game){
        this.game = game;
    }

}
