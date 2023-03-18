package it.polimi.ingsw.Model;

import java.util.ArrayList;

/**
 * Represents the bookshelf where the player puts the Tiles picked from the board to achieve some goals to get points
 */
public class Bookshelf {
    /**
     * An array made up of columns
     */
    private ArrayList<Column> bookshelf;

    /**
     * Constructor which creates the bookshelf and its columns
     */
    public Bookshelf(){
        Column newAdd;
        this.bookshelf = new ArrayList<>();
        for(int i=0; i<5;i++){
            newAdd = new Column();
            bookshelf.add(newAdd);
        }
    }

    /**
     * Checks if a column at given index col is full of tiles
     * @param col a column of the bookshelf
     * @return true if the column is full, false otherwise
     */
    public boolean isFull(int col){
        return bookshelf.get(col).isFull();
    }

    /**
     * Checks if the bookshelf is full
     * @return true if the bookshelf is full, false otherwise
     */
    public boolean isFull(){
        for (Column column : bookshelf) {
            if (!(column.isFull())) {
                return false;
            }
        }
        return true;
    }

    /**
     * Inserts a Tile in a column identified by a given index
     * @param newTile the tile that must be inserted
     * @param col column where to insert the tile
     */
    public void addTile(Tile newTile, int col){
        if(bookshelf.get(col).isFull() || newTile==null || col>=5 || col <0){
            bookshelf.get(col).addTile(newTile);
        }
    }

    /**
     * Returns how many free spaces are left in a column of a given index
     * @param col column considered
     * @return number of free spaces of the column
     */
    public int freeSpaceCol(int col){
        return 6-bookshelf.get(col).getSize();
    }

    /**
     * Returns the color of a tile in the bookshelf
     * @param x column of the bookshelf
     * @param y position of the tile in the column x
     * @return color of the tile
     */
    private TileColor tileColor(int x,int y){
        if (! (bookshelf.get(x).getSize()<y)){
            return bookshelf.get(x).getColor(y);
        }
        return null;
    }

    /**
     *
     * @param x
     * @param y
     * @return
     */
    private int markTile(int x,int y){
        if (bookshelf.get(x).getSize()<y){
            return -1;
        }
        return bookshelf.get(x).getMark(y);
    }

    /**
     *
     * @param x
     * @param y
     * @return
     */
    private boolean validPosition(int x, int y){
        return x >= 0 && x < bookshelf.size() && y >= 0 && y < bookshelf.get(x).getSize();
    }

    /**
     *
     * @param x
     * @param y
     * @param color
     * @return
     */
    private int checkMark(int x, int y, TileColor color){
        if(!(validPosition(x,y)) || !(tileColor(x,y).equals(color)) || markTile(x,y)==1){
            return 0;
        }
        bookshelf.get(x).setMark(y);
        return 1+ checkMark(x+1,y,color) + checkMark(x,y+1, color) + checkMark(x-1,y,color) + checkMark(x,y-1,color);
    }

    /**
     *
     * @return
     */
    public int calcScore(){
        int result=0;
        int numMark;
        TileColor colorTmp;
        for(int i=0; i<5;i++){
            for(int j=0; j<bookshelf.get(i).getSize();j++){
                if(markTile(i,j)==0){
                    colorTmp=tileColor(i,j);
                    numMark=checkMark(i,j,colorTmp);
                    if(numMark>2 && numMark<5){
                        result+=(numMark-1);
                    }
                    else if(numMark==5){
                        result+=5;
                    }
                    else if(numMark>=6){
                        result+=8;
                    }
                }
            }
        }
        return result;
    }

    /**
     *
     * @return
     */
    public ArrayList<Column> getColumns(){
        ArrayList<Column> result = new ArrayList<>();
        for(int i=0;i<5;i++){
            result.add(bookshelf.get(i));
        }
        return result;
    }

    /**
     *
     * @param i
     * @param j
     * @return
     */
    public Tile getTile(int i, int j) {
        return bookshelf.get(i).getTile(j);
    }

}
