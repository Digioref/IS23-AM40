package it.polimi.ingsw.am40.JSONConversion;

import it.polimi.ingsw.am40.Model.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Map;

/**
 *
 * It's a class that manages the conversion of the server messages into JSON strings
 */
public class JSONConverterStoC {
    private final static int NUM = 6;

    /**
     * It creates a JSON string containing the name of the current player of the game
     * @param s name of the current player
     * @return a JSON string
     */
    public static String createJSONCurrentPlayer(String s) {
        JSONObject obj = new JSONObject();
        obj.put("Command", "CurrentPlayer");
        obj.put("Nickname", s);
        return obj.toJSONString();
    }

    /**
     * It creates a JSON string with nicknames of the players and their scores
     * @param map a map between the nicknames of the players and their scores
     * @return a JSON string
     */
    public static String createJSONCurrentScore(Map<String, Integer> map) {
        JSONObject obj = new JSONObject();
        obj.put("Command", "CurrentScore");
        JSONArray arr = new JSONArray();
        for (String s: map.keySet()) {
            JSONObject obj1 = new JSONObject();
            obj1.put("Score", map.get(s));
            obj1.put("Nickname", s);
            arr.add(obj1);
        }
        obj.put("Scores", arr);
        return obj.toJSONString();
    }

    /**
     * It creates a JSON string with the hidden score of the player
     * @param hiddenScore the score visible only to the respective player
     * @return a JSON string
     */
    public static String createJSONHiddenScore(int hiddenScore) {
        JSONObject obj = new JSONObject();
        obj.put("Command", "HiddenScore");
        obj.put("Score", hiddenScore);
        return obj.toJSONString();
    }

    /**
     * It creates a JSON string containing the nicknames of the players in the game
     * @param players an array list containing the players of the game
     * @return a JSON string
     */
    public static String createJSONPlayers(ArrayList<Player> players) {
        JSONObject obj = new JSONObject();
        obj.put("Command", "Players");
        JSONArray arr = new JSONArray();
        for (Player p: players) {
            JSONObject obj1 = new JSONObject();
            obj1.put("Nickname", p.getNickname());
            arr.add(obj1);
        }
        obj.put("Players", arr);
        return obj.toJSONString();
    }

    /**
     * It creates a JSON string containing the numbers identifying the common goals and their available scores
     * @param num1 the number of the first common goal
     * @param score1 the available score of the first common goal
     * @param num2 the number of the second common goal
     * @param score2 the available score of the second common goal
     * @return a JSON string
     */
    public static String createJSONCommonGoals(int num1, int score1, int num2, int score2) {
        JSONObject obj = new JSONObject();
        obj.put("Command", "CommonGoals");
        JSONArray arr = new JSONArray();
        JSONObject obj1 = new JSONObject();
        obj1.put("Number", num1);
        obj1.put("Score", score1);
        arr.add(obj1);
        JSONObject obj2 = new JSONObject();
        obj2.put("Number", num2);
        obj2.put("Score", score2);
        arr.add(obj2);
        obj.put("CommonGoals", arr);
        return obj.toJSONString();
    }

    /**
     * It creates a JSON string containing all the information which describes the personal goal of the player
     * @param personalGoal the personal goal of the player
     * @return a JSON string
     */

    public static String createJSONPersGoal(PersonalGoal personalGoal) {
        JSONObject obj = new JSONObject();
        obj.put("Command", "PersonalGoal");
        obj.put("Number", personalGoal.getKey());
        JSONArray arr1 = new JSONArray();
        for (int i = 0; i < NUM; i++) {
            JSONObject obj1 = new JSONObject();
            obj1.put("x", personalGoal.getPos().get(i).getX());
            obj1.put("y", personalGoal.getPos().get(i).getY());
            obj1.put("color", personalGoal.getColor().get(i).toString());
            arr1.add(obj1);
        }
        obj.put("PersonalGoal", arr1);
        return obj.toJSONString();
    }

    /**
     * It creates a JSON string representing the game board
     * @param board the board of the game
     * @return a JSON string
     */
    public static String createJSONBoard(Board board) {
        JSONObject obj = new JSONObject();
        obj.put("Command", "Board");
        JSONArray arr1 = new JSONArray();
        for (String s: board.getGrid().keySet()) {
            JSONObject obj1 = new JSONObject();
            obj1.put("color", board.getGrid().get(s).getColor().toString());
            obj1.put("x", board.getGrid().get(s).getPos().getX());
            obj1.put("y", board.getGrid().get(s).getPos().getY());
            arr1.add(obj1);
        }
        obj.put("Board", arr1);
        return obj.toJSONString();
    }

