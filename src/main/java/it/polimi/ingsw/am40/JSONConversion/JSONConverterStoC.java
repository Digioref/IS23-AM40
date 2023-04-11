package it.polimi.ingsw.am40.JSONConversion;

import it.polimi.ingsw.am40.Model.Player;
import it.polimi.ingsw.am40.Network.ICommand;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Map;

public class JSONConverterStoC {

    public static String createJSONCurrentPlayer(String s) {
        JSONObject obj = new JSONObject();
        obj.put("Command", "CurrentPlayer");
        obj.put("Nickname", s);
        return obj.toJSONString();
    }

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

    public static String createJSONHiddenScore(int hiddenScore) {
        JSONObject obj = new JSONObject();
        obj.put("Command", "HiddenScore");
        obj.put("Score", hiddenScore);
        return obj.toJSONString();
    }

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
}
