package it.polimi.ingsw.am40.Network;

import it.polimi.ingsw.am40.Controller.Controller;
import it.polimi.ingsw.am40.Model.*;

import java.util.ArrayList;

public class VirtualView implements  IGameObserver{

    private String nickname;
    private ClientHandler clientHandler;
    private Controller controller;

    public VirtualView(String nickname, ClientHandler clientHandler, Controller controller) {
        this.nickname = nickname;
        this.clientHandler = clientHandler;
        this.controller = controller;
    }

    @Override
    public void update() {

    }

    @Override
    public void receiveNumPlayers(int numPlayers) {

    }

    @Override
    public void receiveListPlayers(ArrayList<Player> players) {

    }

    @Override
    public void receiveCommonGoals(ArrayList<CommonGoal> commonGoals) {

    }

    @Override
    public void receivePersonalGoal(PersonalGoal personalGoal) {

    }

    @Override
    public void receiveListBookshelves(ArrayList<Bookshelf> bookshelves) {

    }

    @Override
    public void receiveAllowedPositions(ArrayList<Position> positions) {

    }

    @Override
    public void receiveAvailableColumns(ArrayList<Integer> columns) {

    }

    @Override
    public void receiveBoard(Board board) {

    }

    @Override
    public void receiveCurrentPlayer(Player player) {

    }

    @Override
    public void receiveCurrentScore(int currentScore) {

    }

    @Override
    public void receiveHiddenScore(int hiddenScore) {

    }

    @Override
    public void receiveDoneOrder(ArrayList<Tile> array) {

    }

    @Override
    public void receiveFinalScore(int finalScore) {

    }

    @Override
    public void receivePickedTiles(ArrayList<Tile> array) {

    }

    public String getNickname() {
        return nickname;
    }
}
