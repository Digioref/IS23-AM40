package it.polimi.ingsw.am40.model;
import java.util.ArrayList;

/**
 * Represents the column of the bookshelf
 */
public class Column {
    /**
     * Array of Tiles contained in the column
     */
    private ArrayList<Tile> column;
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
        this.column = new ArrayList<>();
        this.mark = new ArrayList<>();
    }

    /**
     * Adds the tile in the column
     * @param newTile the tile that must be added to the column
     * @return true if the tile is added, false otherwise
     */
    public boolean addTile(Tile newTile){
        if(column == null || newTile ==null){
            return false;
        } else if (column.size()==6) {
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
    public void setMark(int pos){
        mark.set(pos,1);
    }

    /**
     * Returns the value of mark in a given position
     * @param pos given position
     * @return int of the mark at position pos
     */
    public int getMark(int pos){
        return mark.get(pos);
    }

    /**
     * Returns the color of the tile in position pos in the column
     * @param pos position of the column
     * @return color of the tile
     */
    public TileColor getColor(int pos){
        return (column.get(pos).getColor());
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
     * @param i position in the column
     * @return tile in position i
     */
    public Tile getTile (int i) {
        return column.get(i);
    }

}