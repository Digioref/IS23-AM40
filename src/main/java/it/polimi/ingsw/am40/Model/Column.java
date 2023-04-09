package it.polimi.ingsw.am40.Model;
import java.util.ArrayList;

/**
 * Represents the column of the bookshelf
 */
public class Column {
    /**
     * Array of Tiles contained in the column
     */
    private ArrayList<Tile> column;
    private static int DIM = 6;
    /**
     * Mark as ArrayList of integer is used in the process of checking adjacency
     * If a position of mark is set to zero, the default case, it means that the tile in the same position is not marked, and it can be counted as adjacent in the check
     * If a position of mark is set to one it means that the specific tile in the same position as the mark has been already counted as adjacent
     */
    private ArrayList<Integer> mark;

    /**
     * Constructor which sets to null both column and mark arrays
     */
    public Column(){
        this.column = new ArrayList<>(DIM);
        this.mark = new ArrayList<>(DIM);
    }

    /**
     * Adds the tile in the column
     * @param newTile the tile that must be added to the column
     * @return true if the tile is added, false otherwise
     */
    public boolean addTile(Tile newTile){
        if(newTile == null || newTile.getColor().equals(TileColor.NOCOLOR)){
            return false;
        } else if (column.size() == DIM) {
            return false;
        }
        column.add(newTile);
        mark.add(0);
        return true;
    }

    /**
     * Returns the array column
     * @return array of the tiles in the column
     */
    public ArrayList<Tile> getColumn() {
        return column;
    }

    /**
     * Verifies if the column is full
     * @return true if the column is full, false otherwise
     */
    public boolean isFull(){
            return column.size() >= 6;
    }

    /**
     * Sets the value of mark in a given position
     * @param pos provided position
     */
    public boolean setMark(int pos){
        if(pos<0 || pos>=mark.size()){
            return false;
        }
        mark.set(pos,1);
        return true;
    }

    /**
     * Returns the value of mark in a given position
     * @param pos given position
     * @return int of the mark at position pos
     */
    public int getMark(int pos){
        if(pos>=0 && pos <mark.size()){
            return mark.get(pos);
        }
        return -1;
    }

    /**
     * Returns the color of the tile in position pos in the column
     * @param pos position of the column
     * @return color of the tile
     */
    public TileColor getColor(int pos){
        if(pos>=0 && pos<column.size()){
            return (column.get(pos).getColor());
        }
        return null;
    }

    /**
     * Returns the number of tiles in the column
     * @return size of the array column
     */
    public int getSize(){
        return column.size();
    }

    /**
     * Returns the tile in position i
     * @param pos position in the column
     * @return tile in position i
     */
    public Tile getTile (int pos) {
        if (pos>=0 && pos< column.size()) {
            return column.get(pos);
        }
        return null;
    }

    @Override
    public String toString() {
        return "Column{" +
                "column=" + column +
                ", mark=" + mark +
                '}';
    }

    public boolean isEmpty() {
        return column.isEmpty();
    }
}