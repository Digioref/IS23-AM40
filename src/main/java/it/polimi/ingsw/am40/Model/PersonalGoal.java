
package it.polimi.ingsw.am40.Model;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

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
     * Constructor that builds the class using a json file and an index k
     * @param k number of the personal goal chosen
     */
    public PersonalGoal(int k) {
        pos = new ArrayList<>(6);
        color = new ArrayList<>(6);
/*
        JSONParser jsonParser = new JSONParser();
        FileReader reader;
        try {
        ClassLoader classLoader = getClass().getClassLoader();
            File file = new File(Objects.requireNonNull(classLoader.getResource("PersonalGoals.json")).getFile());
            reader = new FileReader(file);
            JSONObject persGoals = (JSONObject) jsonParser.parse(reader);

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
                pos.add(p);
            }
            for (Object obj : obj2) {
                JSONObject t = (JSONObject) obj;
                String x = t.get("Color").toString();
                switch (x) {
                    case "Yellow":
                        color.add(TileColor.YELLOW);
                    case "White":
                        color.add(TileColor.WHITE);
                    case "Blue":
                        color.add(TileColor.BLUE);
                    case "Green":
                        color.add(TileColor.GREEN);
                    case "Cyan":
                        color.add(TileColor.CYAN);
                    case "Violet":
                        color.add(TileColor.VIOLET);
                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
 */
    }

    /**
     * Returns the score obtained by achieving parts of the personal goal
     * @param b the bookshelf of the player to check
     * @return an int representing the score obtained
     */
    public int calcScore(Bookshelf b) {
//        for (int i = 0; i < pos.size(); i++) {
//            System.out.println(color.get(i).toString() + " " + pos.get(i).getKey());
//        }
        int t = 0;
        for (int i = 0; i < pos.size(); i++) {
            Column c = b.getBookshelf().get(pos.get(i).getX());
//            System.out.println(c.toString());
//            System.out.println("qui5");
            if (!(c.isEmpty())) {
                if (pos.get(i).getY() < c.getSize()) {
//                    System.out.println("qui6");
                    if (c.getColor(pos.get(i).getY()).equals(color.get(i))) {
                        t++;
                    }
                }
            }
        }
        int s;
        switch (t) {
            case 1:
                s = 1;
                break;
            case 2:
                s = 2;
                break;
            case 3:
                s = 4;
                break;
            case 4:
                s = 4;
                break;
            case 5:
                s = 9;
                break;
            case 6:
                s = 12;
                break;
            default:
                s = 0;
                break;
        }
//        System.out.println("qui6");
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

    @Override
    public String toString() {

        for (int i = 0; i < pos.size(); i++) {
            System.out.println(pos.get(i).getKey() + " " + color.get(i));
        }

        return "PersonalGoal";
    }
}