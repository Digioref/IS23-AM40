package it.polimi.ingsw.am40.Controller;

import it.polimi.ingsw.am40.Model.*;
import it.polimi.ingsw.am40.Network.VirtualView;

import java.io.IOException;
import java.util.ArrayList;

/**
 * todo
 */
public class GameController {
    Game game;
    Controller controller;

    /**
     * Contructor of the class, initializes the attributes game and controller to the parameters passed
     * @param game
     * @param controller
     */
    public GameController(Game game, Controller controller) {
        this.game = game;
        this.controller = controller;
    }

    /**
     * Checks if the name of the player in the virtualView passed is the active player;
     * if so, updates the pickable tiles, if not sends an error to the virtualView
     * @param virtualView
     * @param position
     */
    public void selectTile(VirtualView virtualView, Position position) {
        if (game.getCurrentPlayer().getNickname().equals(virtualView.getNickname())) {
            game.updatePickableTiles(position);
        } else {
            virtualView.turnError();
        }
    }

    public void pickTiles(VirtualView v) {
        if (game.getCurrentPlayer().getNickname().equals(v.getNickname())) {
            game.setTurn(TurnPhase.PICK);
            game.pickTiles();
        } else {
            v.turnError();
        }
    }

    public void notConfirmSelection(VirtualView v) {
        if (game.getCurrentPlayer().getNickname().equals(v.getNickname())) {
            game.removeSelectedTiles();
        } else {
            v.turnError();
        }
    }

    public void order(VirtualView v, ArrayList<Integer> arr) {
        if (game.getCurrentPlayer().getNickname().equals(v.getNickname())) {
//            System.out.println("qui");
            game.setOrder(arr);
        } else {
            v.turnError();
        }
    }

    public void insert(VirtualView v, int c) {
        if (game.getCurrentPlayer().getNickname().equals(v.getNickname())) {
//            System.out.println("qui");
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
    }

    public void getChat(String name) {
        for (VirtualView v : game.getObservers()) {
            if (name.equals(v.getNickname())) {
                v.receiveChat(game.getGroupChat());
            }
        }
    }


    public void disconnectPlayer(String s) {
        for (Player p: game.getPlayers()) {
            if (p.getNickname().equals(s)) {
                p.setDisconnected(true);
                game.getDiscPlayers().add(p.getNickname());
                break;
            }
        }
        if (game.getCurrentPlayer().getNickname().equals(s)) {
            game.setTurn(TurnPhase.ENDTURN);
            game.nextPlayer();
            game.startTurn();
        }
        if (game.checkDisconnection() == 1) {
            game.startTimer();
        }
        for (VirtualView v: game.getObservers()) {
            v.receiveDisconnection(s);
        }
    }

    public Game getGame() {
        return game;
    }

    public Controller getController() {
        return controller;
    }

    public void reconnect(String s) {
        System.out.println("qui0");
        game.getDiscPlayers().remove(s);
        System.out.println("qui1");
        for (Player p: game.getPlayers()) {
            if(p.getNickname().equals(s)) {
                p.setDisconnected(false);
                System.out.println("qui2");
                break;
            }
        }
        if (game.isTimerStarted()) {
            game.stopTimer();
        }
        game.notifyReconnection(s);
    }

    public void setGame(Game game){
        this.game = game;
    }

}
