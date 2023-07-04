package it.polimi.ingsw.am40.Client;

import it.polimi.ingsw.am40.Model.Position;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Abstract class representing the client handler from client side. Two classes extend this class:
 * <ul>
 *     <li> RMIClient</li>
 *     <li> SocketCLient</li>
 * </ul>
 */
public abstract class Client {

    protected final static int NUMPINGLOST = 3;
    protected final static int WAIT_PING = 20000;

    protected String nickname;
    protected ScheduledExecutorService ping;

    protected Thread parse;
    protected int numPing;
    protected boolean inChat;
    protected ClientState state;

    /**
     * <p> This method takes in input a String and parses it, identifying the command and the parameters.</p>
     * <p> Then, according to which command is identified, it calls the corresponding method of the UI, to show the information coded by the parameters. </p>
     * @param line message received from the server
     * @throws ParseException
     */
    public void parseMessage(String line) throws ParseException {
        JSONParser jsonParser = new JSONParser();
        JSONObject object = (JSONObject) jsonParser.parse(line);
        String command = object.get("Command").toString();
        switch (command) {
            case "CurrentPlayer":
                if (!inChat) {
                    LaunchClient.getView().showCurrentPlayer(object.get("Nickname").toString());
                }
                state.saveCurrentPlayer(object.get("Nickname").toString());
                break;
            case "CurrentScore":
                JSONArray arr = (JSONArray) object.get("Scores");
                Map<String, Integer> map = new HashMap<>();
                for (Object o : arr) {
                    JSONObject obj = (JSONObject) o;
                    map.put(obj.get("Nickname").toString(), Integer.parseInt(obj.get("Score").toString()));
                }
                if (!inChat) {
                    LaunchClient.getView().showCurrentScore(map);
                }
                state.saveCurrentScore(map);
                break;
            case "HiddenScore":
                if (!inChat) {
                    LaunchClient.getView().showHiddenScore(Integer.parseInt(object.get("Score").toString()));
                }
                state.saveHiddenScore(Integer.parseInt(object.get("Score").toString()));
                break;
            case "Players":
                JSONArray arr1 = (JSONArray) object.get("Players");
                ArrayList<String> names = new ArrayList<>();
                for (Object o : arr1) {
                    JSONObject obj = (JSONObject) o;
                    names.add(obj.get("Nickname").toString());
                }
                if (!inChat) {
                    LaunchClient.getView().showPlayers(names);
                }
                state.savePlayers(names);
                break;
            case "CommonGoals":
                JSONArray arr2 = (JSONArray) object.get("CommonGoals");
                Map<Integer, Integer> map1 = new HashMap<>();
                for (Object o : arr2) {
                    JSONObject obj = (JSONObject) o;
                    map1.put(Integer.parseInt(obj.get("Number").toString()), Integer.parseInt(obj.get("Score").toString()));
                }
                if (!inChat) {
                    LaunchClient.getView().showCommonGoals(map1);
                }
                state.saveCommonGoals(map1);
                break;
            case "PersonalGoal":
                JSONArray arr3 = (JSONArray) object.get("PersonalGoal");
                Map<String, String> map2 = new HashMap<>();
                for (Object o : arr3) {
                    JSONObject obj = (JSONObject) o;
                    Position p = new Position(Integer.parseInt(obj.get("x").toString()), Integer.parseInt(obj.get("y").toString()));
                    map2.put(p.getKey(), obj.get("color").toString());
                }
                if (!inChat) {
                    LaunchClient.getView().showPersonalGoal(map2, Integer.parseInt(object.get("Number").toString()));
                }
                state.savePersonalGoal(map2);
                break;
            case "Board":
                JSONArray arr4 = (JSONArray) object.get("Board");
                Map<String, String> map3 = new HashMap<>();
                for (Object o : arr4) {
                    JSONObject obj = (JSONObject) o;
                    Position p = new Position(Integer.parseInt(obj.get("x").toString()), Integer.parseInt(obj.get("y").toString()));
                    map3.put(p.getKey(), obj.get("color").toString());
                }
                if (!inChat) {
                    LaunchClient.getView().showBoard(map3);
                }
                state.saveBoard(map3);
                break;
            case "Bookshelf":
                JSONArray arr5 = (JSONArray) object.get("Bookshelf");
                Map<String, String> map4 = new HashMap<>();
                for (Object o : arr5) {
                    JSONObject obj = (JSONObject) o;
                    Position p = new Position(Integer.parseInt(obj.get("x").toString()), Integer.parseInt(obj.get("y").toString()));
                    map4.put(p.getKey(), obj.get("color").toString());
                }
                if (!inChat) {
                    LaunchClient.getView().showCurrentBookshelf(map4);
                }
                break;
            case "BookshelfAll":
                JSONArray arr6 = (JSONArray) object.get("Bookshelves");
                Map<String, Map<String, String>> map5 = new HashMap<>();
                for (Object o : arr6) {
                    JSONObject obj = (JSONObject) o;
                    JSONArray array = (JSONArray) obj.get("Bookshelf");
                    Map<String, String> map6 = new HashMap<>();
                    for (Object value : array) {
                        JSONObject obj1 = (JSONObject) value;
                        Position p = new Position(Integer.parseInt(obj1.get("x").toString()), Integer.parseInt(obj1.get("y").toString()));
                        map6.put(p.getKey(), obj1.get("color").toString());
                    }
                    map5.put(obj.get("Nickname").toString(), map6);
                }
                if (!inChat) {
                    LaunchClient.getView().showAllBookshelves(map5);
                }
                state.saveBookshelves(map5);
                break;
            case "BoardPickable":
                JSONArray arr7 = (JSONArray) object.get("PickableTiles");
                Map<String, String> map7 = new HashMap<>();
                ArrayList<Position> arrayList = new ArrayList<>();
                for (Object o : arr7) {
                    JSONObject obj = (JSONObject) o;
                    Position p = new Position(Integer.parseInt(obj.get("x").toString()), Integer.parseInt(obj.get("y").toString()));
                    map7.put(p.getKey(), obj.get("color").toString());
                }
                JSONArray arr8 = (JSONArray) object.get("AlreadySel");
                for (Object o : arr8) {
                    JSONObject obj = (JSONObject) o;
                    Position p = new Position(Integer.parseInt(obj.get("x").toString()), Integer.parseInt(obj.get("y").toString()));
                    arrayList.add(p);
                }
                JSONArray arr9 = (JSONArray) object.get("Board");
                Map<String, String> map8 = new HashMap<>();
                for (Object o : arr9) {
                    JSONObject obj = (JSONObject) o;
                    Position p = new Position(Integer.parseInt(obj.get("x").toString()), Integer.parseInt(obj.get("y").toString()));
                    map8.put(p.getKey(), obj.get("color").toString());
                }
                if (!inChat) {
                    LaunchClient.getView().showBoardPickable(map7, arrayList, map8);
                }
                state.savePickable(map7, arrayList, map8);
                break;
            case "SelectedTiles":
                JSONArray arr10 = (JSONArray) object.get("SelectedTiles");
                Map<String, String> map9 = new HashMap<>();
                ArrayList <ArrayList<String>> selected = new ArrayList<>();

                for (Object o : arr10) {
                    ArrayList<String> tmp = new ArrayList<>();
                    JSONObject obj = (JSONObject) o;
                    Position p = new Position(Integer.parseInt(obj.get("x").toString()), Integer.parseInt(obj.get("y").toString()));
                    String tile = obj.get("color").toString();
                    map9.put(p.getKey(), tile);
                    tmp.add(tile);
                    tmp.add(p.getKey());
                    selected.add(tmp);
                }

                if (!inChat) {
                    LaunchClient.getView().showSelectedTiles(map9, object.get("Nickname").toString(),selected);
                }
                state.saveSelectedTiles(map9,selected);
                break;
            case "PickedTiles":
                JSONArray arr11 = (JSONArray) object.get("PickedTiles");
                Map<String, String> map10 = new HashMap<>();
                ArrayList <ArrayList<String>> picked = new ArrayList<>();
                for (Object o : arr11) {
                    ArrayList<String> tmp = new ArrayList<>();
                    JSONObject obj = (JSONObject) o;
                    Position p = new Position(Integer.parseInt(obj.get("x").toString()), Integer.parseInt(obj.get("y").toString()));
                    String tile = obj.get("color").toString();
                    map10.put(p.getKey(), tile);
                    tmp.add(tile);
                    tmp.add(p.getKey());
                    picked.add(tmp);
                }
                if (!inChat) {
                    LaunchClient.getView().showPickedTiles(map10, object.get("Nickname").toString(),picked);
                }
                state.savePickedTiles(map10,picked);
                break;
            case "FinalScores":
                JSONArray arr12 = (JSONArray) object.get("FinalScores");
                Map<String, Integer> map11 = new HashMap<>();
                for (Object o : arr12) {
                    JSONObject obj = (JSONObject) o;
                    map11.put(obj.get("Nickname").toString(), Integer.parseInt(obj.get("Score").toString()));
                }
                if (!inChat) {
                    LaunchClient.getView().showFinalScore(map11, object.get("Nickname").toString());
                }
                state.saveFinalScores(map11, object.get("Nickname").toString());
                break;
            case "Chat":
                JSONArray arr13 = (JSONArray) object.get("Authors");
                JSONArray arr14 = (JSONArray) object.get("Receivers");
                JSONArray arr15 = (JSONArray) object.get("Messages");
                ArrayList<String> array1 = new ArrayList<>(arr13);
                ArrayList<String> array2 = new ArrayList<>(arr14);
                ArrayList<String> array3 = new ArrayList<>(arr15);
                if (!inChat) {
                    LaunchClient.getView().showChat(array1, array2, array3, nickname);
                }
                state.saveChat(arr13, arr14, arr15);
                break;
            case "Name":
                nickname = (String) object.get("Nickname");
                state.saveNickname(nickname);
                break;
            case "Quit":
                sendMessage("Quit");
                close();
//                LaunchClient.getView().quit(nickname);
                break;
            case "Ping":
                sendPong();
                if(ping!=null){
                    ping.shutdownNow();
                }
                numPing = 0;
                startPing();
                break;
            case "Setplayers":
                LaunchClient.getView().setplayers();
                break;
            case "Waiting":
                LaunchClient.getView().waitLobby();
                break;
            case "Suggest":
                JSONArray arr16 = (JSONArray) object.get("Nicknames");
                ArrayList<String> array4 = new ArrayList<>(arr16);
                String s = (String) object.get("Phrase");
                LaunchClient.getView().showSuggestedNicknames(s, array4);
                break;
            case "Error":
                LaunchClient.getView().showError((String) object.get("Error"));
                break;
            case "Game":
                LaunchClient.getView().showGame();
                break;
            case "FirstPlayer":
                if(!inChat) {
                    LaunchClient.getView().showFirstPlayer((String) object.get("Nickname"));
                }
                state.saveFirstPlayer((String) object.get("Nickname"));
                break;
            case "CGDone":
                if(!inChat) {
                    LaunchClient.getView().showCGDone((String) object.get("Nickname"),Integer.parseInt(object.get("Num").toString()), Integer.parseInt(object.get("Score").toString()));
                }
                state.saveFirstPlayer((String) object.get("Nickname"));
                break;
            case "RECONNECT":
                LaunchClient.getView().reconnect(nickname);
            default:
                if (!inChat) {
                    LaunchClient.getView().printMessage(command);
                }
                break;
        }
    }

    /**
     * Abstract method used to close the corresponding client handler.
     */
    public abstract void close();

    /**
     * Abstract method used to send the Pong message, answering a Ping message received by the Server.
     */
    public abstract void sendPong();

    /**
     * Abstract method used to start a timer waiting for a Ping message from the Server.
     */
    public abstract void startPing();

    /**
     * Abstract method used to send messages to the Server.
     * @param s the message to be sent
     */
    public abstract void sendMessage(String s);

    /**
     * Abstract method used to send a chat message to the server
     * @param command chat message to be sent
     */
    public abstract void chat(String command);
}
