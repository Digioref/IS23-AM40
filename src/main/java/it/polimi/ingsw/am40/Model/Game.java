package it.polimi.ingsw.am40.Model;

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
    private final int numPlayers;
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

    private ArrayList<VirtualView> observers;
    private ParsingJSONManager pJSONm;
    private TurnPhase turn;

    /**
     * Constructor which builds the class assigning the number of players and creating the array of players
     * @param numPlayers number of players
     */
    public Game(int numPlayers) {
        this.numPlayers = numPlayers;
        players = new ArrayList<>();
        pJSONm = new ParsingJSONManager();
        observers = new ArrayList<>();
    }

    /**
     * Adds a player to the game
     * @param p player
     */
    public void addPlayer (Player p) { // It doesn't check if we reached the max number of players
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
        pJSONm.createBoard(board.getGrid(), numPlayers);
        endToken = new EndToken();

        createTiles();
        for (Player player : players) {
            player.createBookshelf();
            player.setBoard(board);
        }
        createComGoals();
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
        turn = TurnPhase.START;
        startTurn();
    }

    /**
     * Creates the tiles of the game
     */
    public void createTiles() {
        pJSONm.createTiles(bag);
    }

    /**
     * Creates the 12 common goals
     */
    private void createComGoals() {
        final int NCOMGOALS = 12;
        for (int i = 0; i < NCOMGOALS; i++) {
            commonGoals.add(new CommonGoal(i+1,numPlayers));
        }
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
    private void assignComGoal() {
        Random rand = new Random();
        currentComGoals.add(commonGoals.get(rand.nextInt(commonGoals.size())));
        int x;
        do {
            x = rand.nextInt(commonGoals.size());
        } while (commonGoals.indexOf(currentComGoals.get(0)) == x);
        currentComGoals.add(commonGoals.get(x));
    }

    /**
     * Sets the current player to the next player
     */
    public void nextPlayer() {
        //System.out.println(turn);
        if (checkEndGame() || turn != TurnPhase.ENDTURN) {
            return;
        }
        currentPlayer = currentPlayer.getNext();

    }

    public boolean checkEndGame () {
        if (endToken.isEnd() && currentPlayer.getNext().equals(firstPlayer) && turn == TurnPhase.ENDTURN) {
            setHasEnded(true);
            return true;
        }
        return false;
    }
    public void updatePickableTiles (Position pos) {
        if (turn == TurnPhase.SELECTION) {
            if (board.getPickableTiles().contains(pos)) {
                board.updatePickable(pos);
                currentPlayer.getSelectedPositions().add(pos);
                board.updateAfterSelect(pos, currentPlayer);
                notifyObservers(turn);
            } else {
                for (VirtualView v : observers) {
                    if (currentPlayer.getNickname().equals(v.getNickname())) {
                        v.selectionError();
                    }
                }
            }
        }
        else {
            for (VirtualView v : observers) {
                if (currentPlayer.getNickname().equals(v.getNickname())) {
                    v.selectionTurnError();
                }
            }
        }
    }

    /**
     * Removes the tiles picked by player p
     */
    public void removeSelectedTiles() {
        if (turn == TurnPhase.SELECTION) {
            currentPlayer.clearSelected();
            notifyObservers(turn);
        }
        else {
            for (VirtualView v : observers) {
                if (currentPlayer.getNickname().equals(v.getNickname())) {
                    v.removingTurnError();
                }
            }
        }
    }

    /**
     * Selects the tiles according to the position in the array selectedPositions, which is in the player, and inserts it in the tilesPicked array of player p
     */
    public void pickTiles() {
        if (turn == TurnPhase.PICK) {
            for (Position p : currentPlayer.getSelectedPositions()) {
                currentPlayer.pickTile(p);
            }
            currentPlayer.getSelectedPositions().clear();
            notifyObservers(turn);
            setTurn(TurnPhase.ORDER);
        }
        else {
            for (VirtualView v : observers) {
                if (currentPlayer.getNickname().equals(v.getNickname())) {
                    v.pickingTurnError();
                }
            }
        }
    }

    /**
     * Sets the order of the tiles picked by player p according to the array t
     *
     * @param t array of tiles that specifies the order of the tiles selected
     */
    public void setOrder (ArrayList<Integer> t) {
        if (turn == TurnPhase.ORDER) {
            if (t.size() == currentPlayer.getTilesPicked().size()) {
                currentPlayer.selectOrder(t);
                notifyObservers(turn);
                setTurn(TurnPhase.INSERT);
            } else {
                for (VirtualView v: observers) {
                    if (currentPlayer.getNickname().equals(v.getNickname())) {
                        v.orderingError();
                    }
                }
            }
        }
        else {
            for (VirtualView v : observers) {
                if (currentPlayer.getNickname().equals(v.getNickname())) {
                    v.orderingTurnError();
                }
            }
        }

    }

    /**
     * Inserts the tiles picked by player p in the column c of his bookshelf
     *
     * @param c column of player p's bookshelf
     */
    public void insertInBookshelf (int c) {
        if (turn == TurnPhase.INSERT) {
            setTurn(TurnPhase.ENDTURN);
//            System.out.println("qui2");
            currentPlayer.placeInBookshelf(c);
            currentPlayer.updateCurrScore(currentComGoals);
            endToken.updateScore(currentPlayer);
//            System.out.println("qui4");
            currentPlayer.updateHiddenScore();
            notifyObservers(turn);

        }
        else {
            for (VirtualView v : observers) {
                if (currentPlayer.getNickname().equals(v.getNickname())) {
                    v.insertTurnError();
                }
            }
        }
    }

    /**
     * Calculates the score of each player at the end of the game
     */
    public void endGame() {
        ArrayList<Integer> score = new ArrayList<>(numPlayers);
        if (checkEndGame() && turn == TurnPhase.ENDGAME) {
            for (Player p : players) {
                p.calculateScore();
                score.add(p.getFinalScore());
            }
        }
        notifyObservers(turn);
    }

    public boolean controlRefill () {
        return board.needRefill();
    }

    public void startTurn () {
//        System.out.println("print wrong");
        if (turn == TurnPhase.START) {
            board.clearPickable();
            board.setSideFreeTile();
            for (VirtualView v: observers) {
                if (currentPlayer.getNickname().equals(v.getNickname())) {
                    v.receiveCurrentPlayer(currentPlayer);
                }
            }
            notifyObservers(turn);
            setTurn(TurnPhase.SELECTION);
        }
    }

    public void endTurn () {
        //System.out.println("---" + turn);
        if (turn == TurnPhase.ENDTURN) {
            if (controlRefill()) {
                board.remove(bag);
                board.config(bag);
            }
            if (checkEndGame()){
                setTurn(TurnPhase.ENDGAME);
            }
            else {
                //System.out.println("---" + turn);
                currentPlayer.clearTilesPicked();
                nextPlayer();
                turn = TurnPhase.START;
            }
        }
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

    public void setTurn(TurnPhase turn) {
        this.turn = turn;
    }

    public void register(VirtualView virtualView) {
        observers.add(virtualView);
    }

    public void unregister(VirtualView virtualView) {
        observers.remove(virtualView);
    }

    public void notifyObservers(TurnPhase turnPhase) {

        switch (turnPhase) {
            case START:
                for (VirtualView v : observers) {
                    ArrayList<Bookshelf> b = new ArrayList<>();
                    v.receiveBoard(board);
                    if (currentPlayer.getNickname().equals(v.getNickname())) {
                        v.receiveAllowedPositions(board.getPickableTiles());
                    }
//                    v.receiveCurrentPlayer(currentPlayer);
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
                break;

            case SELECTION:
//                System.out.println("qui");
                for (VirtualView v : observers) {
                    if (currentPlayer.getNickname().equals(v.getNickname())) {
                        v.receiveAllowedPositions(board.getPickableTiles());
                    }
                };
                break;

            case PICK:
                for (VirtualView v : observers) {
                    v.receiveBoard(board);
                    if (currentPlayer.getNickname().equals(v.getNickname())) {
                        v.receivePickedTiles(currentPlayer.getTilesPicked());
                    }
                };
                break;

            case ORDER:
                for (VirtualView v : observers) {
                    if (currentPlayer.getNickname().equals(v.getNickname())) {
                        v.receiveDoneOrder(currentPlayer.getTilesPicked());
                    }
                };
                break;

            case INSERT:
                for (VirtualView v : observers) {
                    ArrayList<Bookshelf> b = new ArrayList<>();
                    for (Player p : players) {
                        if (p.getNickname().equals(v.getNickname())) {
                            v.receiveCurrentScore(p.getCurrentScore());
                            v.receiveHiddenScore(p.getHiddenScore());
                        }
                        b.add(p.getBookshelf());
                    }
                    v.receiveListBookshelves(b);
                };
                break;

            case ENDGAME:
                for (VirtualView v : observers) {
                    for (Player p : players) {
                        if (p.getNickname().equals(v.getNickname())) {
                            v.receiveFinalScore(p.getFinalScore());
                        }
                    }
                };
                break;

        }

    }

    public ArrayList<CommonGoal> getCurrentComGoals() {
        return currentComGoals;
    }

    public TurnPhase getTurn() {
        return turn;
    }
}