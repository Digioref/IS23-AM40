package it.polimi.ingsw.am40.Controller;

import it.polimi.ingsw.am40.Model.*;
import it.polimi.ingsw.am40.Network.VirtualView;

import java.util.ArrayList;

public class GameController {
    Game game;
    Controller controller;
    public GameController(Game game) {
        this.game = game;
    }

    public void selectTile(VirtualView v, Position p) {
        game.updatePickableTiles(p);
    }

    public void pickTiles(VirtualView v) {
        game.pickTiles();
    }

    public void notConfirmSelection(VirtualView v) {
        game.removeSelectedTiles();
    }

    public void order(VirtualView v, ArrayList<Tile> arr) {
        game.setOrder(arr);
    }

    public void insert(VirtualView v, int c) {
        game.insertInBookshelf(c);
        boolean b = game.endTurn();
        if (!b) {
            game.endGame();
        }
        else {
            game.startTurn();
        }
    }


    public void setController(Controller controller) {
    }


}
