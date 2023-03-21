package it.polimi.ingsw.am40.model;

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
    private static String buildKey (int x, int y) {
        return ("(" + x + "," + y + ")");
    }
}