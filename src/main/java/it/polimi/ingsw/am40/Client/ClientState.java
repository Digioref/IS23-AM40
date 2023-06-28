package it.polimi.ingsw.am40.Client;

import it.polimi.ingsw.am40.Model.Position;

import java.util.ArrayList;
import java.util.Map;

/**
 * <p>It's a class used to store each information about the game when the player is in the chat</p>
 * <p>If the player is in the chat, no updates are shown to the player</p>
 * <p>When the player exits from the chat, each information stored in this class is shown to the player</p>
 */
public class ClientState {
    private Client client;
    private String currplayer;
    private Map<String, Integer> currscore;
    private int hiddenscore;
    private ArrayList<String> players;
    private Map<Integer, Integer> commongoals;
    private Map<String, String> personalgoal;
    private Map<String, String> board;
    private Map<String, String> bookshelf;
    private Map<String, Map<String, String>> bookshelves;
    private Map<String, String> pickabletiles;
    private ArrayList<Position> alreadysel;
    private Map<String, String> boardsel;
    private Map<String, String> selectedtiles;
    private Map<String, String> pickedtiles;
    private Map<String, Integer> finalscores;
    private String winner;
    private String nickname;
    private ArrayList<String> authors;
    private ArrayList<String> receivers;
    private ArrayList<String> messages;
    private ArrayList<ArrayList<String>> tileselected;
    private ArrayList<ArrayList<String>> tilespicked;
    private String firstPlayer;

    /**
     * Constructor that sets the client to which this state is associated
     * @param client client
     */
    public ClientState(Client client) {
        this.client = client;
    }

    /**
     * Saves the name of the current player in currplayer
     * @param nickname nickname of the current player
     */
    public void saveCurrentPlayer(String nickname) {
        currplayer = nickname;
    }

    /**
     * Saves the map made of name of the player and score
     * @param map map between nicknames and current scores
     */
    public void saveCurrentScore(Map<String, Integer> map) {
        currscore = map;
    }

    /**
     * Saves the map made of name of the player and hidden score
     * @param score hidden score of the player
     */
    public void saveHiddenScore(int score) {
        hiddenscore = score;
    }

    /**
     * Saves the names of the players
     * @param names nicknames of the other players in the game
     */
    public void savePlayers(ArrayList<String> names) {
        players = names;
    }

    /**
     * Saves the commongoals (passed parameter) in the attribute commongoals
     * @param commonGoals common goals and their scores in a map
     */
    public void saveCommonGoals(Map<Integer, Integer> commonGoals) {
        commongoals = commonGoals;
    }

    /**
     * Saves the personalgoals (passed parameter) in the attribute commongoals
     * @param personalGoal personal goal of the player
     */
    public void savePersonalGoal(Map<String, String> personalGoal) {
        personalgoal = personalGoal;
    }

    /**
     * Saves the board (passed parameter) in the attribute board
     * @param board_map map representing the board (position -> tile)
     */
    public void saveBoard(Map<String, String> board_map) {
        board = board_map;
    }

    /**
     * Saves the bookshelf (parameter passed) in the attribute bookshelf
     * @param bookshelf_map map representing the bookshelf (position -> tile)
     */
    public void saveBookshelf(Map<String, String> bookshelf_map) {
        bookshelf = bookshelf_map;
    }

    /**
     * Saves the other bookshelves (parameter passed, other players bookshelves) in the attribute bookshelves
     * @param bookshelves_map map representing bookshelves (nickname -> (position -> tile))
     */
    public void saveBookshelves(Map<String, Map<String, String>> bookshelves_map) {
        bookshelves = bookshelves_map;
    }

    /**
     * Saves the pickable tiles
     * @param pickabletiles_map tiles that can be picked
     * @param arrayList tiles already selected by the player
     * @param boardsel_map map of the board
     */
    public void savePickable(Map<String, String> pickabletiles_map, ArrayList<Position> arrayList, Map<String, String> boardsel_map) {
        pickabletiles = pickabletiles_map;
        alreadysel = arrayList;
        boardsel = boardsel_map;
    }

