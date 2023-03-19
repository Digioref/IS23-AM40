package it.polimi.ingsw.Model;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.json.JsonArray;
import javax.json.JsonObject;

/**
 * Represents the Personal Goal of the game
 */
public class PersonalGoal {
    /**
     * An array of positions representing the positions where, in the personal goal card, there are specific tiles (colors)
     */
    private ArrayList<Position> pos;
    /**
     * An array of colours representing the colours of the tiles in the specific positions contained in the array pos
     */
    private ArrayList<TileColor> color;
    /**
     * A Json Array used to build the instance of the class
     */
    private static JsonArray PersGoals;

    /**
     * A static block of code used to transform a provided Json file to the Json array PersGoals
     */
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

    /**
     * The constructor which uses the Json Array to build a specific personal goal according to the specific features in the Json file
     * @param i
     */
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

    /**
     * Returns the score obtained by achieving parts of the personal goal
     * @param b the bookshelf of the player to check
     * @return an int representing the score obtained
     */
    public int calcScore(Bookshelf b) {
        int t = 0;
        for (int i = 0; i < pos.size(); i++) {
            if (b.getColumns().get(pos.get(i).getX()).getColor(pos.get(i).getY()).equals(color.get(i))) {
                t++;
            }
        }
        int s;
        switch (t) {
            case 1:
                s = 1;
            case 2:
                s = 2;
            case 3:
                s = 4;
            case 4:
                s = 4;
            case 5:
                s = 9;
            case 6:
                s = 12;
            default:
                s = 0;
        }
        return s;
    }

    /**
     * Returns the array of the specific positions of the personal goal
     * @return the feature pos
     */
    public ArrayList<Position> getPos() {
        return pos;
    }

    /**
     * Sets the array pos to the provided one
     * @param pos a provided array of positions
     */
    public void setPos(ArrayList<Position> pos) {
        this.pos = pos;
    }

    /**
     * Returns the specific colours of the personal goal
     * @return the feature color
     */
    public ArrayList<TileColor> getColor() {
        return color;
    }

    /**
     * Sets the aaray of colours to the provided one
     * @param color the provided array of colours
     */
    public void setColor(ArrayList<TileColor> color) {
        this.color = color;
    }
}
