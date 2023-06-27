package it.polimi.ingsw.am40.CLI;

import it.polimi.ingsw.am40.Client.SocketClient;
import it.polimi.ingsw.am40.Model.Position;

import java.util.ArrayList;
import java.util.Map;

/**
 * TODO
 */
public interface View {

    /**
     * Method to choose the connection
     */
    public void chooseConnection();

    /**
     * Method to show the current player
     * @param s (name of the current player)
     */
    public void showCurrentPlayer(String s);

    /**
     * Shows the current score
     * @param map the keys are the players name and the valures linked are the points
     */
    public void showCurrentScore(Map<String, Integer> map);

    /**
     * Shows the hidden score
     * @param score (hidden score to be displayed)
     */
    public void showHiddenScore(int score);

    /**
     * Prints the common goals of the game
     * @param map the number of the commonGoal to the points you get if completed
     * (updated based on how many players have completed it)
     */
    public void showCommonGoals(Map<Integer, Integer> map);

    /**
     * Shows the personal goal
     * @param map represents the personal goal, the key is the position and value associeted is type of the tile
     * @param number parameter used to choose which graphic to display (only in GUI, each color has different graphics)
     */
    public void showPersonalGoal(Map<String, String> map, int number);

    /**
     * Prints the board with the colours of the tiles (key of the map is the position while the value associated to the key is the color of the tile)
     * position e color
     * @param map represents the board
     */
    public void showBoard(Map<String, String> map);

    /**
     * This method calls the method showBookshelf passing a map made of positions and colours (positions are the keys, colors are the contents)
     * @param map represents the bookshelf
     */
    public void showCurrentBookshelf(Map<String, String> map);

    /**
     * This method prints all the bookshelves (a single bookshelf is a map, whose key is the name of the player)
     * @param map is the map that contains the bookshelves
     */
    public void showAllBookshelves(Map<String, Map<String, String>> map);

    /**
     * Prints the bookshelf (the keys of the map are the positions, while the value associated to the key are the colors of the tile in that position)
     * @param map represents the bookshelves
     */
    public void showBookshelf(Map<String, String> map);


    /**
     * Shows which tiles can be picked
     * @param map of the positions that can be taken (the key is the position, while the value associated to the key is the colour)
     * @param arr the tiles already taken
     * @param board represents the board
     */
    public void showBoardPickable(Map<String, String> map, ArrayList<Position> arr, Map<String, String> board);

    /**
     * TODO
     * Shows the selected tiles (if any selected)
     * @param map is the map used to represent the selected tiles
     * @param s is the name of the players
     * @param selected are the numbers of the tiles
     */
    public void showSelectedTiles(Map<String, String> map, String s,ArrayList<ArrayList<String>> selected);


    /**TODO
     * Shows thw picked tiles
     * @param map is the map used to represent the picked tiles
     * @param s is the name of the player
     * @param picked are the numbers of the tiles
     */
    public void showPickedTiles(Map<String, String> map, String s,ArrayList<ArrayList<String>> picked);

    /**
     * Shows the final score and prints the winner
     * @param map has as key the names of the players
     * @param winner is the name of the winner
     */
    public void showFinalScore(Map<String, Integer> map, String winner);

    /**
     * Prints the list of the plauers
     * @param names (array list containing the names of the players)
     */
    public void showPlayers(ArrayList<String> names);

    /**
     * Prints the message passed as argument
     * @param s is the message
     */
    public void printMessage(String s);

    /**
     * Opens the chat for a player
     * @param socketClient represents the client handler from client side
     */
    void chat(SocketClient socketClient);

    /**TODO
     * Shows the chat and the previous messages
     * @param array1 are the indexs of the message
     * @param array2 are the destinataries of the message
     * @param array3
     * @param nickname
     */
    void showChat(ArrayList<String> array1, ArrayList<String> array2, ArrayList<String> array3, String nickname);

    /**
     * Method to print that the player with the nickname passed has quitted the game
     * @param nickname (name of the player that quits)
     */
    void quit(String nickname);

    /**
     * Method used to ask the first player with how many people he wants to play
     */
    void setplayers();

    /**
     * Prints "Waiting for the lobby..."
     */
    void waitLobby();

    /**
     * Prints the name already used (to be substituted) and some other alternatives
     * @param to_be_replaced (name to be replaced)
     * @param suggested (list of the suggested nicknames)
     */
    void showSuggestedNicknames(String to_be_replaced, ArrayList<String> suggested);

    /**
     * Method to print the error message
     * @param error (error message)
     */
    void showError(String error);

    /**
     * Method to print the message that the game has been created
     */
    void showGame();

    /**
     * Method to print the name of the first player
     * @param nickname (name of the first player)
     */
    void showFirstPlayer(String nickname);

    void showCGDone(String nickname, int num, int score);
}
