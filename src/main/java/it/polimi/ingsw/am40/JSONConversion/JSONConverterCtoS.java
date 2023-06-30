package it.polimi.ingsw.am40.JSONConversion;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

/**
 * <p>It's a class used to convert:</p>
 * <ul>
 *     <li>a string to a JSON Object and a JSON array</li>
 *     <li>a JSON string to a string and an array of parameters</li>
 * </ul>
 */
public class JSONConverterCtoS {
    private final JSONObject obj;
    private final JSONArray arr;
    private String command;
    private final ArrayList<String> par;

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
        obj.put("Command", command[0]);
        arr.addAll(Arrays.asList(command).subList(1, command.length));
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
            for (Object parameter : parameters) {
                par.add((String) parameter);
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
        arr.add(0, Objects.requireNonNullElse(receiver, "all"));
        arr.add(1, message);
        obj.put("Params", arr);
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
     * @return a list of string
     */
    public ArrayList<String> getPar() {
        return par;
    }
}
