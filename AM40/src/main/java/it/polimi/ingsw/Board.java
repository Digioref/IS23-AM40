package it.polimi.ingsw;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.json.JsonArray;
import javax.json.JsonObject;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class Board {
    private Map<Position, Tile> grid;

    private static JsonObject configs = null;

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

    public void config(Bag b) {
        for (Position pos : grid.keySet()) {
            grid.put(pos, b.pick());
        }
    }

    public Tile pick(Position pos) {
        if (grid.containsKey(pos) && grid.get(pos) != null) {
            Tile t = grid.get(pos);
            grid.put(pos, null);
            return t;
        }
        return null;
    }

    public void remove(Bag b) {
        for (Position pos : grid.keySet()) {
            if (grid.get(pos) != null) {
                b.insert(grid.get(pos));
                grid.put(pos, null);
            }
        }
    }

    public Map<Position, Tile> getGrid() {
        return grid;
    }

    public void setGrid(Map<Position, Tile> grid) {
        this.grid = grid;
    }
}
