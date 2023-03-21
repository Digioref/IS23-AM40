package it.polimi.ingsw.am40.model;
import java.util.ArrayList;
import java.util.Random;
import java.util.ArrayList;
import java.util.Random;

/**
 * Represents the Bag which contains all the game Tiles
 */
public class Bag {
    /**
     * Array of Tiles that are not yet used
     */
    private ArrayList<Tile> availableTiles;

    /**
     * Default constructor, which sets the availableTiles array to null
     */
    public Bag() {
        this.availableTiles = new ArrayList<>();
    }

    /**
     * Selects a tile randomly from the availabletiles Array
     * @return tile from the bag
     */
    public Tile pick() {
        Random rand = new Random();
        return availableTiles.remove(rand.nextInt(availableTiles.size()));
    }

    /**
     * Inserts a tile in the availableTiles array
     * @param t: a tile that must be inserted
     */
    public void insert (Tile t) {
        availableTiles.add(t);
    }

    /**
     * Returns the availableTiles array
     * @return availableTiles array
     */
    public ArrayList<Tile> getAvailableTiles() {
        return availableTiles;
    }

    /**
     * Sets the availableTiles array according to the parameter
     * @param availableTiles: an array of tiles
     */

    public void setAvailableTiles(ArrayList<Tile> availableTiles) {
        this.availableTiles = availableTiles;
    }
}