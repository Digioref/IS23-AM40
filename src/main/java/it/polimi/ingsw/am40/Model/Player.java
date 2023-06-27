package it.polimi.ingsw.am40.Model;

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
     *  The player has completed the first common goal
     */
    private boolean doneCG1;

    /**
     *  The player has completed the second common goal
     */
    private boolean doneCG2;

    private ArrayList<Position> selectedPositions;
    private int hiddenScore;
    private ParsingJSONManager pJSONm;
    private boolean disconnected;
    private Game game;


    /**
     * Constructor which assigns the nickname, and sets to 0 both current score and final score
     * @param nickname
     */
    public Player(String nickname) {
        this.nickname = nickname;
        currentScore = 0;
        finalScore = 0;
        hiddenScore = 0;
        this.tilesPicked = new ArrayList<>();
        doneCG1 = false;
        doneCG2 = false;
        pJSONm = new ParsingJSONManager();
        selectedPositions = new ArrayList<>();
        disconnected = false;
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
        //System.out.println("qui3");
            for (Tile tile : tilesPicked) {
                //System.out.println(tile.toString());
                bookshelf.addTile(tile, col);
            }
    }

    /**
     * Orders the tiles picked according to the order provided by the parameter
     * @param at an array of tiles, equals to the tilesArray, but with eventually a different order of the tiles
     */

    public void selectOrder(ArrayList<Integer> at) {
        System.out.println(at);
        System.out.println(tilesPicked);
        ArrayList<Tile> arr = new ArrayList<>();
        for (int i = 1; i <= at.size(); i++) {
            for (int j = 0; j < at.size(); j++) {
                if(at.get(j) == i) {
                    arr.add(tilesPicked.get(j));
                }
            }
        }
        System.out.println(arr);
        tilesPicked.clear();
        tilesPicked.addAll(arr);
        for (Tile t : tilesPicked) {
            System.out.println(t.toString());
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

        int score;
        /*
        for (CommonGoal commonGoal : commgoal) {
            currentScore += commonGoal.check(bookshelf);
        }
        */
        if (!doneCG1) {
            score = commgoal.get(0).check(bookshelf);
            currentScore += score;
            if (score != 0) {
                doneCG1 = true;
                game.notifyCommongoal(nickname,commgoal.get(0).getNum(), score);
            }
        }
        if (!doneCG2) {
            score = commgoal.get(1).check(bookshelf);
            currentScore += score;
            if (score != 0) {
                doneCG2 = true;
                game.notifyCommongoal(nickname,commgoal.get(1).getNum(), score);
            }
        }
    }

    /**
     * It updates the hidden score (visible to only the corresponding player) of the player
     */
    public void updateHiddenScore() {
        hiddenScore = currentScore + personalGoal.calcScore(bookshelf);
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

    /**
     * It returns the tiles picked by the player
     * @return an array containing the tiles picked by the player
     */
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

    /**
     * It returns the bookshelf of the player
     * @return bookshelf of the player
     */
    public Bookshelf getBookshelf() {
        return bookshelf;
    }

    /**
     * It sets the bookshelf of the player to the provided one
     * @param bookshelf the bookshelf provided
     */
    public void setBookshelf(Bookshelf bookshelf) {
        this.bookshelf = bookshelf;
    }

    /**
     * It returns the game board
     * @return the board of the game
     */
    public Board getBoard() {
        return board;
    }

    /**
     * It sets the board to the provided one
     * @param board the board provided
     */
    public void setBoard(Board board) {
        this.board = board;
    }

    /**
     * It returns the personal goal of the player
     * @return the personal goal of the player
     */
    public PersonalGoal getPersonalGoal() {
        return personalGoal;
    }

    /**
     * Sets the personal goal of the player to the personal goal number i
     * @param i the number of the personal goal, it identifies a specific personal goal
     */
    public void setPersonalGoal(int i) {
        this.personalGoal = new PersonalGoal(i);
        pJSONm.createPersonalGoals(personalGoal, i);
    }

    /**
     * It returns the positions of the tiles on the board selected by the player
     * @return the positions of the tiles selected by the player
     */
    public ArrayList<Position> getSelectedPositions() {
        return selectedPositions;
    }


    public void setSelectedPositions(ArrayList<Position> selectedPositions) {
        this.selectedPositions = selectedPositions;
    }

    /**
     * It clears the selected postions
     */
    public void clearSelected() {
        selectedPositions.clear();
        board.clearPickable();
        board.setSideFreeTile();
    }

    /**
     * It returns the hidden score of the player
     * @return the hidden score
     */
    public int getHiddenScore() {
        return hiddenScore;
    }

    /**
     *
     * It returns true if the player is disconnected
     * @return true if the player is disconnected, false otherwise
     */
    public boolean isDisconnected() {
        return disconnected;
    }

    /**
     * It sets the boolean flag "disconnected" according to the provided one
     * @param disconnected a boolean value
     */
    public void setDisconnected(boolean disconnected) {
        this.disconnected = disconnected;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}