    /**
     * It creates a JSON string representing the bookshelf of the player (which tile in which position)
     * @param bookshelf the bookshelf of the player
     * @return a JSON string
     */
    public static String createJSONBook(Bookshelf bookshelf) {
        JSONObject obj = new JSONObject();
        obj.put("Command", "Bookshelf");
        JSONArray arr1 = new JSONArray();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 6; j++) {
                JSONObject obj1 = new JSONObject();
                if (bookshelf.getBookshelf().get(i).getTile(j) == null) {
                    obj1.put("color", TileColor.NOCOLOR.toString());
                }
                else {
                    obj1.put("color", bookshelf.getBookshelf().get(i).getTile(j).getColor().toString());
                }
                obj1.put("x", i);
                obj1.put("y", j);
                arr1.add(obj1);
            }
        }
        obj.put("Bookshelf", arr1);
        return obj.toJSONString();
    }

    /**
     * It creates a JSON string containing all the information about the bookshelves of the players
     * @param players array containing the players of the game
     * @return a JSON string
     */
    public static String createJSONBookAll(ArrayList<Player> players) {
        JSONObject obj = new JSONObject();
        JSONArray arr = new JSONArray();
        obj.put("Command", "BookshelfAll");
        for (Player p: players) {
            JSONArray arr1 = new JSONArray();
            JSONObject obj2 = new JSONObject();
            obj2.put("Nickname", p.getNickname());
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 6; j++) {
                    JSONObject obj1 = new JSONObject();
//                    System.out.println("---" + i + "???" + j + "---");
                    if (p.getBookshelf().getBookshelf().get(i).getTile(j) == null) {
                        obj1.put("color", TileColor.NOCOLOR.toString());
                    }
                    else {
                        obj1.put("color", p.getBookshelf().getBookshelf().get(i).getTile(j).getColor().toString());
                    }
                    obj1.put("x", i);
                    obj1.put("y", j);
                    arr1.add(obj1);
                }
            }
            obj2.put("Bookshelf", arr1);
            arr.add(obj2);
        }
        obj.put("Bookshelves" , arr);
        return obj.toJSONString();
    }

    /**
     * It creates a JSON string about the tiles of the board that can be selected
     * @param board the board of the game
     * @param arr array of the positions of the tiles already selected
     * @return a JSON string
     */
    public static String createJSONBoardPickable(Board board, ArrayList<Position> arr) {
        JSONObject obj = new JSONObject();
        obj.put("Command", "BoardPickable");
        JSONArray arr1 = new JSONArray();
        for (Position p: board.getPickableTiles()) {
            JSONObject obj1 = new JSONObject();
            if (board.getGrid().containsKey(p.getKey()) && !arr.contains(p)) {
                obj1.put("color", board.getGrid().get(p.getKey()).getColor().toString());
                obj1.put("x", p.getX());
                obj1.put("y", p.getY());
                arr1.add(obj1);
            }
        }
        obj.put("PickableTiles", arr1);
        JSONArray arr2 = new JSONArray();
        for (Position p: arr) {
            JSONObject obj1 = new JSONObject();
            obj1.put("x", p.getX());
            obj1.put("y", p.getY());
            arr2.add(obj1);
        }
        obj.put("AlreadySel", arr2);
        JSONArray arr3 = new JSONArray();
        for (String s: board.getGrid().keySet()) {
            JSONObject obj1 = new JSONObject();
            obj1.put("color", board.getGrid().get(s).getColor().toString());
            obj1.put("x", board.getGrid().get(s).getPos().getX());
            obj1.put("y", board.getGrid().get(s).getPos().getY());
            arr3.add(obj1);
        }
        obj.put("Board", arr3);
        return obj.toJSONString();
    }

    /**
     * It creates a JSON string about the tiles already selected by the player
     * @param player the player who has already selected some tiles
     * @return a JSON string
     */
    public static String createJSONSelectedTiles(Player player) {
        JSONObject obj = new JSONObject();
        obj.put("Command", "SelectedTiles");
        JSONArray arr1 = new JSONArray();
        for (Position p: player.getSelectedPositions()) {
            JSONObject obj1 = new JSONObject();
            obj1.put("color", player.getBoard().getGrid().get(p.getKey()).getColor().toString());
            obj1.put("x", p.getX());
            obj1.put("y", p.getY());
            arr1.add(obj1);
        }
        obj.put("SelectedTiles", arr1);
        obj.put("Nickname", player.getNickname());
        return obj.toJSONString();
    }

    /**
     * It creates a JSON string containing the tiles picked by the player
     * @param player the player
     * @return a JSON string
     */
    public static String createJSONPickedTiles(Player player) {
        JSONObject obj = new JSONObject();
        obj.put("Command", "PickedTiles");
        JSONArray arr1 = new JSONArray();
        for (Tile t: player.getTilesPicked()) {
            JSONObject obj1 = new JSONObject();
            obj1.put("color", t.getColor().toString());
            obj1.put("x", t.getPos().getX());
            obj1.put("y", t.getPos().getY());
            arr1.add(obj1);
        }
        obj.put("PickedTiles", arr1);
        obj.put("Nickname", player.getNickname());
        return obj.toJSONString();
    }

    /**
     * It creates a JSON string with the final scores of the players
     * @param players the players of the game
     * @param winner the winner of the game
     * @return a JSON string
     */
    public static String createJSONFinalScore(ArrayList<Player> players, Player winner) {
        JSONObject obj = new JSONObject();
        obj.put("Command", "FinalScores");
        JSONArray arr1 = new JSONArray();
        for (Player p: players) {
            JSONObject obj1 = new JSONObject();
            obj1.put("Nickname", p.getNickname());
            System.out.println("!!!!!!!!! "+p.getNickname());
            obj1.put("Score", p.getFinalScore());
            System.out.println("XXXXXXXX: "+p.getFinalScore());
            arr1.add(obj1);
        }
        obj.put("FinalScores", arr1);
        obj.put("Nickname", winner.getNickname());
        return obj.toJSONString();
    }

    /**
     * It creates a JSON string from a simple string representing a message for the player from the server
     * @param s a string representing a message from the server
     * @return a JSON string
     */
    public static String normalMessage(String s) {
        JSONObject obj = new JSONObject();
        obj.put("Command", s);
        return obj.toJSONString();
    }

    /**
     * It creates a JSON string representing the chat of the game
     * @param authors the senders of the messages
     * @param receivers the receivers of the messages
     * @param messages the messages
     * @return a JSON string
     */
    public static String createJSONChat(ArrayList<String> authors, ArrayList<String> receivers, ArrayList<String> messages){
        JSONObject obj = new JSONObject();
        obj.put("Command", "Chat");
        JSONArray arr1 = new JSONArray();
        JSONArray arr2 = new JSONArray();
        JSONArray arr3 = new JSONArray();
        arr1.addAll(authors);
        arr2.addAll(receivers);
        arr3.addAll(messages);
        obj.put("Authors", arr1);
        obj.put("Receivers", arr2);
        obj.put("Messages", arr3);
        return obj.toJSONString();
    }

    /**
     * It transforms the nickname of the player into a JSON string
     * @param nickname the nickname of the player
     * @return a JSON string
     */
    public static String createJSONNickname(String nickname) {
        JSONObject obj = new JSONObject();
        obj.put("Command", "Name");
        obj.put("Nickname", nickname);
        return obj.toJSONString();
    }

    /**
     * It creates a JSON string for the ping-pong messages exchange
     * @return a JSON string
     */
    public static String createJSONPing() {
        JSONObject obj = new JSONObject();
        obj.put("Command", "Ping");
        return obj.toJSONString();
    }

    /**
     * It creates a JSON string for an error message
     * @param error the error message
     * @return a JSON string
     */
    public static String createJSONError(String error) {
        JSONObject obj = new JSONObject();
        obj.put("Command", "Error");
        obj.put("Error", error);
        return obj.toJSONString();
    }

    /**
     * It transforms the nicknames of the players into a JSON string
     * @param nicknames the nickname sof the players in game
     * @return a JSON string
     */
    public static String createJSONNicknames(ArrayList<String> nicknames) {
        JSONObject obj = new JSONObject();
        obj.put("Command", "Suggest");
        obj.put("Phrase", "What about these nicknames: ");
        JSONArray arr1 = new JSONArray();
        arr1.addAll(nicknames);
        obj.put("Nicknames", arr1);
        return obj.toJSONString();
    }

    /**
     * It returns the nickname of the first player of the game (the one who holds the chair) as a JSON string
     * @param nickname the nickname of the first player
     * @return a JSON string
     */
    public static String createJSONFirstPlayer(String nickname) {
        JSONObject obj = new JSONObject();
        obj.put("Command", "FirstPlayer");
        obj.put("Nickname", nickname);
        return obj.toJSONString();
    }

    public static String createJSONCGDone(String name, int index, int score) {
        JSONObject obj = new JSONObject();
        obj.put("Command", "CGDone");
        obj.put("Nickname", name);
        obj.put("Num", index);
        obj.put("Score", score);
        return obj.toJSONString();
    }
}
