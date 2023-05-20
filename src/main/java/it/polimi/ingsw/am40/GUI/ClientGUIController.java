package it.polimi.ingsw.am40.GUI;

import it.polimi.ingsw.am40.CLI.View;
import it.polimi.ingsw.am40.Client.SocketClient;
import it.polimi.ingsw.am40.Model.Position;

import java.util.ArrayList;
import java.util.Map;

public class ClientGUIController implements View {
    public ClientGUIController() {
    }

    @Override
    public void chooseConnection() {

    }

    @Override
    public void showCurrentPlayer(String s) {

    }

    @Override
    public void showCurrentScore(Map<String, Integer> map) {

    }

    @Override
    public void showHiddenScore(int score) {

    }

    @Override
    public void showCommonGoals(Map<Integer, Integer> map) {

    }

    @Override
    public void showPersonalGoal(Map<String, String> map) {

    }

    @Override
    public void showBoard(Map<String, String> map) {

    }

    @Override
    public void showCurrentBookshelf(Map<String, String> map) {

    }

    @Override
    public void showAllBookshelves(Map<String, Map<String, String>> map) {

    }

    @Override
    public void showBookshelf(Map<String, String> map) {

    }

    @Override
    public void showBoardPickable(Map<String, String> map, ArrayList<Position> arr, Map<String, String> board) {

    }

    @Override
    public void showSelectedTiles(Map<String, String> map, String s) {

    }

    @Override
    public void showPickedTiles(Map<String, String> map, String s) {

    }

    @Override
    public void showFinalScore(Map<String, Integer> map, String winner) {

    }

    @Override
    public void showPlayers(ArrayList<String> names) {

    }

    @Override
    public void printMessage(String s) {

    }

    @Override
    public void chat(SocketClient socketClient) {

    }

    @Override
    public void showChat(ArrayList<String> array1, ArrayList<String> array2, ArrayList<String> array3, String nickname) {

    }

    @Override
    public void quit(String nickname) {

    }
}
