package it.polimi.ingsw;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue;


public class PersonalGoal {

    private ArrayList<Position> pos;
    private ArrayList<TileColor> color;
    private static JsonArray PersGoals = null;

    static {
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader("PersonalGoals")) {
            Object obj = jsonParser.parse(reader);
            PersGoals = (JsonArray) obj;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public PersonalGoal (int i) {
        JsonObject a = (JsonObject) PersGoals.get(i);
        JsonArray obj1 = (JsonArray) a.get("position");
        JsonArray obj2 = (JsonArray) a.get("color");

        pos = new ArrayList<>(6);
        color = new ArrayList<>(6);

        for (Object obj : obj1) {
            JsonObject t = (JsonObject) obj;
            String t1 = t.get("x").toString();
            int x = Integer.parseInt(t1);
            pos.get(0).setX(x);
            String t2 = t.get("y").toString();
            int y = Integer.parseInt(t2);
            pos.get(0).setY(y);
        }
        int j = 0;
        for (Object obj : obj2) {
            JsonObject t = (JsonObject) obj;
            String x = t.get("Color").toString();
            switch (x) {
                case "Yellow":
                    color.set(j, TileColor.YELLOW);
                case "White":
                    color.set(j, TileColor.WHITE);
                case "Blue":
                    color.set(j, TileColor.BLUE);
                case "Green":
                    color.set(j, TileColor.GREEN);
                case "Cyan":
                    color.set(j, TileColor.CYAN);
                case "Violet":
                    color.set(j, TileColor.VIOLET);
            }
        }
    }

    public int calcScore(Bookshelf b) {
        int t = 0;
        for (int i = 0; i < pos.size(); i++) {
            if (b.getColumns().get(pos.get(i).getX()).get(pos.get(i).getY()).getColor().equals(color.get(i))) {
                t++;
            }
        }
        switch (t) {
            case 1:
                return 1;
                break;
            case 2:
                return 2;
                break;
            case 3:
                return 4;
                break;
            case 4:
                return 6;
                break;
            case 5:
                return 9;
                break;
            case 6:
                return 12;
                break;
            default:
                return 0;
        }

    }

    public ArrayList<Position> getPos() {
        return pos;
    }

    public void setPos(ArrayList<Position> pos) {
        this.pos = pos;
    }

    public ArrayList<TileColor> getColor() {
        return color;
    }

    public void setColor(ArrayList<TileColor> color) {
        this.color = color;
    }
}
