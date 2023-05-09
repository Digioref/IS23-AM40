package it.polimi.ingsw.am40.Controller;

import it.polimi.ingsw.am40.Model.*;
import it.polimi.ingsw.am40.Network.VirtualView;

import java.io.IOException;
import java.util.ArrayList;

public class GameController {
    Game game;
    Controller controller;
    public GameController(Game game) {
        this.game = game;
    }

    public void selectTile(VirtualView v, Position p) {
//        System.out.println("contr");
        if (game.getCurrentPlayer().getNickname().equals(v.getNickname())) {
            game.updatePickableTiles(p);
        } else {
            v.turnError();
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

    public void chat(String name, String message, String from, long time) {
        game.getGroupChat().addMessage(name, message, from, time);
        for (VirtualView v: game.getObservers()) {
            if (name.equals(v.getNickname())) {
                v.receiveChat(game.getGroupChat());
            }
        }
    }
    public void chatAll(String message, String from, long time) {
        game.getGroupChat().addMessage(null, message, from, time);
        for (VirtualView v: game.getObservers()) {
            v.receiveChat(game.getGroupChat());
        }
    }


    public void setController(Controller controller) {
    }


}
