package it.polimi.ingsw.am40.Model;
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
     * Default constructor, which creates the availableTiles array
     */
    public Bag() {
        this.availableTiles = new ArrayList<>();
    }

    /**
     * Selects a tile randomly from the availableTiles Array
     * @return tile from the bag
     */
    public Tile pick() {
        Random rand = new Random();
        if (!(availableTiles.isEmpty())) {
            return availableTiles.remove(rand.nextInt(availableTiles.size()));
        }
        return null;
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