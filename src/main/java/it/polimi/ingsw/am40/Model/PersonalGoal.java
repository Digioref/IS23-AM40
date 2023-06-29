
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
    private int key;

    /**
     * Constructor that builds the class using a json file and an index k
     * @param k number of the personal goal chosen
     */
    public PersonalGoal(int k) {
        pos = new ArrayList<>(6);
        color = new ArrayList<>(6);
        key = k;
    }

    /**
     * Returns the score obtained by achieving parts of the personal goal
     * @param b the bookshelf of the player to check
     * @return an int representing the score obtained
     */
    public int calcScore(Bookshelf b) {
        int t = 0;
        for (int i = 0; i < pos.size(); i++) {
            Column c = b.getBookshelf().get(pos.get(i).getX());
            if (!(c.isEmpty())) {
                if (pos.get(i).getY() < c.getSize()) {
                    if (c.getColor(pos.get(i).getY()).equals(color.get(i))) {
                        t++;
                    }
                }
            }
        }
        int s = switch (t) {
            case 1 -> 1;
            case 2 -> 2;
            case 3 -> 4;
            case 4 -> 6;
            case 5 -> 9;
            case 6 -> 12;
            default -> 0;
        };
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
     * Returns the specific colours of the personal goal
     * @return the feature color
     */
    public ArrayList<TileColor> getColor() {
        return color;
    }

    @Override
    public String toString() {

        for (int i = 0; i < pos.size(); i++) {
            System.out.println(pos.get(i).getKey() + " " + color.get(i));
        }

        return "PersonalGoal";
    }

    /**
     * It returns the number identifying the personal goal
     * @return the number representing the personal goal
     */
    public int getKey() {
        return key;
    }

    public void setPos(ArrayList<Position> pos) {
        this.pos = pos;
    }

    public void setColor(ArrayList<TileColor> color) {
        this.color = color;
    }
}