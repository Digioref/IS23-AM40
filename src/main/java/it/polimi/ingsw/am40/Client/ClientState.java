package it.polimi.ingsw.am40.Client;

import it.polimi.ingsw.am40.Model.Board;
import it.polimi.ingsw.am40.Model.Position;

import java.util.ArrayList;
import java.util.Map;

public class ClientState {
    private Client client;
    private String currplayer;
    private Map<String, Integer> currscore;
    private int hiddenscore;
    private ArrayList<String> players;
    private Map<Integer, Integer> commongoals;
    private Map<String, String> personalgoal;
    private Map<String, String> board;
    private Map<String, String> bookshelf;
    private Map<String, Map<String, String>> bookshelves;
    private Map<String, String> pickabletiles;
    private ArrayList<Position> alreadysel;
    private Map<String, String> boardsel;
    private Map<String, String> selectedtiles;
    private Map<String, String> pickedtiles;
    private Map<String, Integer> finalscores;
    private String winner;
    private String nickname;
    private ArrayList<String> authors;
    private ArrayList<String> receivers;
    private ArrayList<String> messages;

    public ClientState(Client client) {
        this.client = client;
    }

    public void saveCurrentPlayer(String nickname) {
        currplayer = nickname;
    }

    public void saveCurrentScore(Map<String, Integer> map) {
        currscore = map;
    }

    public void saveHiddenScore(int score) {
        hiddenscore = score;
    }

    public void savePlayers(ArrayList<String> names) {
        players = names;
    }

    public void saveCommonGoals(Map<Integer, Integer> map1) {
        commongoals = map1;
    }

    public void savePersonalGoal(Map<String, String> map2) {
        personalgoal = map2;
    }

    public void saveBoard(Map<String, String> map3) {
        board = map3;
    }

    public void saveBookshelf(Map<String, String> map4) {
        bookshelf = map4;
    }

    public void saveBookshelves(Map<String, Map<String, String>> map5) {
        bookshelves = map5;
    }

    public void savePickable(Map<String, String> map7, ArrayList<Position> arrayList, Map<String, String> map8) {
        pickabletiles = map7;
        alreadysel = arrayList;
        boardsel = map8;
    }

    public void saveSelectedTiles(Map<String, String> map9) {
        selectedtiles = map9;
    }

    public void savePickedTiles(Map<String, String> map10) {
        pickedtiles = map10;
    }

    public void saveFinalScores(Map<String, Integer> map11, String s) {
        finalscores = map11;
        winner = s;
    }

    public void saveNickname(String nickname) {
        this.nickname = nickname;
    }
    public void saveChat(ArrayList<String> authors, ArrayList<String> receivers, ArrayList<String> messages) {
        this.authors = authors;
        this.receivers = receivers;
        this.messages = messages;
    }
    public void refresh() {
        if (currscore != null) {
            LaunchClient.getView().showCurrentScore(currscore);
        }
        LaunchClient.getView().showHiddenScore(hiddenscore);
        if (players != null) {
            LaunchClient.getView().showPlayers(players);
        }
        if (currplayer != null) {
            LaunchClient.getView().showCurrentPlayer(currplayer);
        }
        if (commongoals != null) {
            LaunchClient.getView().showCommonGoals(commongoals);
        }
        if (personalgoal != null) {
            LaunchClient.getView().showPersonalGoal(personalgoal);
        }
        if (board != null) {
            LaunchClient.getView().showBoard(board);
        }
        if (bookshelves != null) {
            LaunchClient.getView().showAllBookshelves(bookshelves);
        }
        System.out.println(nickname);
        if (pickabletiles != null && alreadysel != null && boardsel != null && currplayer.equals(nickname)) {
            LaunchClient.getView().showBoardPickable(pickabletiles, alreadysel, boardsel);
        }
        if (currplayer.equals(nickname) && selectedtiles != null) {
            LaunchClient.getView().showSelectedTiles(selectedtiles, nickname);
        }
        if (currplayer.equals(nickname) && pickedtiles != null) {
            LaunchClient.getView().showPickedTiles(pickedtiles, nickname);
        }
        if (finalscores != null && winner != null) {
            LaunchClient.getView().showFinalScore(finalscores, winner);
        }
        if (authors != null && receivers != null && messages != null) {
            LaunchClient.getView().showChat(authors, receivers, messages, nickname);
        }
        if (currplayer != null) {
            LaunchClient.getView().showCurrentPlayer(currplayer);
        }

    }

    public void setSelectedtiles(Map<String, String> selectedtiles) {
        this.selectedtiles = selectedtiles;
    }

    public void setPickedtiles(Map<String, String> pickedtiles) {
        this.pickedtiles = pickedtiles;
    }
}
