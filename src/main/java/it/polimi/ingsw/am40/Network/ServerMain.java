package it.polimi.ingsw.am40.Network;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

public class ServerMain {
    private static String hostName;
    private static int portNumber;
    private static GameServer gameServer;

    public static void main(String[] args) throws InterruptedException, IOException {
        System.out.println("Server started!");
        if (args.length == 2) {
            hostName = args[0];
            portNumber = Integer.parseInt(args[1]);
        }
        else {
            hostName = ReadHostFromJSON();
            portNumber = ReadPortFromJSON();
        }

        gameServer = new GameServer(hostName, portNumber);
        Thread thread = new Thread(gameServer);
        thread.start();
        thread.join();
    }

    private static int ReadPortFromJSON() {
        JSONParser jsonParser = new JSONParser();
        FileReader reader;
        try {
            reader = new FileReader("Server.json");
            JSONObject obj = (JSONObject) jsonParser.parse(reader);
            JSONObject server = (JSONObject) obj.get("Server");
            return Integer.parseInt(server.get("PortNumber").toString());
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private static String ReadHostFromJSON() {
        JSONParser jsonParser = new JSONParser();
        FileReader reader;
        try {
            reader = new FileReader("Server.json");
            JSONObject obj = (JSONObject) jsonParser.parse(reader);
            JSONObject server = (JSONObject) obj.get("Server");
            return server.get("HostName").toString();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return null;
    }


}
