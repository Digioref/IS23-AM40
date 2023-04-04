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
            try {
                v.getClientHandler().sendMessage("It's not your turn!");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void pickTiles(VirtualView v) {
        game.setTurn(TurnPhase.PICK);
        if (game.getCurrentPlayer().getNickname().equals(v.getNickname())) {
            game.pickTiles();
        } else {
            try {
                v.getClientHandler().sendMessage("It's not your turn!");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void notConfirmSelection(VirtualView v) {
        if (game.getCurrentPlayer().getNickname().equals(v.getNickname())) {
            game.removeSelectedTiles();
        } else {
            try {
                v.getClientHandler().sendMessage("It's not your turn!");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void order(VirtualView v, ArrayList<Integer> arr) {
        if (game.getCurrentPlayer().getNickname().equals(v.getNickname())) {
//            System.out.println("qui");
            game.setOrder(arr);
        } else {
            try {
                v.getClientHandler().sendMessage("It's not your turn!");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void insert(VirtualView v, int c) {
        if (game.getCurrentPlayer().getNickname().equals(v.getNickname())) {
            System.out.println("qui");
            game.insertInBookshelf(c);
            boolean b = game.endTurn();
            if (!b) {
                game.setTurn(TurnPhase.ENDGAME);
                game.endGame();
            }
            else {
                game.startTurn();
            }
        } else {
            try {
                v.getClientHandler().sendMessage("It's not your turn!");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }


    public void setController(Controller controller) {
    }


}
