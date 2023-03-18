package it.polimi.ingsw.Model;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.json.JsonArray;
import javax.json.JsonObject;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents the game board form which the players pick the Tiles
 */
public class Board {
    /**
     * A map that maps the positions in the board to a tile
     */
    private Map<Position, Tile> grid;
    /**
     * A JsonObject that is used in the parsing of a json file, used to construct the personal goals
     */
    private static JsonObject configs = null;

    /**
     * A static block which converts a json file into the json object configs
     */
    static {
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader("PositionsBoard")) {
            Object configs = jsonParser.parse(reader);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * The constructor which builds the instance of the class according to the positions in the json file
     * @param num number of players
     */
    public Board(int num) {
        grid = new HashMap<>();
        switch (num) {
            case 2:
                JsonArray obj1 = (JsonArray) configs.get("Players2");
                Position p = new Position(-10, -10);
                for (Object obj : obj1) {
                    JsonObject t = (JsonObject) obj;
                    String t1 = t.get("x").toString();
                    p.setX(Integer.parseInt(t1));
                    String t2 = t.get("y").toString();
                    p.setY(Integer.parseInt(t2));
                    grid.put(p, null);
                }
            case 3:
                JsonArray obj2 = (JsonArray) configs.get("Players3");
                Position p1 = new Position(-10, -10);
                for (Object obj : obj2) {
                    JsonObject t = (JsonObject) obj;
                    String t1 = t.get("x").toString();
                    p1.setX(Integer.parseInt(t1));
                    String t2 = t.get("y").toString();
                    p1.setY(Integer.parseInt(t2));
                    grid.put(p1, null);
                }
            case 4:
                JsonArray obj3 = (JsonArray) configs.get("Players4");
                Position p2 = new Position(-10, -10);
                for (Object obj : obj3) {
                    JsonObject t = (JsonObject) obj;
                    String t1 = t.get("x").toString();
                    p2.setX(Integer.parseInt(t1));
                    String t2 = t.get("y").toString();
                    p2.setY(Integer.parseInt(t2));
                    grid.put(p2, null);
                }
        }
    }

    /**
     * Picks a tile from the bag b and puts it in a position on the board
     * @param b bag
     */
    public void config(Bag b) {
        for (Position pos : grid.keySet()) {
            grid.put(pos, b.pick());
        }
    }

    /**
     * A tile is picked form the board (it's removed form the board)
     * @param pos a position in the board
     * @return the tile picked
     */
    public Tile pick(Position pos) {
        if (grid.containsKey(pos) && grid.get(pos) != null) {
            Tile t = grid.get(pos);
            grid.put(pos, null);
            return t;
        }
        return null;
    }

    /**
     * Removes the remaining tiles from the board in order to reconfig it later
     * @param b bag
     */
    public void remove(Bag b) {
        for (Position pos : grid.keySet()) {
            if (grid.get(pos) != null) {
                b.insert(grid.get(pos));
                grid.put(pos, null);
            }
        }
    }

    /**
     * Returns the map representing the board
     * @return map representing the board
     */
    public Map<Position, Tile> getGrid() {
        return grid;
    }

    /**
     * Sets the map according to the parameter
     * @param grid a map representing a board
     */
    public void setGrid(Map<Position, Tile> grid) {
        this.grid = grid;
    }
}
