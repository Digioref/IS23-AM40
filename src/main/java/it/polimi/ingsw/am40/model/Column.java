package it.polimi.ingsw.am40.model;
import java.util.ArrayList;

public class Column {
    private ArrayList<Tile> column;

    /**
     * mark as ArrayList of integer is used in the process of checking adjacency
     * if a position of mark is set to zero, the default case, it means that the tile in the same position is not marked and it can be count as adjacent in the check
     * if a position of mark is set to one it means that the specific tile in the same position as the mark has been already counted as adjacent
     */
    private ArrayList<Integer> mark;

    /**
     * constructor
     */
    public Column(){
        this.column = new ArrayList<Tile>();
        this.mark = new ArrayList<Integer>();
    }

    /**
     * method that add a Tile in the ArrayList
     *
     * @param newTile
     * @return boolean
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
     * method that return the Arraylist of Tile
     *
     * @return column
     */
    public ArrayList<Tile> getColumn() {
        return column;
    }


    /**
     * method that check is the column is full
     *
     * @return true if the size of arraylist is six
     */
    public boolean isFull(){
        if(column.size()<6){
            return false;
        }
        return true;
    }

    /**
     * method that set the value of mark in a given position
     *
     * @param pos
     */
    public void setMark(int pos){
        mark.set(pos,1);
    }

    /**
     * method that return the value of mark in a given position
     *
     * @param pos
     * @return int value of mark
     */
    public int getMark(int pos){
        return mark.get(pos);
    }

    /**method that return a string of the color of a tile at given position
     *
     * @param pos
     * @return string of color
     */
    public String getColor(int pos){
        if(pos>=column.size()){
            return "exceeded_high";
        }
        return (column.get(pos).getColor().name());
    }

    /**
     * method that return the size of the column
     *
     * @return in value of size
     */
    public int getSize(){
        return column.size();
    }

    /**
     * method that return the tile at a given position
     * @param pos
     * @return Tile at index pos
     */
    public Tile getTile(int pos){
        return column.get(pos);
    }
}
