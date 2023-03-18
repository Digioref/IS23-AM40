package it.polimi.ingsw.Model;
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
     *
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
     *
     * @param pos
     */
    public void setMark(int pos){
        mark.set(pos,1);
    }

    /**
     *
     * @param pos
     * @return
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
