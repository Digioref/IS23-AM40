package it.polimi.ingsw.am40.Model;

import it.polimi.ingsw.am40.JSONConversion.JSONConverterStoC;
import it.polimi.ingsw.am40.Network.VirtualView;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * It is the main class of the game MyShelfie, it has all the methods to update the state of the corresponding match
 */
public class Game implements IGame {
    private static final int WAIT_TIME = 10000;
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
    private Player winner;
    private GroupChat groupChat;
    private ScheduledExecutorService timer;
    private boolean timerstate;
    private ArrayList<String> discPlayers;

    /**
     * Constructor which builds the class assigning the number of players and creating the array of players
     * @param numPlayers number of players
     */
    public Game(int numPlayers) {
        this.numPlayers = numPlayers;
        players = new ArrayList<>();
        pJSONm = new ParsingJSONManager();
        observers = new ArrayList<>();
        groupChat = new GroupChat();
        discPlayers = new ArrayList<>();
        timerstate = false;
        currentComGoals = new ArrayList<>();
        winner = new Player("");
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
        p.setGame(this);
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
        board = new Board();
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
        boolean ok = false;
        do {
            currentPlayer = currentPlayer.getNext();
            if (!currentPlayer.isDisconnected()) {
                ok = true;
            }
        } while (!ok);

    }

    /**
     * It returns the number of players in game that are not disconnected
     * @return number of active players
     */
    public int checkDisconnection() {
        int count = 0;
        for (Player p: players) {
            if (p.isDisconnected()) {
                count++;
            }
        }
        return (numPlayers - count);
    }

    /**
     * <p>It starts a timer because there is only one active players, the others are all disconnected</p>
     *<p>If the timer expires, the game ends and the only player active is the winner</p>
     * <p>If another player reconnects, the game resumes</p>
     */
    public void startTimer() {
        timer = Executors.newScheduledThreadPool(1);
        Runnable task = () -> {
            setTurn(TurnPhase.ENDGAME);
            setHasEnded(true);
            endGame();
        };
        timer.schedule(task, WAIT_TIME, TimeUnit.MILLISECONDS);
        timerstate = true;
        for (VirtualView v: observers) {
            for (Player p: players) {
                if (!p.isDisconnected() && p.getNickname().equals(v.getNickname())) {
                    v.receiveTimer();
                }
            }
        }
    }

    /**
     * It stops the timer started due to the disconnection of the players except one.
     */
    public void stopTimer() {
        timerstate = false;
        timer.shutdownNow();
    }

    /**
     * It checks if the game is ended, so if there is at least one player who has completed his bookshelf
     * @return true if the condition of game ending is fulfilled
     */
    public boolean checkEndGame () {
        if (endToken.isEnd() && currentPlayer.getNext().equals(firstPlayer) && turn == TurnPhase.ENDTURN) {
            setHasEnded(true);
            return true;
        }
        return false;
    }