    /**
     * Saves the selected tiles
     * @param selectedtiles_map selected tiles
     * @param selected selected tiles
     */
    public void saveSelectedTiles(Map<String, String> selectedtiles_map,ArrayList<ArrayList<String>> selected) {
        selectedtiles = selectedtiles_map;
        tileselected =  selected;
    }

    /**
     * Saves the tiles already picked
     * @param pickabletiles_map picked tiles
     * @param picked picked tiles
     */
    public void savePickedTiles(Map<String, String> pickabletiles_map,ArrayList<ArrayList<String>> picked) {
        pickedtiles = pickabletiles_map;
        tilespicked = picked;
    }

    /**
     * Saves the final scores and the winner
     * @param finalscores_map final scores associated to the corresponding player
     * @param s nickname of the winner
     */
    public void saveFinalScores(Map<String, Integer> finalscores_map, String s) {
        finalscores = finalscores_map;
        winner = s;
    }

    /**
     * Sets the nickname (parameter passed) in the attribute nickname
     * @param nickname nickname of the player
     */
    public void saveNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * Saves the chat
     * @param authors senders of the messages
     * @param receivers receivers of the messages
     * @param messages messages sent
     */
    public void saveChat(ArrayList<String> authors, ArrayList<String> receivers, ArrayList<String> messages) {
        this.authors = authors;
        this.receivers = receivers;
        this.messages = messages;
    }

    /**
     * It refreshes the CLI when the user exits from the chat
     */
    public void refresh() {
        if (currscore != null) {
            LaunchClient.getView().showCurrentScore(currscore);
        }
        LaunchClient.getView().showHiddenScore(hiddenscore);
        if (players != null) {
            LaunchClient.getView().
                    showPlayers(players);
        }
        if (currplayer != null) {
            LaunchClient.getView().showCurrentPlayer(currplayer);
        }
        if (commongoals != null) {
            LaunchClient.getView().showCommonGoals(commongoals);
        }
        if (personalgoal != null) {
            LaunchClient.getView().showPersonalGoal(personalgoal, 0);
        }
        if (board != null) {
            LaunchClient.getView().showBoard(board);
        }
        if (bookshelves != null) {
            LaunchClient.getView().showAllBookshelves(bookshelves);
        }
        System.out.println(nickname);
        if (pickabletiles != null && alreadysel != null && boardsel != null && currplayer.equals(nickname)) {
            LaunchClient.getView().showBoardPickable(pickabletiles, alreadysel, boardsel);
        }
        if (currplayer.equals(nickname) && selectedtiles != null) {
            LaunchClient.getView().showSelectedTiles(selectedtiles, nickname,tileselected);
        }
        if (currplayer.equals(nickname) && pickedtiles != null) {
            LaunchClient.getView().showPickedTiles(pickedtiles, nickname,tilespicked);
        }
        if (finalscores != null && winner != null) {
            LaunchClient.getView().showFinalScore(finalscores, winner);
        }
        if (authors != null && receivers != null && messages != null) {
            LaunchClient.getView().showChat(authors, receivers, messages, nickname);
        }
        if (currplayer != null) {
            LaunchClient.getView().showCurrentPlayer(currplayer);
        }

    }

    /**
     * Sets the attribute selectedTiles to the parameter passed
     * @param selectedtiles
     */
    public void setSelectedtiles(Map<String, String> selectedtiles) {
        this.selectedtiles = selectedtiles;
    }

    /**
     * Sets the attribute selectedTiles to the parameter passed
     * @param pickedtiles
     */
    public void setPickedtiles(Map<String, String> pickedtiles) {
        this.pickedtiles = pickedtiles;
    }

    /**
     * It saves the nickname of the first player
     * @param nickname nickname of the first player
     */
    public void saveFirstPlayer(String nickname) {
        firstPlayer = nickname;
    }
}
