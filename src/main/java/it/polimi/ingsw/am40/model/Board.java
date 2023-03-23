
package it.polimi.ingsw.am40.model;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
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
    private Map<String, Tile> grid;



    /**
     * The constructor which builds the instance of the class according to the positions in the json file
     * @param num number of players
     */
    public Board(int num) {
        grid = new HashMap<>();

        JSONParser jsonParser = new JSONParser();
        FileReader reader;
        try {
            reader = new FileReader("PositionsBoard.json");
            JSONObject configs = (JSONObject) jsonParser.parse(reader);
            JSONArray posArray = (JSONArray) configs.get("Positions");
            JSONObject o = (JSONObject) posArray.get(num - 2);
            JSONArray obj1 = (JSONArray) o.get("Players" + Integer.valueOf(num).toString());
            for (int i = 0; i < obj1.size(); i++) {
                JSONObject t = (JSONObject) obj1.get(i);
                String t1 = t.get("x").toString();
                String t2 = t.get("y").toString();
                Position p = new Position(Integer.parseInt(t1), Integer.parseInt(t2));
                Tile tile = new Tile(TileColor.NOCOLOR, TileType.EMPTY);
                tile.setPos(p);
                grid.put(p.getKey(), tile);
            }

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * Picks a tile from the bag b and puts it in a position on the board
     * @param b bag
     */
    public void config(Bag b) {
        for (String pos : grid.keySet()) {
          //  Position p = new Position(-20, -20);
          //  p.convertKey(pos);
          //  grid.put(pos, b.pick());
          //  grid.get(pos).setPos(p);

            Tile t = b.pick();
            t.setPos(grid.get(pos).getPos());
            grid.put(pos, t);
        }
    }

    /**
     * A tile is picked form the board (it's removed form the board)
     * @param pos a position in the board
     * @return the tile picked
     */
    public Tile pick(String pos) {
        if (grid.containsKey(pos) && grid.get(pos).getType() != TileType.EMPTY) {
            Tile t = grid.get(pos);
            grid.put(pos, new Tile(TileColor.NOCOLOR, TileType.EMPTY));
            return t;
        }
        return null;
    }

    /**
     * Removes the remaining tiles from the board in order to reconfig it later
     * @param b bag
     */
    public void remove(Bag b) {
        for (String pos : grid.keySet()) {
            if (grid.get(pos).getType() != TileType.EMPTY) {
                b.insert(grid.get(pos));
                grid.put(pos, new Tile(TileColor.NOCOLOR, TileType.EMPTY));
            }
        }
    }

    /**
     * Returns the map representing the board
     * @return map representing the board
     */
    public Map<String, Tile> getGrid() {
        return grid;
    }

    /**
     * Sets the map according to the parameter
     * @param grid a map representing a board
     */
    public void setGrid(Map<String, Tile> grid) {
        this.grid = grid;
    }
}