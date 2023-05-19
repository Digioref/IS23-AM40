package it.polimi.ingsw.am40.Controller;

import it.polimi.ingsw.am40.Model.*;
import it.polimi.ingsw.am40.Network.VirtualView;

import java.io.IOException;
import java.util.ArrayList;

public class GameController {
    Game game;
    Controller controller;
    public GameController(Game game, Controller controller) {
        this.game = game;
        this.controller = controller;
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
    public void setController(Controller controller) {
    }

    public void disconnectPlayer(String s) {
        for (Player p: game.getPlayers()) {
            if (p.getNickname().equals(s)) {
                p.setDisconnected(true);
                game.getDiscPlayers().add(p.getNickname());
                if (game.getCurrentPlayer().getNickname().equals(s)) {
                    game.setTurn(TurnPhase.ENDTURN);
                    game.endTurn();
                    game.startTurn();
                }
                break;
            }
        }
        if (game.checkDisconnection() == 1) {
            game.startTimer();
        }
        for (VirtualView v: game.getObservers()) {
            v.receiveDisconnection(s);
        }
        if (game.getDiscPlayers().size() == game.getNumPlayers()) {
            for (String nickname: game.getDiscPlayers()) {
                controller.getLobby().getGames().remove(nickname);
                controller.getLobby().getNicknameInGame().remove(nickname);
            }
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
        System.out.println("qui3");
        if (game.isTimerStarted()) {
            game.stopTimer();
        }
        game.notifyReconnection(s);
    }
}
