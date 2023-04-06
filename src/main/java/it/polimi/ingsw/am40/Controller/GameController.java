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
        game.setTurn(TurnPhase.PICK);
        if (game.getCurrentPlayer().getNickname().equals(v.getNickname())) {
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
            System.out.println("qui");
            game.insertInBookshelf(c);
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


    public void setController(Controller controller) {
    }


}
