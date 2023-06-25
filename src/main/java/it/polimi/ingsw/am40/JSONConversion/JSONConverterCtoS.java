package it.polimi.ingsw.am40.JSONConversion;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;

/**
 * <p>It's a class used to convert:</p>
 * <ul>
 *     <li>a string to a JSON Object and a JSON array</li>
 *     <li>a JSON string to a string and an array of parameters</li>
 * </ul>
 */
public class JSONConverterCtoS {
    private JSONObject obj;
    private JSONArray arr;
    private String command;
    private ArrayList<String> par;

    /**
     * Constructor which initializes all the data structures
     */
    public JSONConverterCtoS() {
        obj = new JSONObject();
        arr = new JSONArray();
        par = new ArrayList<>();
    }

    /**
     * It converts the string in the parameters to a JSON Object and a JSON array
     * @param s the string to be converted
     */
    public void toJSON (String s) {
        String[] command = s.split("\\s");
//        if(command[0].equals("chat") || command[0].equals("chatall")) {
//            command = s.split("#");
//        }
        obj.put("Command", command[0]);
        for (int i = 1; i < command.length; i++) {
            arr.add(command[i]);
        }
        if (!arr.isEmpty()) {
            obj.put("Params", arr);
        }
    }

    /**
     * It converts a JSON string to a string and an array of parameters
     * @param s the JSON string to be converted
     * @throws ParseException
     */
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

    /**
     * It converts a message and the receiver to a JSON object
     * @param receiver the receiver of the message
     * @param message the message exchanged
     */
    public void toJSONChat(String receiver, String message) {
        obj.put("Command", "chat");
        if (receiver == null) {
            arr.add(0, "all");
        } else {
            arr.add(0, receiver);
        }
        arr.add(1, message);
        if (!arr.isEmpty()) {
            obj.put("Params", arr);
        }
    }

    /***
     * it converts a JSON object to a JSON string
     * @return a JSON string
     */
    @Override
    public String toString() {
        return obj.toJSONString();
    }

    /**
     * It returns the JSON object
     * @return a JSON object
     */
    public JSONObject getObj() {
        return obj;
    }

    public JSONArray getArr() {
        return arr;
    }

    /**
     * It returns a string representing the command of the user
     * @return a string
     */
    public String getCommand() {
        return command;
    }

    /**
     * It returns the array containing the parameters of the command
     * @return an array of string
     */
    public ArrayList<String> getPar() {
        return par;
    }
}
