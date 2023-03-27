package it.polimi.ingsw.am40.Model;

import it.polimi.ingsw.am40.Network.IGameObserver;
import it.polimi.ingsw.am40.Network.VirtualView;

import java.util.ArrayList;
import java.util.Random;

/**
 * Represents a game of MyShelfie
 */
public class Game implements IGame {
    /**
     * Number of players in game
     */
    private int numPlayers;
    /**
     * Array of the players in game
     */
    private ArrayList<Player> players;
    /**
     * Array of common goals of the game
     */
    private ArrayList<CommonGoal> commonGoals;
    /**
     * Array of the two common goals that are actually in game
     */
    private ArrayList<CommonGoal> currentComGoals;
    /**
     * The first player chosen randomly to start the game
     */
    private Player firstPlayer;
    /**
     * The bag which contains the tiles of the game
     */
    private Bag bag;
    /**
     * The game board
     */
    private Board board;
    /**
     * The token representing the score assigned to the player who completes first his bookshelf
     */
    private EndToken endToken;
    /**
     * A boolean which, if it is true, means the game has started
     */
    private boolean hasStarted;
    /**
     * A boolean which, if it is true, means the game has ended
     */
    private boolean hasEnded;
    /**
     * The player who is carrying out his turn
     */
    private Player currentPlayer;

    private ArrayList<VirtualView> observers = new ArrayList<>();

    /**
     * Constructor which builds the class assigning the number of players and creating the array of players
     * @param numPlayers number of players
     */
    public Game(int numPlayers) {
        this.numPlayers = numPlayers;
        players = new ArrayList<>();
    }

    /**
     * Adds a player to the game
     * @param p player
     */

    public void addPlayer (Player p) {
        if (players.size() != 0) {
            players.get(players.size() - 1).setNext(p);
        }
        players.add(p);
        if (players.size() == numPlayers) {
            players.get(numPlayers - 1).setNext(players.get(0));
        }
    }

    /**
     * Configures the game, creating common goals, bag, board,end token, common goal tokens, tiles
     */
    public void configureGame() {
        commonGoals = new ArrayList<>(12);
        currentComGoals = new ArrayList<>(2);
        bag = new Bag();
        board = new Board(numPlayers);
        endToken = new EndToken();

        createTiles();
        for (Player player : players) {
            player.createBookshelf();
            player.setBoard(board);
        }
        createComGoals();
    }

    /**
     * Creates the tiles of the game
     */
    public void createTiles() {
        Tile t;
        for (int i = 0; i < 132; i++) {

            if(i < 22) {
                t = new Tile(TileColor.GREEN, TileType.CATS);
                bag.insert(t);
            }
            if(i >= 22 && i < 44) {
                t = new Tile(TileColor.WHITE, TileType.BOOKS);
                bag.insert(t);
            }
            if(i >= 44 && i < 66) {
                t = new Tile(TileColor.YELLOW, TileType.GAMES);
                bag.insert(t);
            }
            if(i >= 66 && i < 88) {
                t = new Tile(TileColor.BLUE, TileType.FRAMES);
                bag.insert(t);
            }
            if(i >= 88 && i < 110) {
                t = new Tile(TileColor.CYAN, TileType.TROPHIES);
                bag.insert(t);
            }
            if(i >= 110) {
                t = new Tile(TileColor.VIOLET, TileType.PLANTS);
                bag.insert(t);
            }
        }
    }

    /**
     * Creates the 12 common goals
     */
    public void createComGoals() {
        commonGoals.add(new CommonGoal1(numPlayers));
        commonGoals.add(new CommonGoal2(numPlayers));
        commonGoals.add(new CommonGoal3(numPlayers));
        commonGoals.add(new CommonGoal4(numPlayers));
        commonGoals.add(new CommonGoal5(numPlayers));
        commonGoals.add(new CommonGoal6(numPlayers));
        commonGoals.add(new CommonGoal7(numPlayers));
        commonGoals.add(new CommonGoal8(numPlayers));
        commonGoals.add(new CommonGoal9(numPlayers));
        commonGoals.add(new CommonGoal10(numPlayers));
        commonGoals.add(new CommonGoal11(numPlayers));
        commonGoals.add(new CommonGoal12(numPlayers));
    }

    /**
     * Assigns the personal goals to the players, each player has his own personal goal
     */
    public void assignPersonalGoal() {
        ArrayList<Integer> t = new ArrayList<>();
        Random rand = new Random();
        int x;
        for (Player player : players) {
            do {
                x = rand.nextInt(12);
                if (!t.contains(x)) {
                    player.setPersonalGoal(x);
                }
            } while (t.contains(x));
            t.add(x);
        }
    }

    /**
     * Assigns the 2 common goals
     */
    public void assignComGoal() {
        Random rand = new Random();
        currentComGoals.add(commonGoals.get(rand.nextInt(commonGoals.size())));
        int x;
        do {
            x = rand.nextInt(commonGoals.size());
        } while (commonGoals.indexOf(currentComGoals.get(0)) == x);
        currentComGoals.add(commonGoals.get(x));
    }

