package it.polimi.ingsw.am40.CLI;

import it.polimi.ingsw.am40.Client.SocketClient;
import it.polimi.ingsw.am40.Model.Position;

import java.util.ArrayList;
import java.util.Map;

/**
 * Interface implemented by the CLI and the GUI. It contains the methods that are called to refresh the corresponding UI
 */
public interface View {

    /**
     * Method to choose the connection
     */
    void chooseConnection();

    /**
     * Method to show the current player
     * @param s (name of the current player)
     */
    void showCurrentPlayer(String s);

    /**
     * Shows the current score
     * @param map the keys are the players name and the values linked are the points
     */
    void showCurrentScore(Map<String, Integer> map);

    /**
     * Shows the hidden score
     * @param score (hidden score to be displayed)
     */
    void showHiddenScore(int score);

    /**
     * Prints the common goals of the game
     * @param map the number of the commonGoal to the points you get if completed
     * (updated based on how many players have completed it)
     */
    void showCommonGoals(Map<Integer, Integer> map);

    /**
     * Shows the personal goal
     * @param map represents the personal goal, the key is the position and value associated is type of the tile
     * @param number parameter used to choose which graphic to display (only in GUI, each color has different graphics)
     */
    void showPersonalGoal(Map<String, String> map, int number);

    /**
     * Prints the board with the colours of the tiles (key of the map is the position while the value associated to the key is the color of the tile)
     * position e color
     * @param map represents the board
     */
    void showBoard(Map<String, String> map);

    /**
     * This method calls the method showBookshelf passing a map made of positions and colours (positions are the keys, colors are the contents)
     * @param map represents the bookshelf
     */
    void showCurrentBookshelf(Map<String, String> map);

    /**
     * This method prints all the bookshelves (a single bookshelf is a map, whose key is the name of the player)
     * @param map is the map that contains the bookshelves
     */
    void showAllBookshelves(Map<String, Map<String, String>> map);


    /**
     * Shows which tiles can be picked
     * @param map of the positions that can be taken (the key is the position, while the value associated to the key is the colour)
     * @param arr the tiles already taken
     * @param board represents the board
     */
    void showBoardPickable(Map<String, String> map, ArrayList<Position> arr, Map<String, String> board);

    /**
     * Shows the selected tiles (if any selected)
     * @param map is the map used to represent the selected tiles
     * @param s is the name of the players
     * @param selected are the numbers of the tiles
     */
    void showSelectedTiles(Map<String, String> map, String s,ArrayList<ArrayList<String>> selected);


    /**
     * Shows thw picked tiles
     * @param map is the map used to represent the picked tiles
     * @param s is the name of the player
     * @param picked are the numbers of the tiles
     */
    void showPickedTiles(Map<String, String> map, String s,ArrayList<ArrayList<String>> picked);

    /**
     * Shows the final score and prints the winner
     * @param map has as key the names of the players
     * @param winner is the name of the winner
     */
    void showFinalScore(Map<String, Integer> map, String winner);

    /**
     * Prints the list of the players
     * @param names (array list containing the names of the players)
     */
    void showPlayers(ArrayList<String> names);

    /**
     * Prints the message passed as argument
     * @param s is the message
     */
    void printMessage(String s);

    /**
     * Opens the chat for a player
     * @param socketClient represents the client handler from client side
     */
    void chat(SocketClient socketClient);

    /**
     * Shows the chat and the previous messages
     * @param array1 are the indexes of the message
     * @param array2 are the receivers of the message
     * @param array3 messages to be displayed
     * @param nickname nickname of the player
     */
    void showChat(ArrayList<String> array1, ArrayList<String> array2, ArrayList<String> array3, String nickname);

    /**
     * Method to print that the player with the nickname passed has quit the game
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

    /**
     * It informs the player that another player has done a common goal obtaining the specified score
     * @param nickname nickname of the player who achieved the common goal
     * @param num number of the common goal
     * @param score score obtained
     */
    void showCGDone(String nickname, int num, int score);
    /**
     * It informs the player with the specific nickname that he is reconnecting to a game
     * @param nickname nickname of the reconnecting player
     */
    void reconnect(String nickname);
}
