package it.polimi.ingsw.am40.JSONConversion;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class ServerArgs {
    public static String[] ReadServerConfigFromJSON() {
        JSONParser jsonParser = new JSONParser();
        String[] args = new String[2];
        try {
            InputStream is = ServerArgs.class.getClassLoader().getResourceAsStream("Server.json");
            JSONObject obj = (JSONObject) jsonParser.parse(new InputStreamReader(is, StandardCharsets.UTF_8));
            JSONObject server = (JSONObject) obj.get("Server");
            args[1]=server.get("PortNumber").toString();
            args[0]=server.get("HostName").toString();
            return args;
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
