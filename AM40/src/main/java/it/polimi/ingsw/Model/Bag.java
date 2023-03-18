package it.polimi.ingsw.Model;

import java.util.ArrayList;
import java.util.Random;

public class Bag {
    private ArrayList<Tile> availableTiles;

    public Bag() {
        this.availableTiles = null;
    }

    public Tile pick() {
        Random rand = new Random();
        return availableTiles.remove(rand.nextInt(availableTiles.size()));
    }

    public void insert (Tile t) {
        availableTiles.add(t);
    }

    public ArrayList<Tile> getAvailableTiles() {
        return availableTiles;
    }

    public void setAvailableTiles(ArrayList<Tile> availableTiles) {
        this.availableTiles = availableTiles;
    }
}