    /**
     * It updates the selectable tiles of the board according to the tiles selected in the position specified by the perameter
     * @param pos the position of the tiles selected
     */
    public void updatePickableTiles (Position pos) {
        if (turn == TurnPhase.SELECTION) {
            if (board.getPickableTiles().contains(pos)) {
                currentPlayer.getSelectedPositions().add(pos);
                board.updatePickable(pos,currentPlayer);
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
     * Removes the tiles selected by a sepcific player
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
            if (currentPlayer.getBookshelf().checkSpace(currentPlayer.getSelectedPositions().size())) {
                for (Position p : currentPlayer.getSelectedPositions()) {
                    currentPlayer.pickTile(p);
                }
                currentPlayer.getSelectedPositions().clear();
                notifyObservers(turn);
                setTurn(TurnPhase.ORDER);
            } else {
                for (VirtualView v : observers) {
                    if (currentPlayer.getNickname().equals(v.getNickname())) {
                        v.pickColumnFullError();
                    }
                }
            }
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
            if (currentPlayer.getBookshelf().getBookshelf().get(c).isFull() || currentPlayer.getTilesPicked().size()> currentPlayer.getBookshelf().getBookshelf().get(c).getFreeSpace()) {
                for (VirtualView v : observers) {
                    if (currentPlayer.getNickname().equals(v.getNickname())) {
                        v.insertError();
                    }
                }
            } else {
                currentPlayer.placeInBookshelf(c);
                currentPlayer.updateCurrScore(currentComGoals);

                endToken.updateScore(currentPlayer);
//            System.out.println("qui4");
                currentPlayer.updateHiddenScore();
                notifyObservers(turn);
                setTurn(TurnPhase.ENDTURN);
            }
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
        if (checkEndGame() && turn == TurnPhase.ENDGAME) {
            for (Player p : players) {
                p.calculateScore();
                setWinner();
            }
        }
        if (checkDisconnection() == 1) {
            setDiscWinner();
        }
        notifyObservers(turn);
    }
    private void setDiscWinner() {
        for (Player p: players) {
            if (!p.isDisconnected()) {
                winner = p;
                break;
            }
        }
    }

    /**
     * It controls if the board needs to be refilled
     * @return true if the board needs refill, false otherwise
     */
    public boolean controlRefill () {
        return board.needRefill();
    }

    /**
     * It organises the board for the beginning of the turn of a specific player
     */
    public void startTurn () {
        if (turn == TurnPhase.START) {
            board.clearPickable();
            board.setSideFreeTile();
            for (VirtualView v: observers) {
                if (currentPlayer.getNickname().equals(v.getNickname())) {
                    try {
                        v.getClientHandler().sendMessage(JSONConverterStoC.normalMessage("It's your turn!"));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            notifyObservers(turn);
            setTurn(TurnPhase.SELECTION);
        }
    }

    /**
     * It organises the end of the turn, controlling if the board needs refill, if the game ending condition is satisfied and changing the current player
     */
    public void endTurn () {
        System.out.println("---" + turn);
        if (turn == TurnPhase.ENDTURN) {
            if (controlRefill()) {
                board.remove(bag);
                board.config(bag);
            }
            if (checkEndGame()){
                setTurn(TurnPhase.ENDGAME);
            }
            else {
                currentPlayer.clearTilesPicked();
                nextPlayer();
                turn = TurnPhase.START;
            }
        }
   }

    /**
     * It sets the winner of the game
     */
   public void setWinner() {
        ArrayList<Player> pl = new ArrayList<>();
        int max = 0;
        for (Player p: players) {
            if (p.getFinalScore() >= max) {
               max = p.getFinalScore();
           }
        }
        for (Player p: players) {
           if (p.getFinalScore() == max) {
               pl.add(p);
           }
        }
        if (pl.size() == 1) {
           winner = pl.get(0);
        }
        else if (pl.size() >= 2) {
            int fp = players.indexOf(firstPlayer);
            ArrayList<Integer> ind = new ArrayList<>();
            for (Player p: pl) {
                ind.add(players.indexOf(p));
            }
            switch (fp) {
                case 0:
                   winner = players.get(ind.stream().mapToInt(x->x).max().getAsInt());
                   break;
                case 1:
                    if (ind.contains(0)) {
                        winner = players.get(0);
                    } else {
                        winner = players.get(ind.stream().mapToInt(x->x).max().getAsInt());
                    }
                    break;
                case 2:
                    if (ind.contains(1)) {
                        winner = players.get(1);
                    } else if (ind.contains(0)) {
                        winner = players.get(0);
                    } else {
                        winner = players.get(ind.stream().mapToInt(x->x).max().getAsInt());
                    }
                    break;
                case 3:
                    if (ind.contains(3)) {
                        ind.remove(ind.indexOf(3));
                    }
                    winner = players.get(ind.stream().mapToInt(x->x).max().getAsInt());
                    break;
            }
        }

   }

    /**
     * Sets the feature hasStarted to the provided one
     * @param hasStarted a provided boolean
     */
    public void setHasStarted(boolean hasStarted) {
        this.hasStarted = hasStarted;
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

    /**
     * It returns the players in the game
     * @return an array containing the players in the game
     */
    public ArrayList<Player> getPlayers() {
        return players;
    }

    /**
     * It returns the board of the game
     * @return the board of the game
     */
    public Board getBoard() {
        return board;
    }

    /**
     * It returns the bag of the game
     * @return the bag of the game
     */
    public Bag getBag() {
        return bag;
    }

    /**
     * It sets the turn phase according to the parameter
     * @param turn a turn phase
     */
    public void setTurn(TurnPhase turn) {
        this.turn = turn;
    }

    /**
     * It registers the parameter as an observer of the game
     * @param virtualView the observer of the game
     */
    public void register(VirtualView virtualView) {
        observers.add(virtualView);
    }

    /**
     * It unregisters the parameter which was an observer of the game
     * @param virtualView an observer of the game
     */
    public void unregister(VirtualView virtualView) {
        observers.remove(virtualView);
    }

    /**
     * It notifies the observers of the game according to the parameter which represents a turn phase
     * @param turnPhase the turn phase
     */
    public void notifyObservers(TurnPhase turnPhase) {

        switch (turnPhase) {
            case START -> {
                for (VirtualView v : observers) {
                    v.receiveCurrentPlayer(currentPlayer);
                    v.receiveBoard(board);
                    if (currentPlayer.getNickname().equals(v.getNickname())) {
                        v.receiveAllowedPositions(currentPlayer.getSelectedPositions(), board);
                    }
                    v.receiveListPlayers(players);
                    v.receiveCommonGoals(currentComGoals);
                    for (Player p : players) {
                        if (p.getNickname().equals(v.getNickname())) {
                            v.receiveHiddenScore(p.getHiddenScore());
                            v.receivePersonalGoal(p.getPersonalGoal());
                        }
                    }
                    Map<String, Integer> map = new HashMap<>();
                    for (Player p : players) {
                        map.put(p.getNickname(), p.getCurrentScore());
                    }
                    v.receiveCurrentScore(map);
                    v.receiveNumPlayers(numPlayers);
                    v.receiveListBookshelves(players);
                    v.receiveFirstPlayer(firstPlayer);
                    v.receiveCurrentPlayer(currentPlayer);
                }
            }
            case SELECTION -> {
                for (VirtualView v : observers) {
                    if (currentPlayer.getNickname().equals(v.getNickname())) {
                        v.receiveSelectedTiles(currentPlayer);
                        v.receiveAllowedPositions(currentPlayer.getSelectedPositions(), board);
                    }
                }
                ;
            }
            case PICK -> {
                for (VirtualView v : observers) {
                    v.receiveBoard(board);
                    if (currentPlayer.getNickname().equals(v.getNickname())) {
                        v.receivePickedTiles(currentPlayer);
                    }
                }
                ;
            }
            case ORDER -> {
                for (VirtualView v : observers) {
                    if (currentPlayer.getNickname().equals(v.getNickname())) {
                        v.receiveDoneOrder(currentPlayer.getTilesPicked());
                    }
                }
                ;
            }
            case INSERT -> {
                for (VirtualView v : observers) {
                    for (Player p : players) {
                        if (p.getNickname().equals(v.getNickname())) {
                            v.receiveHiddenScore(p.getHiddenScore());
                        }
                    }
                    v.receiveListBookshelves(players);
                }
                ;
            }
            case ENDGAME -> {
                for (VirtualView v : observers) {
                    for (Player p : players) {
                        if (p.getNickname().equals(v.getNickname()) && !p.isDisconnected()) {
                            v.receiveFinalScore(players, winner);
                        }
                    }
                }
                setHasEnded(true);
            }
        }

    }

    /**
     * It returns the common goals actually in game
     * @return an array containing the common goals in game
     */
    public ArrayList<CommonGoal> getCurrentComGoals() {
        return currentComGoals;
    }

    /**
     * It returns the current turn phase
     * @return the current turn phase
     */
    public TurnPhase getTurn() {
        return turn;
    }

    /**
     * It returns the observer of the game
     * @return an array containing the observers of the game
     */
    public ArrayList<VirtualView> getObservers() {
        return observers;
    }

    /**
     * It returns the class representing the chat of the game
     * @return the class representing the chat of the game
     */
    public GroupChat getGroupChat() {
        return groupChat;
    }

    /**
     * It returns the players disconnected from the game
     * @return an array containing the players disconnected
     */
    public ArrayList<String> getDiscPlayers() {
        return discPlayers;
    }

    /**
     * It returns the number of players playing in this game
     * @return number of players of this game
     */
    public int getNumPlayers() {
        return numPlayers;
    }

    /**
     * It returns true if the timer of the game has already started due to disconnections
     * @return true if the timer has started, false otherwise
     */
    public boolean isTimerStarted() {
        return timerstate;
    }

    /**
     * It notifies to other players that another player has just reconnected
     * @param s the nickname of the players reconnected
     */

    public void notifyReconnection(String s) {
        for (VirtualView v: observers) {
            if (v.getNickname().equals(s)) {
                v.receiveBoard(board);
                if (currentPlayer.getNickname().equals(v.getNickname())) {
                    v.receiveAllowedPositions(currentPlayer.getSelectedPositions(), board);
                }
                v.receiveCommonGoals(currentComGoals);
                for (Player p : players) {
                    if (p.getNickname().equals(v.getNickname())) {
                        v.receiveHiddenScore(p.getHiddenScore());
                        v.receivePersonalGoal(p.getPersonalGoal());
                    }
                }
                v.receiveListBookshelves(players);
                v.receiveListPlayers(players);
                v.receiveNumPlayers(numPlayers);
                v.receiveCurrentPlayer(currentPlayer);
                v.receiveChat(groupChat);
                for (Player p: players) {
                    if (p.isDoneCG1()) {
                        v.receiveCommonGoalDone(p.getNickname(), commonGoals.get(0).getNum(), p.getScoreCG1());
                    }
                    if (p.isDoneCG2()) {
                        v.receiveCommonGoalDone(p.getNickname(), commonGoals.get(1).getNum(), p.getScoreCG2());
                    }
                }
            }
        }
        for (VirtualView v: observers) {
            v.receiveReconnection(s);
        }
    }

    /**
     * It notifies to the observer that the common goal identified by the number index has been completed by a player, who obtained a specific score
     * @param name name of the player who ha completed the common goal
     * @param index number identifying the common goal
     * @param score score of the common goal obtained by the player
     */
    public void notifyCommongoal(String name, int index, int score) {
        for (VirtualView v: observers) {
            v.receiveCommonGoalDone(name, index, score);
        }
    }

    /**
     * It sets the current player to the player in the parameter
     * @param currentPlayer a player
     */
    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    /**
     * It sets the turn phase to the ENDGAME phase, so the game has ended
     */
    public void setEnd(){
        turn = TurnPhase.ENDGAME;
    }

    /**
     * It sets the end token to the one provided
     * @param endToken an end token
     */
    public void setEndToken(EndToken endToken){
        this.endToken = endToken;
    }

    public EndToken getEndToken(){
        return endToken;
    }

    public ArrayList<CommonGoal> getCommonGoals() {
        return commonGoals;

    }

    public void resetPickedDisc() {
        if (!currentPlayer.getTilesPicked().isEmpty()) {
            for (Tile t: currentPlayer.getTilesPicked()) {
                board.getGrid().remove(t.getPos().getKey());
                board.getGrid().put(t.getPos().getKey(), t);
            }
        }
        currentPlayer.getTilesPicked().clear();
    }

    public void setBoard(Board board){
        this.board = board;
    }

    public void setFirstPlayer(Player firstPlayer){
        this.firstPlayer = firstPlayer;
    }


}