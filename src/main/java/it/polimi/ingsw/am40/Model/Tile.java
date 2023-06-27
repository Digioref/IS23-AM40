package it.polimi.ingsw.am40.Model;

import it.polimi.ingsw.am40.CLI.Colors;
import javafx.scene.paint.Color;

import java.util.Random;

/**
 * Represents the tiles in the game
 */
public class Tile {
    private final static int DEFAULTINVALIDPOSITION = -20;
    /**
     * The colour of the tile
     */
    private TileColor color;
    /**
     * The type of the tile
     */
    private TileType type;

    private Position pos;

    /**
     * Constructor which builds the tile assigning colour and type
     * @param color a colour
     * @param type a type
     */

    public Tile(TileColor color, TileType type) {
        this.color = color;
        this.type = type;
        this.pos = new Position(DEFAULTINVALIDPOSITION, DEFAULTINVALIDPOSITION);
    }

    /**
     * Constructor that creates a tile defining its color, type and also position
     * @param color color of the tile
     * @param type type of the tile
     * @param pos position of the tile, in the board or in the bookshelf
     */
    public Tile(TileColor color, TileType type, Position pos){
        this.color = color;
        this.type = type;
        this.pos = pos;
    }

    /**
     * Override of the method equals, it compares two tile according to the colour
     * @param o an object, in this case a tile
     * @return true if the two tiles have the same colour
     */
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tile tile = (Tile) o;
        return color == tile.color;
    }

    /**
     * Returns the colour of the tile
     * @return the feature color
     */
    public TileColor getColor() {
        return color;
    }

    /**
     * Sets the colour to the provided one
     * @param color a provided colour
     */

    public void setColor(TileColor color) {
        this.color = color;
    }

    /**
     * Returns the type of the tile
     * @return the feature type
     */
    public TileType getType() {
        return type;
    }

    /**
     * Sets the type to the provided one
     * @param type a provided type
     */
    public void setType(TileType type) {
        this.type = type;
    }

    /**
     * It returns the position of the tile
     * @return the position of the tile
     */
    public Position getPos() {
        return pos;
    }

    /**
     * It sets the position of the tile to the provided one
     * @param pos a position
     */
    public void setPos(Position pos) {
        this.pos = pos;
    }

    /**
     * It returns the tile in a string format, specifying the color, the type and the position of the tile
     * @return a string representing the tile
     */
    @Override
    public String toString() {
        return "Tile { " +
                "color = " + color +
                ", type = " + type +
                ", pos = " + pos.getKey() +
                " }";

    }

}