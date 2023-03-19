package it.polimi.ingsw.am40.model;

import java.util.ArrayList;

public class Bookshelf {

    private ArrayList<Column> bookshelf;

    /**
     * contructor
     *
     */
    public Bookshelf(){
        Column newAdd;
        this.bookshelf = new ArrayList<Column>();
        for(int i=0; i<5;i++){
            newAdd = new Column();
            bookshelf.add(newAdd);
        }
    }

    /**
     * method to check if a column at given index col is full of tiles
     *
     * @param col
     * @return true if column is full
     */
    public boolean isFull(int col){
        return bookshelf.get(col).isFull();
    }

    /**
     * method to check if the bookshelf is full
     *
     * @return true if all the column are full
     */
    public boolean isFull(){
        for(int i=0; i<bookshelf.size(); i++){
            if(!(bookshelf.get(i).isFull())){
                return false;
            }
        }
        return true;
    }

    /**
     * method to insert a Tile in a Column of a given index
     *
     * @param newTile
     * @param col
     * @return true if it is added correctly
     */
    public boolean addTile(Tile newTile,int col){
        if(bookshelf.get(col).isFull() || newTile==null || col>=5 || col <0){
            return false;
        }
        bookshelf.get(col).addTile(newTile);
        return true;
    }

    /**
     * method that return how many free spaces are left in a Column of a given index
     *
     * @param col
     * @return int value of available space left
     */
    public int freeSpaceCol(int col){
        return 6-bookshelf.get(col).getSize();
    }

    /**
     * method to get the color of a tile at a given position
     *
     * @param x
     * @param y
     * @return string value of a tile color
     */
    private String tileColor(int x,int y){
        if (bookshelf.get(x).getSize()<y){
            return "out_of_limit";
        }
        return bookshelf.get(x).getColor(y);
    }

    /**
     * method to get the mark of a tile at a given position
     *
     * @param x
     * @param y
     * @return int value of the mark
     */
    private int markTile(int x,int y){
        if (bookshelf.get(x).getSize()<y){
            return -1;
        }
        return bookshelf.get(x).getMark(y);
    }

    /**
     * method that check if given coordinates are a valid position in the bookshelf
     *
     * @param x
     * @param y
     * @return true if the coordinates are valid
     */
    private boolean validPosition(int x, int y){
        if(x<0 || x >= bookshelf.size() || y<0 || y >= bookshelf.get(x).getSize()){
            return false;
        }
        return true;
    }

    /**recursive method that return the number of adjacent tiles given a starting one
     *
     * @param x
     * @param y
     * @param color
     * @return int value of how many adjacent tile has been found
     */
    private int checkMark(int x, int y, String color){
        if(!(validPosition(x,y)) || tileColor(x,y)!=color || markTile(x,y)==1){
            return 0;
        }
        bookshelf.get(x).setMark(y);
        return 1+ checkMark(x+1,y,color) + checkMark(x,y+1, color) + checkMark(x-1,y,color) + checkMark(x,y-1,color);
    }

    /** method that check all the adjacency of tiles in the board and return the total score
     *
     * @return int value of the total score for the adjacency
     */
    public int calcScore(){
        int result=0;
        int numMark;
        String colorTmp;
        for(int i=0; i<5;i++){
            //iterate the bookshelf in all valid position
            for(int j=0; j<bookshelf.get(i).getSize();j++){
                numMark=0;
                //if the tile isn't marked then proceed to check the adjacent one
                if(markTile(i,j)==0){
                    colorTmp=tileColor(i,j);
                    numMark=checkMark(i,j,colorTmp);
                    //given the result of method checkMark update the score
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

    /** method that return a copy of columns
     *
     * @return ArrayList of column in the bookshelf
     */
    public ArrayList<Column> getColumns(){
        ArrayList<Column> result = new ArrayList<Column>();
        for(int i=0;i<5;i++){
            result.add(bookshelf.get(i));
        }
        return result;
    }

    /**
     *  method that return the Tile in the bookshelf at a given position
     *
     * @param x
     * @param y
     * @return Tile at specified coordinates
     */
    public Tile getTile(int x, int y){
        return bookshelf.get(x).getTile(y);
    }
}
