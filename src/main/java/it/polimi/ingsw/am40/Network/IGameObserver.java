package it.polimi.ingsw.am40.Network;

import it.polimi.ingsw.am40.Model.*;

import java.util.ArrayList;
import java.util.Map;

/**
 * The interface implemented by the virtual view that defines the methods use dby the virtual view to receive information from the game and send them to the client
 */
public interface IGameObserver {
    /**
     * It receives the number of players from the game and sends it to the client
     * @param numPlayers number of players
     */
    public void receiveNumPlayers(int numPlayers);

    /**
     * It receives the players from the game and sends their nicknames to the client
     * @param players players of the game
     */
    public void receiveListPlayers(ArrayList<Player> players);

    /**
     * It receives the common goals from the game and sends them to the client
     * @param commonGoals common goals of the game
     */
    public void receiveCommonGoals(ArrayList<CommonGoal> commonGoals);

    /**
     * It receives the personal goal from the game and sends it to the client
     * @param personalGoal personal goal
     */
    public void receivePersonalGoal(PersonalGoal personalGoal);

    /**
     * It receives the bookshelves from the game and sends them to the client
     * @param players players of the game; from the players, their bookshelves are obtained
     */
    public void receiveListBookshelves(ArrayList<Player> players);

    /**
     * It receives the positions of the tiles that can be selected from the game and sends them to the client
     * @param positions the positions of the tile already selected
     * @param board the board of the game
     */
    public void receiveAllowedPositions(ArrayList<Position> positions, Board board);

    /**
     * It receives the board from the game and sends it to the client
     * @param board the board
     */
    public void receiveBoard(Board board);

    /**
     * It receives the current player from the game and sends it to the client
     * @param player current player
     */
    public void receiveCurrentPlayer(Player player);

    /**
     * It receives the hidden score of the player from the game and sends it to the client
     * @param hiddenScore the hidden score of the player
     */
    public void receiveHiddenScore(int hiddenScore);

    /**
     * It receives the order of the tiles picked from the game and sends that the ordering went well to the client
     * @param array the array of the tiles in the order specified by the player
     */
    public void receiveDoneOrder(ArrayList<Tile> array);

    /**
     * It receives the final score of each player from the game and sends it to the client
     * @param players players of the game
     * @param winner winner
     */
    public void receiveFinalScore(ArrayList<Player> players, Player winner);

    /**
     * It receives the picked tiles from the game and sends them to the client
     * @param player player who has picked the tiles
     */
    public void receivePickedTiles(Player player);

    /**
     * It receives the tiles selected by the players from the game and sends them to the client
     * @param player player who has selected the tiles
     */
    public void receiveSelectedTiles(Player player);

    /**
     * It receives the starting of the timer from the game and sends a message, specifying that the disconnection timer has started, to the client
     */
    public void receiveTimer();

    /**
     * It receives the nickname of the player who disconnected from the game and sends it to the client
     * @param s nickname of the disconnected player
     */
    public void receiveDisconnection(String s);

    /**
     * It receives the current score of each player from the game and sends it to the client
     * @param map a map between the nicknames of the players and theirs current scores
     */
    public void receiveCurrentScore(Map<String, Integer> map);

    /**
     * It receives the first player from the game and sends his nickname to the client
     * @param p first player
     */
    void receiveFirstPlayer(Player p);

    void receiveCommonGoalDone(String name, int index, int score);

    void receiveReconnection(String s);

    void receiveBookshelf(Bookshelf bookshelf);
}
