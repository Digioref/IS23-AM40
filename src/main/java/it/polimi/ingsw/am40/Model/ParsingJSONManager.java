package it.polimi.ingsw.am40.Model;

import it.polimi.ingsw.am40.JSONConversion.ServerArgs;
import it.polimi.ingsw.am40.Network.Commands.*;
import it.polimi.ingsw.am40.Network.ICommand;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Map;

/**
 * It is a JSON manager, it manages all the JSON files used to initialize the board, the personal goals, the bag and the game commands
 */
public class ParsingJSONManager {

    /**
     * It fills the board with nocolor tiles
     * @param map the board
     * @param num number of players
     */
    public void createBoard (Map<String, Tile> map, int num) {
        JSONParser jsonParser = new JSONParser();
        try {
            InputStream is = ServerArgs.class.getClassLoader().getResourceAsStream("PositionsBoard.json");
            JSONObject configs = (JSONObject) jsonParser.parse(new InputStreamReader(is, StandardCharsets.UTF_8));
            JSONArray posArray = (JSONArray) configs.get("Positions");
            JSONObject o = (JSONObject) posArray.get(num - 2);
            JSONArray obj1 = (JSONArray) o.get("Players" + Integer.valueOf(num).toString()); // I think it is redundant, you don't need to specify the position in the array and also the name of the elementt, you need just one of the two info
            for (int i = 0; i < obj1.size(); i++) {
                JSONObject t = (JSONObject) obj1.get(i);
                String t1 = t.get("x").toString();
                String t2 = t.get("y").toString();
                Position p = new Position(Integer.parseInt(t1), Integer.parseInt(t2));
                Tile tile = new Tile(TileColor.NOCOLOR, TileType.EMPTY);
                tile.setPos(p);
                map.put(p.getKey(), tile);
                // System.out.println(p.getKey());
            }

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * It takes from the JSON file the structure of the personal goal, according to the parameter k, and initialize the parameter pg
     * with the information in the file
     * @param pg the personal goal to be initialized
     * @param k the number of the personal goal chosen
     */
    public void createPersonalGoals (PersonalGoal pg, int k) {
        JSONParser jsonParser = new JSONParser();
        try {
            InputStream is = ServerArgs.class.getClassLoader().getResourceAsStream("PersonalGoals.json");
            JSONObject persGoals = (JSONObject) jsonParser.parse(new InputStreamReader(is, StandardCharsets.UTF_8));

            JSONArray array = (JSONArray) persGoals.get("PersonalGoals");
            JSONObject o = (JSONObject) array.get(k);

            JSONArray obj1 = (JSONArray) o.get("position");
            JSONArray obj2 = (JSONArray) o.get("color");


            for (Object obj : obj1) {
                JSONObject t = (JSONObject) obj;
                String t1 = t.get("x").toString();
                int x = Integer.parseInt(t1);
                String t2 = t.get("y").toString();
                int y = Integer.parseInt(t2);
                Position p = new Position(x, y);
                pg.getPos().add(p);
            }
            for (Object obj : obj2) {
                JSONObject t = (JSONObject) obj;
                String x = t.get("Color").toString();
                switch (x) {
                    case "Yellow":
                        pg.getColor().add(TileColor.YELLOW);
                        break;
                    case "White":
                        pg.getColor().add(TileColor.WHITE);
                        break;
                    case "Blue":
                        pg.getColor().add(TileColor.BLUE);
                        break;
                    case "Green":
                        pg.getColor().add(TileColor.GREEN);
                        break;
                    case "Cyan":
                        pg.getColor().add(TileColor.CYAN);
                        break;
                    case "Violet":
                        pg.getColor().add(TileColor.VIOLET);
                        break;
                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * It initializes the bag with the correct number of tiles of each color and type
     * @param b the bag
     */
    public void createTiles(Bag b) {
        JSONParser jsonParser = new JSONParser();
        try {
            InputStream is = ServerArgs.class.getClassLoader().getResourceAsStream("Tiles.json");
            JSONObject configs = (JSONObject) jsonParser.parse(new InputStreamReader(is, StandardCharsets.UTF_8));
            JSONArray posArray = (JSONArray) configs.get("Tiles");
            for (int i = 0; i < posArray.size(); i++) {
                JSONObject t = (JSONObject) posArray.get(i);
                String t1 = t.get("color").toString();
                String t2 = t.get("type").toString();
                Tile tile = new Tile(TileColor.NOCOLOR, TileType.EMPTY);
                switch (t1) {
                    case "yellow":
                        tile.setColor(TileColor.YELLOW);
                        break;
                    case "white":
                        tile.setColor(TileColor.WHITE);
                        break;
                    case "blue":
                        tile.setColor(TileColor.BLUE);
                        break;
                    case "green":
                        tile.setColor(TileColor.GREEN);
                        break;
                    case "cyan":
                        tile.setColor(TileColor.CYAN);
                        break;
                    case "violet":
                        tile.setColor(TileColor.VIOLET);
                        break;
                }
                switch (t2) {
                    case "cats":
                        tile.setType(TileType.CATS);
                        break;
                    case "books":
                        tile.setType(TileType.BOOKS);
                        break;
                    case "games":
                        tile.setType(TileType.GAMES);
                        break;
                    case "frames":
                        tile.setType(TileType.FRAMES);
                        break;
                    case "trophies":
                        tile.setType(TileType.TROPHIES);
                        break;
                    case "plants":
                        tile.setType(TileType.PLANTS);
                        break;
                }
                b.insert(tile);
            }

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * It puts in a map the name of the commands and the corresponding interface representing that command
     * @param map a map which maps a command to the corresponding interface
     */
    public void configureCommands(Map<String, ICommand> map) {
        JSONParser jsonParser = new JSONParser();
        try {
            InputStream is = ServerArgs.class.getClassLoader().getResourceAsStream("Commands.json");
            JSONObject configs = (JSONObject) jsonParser.parse(new InputStreamReader(is,StandardCharsets.UTF_8));
            JSONArray posArray = (JSONArray) configs.get("Commands");
            for (int i = 0; i < posArray.size(); i++) {
                JSONObject t = (JSONObject) posArray.get(i);
                String t1 = t.get("Command" + i).toString();
                switch(t1) {
                    case "login":
                        map.put("login", new Login());
                        break;
                    case "help":
                        map.put("help", new Help());
                        break;
                    case "quit":
                        map.put("quit", new Quit());
                        break;
                    case "select":
                        map.put("select", new Select());
                        break;
                    case "pick":
                        map.put("pick", new Pick());
                        break;
                    case "order":
                        map.put("order", new Order());
                        break;
                    case "insert":
                        map.put("insert", new Insert());
                        break;
                    case "remove":
                        map.put("remove", new Remove());
                        break;
                    case "setplayers":
                        map.put("setplayers", new SetPlayers());
                        break;
                    case "getcurrent":
                        map.put("getcurrent", new GetCurrent());
                        break;
                    case "getcurscore":
                        map.put("getcurscore", new GetCurScore());
                        break;
                    case "gethiddenscore":
                        map.put("gethiddenscore", new GetHiddenScore());
                        break;
                    case "getplayers":
                        map.put("getplayers", new GetPlayers());
                        break;
                    case "getcommgoals":
                        map.put("getcommgoals", new GetCommGoals());
                        break;
                    case "getpersgoal":
                        map.put("getpersgoal", new GetPersGoal());
                        break;
                    case "getboard":
                        map.put("getboard", new GetBoard());
                        break;
                    case "getbook":
                        map.put("getbook", new GetBook());
                        break;
                    case "getbookall":
                        map.put("getbookall", new GetBookAll());
                        break;
                    case "chat":
                        map.put("chat", new Chat());
                        break;
                    case "viewchat":
                        map.put("viewchat", new ViewChat());
                        break;
                    case "pong":
                        map.put("pong", new Pong());
                        break;

                }
            }

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * It puts in an array the name sof the commands
     * @param arr the array to be filled
     */
    public static void commands(ArrayList<String> arr) {
        JSONParser jsonParser = new JSONParser();
        try {
            InputStream is = ServerArgs.class.getClassLoader().getResourceAsStream("Commands.json");
            JSONObject configs = (JSONObject) jsonParser.parse(new InputStreamReader(is,StandardCharsets.UTF_8));
            JSONArray posArray = (JSONArray) configs.get("Commands");
            for (int i = 0; i < posArray.size(); i++) {
                JSONObject t = (JSONObject) posArray.get(i);
                String t1 = t.get("Command" + i).toString();
                arr.add(t1);
                }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

}
