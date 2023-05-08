package it.polimi.ingsw.am40.JSONConversion;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;

public class JSONConverterCtoS {
    private JSONObject obj;
    private JSONArray arr;
    private String command;
    private ArrayList<String> par;

    public JSONConverterCtoS() {
        obj = new JSONObject();
        arr = new JSONArray();
        par = new ArrayList<>();
    }

    public void toJSON (String s) {
        String[] command = s.split("\\s");
        if(command[0].equals("chat") || command[0].equals("chatall")) {
            command = s.split("#");
        }
        obj.put("Command", command[0]);
        for (int i = 1; i < command.length; i++) {
            arr.add(command[i]);
        }
        if (!arr.isEmpty()) {
            obj.put("Params", arr);
        }

    }

    public void fromJSON(String s) throws ParseException {
        JSONParser jsonParser = new JSONParser();
        JSONObject object = (JSONObject) jsonParser.parse(s);
        command = object.get("Command").toString();
        JSONArray parameters = (JSONArray) object.get("Params");
        if (parameters != null) {
            for (int i = 0; i < parameters.size(); i++) {
                par.add((String) parameters.get(i));
            }
        }
    }

    @Override
    public String toString() {
        return obj.toJSONString();
    }

    public JSONObject getObj() {
        return obj;
    }

    public JSONArray getArr() {
        return arr;
    }

    public String getCommand() {
        return command;
    }

    public ArrayList<String> getPar() {
        return par;
    }
}
