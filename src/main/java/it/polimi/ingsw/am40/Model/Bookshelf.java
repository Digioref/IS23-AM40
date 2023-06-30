package it.polimi.ingsw.am40.Model;


import java.util.ArrayList;

/**
 * Represents the bookshelf where the player puts the Tiles picked from the board to achieve some goals to get points
 */
public class Bookshelf {
    /**
     * An array made up of columns
     */
    private final ArrayList<Column> bookshelf;
    private static final int DIMCOL = 5;

    /**
     * Constructor which creates the bookshelf and its columns
     */
    public Bookshelf(){
        Column newAdd;
        this.bookshelf = new ArrayList<>(5);
        for(int i = 0; i < DIMCOL; i++){
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
        if (col >= 0 && col < bookshelf.size()) {
            return bookshelf.get(col).isFull();
        }
        return false;
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
     * @param col column where to gameUpdate the tile
     */
    public boolean addTile(Tile newTile, int col){
        if (!((this.isFull(col)) || newTile == null || col >= 5 || col < 0)){
            bookshelf.get(col).addTile( new Tile(newTile.getColor(), newTile.getType(), new Position(col,bookshelf.get(col).getSize())) );
//            System.out.println(newTile.toString());
            return true;
        }
        return false;
    }

    /**
     * Returns the color of a tile in the bookshelf
     * @param x column of the bookshelf
     * @param y position of the tile in the column x
     * @return color of the tile
     */
    private TileColor tileColor(int x,int y){
        if (x>=0 && x<DIMCOL && y<bookshelf.get(x).getSize()){
            return bookshelf.get(x).getColor(y);
        }
        return null;
    }

    /**
     * Returns the mark of a tile at a given position
     * @param x x coordinate
     * @param y y coordinate
     * @return int value of the mark
     */
    private int markTile(int x,int y){
        if (x<0 || x>DIMCOL || bookshelf.get(x).getSize() <= y){
            return -1;
        }
        return bookshelf.get(x).getMark(y);
    }

    /**
     * Check if given coordinates are a valid position in the bookshelf
     * @param x x coordinate
     * @param y coordinate
     * @return true if the coordinates are valid, false otherwise
     */
    private boolean validPosition(int x, int y){
        return x >= 0 && x < bookshelf.size() && y >= 0 && y < bookshelf.get(x).getSize();
    }

    /**
     * Recursive method that returns the number of adjacent tiles given a starting one
     * @param x x coordinate
     * @param y y coordinate
     * @param color colour of the tile
     * @return int value of how many adjacent tile has been found
     */
    private int checkMark(int x, int y, TileColor color){
        if(!(validPosition(x,y)) || !(tileColor(x,y).equals(color)) || markTile(x,y)==1){
            return 0;
        }
        bookshelf.get(x).setMark(y);
        return 1+ checkMark(x+1,y,color) + checkMark(x,y+1, color) + checkMark(x-1,y,color) + checkMark(x,y-1,color);
    }

    /**
     * Checks all the adjacency of tiles in the board and returns the total score
     * @return int value of the total score for the adjacency
     */
    public int calcScore(){
        int result=0;
        int numMark=markTile(-1,-1); //purely for test coverage
        TileColor colorTmp=tileColor(-1,-1);    //purely for test coverage
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
     * It returns the bookshelf as an array of columns
     * @return the array made up of columns, representing the bookshelf
     */
    public ArrayList<Column> getBookshelf() {
        return bookshelf;
    }

    /**
     * Returns a tile in the bookshelf given its position
     * @param i index of the column
     * @param j position inside the column i
     * @return a tile
     */
    public Tile getTile(int i, int j) {
        return bookshelf.get(i).getTile(j);
    }

    /**
     * It returns the bookshelf as a string
     * @return a string representing the bookshelf
     */
    @Override
    public String toString() {
        return "Bookshelf{" +
                "bookshelf=" + bookshelf +
                '}';
    }

    /**
     * It is used only for testing
     */
    public void print(){
        ArrayList<String> tmp = new ArrayList<>();
        for(int i=DIMCOL; i>=0 ; i--){
            tmp.clear();
            for(int j=0; j<5; j++){
                if(this.getTile(j,i)!=null){
                    tmp.add(this.getTile(j,i).getColor().toString());
                }
                else{
                    tmp.add("----");
                }
            }
            System.out.println(tmp);
        }
    }

    /**
     * It checks if there is at least one column with a number of free positions equals to the integer provided
     * @param num an integer
     * @return a boolean
     */
    public boolean checkSpace(int num) {
        for (Column c: bookshelf) {
            if (c.getFreeSpace() >= num) {
                return true;
            }
        }
        return false;
    }

    /**
     * It sets the mark of the tiles to zero. It is used to calculate the score related to the adjacency
     */
    public void setMarkZero(){
        for(int i=0; i<DIMCOL;i++){
            bookshelf.get(i).resetMark();
        }
    }
}