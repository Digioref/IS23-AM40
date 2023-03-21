package it.polimi.ingsw.am40.model;

import java.util.ArrayList;

/**
 * Represents the player of the game
 */
public class Player {
    /**
     * The nickname of the player used to be identified during the game
     */
    private final String nickname;
    /**
     * An array containing the tiles selected and picked by the player
     */
    private ArrayList<Tile> tilesPicked;
    /**
     * The current score achieved by the player during the game
     */
    private int currentScore;
    /**
     * The score achieved at the end of the game
     */
    private int finalScore;
    /**
     * The bookshelf of the player
     */
    private Bookshelf bookshelf;
    /**
     * The game board
     */
    private Board board;
    /**
     * The personal goal assigned to the player
     */
    private PersonalGoal personalGoal;
    /**
     * The next player to play his turn, clockwise
     */
    private  Player next;

    /**
     * Constructor which assigns the nickname, and sets to 0 both current score and final score
     * @param nickname
     */
    public Player(String nickname) {
        this.nickname = nickname;
        currentScore = 0;
        finalScore = 0;
        this.tilesPicked = new ArrayList<>();
    }

    /**
     * Selects a tile in position pos on the board and puts it in the tilesPicked array
     * @param pos
     */
    public void pickTile(Position pos) {
        if (board.getGrid().containsKey(pos.getKey())) {
            tilesPicked.add(board.pick(pos.getKey()));
        }
    }

    /**
     * Clears the tilesPicked array
     */
    public void clearTilesPicked() {
        tilesPicked.clear();
    }

    /**
     * Creates the bookshelf of the player
     */
    public void createBookshelf() {
        bookshelf = new Bookshelf();
    }

    /**
     * Places the tiles picked in the column col of player's bookshelf
     * @param col the column of the bookshelf where the tiles must be inserted
     */

    public void placeInBookshelf(int col) {
        for (Tile tile : tilesPicked) {
            bookshelf.addTile(tile, col);
        }
    }

    /**
     * Orders the tiles picked according to the order provided by the parameter
     * @param at an array of tiles, equals to the tilesArray, but with eventually a different order of the tiles
     */

    public void selectOrder(ArrayList<Tile> at) {
        for (int i = 0; i < tilesPicked.size(); i++) {
            tilesPicked.set(i, at.get(i));
        }
    }

    /**
     * Calculates the final score of the player
     */
    public void calculateScore() {
        finalScore = personalGoal.calcScore(bookshelf) + bookshelf.calcScore() + currentScore;
    }

    /**
     * Updates the current score of the player
     * @param commgoal the array of common goals that are actually in game
     */
    public void updateCurrScore (ArrayList<CommonGoal> commgoal) {
        for (CommonGoal commonGoal : commgoal) {
            currentScore += commonGoal.check(bookshelf);
        }
    }

    /**
     * Returns the nickname of the player
     * @return the feature nickname
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * Returns the next player to play
     * @return the feature next
     */
    public Player getNext() {
        return next;
    }

    /**
     * Sets the next player
     * @param next player to play next
     */
    public void setNext(Player next) {
        this.next = next;
    }

    /**
     * Updates the current score
     * @param s the score which ha sto be added to the current score
     */
    public void addCurrentScore(int s) {
        currentScore += s;
    }

    public ArrayList<Tile> getTilesPicked() {
        return tilesPicked;
    }

    public void setTilesPicked(ArrayList<Tile> tilesPicked) {
        this.tilesPicked = tilesPicked;
    }

    /**
     * Return the current score of the player
     * @return the feature current score
     */
    public int getCurrentScore() {
        return currentScore;
    }

    public void setCurrentScore(int currentScore) {
        this.currentScore = currentScore;
    }

    /**
     * Returns the final score of the player
     * @return -the feature final score
     */
    public int getFinalScore() {
        return finalScore;
    }

    public void setFinalScore(int finalScore) {
        this.finalScore = finalScore;
    }

    public Bookshelf getBookshelf() {
        return bookshelf;
    }

    public void setBookshelf(Bookshelf bookshelf) {
        this.bookshelf = bookshelf;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public PersonalGoal getPersonalGoal() {
        return personalGoal;
    }

    /**
     * Sets the personal goal of the player to the personal goal number i
     * @param i the number of the personal goal, it identifies a specific personal goal
     */
    public void setPersonalGoal(int i) {
        this.personalGoal = new PersonalGoal(i);
    }

}
