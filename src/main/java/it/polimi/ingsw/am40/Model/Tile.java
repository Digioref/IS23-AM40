package it.polimi.ingsw.am40.Model;

import it.polimi.ingsw.am40.CLI.Colors;
import javafx.scene.paint.Color;

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
    public Tile(TileColor color, TileType type, Position pos){
        this.color = color;
        this.type = type;
        this.pos = pos;
    }

    public Tile(TileColor color, TileType type, int x, int y){
        this.color = color;
        this.type = type;
        this.pos = new Position(x,y);
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

    public Position getPos() {
        return pos;
    }

    public void setPos(Position pos) {
        this.pos = pos;
    }
/*
    public String print() {
        Colors col = new Colors();
        if (color.equals(TileColor.GREEN)) {
            return col.green() + "G " + col.rst();
        }
        if (color.equals(TileColor.WHITE)) {
            return col.white() + "W " + col.rst();
        }
        if (color.equals(TileColor.YELLOW)) {
            return col.yellow() + "Y " + col.rst();
        }
        if (color.equals(TileColor.BLUE)) {
            return col.blue() + "B " + col.rst();
        }
        if (color.equals(TileColor.CYAN)) {
            return col.cyan() + "C " + col.rst();
        }
        if (color.equals(TileColor.VIOLET)) {
            return col.purple() + "V " + col.rst();
        }
        else return col.black() + "X " + col.rst();
    }
*/
    public String print() {
        Colors col = new Colors();
        if (color.equals(TileColor.GREEN)) {
            return col.greenBg() + " G " + col.rst();
        }
        if (color.equals(TileColor.WHITE)) {
            return col.whiteBg() + " W " + col.rst();
        }
        if (color.equals(TileColor.YELLOW)) {
            return col.yellowBg() + " Y " + col.rst();
        }
        if (color.equals(TileColor.BLUE)) {
            return col.blueBg() + " B " + col.rst();
        }
        if (color.equals(TileColor.CYAN)) {
            return col.cyanBg() + " C " + col.rst();
        }
        if (color.equals(TileColor.VIOLET)) {
            return col.purpleBg() + " V " + col.rst();
        }
        else return col.blackBg() + "   " + col.rst();
    }

    @Override
    public String toString() {
        return "Tile { " +
                "color = " + color +
                ", type = " + type +
                ", pos = " + pos.getKey() +
                " }";

    }

    public boolean isInvalid() {
        return ((pos.getX() == DEFAULTINVALIDPOSITION) && (pos.getY() == DEFAULTINVALIDPOSITION));
    }
}