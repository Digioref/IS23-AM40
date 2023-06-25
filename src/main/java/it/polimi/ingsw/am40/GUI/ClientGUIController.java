package it.polimi.ingsw.am40.GUI;

import it.polimi.ingsw.am40.CLI.View;
import it.polimi.ingsw.am40.Client.SocketClient;
import it.polimi.ingsw.am40.Model.Position;

import java.util.ArrayList;
import java.util.Map;

import static javafx.application.Platform.runLater;

public class ClientGUIController implements View {
    private Viewer gui;

    /**
     * todo
     */
    public ClientGUIController() {
        gui = Viewer.getGUI();
    }

    /**
     * todo
     */
    @Override
    public void chooseConnection() {
        runLater(gui::chooseConnection);
    }

    /**
     * TODO
     * @param s
     */
    @Override
    public void showCurrentPlayer(String s) {
        runLater(()->gui.setCurrentPlayer(s));
    }

    @Override
    public void showCurrentScore(Map<String, Integer> map) {
        runLater(()->gui.showCurrentScore(map));
    }

    @Override
    public void showHiddenScore(int score) {
        runLater(()->gui.showHiddenScore(score));
    }

    @Override
    public void showCommonGoals(Map<Integer, Integer> map) {
        runLater(()-> gui.setCommonGoal(map));
    }

    @Override
    public void showPersonalGoal(Map<String, String> map, int number) {
        runLater(()->gui.setPersonalGoal(map, number));
    }

    @Override
    public void showBoard(Map<String, String> map) {
        runLater(()->gui.setBoard(map));
    }

    @Override
    public void showCurrentBookshelf(Map<String, String> map) {}

    @Override
    public void showAllBookshelves(Map<String, Map<String, String>> map) {
        runLater(()->gui.updateBookshelves(map));
    }

    @Override
    public void showBookshelf(Map<String, String> map) {}

    @Override
    public void showBoardPickable(Map<String, String> map, ArrayList<Position> arr, Map<String, String> board) {
        runLater(()->gui.setPickableTiles(map, arr, board));
    }

    @Override
    public void showSelectedTiles(Map<String, String> map, String s,ArrayList<ArrayList<String >> sel) {}

    @Override
    public void showPickedTiles(Map<String, String> map, String s,ArrayList<ArrayList<String>> picked) {
        runLater(()->gui.setPicked(map));
    }

    @Override
    public void showFinalScore(Map<String, Integer> map, String winner) {
        runLater(()->gui.showFinalScores(map, winner));
    }

    @Override
    public void showPlayers(ArrayList<String> names) {
        runLater(()->gui.numPlayers(names));
    }

    @Override
    public void printMessage(String s) {
        if(s.equals("Error")){
            runLater(()->gui.showMessage(s));
        }

    }

    @Override
    public void chat(SocketClient socketClient) {

    }

    @Override
    public void showChat(ArrayList<String> array1, ArrayList<String> array2, ArrayList<String> array3, String nickname) {
        runLater(() -> gui.newMessage(array1,array2,array3,nickname));
    }

    @Override
    public void quit(String nickname) {
        runLater(gui::quit);
    }

    @Override
    public void setplayers() {
        runLater(gui::setplayers);
    }

    @Override
    public void waitLobby() {
        runLater(gui::waitingAnimation);
    }

    @Override
    public void showSuggestedNicknames(String s, ArrayList<String> array4) {
        runLater(()->gui.suggestNicknames(s, array4));
    }

    @Override
    public void showError(String error) {
        runLater(()->gui.showError(error));
    }

    @Override
    public void showGame() {
        runLater(gui::startGame);
    }

    @Override
    public void showFirstPlayer(String nickname) {
        runLater(()->gui.setFirstPlayer(nickname));
    }

}
