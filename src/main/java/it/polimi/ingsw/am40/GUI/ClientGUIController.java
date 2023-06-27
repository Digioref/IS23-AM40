package it.polimi.ingsw.am40.GUI;

import it.polimi.ingsw.am40.CLI.View;
import it.polimi.ingsw.am40.Client.SocketClient;
import it.polimi.ingsw.am40.Model.Position;

import java.util.ArrayList;
import java.util.Map;

import static javafx.application.Platform.runLater;

/**
 * todo
 */
public class ClientGUIController implements View {
    private Viewer gui;

    /**
     * todo
     */
    public ClientGUIController() {
        gui = Viewer.getGUI();
    }

    /**
     * Method to choose the connection
     */
    @Override
    public void chooseConnection() {
        runLater(gui::chooseConnection);
    }

    /**
     * Method to show the current player
     * @param s (name of the current player)
     */
    @Override
    public void showCurrentPlayer(String s) {
        runLater(()->gui.setCurrentPlayer(s));
    }

    /**
     * Shows the current score
     * @param map the keys are the players name and the valures linked are the points
     */
    @Override
    public void showCurrentScore(Map<String, Integer> map) {
        runLater(()->gui.showCurrentScore(map));
    }

    /**
     * Shows the hidden score
     * @param score (hidden score to be displayed)
     */
    @Override
    public void showHiddenScore(int score) {
        runLater(()->gui.showHiddenScore(score));
    }

    /**
     * Prints the common goals of the game
     * @param map the number of the commonGoal to the points you get if completed
     * (updated based on how many players have completed it)
     */
    @Override
    public void showCommonGoals(Map<Integer, Integer> map) {
        runLater(()-> gui.setCommonGoal(map));
    }

    /**
     * Shows the personal goal
     * @param map represents the personal goal, the key is the position and value associeted is type of the tile
     * @param number parameter used to choose which graphic to display (only in GUI, each color has different graphics)
     */
    @Override
    public void showPersonalGoal(Map<String, String> map, int number) {
        runLater(()->gui.setPersonalGoal(map, number));
    }

    /**
     * Prints the board with the colours of the tiles (key of the map is the position while the value associated to the key is the color of the tile)
     * position e color
     * @param map represents the board
     */
    @Override
    public void showBoard(Map<String, String> map) {
        runLater(()->gui.setBoard(map));
    }

    /**
     * This method calls the method showBookshelf passing a map made of positions and colours (positions are the keys, colors are the contents)
     * @param map represents the bookshelf
     */
    @Override
    public void showCurrentBookshelf(Map<String, String> map) {}

    /**
     * This method prints all the bookshelves (a single bookshelf is a map, whose key is the name of the player)
     * @param map is the map that contains the bookshelves
     */
    @Override
    public void showAllBookshelves(Map<String, Map<String, String>> map) {
        runLater(()->gui.updateBookshelves(map));
    }

    /**
     * Prints the bookshelf (the keys of the map are the positions, while the value associated to the key are the colors of the tile in that position)
     * @param map represents the bookshelves
     */
    @Override
    public void showBookshelf(Map<String, String> map) {}

    /**
     * Shows which tiles can be picked
     * @param map of the positions that can be taken (the key is the position, while the value associated to the key is the colour)
     * @param arr the tiles already taken
     * @param board represents the board
     */
    @Override
    public void showBoardPickable(Map<String, String> map, ArrayList<Position> arr, Map<String, String> board) {
        runLater(()->gui.setPickableTiles(map, arr, board));
    }

    /**
     * TODO
     * Shows the selected tiles (if any selected)
     * @param map is the map used to represent the selected tiles
     * @param s is the name of the players
     * @param selected are the numbers of the tiles
     */
    @Override
    public void showSelectedTiles(Map<String, String> map, String s,ArrayList<ArrayList<String >> selected) {}

    /**TODO
     * Shows thw picked tiles
     * @param map is the map used to represent the picked tiles
     * @param s is the name of the player
     * @param picked are the numbers of the tiles
     */
    @Override
    public void showPickedTiles(Map<String, String> map, String s,ArrayList<ArrayList<String>> picked) {
        runLater(()->gui.setPicked(map));
    }

    /**
     * Shows the final score and prints the winner
     * @param map has as key the names of the players
     * @param winner is the name of the winner
     */
    @Override
    public void showFinalScore(Map<String, Integer> map, String winner) {
        runLater(()->gui.showFinalScores(map, winner));
    }

    /**
     * Prints the list of the plauers
     * @param names (array list containing the names of the players)
     */
    @Override
    public void showPlayers(ArrayList<String> names) {
        runLater(()->gui.numPlayers(names));
    }

    /**
     * Prints the message passed as argument
     * @param s is the message
     */
    @Override
    public void printMessage(String s) {
        if(s.equals("Error")){
            runLater(()->gui.showMessage(s));
        }

    }

    /**
     * Opens the chat for a player
     * @param socketClient represents the client handler from client side
     */
    @Override
    public void chat(SocketClient socketClient) {

    }


    /**TODO
     * Shows the chat and the previous messages
     * @param array1 are the indexs of the message
     * @param array2 are the destinataries of the message
     * @param array3
     * @param nickname
     */
    @Override
    public void showChat(ArrayList<String> array1, ArrayList<String> array2, ArrayList<String> array3, String nickname) {
        runLater(() -> gui.newMessage(array1,array2,array3,nickname));
    }

    /**
     * Method to print that the player with the nickname passed has quitted the game
     * @param nickname (name of the player that quits)
     */
    @Override
    public void quit(String nickname) {
        runLater(gui::quit);
    }

    /**
     * Method used to ask the first player with how many people he wants to play
     */
    @Override
    public void setplayers() {
        runLater(gui::setplayers);
    }


    @Override
    public void waitLobby() {
        runLater(gui::waitingAnimation);
    }

    @Override
    public void showSuggestedNicknames(String to_be_replaced, ArrayList<String> suggested) {
        runLater(()->gui.suggestNicknames(to_be_replaced, suggested));
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

    @Override
    public void showCGDone(String nickname, int num, int score) {
        runLater(()->gui.setPickToken(nickname, num, score));
    }

}
