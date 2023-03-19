package it.polimi.ingsw.am40;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;


public class PersonalGoal {

    private ArrayList<Position> pos;
    private ArrayList<TileColor> color;
    private static JSONArray PersGoals = null;

    static {
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader("PersonalGoals")) {
            Object obj = jsonParser.parse(reader);
            PersGoals = (JSONArray) obj;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public PersonalGoal (int i) {
        JSONObject a = (JSONObject) PersGoals.get(i);
        JSONArray obj1 = (JSONArray) a.get("position");
        JSONArray obj2 = (JSONArray) a.get("color");

        pos = new ArrayList<>(6);
        color = new ArrayList<>(6);

        for (Object obj : obj1) {
            JSONObject t = (JSONObject) obj;
            String t1 = t.get("x").toString();
            int x = Integer.parseInt(t1);
            pos.get(0).setX(x);
            String t2 = t.get("y").toString();
            int y = Integer.parseInt(t2);
            pos.get(0).setY(y);
        }
        int j = 0;
        for (Object obj : obj2) {
            JSONObject t = (JSONObject) obj;
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
        int x =0;
        int y =0;
        for (int i = 0; i < pos.size(); i++) {
            x=getPos().get(i).getX();
            y=getPos().get(i).getY();
            //method getColor(int pos) defined in Class Column return string
            if (b.getColumns().get(x).getColor(y).equals(color.get(i).name())) {
                t++;
            }
            //b.get(x).getColor(y)
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
