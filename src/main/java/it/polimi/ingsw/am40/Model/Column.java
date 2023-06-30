package it.polimi.ingsw.am40.Model;
import java.util.ArrayList;

/**
 * Represents the column of the bookshelf
 */
public class Column {

    private final ArrayList<Tile> column;
    private static final int DIM = 6;

    private final ArrayList<Integer> mark;

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
     * @return list of the tiles in the column
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
     * Returns the space available in the column
     * @return the number of free positions in the column
     */
    public int getFreeSpace(){
        return DIM- column.size();
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

    /**
     * Returns the column as a string with the tiles in it
     * @return a string representing the column
     */
    @Override
    public String toString() {
        return "Column{" +
                "column=" + column +
                ", mark=" + mark +
                '}';
    }

    /**
     * Returns true if the column is empty, false otherwise
     * @return true if the column is empty, it has no tiles in it
     */
    public boolean isEmpty() {
        return column.isEmpty();
    }

    /**
     * It sets the mark of the tiles in the specific column to zero
     */
    public void resetMark(){
        for(int i=0; i<column.size();i++){
            mark.set(i,0);
        }
    }
}