    /**
     * Starts the game, choosing the first player, assigning common goals and personal goals and configuring the board
     */
    public void startGame() {
        Random rand = new Random();
        firstPlayer = players.get(rand.nextInt(players.size()));
        currentPlayer = firstPlayer;
        board.config(bag);
        assignComGoal();
        assignPersonalGoal();
        setHasStarted(true);
        setHasEnded(false);
        startTurn();
    }

    /**
     * Sets the current player to the next player
     * @return false if the game has ended and the last player who did the turn was the one at the right of the first player, true otherwise
     */
    public boolean nextPlayer() {
        if (checkEndGame()) {
            return false;
        }
        currentPlayer = currentPlayer.getNext();
        return true;

    }

    public boolean checkEndGame () {
        if (endToken.isEnd() && currentPlayer.getNext().equals(firstPlayer)) {
            setHasEnded(true);
            return true;
        }
        return false;
    }
    public void updatePickableTiles (Position pos) {
        board.updatePickable(pos);
        currentPlayer.getSelectedPositions().add(pos);
        notifyObservers(1);
    }

    /**
     * Removes the tiles picked by player p
     */
    public void removeSelectedTiles() {
        currentPlayer.clearSelected();
        notifyObservers(1);
    }

    /**
     * Selects the tiles according to the position in the array selectedPositions, which is in the player, and inserts it in the tilesPicked array of player p
     */
    public void pickTiles() {
        for (Position p : currentPlayer.getSelectedPositions()) {
            currentPlayer.pickTile(p);
        }
        notifyObservers(2);
    }

    /**
     * Sets the order of the tiles picked by player p according to the array t
     *
     * @param t array of tiles that specifies the order of the tiles selected
     */
    public void setOrder (ArrayList<Tile> t) {
        currentPlayer.selectOrder(t);
        notifyObservers(3);
    }

    /**
     * Inserts the tiles picked by player p in the column c of his bookshelf
     *
     * @param c column of player p's bookshelf
     */
    public void insertInBookshelf (int c) {
            currentPlayer.placeInBookshelf(c);
            currentPlayer.updateCurrScore(currentComGoals);
            endToken.updateScore(currentPlayer);
            currentPlayer.updateHiddenScore();
            notifyObservers(4);
    }

    /**
     * Calculates the score of each player at the end of the game
     */
    public void endGame() {
        ArrayList<Integer> score = new ArrayList<>(numPlayers);
        if (checkEndGame()) {
            for (Player p : players) {
                p.calculateScore();
                score.add(p.getFinalScore());
            }
        }
        notifyObservers(5);
    }

    public boolean controlRefill () {
        return board.needRefill();
    }

    public void startTurn () {
        board.setSideFreeTile();
        notifyObservers(0);
    }

    public boolean endTurn () {
        if (controlRefill()) {
            board.remove(bag);
            board.config(bag);
        }
        return nextPlayer();
   }

    /**
     * Returns true if the game has started
     * @return true if the game has started, false otherwise
     */
    public boolean HasStarted() {
        return hasStarted;
    }
    /**
     * Sets the feature hasStarted to the provided one
     * @param hasStarted a provided boolean
     */
    public void setHasStarted(boolean hasStarted) {
        this.hasStarted = hasStarted;
    }

    /**
     * Returns true if the game has ended
     * @return true if the game has ended, false otherwise
     */
    public boolean HasEnded() {
        return hasEnded;
    }

    /**
     * Sets the feature hasEnded to the provided one
     * @param hasEnded a provided boolean
     */
    public void setHasEnded(boolean hasEnded) {
        this.hasEnded = hasEnded;
    }

    /**
     * Returns the current player
     * @return the feature current player
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public Board getBoard() {
        return board;
    }

    public Bag getBag() {
        return bag;
    }

    public void setBag(Bag bag) {
        this.bag = bag;
    }

    public void register(VirtualView virtualView) {
        observers.add(virtualView);
    }

    public void unregister(VirtualView virtualView) {
        observers.remove(virtualView);
    }

    public void notifyObservers(int i) {
        switch (i) {
            case 0:
                for (VirtualView v : observers) {
                    ArrayList<Bookshelf> b = new ArrayList<>();
                    v.receiveBoard(board);
                    if (currentPlayer.getNickname().equals(v.getNickname())) {
                        v.receiveAllowedPositions(board.getPickableTiles());
                    }
                    v.receiveCurrentPlayer(currentPlayer);
                    v.receiveCommonGoals(currentComGoals);
                    for (Player p : players) {
                        if (p.getNickname().equals(v.getNickname())) {
                            v.receiveCurrentScore(p.getCurrentScore());
                            v.receiveHiddenScore(p.getHiddenScore());
                            v.receivePersonalGoal(p.getPersonalGoal());
                        }
                        b.add(p.getBookshelf());
                    }
                    v.receiveListBookshelves(b);
                    v.receiveListPlayers(players);
                    v.receiveNumPlayers(numPlayers);

                };

            case 1:



        }

    }

}