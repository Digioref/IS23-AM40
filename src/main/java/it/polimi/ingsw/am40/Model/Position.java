package it.polimi.ingsw.am40.Model;

import java.util.ArrayList;

/**
 * Represents the position of a Tile in the bookshelf or in the board
 */
public class Position {
    /**
     * The x coordinate
     */
    private int x;
    /**
     * The y coordinate
     */
    private int y;

    public Position(){

    }
    /**
     * Constructor which builds the class according to the provided x and y
     * @param x a x coordinate
     * @param y a y coordinate
     */
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * The override of the method equals, it compares two positions according to their coordinates
     * @param o an object, which is another position in general
     * @return true if the two positions have the same coordinates
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return x == position.x && y == position.y;
    }

    /**
     * Returns the x coordinate
     * @return the feature x
     */
    public int getX() {
        return x;
    }

    /**
     * Sets the x coordinate to the provided one
     * @param x the coordinate provided
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Returns the y coordinate
     * @return the feature y
     */
    public int getY() {
        return y;
    }

    /**
     *Sets the y coordinate to the provided one
     * @param y the coordinate provided
     */

    public void setY(int y) {
        this.y = y;
    }

    public String getKey() {
        return buildKey(this.x, this.y);
    }

    public static String getKey(int x, int y) {
        return buildKey(x, y);
    }
    private static String buildKey(int x, int y) {
        return ("(" + x + "," + y + ")");
    }

    public void convertKey(String s) {
        ArrayList<Integer> arr = new ArrayList<>();
        for (int i = 0; i< s.length(); i++) {
            if ((s.charAt(i) != '(') && (s.charAt(i) != ',') && (s.charAt(i) != ')')) {
                if (s.charAt(i) == '-') {
                    String s1 = '-' + String.valueOf(s.charAt(i+1));
                    arr.add(Integer.parseInt(s1));
                    i++;
                }
                else {
                    String s1 = String.valueOf(s.charAt(i));
                    arr.add(Integer.parseInt(s1));
                }
            }
        }
        setX(arr.get(0));
        setY(arr.get(1));
    }


    /**
     * set a position at distance of one in only one direction
     * @param pos starting position
     * @param type int for select the correct offest
     */
    public void setNext(Position pos, int type) {
        copyPos(pos);
        switch(type){
            case 1 -> this.x = x+1;
            case 2 -> this.x = x-1;
            case 3 -> this.y = y+1;
            case 4 -> this.y = y-1;
        }
    }

    /**
     * copy the x and y of a given position
     * @param pos position to be copied
     */
    public void copyPos(Position pos){
        this.x= pos.getX();
        this.y= pos.getY();
    }

    @Override
    public String toString() {
        return "(x=" + x + ",y=" + y + ")";
    }
}