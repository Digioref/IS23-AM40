package it.polimi.ingsw.am40.Client;

import it.polimi.ingsw.am40.CLI.CliView;
import it.polimi.ingsw.am40.Model.Position;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ClientAdapter implements Runnable {
    private BufferedReader in;
    private CliView cliView;
    public ClientAdapter(Socket socket) {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        cliView = new CliView();
    }

    @Override
    public void run() {
        while (true) {
            String line = null;
            try {
                line = in.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
//            print(line);
            try {
                parseMessage(line);
            } catch (ParseException e) {
                System.out.println("Error in parsing!");
                throw new RuntimeException(e);
            }
        }
    }

    private void parseMessage(String line) throws ParseException {
        JSONParser jsonParser = new JSONParser();
        JSONObject object = (JSONObject) jsonParser.parse(line);
        String command = object.get("Command").toString();
        switch (command) {
            case "CurrentPlayer":
                cliView.showCurrentPlayer(object.get("Nickname").toString());
                break;
            case "CurrentScore":
                JSONArray arr = (JSONArray) object.get("Scores");
                Map<String, Integer> map = new HashMap<>();
                for (int i = 0; i < arr.size(); i++) {
                    JSONObject obj = (JSONObject) arr.get(i);
                    map.put(obj.get("Nickname").toString(), Integer.parseInt(obj.get("Score").toString()));
                }
                cliView.showCurrentScore(map);
                break;
            case "HiddenScore":
                cliView.showHiddenScore(Integer.parseInt(object.get("Score").toString()));
                break;
            case "Players":
                JSONArray arr1 = (JSONArray) object.get("Players");
                ArrayList<String> names = new ArrayList<>();
                for (int i = 0; i < arr1.size(); i++) {
                    JSONObject obj = (JSONObject) arr1.get(i);
                    names.add(obj.get("Nickname").toString());
                }
                cliView.showPlayers(names);
                break;
            case "CommonGoals":
                JSONArray arr2 = (JSONArray) object.get("CommonGoals");
                Map<Integer, Integer> map1 = new HashMap<>();
                for (int i = 0; i < arr2.size(); i++) {
                    JSONObject obj = (JSONObject) arr2.get(i);
                    map1.put(Integer.parseInt(obj.get("Number").toString()), Integer.parseInt(obj.get("Score").toString()));
                }
                cliView.showCommonGoals(map1);
                break;
            case "PersonalGoal":
                JSONArray arr3 = (JSONArray) object.get("PersonalGoal");
                Map<String, String> map2 = new HashMap<>();
                for (int i = 0; i < arr3.size(); i++) {
                    JSONObject obj = (JSONObject) arr3.get(i);
                    Position p = new Position(Integer.parseInt(obj.get("x").toString()), Integer.parseInt(obj.get("y").toString()));
                    map2.put(p.getKey(), obj.get("color").toString());
                }
                cliView.showPersonalGoal(map2);
                break;
            case "Board":
                JSONArray arr4 = (JSONArray) object.get("Board");
                Map<String, String> map3 = new HashMap<>();
                for (int i = 0; i < arr4.size(); i++) {
                    JSONObject obj = (JSONObject) arr4.get(i);
                    Position p = new Position(Integer.parseInt(obj.get("x").toString()), Integer.parseInt(obj.get("y").toString()));
                    map3.put(p.getKey(), obj.get("color").toString());
                }
                cliView.showBoard(map3);
                break;
            case "Bookshelf":
                JSONArray arr5 = (JSONArray) object.get("Bookshelf");
                Map<String, String> map4 = new HashMap<>();
                for (int i = 0; i < arr5.size(); i++) {
                    JSONObject obj = (JSONObject) arr5.get(i);
                    Position p = new Position(Integer.parseInt(obj.get("x").toString()), Integer.parseInt(obj.get("y").toString()));
                    map4.put(p.getKey(), obj.get("color").toString());
                }
                cliView.showCurrentBookshelf(map4);
                break;
            case "BookshelfAll":
                JSONArray arr6 = (JSONArray) object.get("Bookshelves");
                Map<String, Map<String, String>> map5  = new HashMap<>();
                for (int i = 0; i < arr6.size(); i++) {
                    JSONObject obj = (JSONObject) arr6.get(i);
                    JSONArray array = (JSONArray) obj.get("Bookshelf");
                    Map<String, String> map6 = new HashMap<>();
                    for (int j = 0; j < array.size(); j++) {
                        JSONObject obj1 = (JSONObject) array.get(j);
                        Position p = new Position(Integer.parseInt(obj1.get("x").toString()), Integer.parseInt(obj1.get("y").toString()));
                        map6.put(p.getKey(), obj1.get("color").toString());
                    }
                    map5.put(obj.get("Nickname").toString(), map6);
                }
                cliView.showAllBookshelves(map5);
                break;
            case "BoardPickable":
                JSONArray arr7 = (JSONArray) object.get("PickableTiles");
                Map<String, String> map7 = new HashMap<>();
                ArrayList<Position> arrayList = new ArrayList<>();
                for (int i = 0; i < arr7.size(); i++) {
                    JSONObject obj = (JSONObject) arr7.get(i);
                    Position p = new Position(Integer.parseInt(obj.get("x").toString()), Integer.parseInt(obj.get("y").toString()));
                    map7.put(p.getKey(), obj.get("color").toString());
                }
                JSONArray arr8 = (JSONArray) object.get("AlreadySel");
                for (int i = 0; i < arr8.size(); i++) {
                    JSONObject obj = (JSONObject) arr8.get(i);
                    Position p = new Position(Integer.parseInt(obj.get("x").toString()), Integer.parseInt(obj.get("y").toString()));
                    arrayList.add(p);
                }
                JSONArray arr9 = (JSONArray) object.get("Board");
                Map<String, String> map8 = new HashMap<>();
                for (int i = 0; i < arr9.size(); i++) {
                    JSONObject obj = (JSONObject) arr9.get(i);
                    Position p = new Position(Integer.parseInt(obj.get("x").toString()), Integer.parseInt(obj.get("y").toString()));
                    map8.put(p.getKey(), obj.get("color").toString());
                }
                cliView.showBoardPickable(map7, arrayList, map8);
                break;
            case "SelectedTiles":
                JSONArray arr10 = (JSONArray) object.get("SelectedTiles");
                Map<String, String> map9 = new HashMap<>();
                for (int i = 0; i < arr10.size(); i++) {
                    JSONObject obj = (JSONObject) arr10.get(i);
                    Position p = new Position(Integer.parseInt(obj.get("x").toString()), Integer.parseInt(obj.get("y").toString()));
                    map9.put(p.getKey(), obj.get("color").toString());
                }
                cliView.showSelectedTiles(map9, object.get("Nickname").toString());
                break;
            case "PickedTiles":
                JSONArray arr11 = (JSONArray) object.get("PickedTiles");
                Map<String, String> map10 = new HashMap<>();
                for (int i = 0; i < arr11.size(); i++) {
                    JSONObject obj = (JSONObject) arr11.get(i);
                    Position p = new Position(Integer.parseInt(obj.get("x").toString()), Integer.parseInt(obj.get("y").toString()));
                    map10.put(p.getKey(), obj.get("color").toString());
                }
                cliView.showPickedTiles(map10, object.get("Nickname").toString());
                break;
            case "FinalScores":
                JSONArray arr12 = (JSONArray) object.get("FinalScores");
                Map<String, Integer> map11 = new HashMap<>();
                for (int i = 0; i < arr12.size(); i++) {
                    JSONObject obj = (JSONObject) arr12.get(i);
                    map11.put(obj.get("Nickname").toString(), Integer.parseInt(obj.get("Scores").toString()));
                }
                cliView.showFinalScore(map11, object.get("Nickname").toString());
                break;
            default:
                cliView.printMessage(command);
                break;
        }
    }

    public synchronized void print(String s) {
        System.out.println(s);
        System.out.flush();
    }
}
