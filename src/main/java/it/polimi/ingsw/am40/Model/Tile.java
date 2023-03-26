package it.polimi.ingsw.am40.Model;

/**
 * Represents the tiles in the game
 */
public class Tile {
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
        this.pos = new Position(-20, -20);
    }
    public void Tile(TileColor color, TileType type, Position pos){
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

    public Position getPos() {
        return pos;
    }

    public void setPos(Position pos) {
        this.pos = pos;
    }

    @Override
    public String toString() {
        return "Tile { " +
                "color = " + color +
                ", type = " + type +
                ", pos = " + pos.getKey() +
                " }";
    }
}