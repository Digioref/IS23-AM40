package it.polimi.ingsw.am40;

import java.util.ArrayList;

public class Bookshelf {

    private ArrayList<Column> bookshelf;

    //contructor
    public Bookshelf(){
        Column newAdd;
        this.bookshelf = new ArrayList<Column>();
        for(int i=0; i<5;i++){
            newAdd = new Column();
            bookshelf.add(newAdd);
        }
    }

    //method to check if a column at given index col is full of tiles
    //return true if so
    public boolean isFull(int col){
        return bookshelf.get(col).isFull();
    }

    //mothod to check if the bookshelf is full
    public boolean isFull(){
        for(int i=0; i<bookshelf.size(); i++){
            if(!(bookshelf.get(i).isFull())){
                return false;
            }
        }
        return true;
    }

    //method to insert a Tile in a Column of a given index
    public boolean addTile(Tile newTile,int col){
        if(bookshelf.get(col).isFull() || newTile==null || col>=5 || col <0){
            return false;
        }
        bookshelf.get(col).addTile(newTile);
        return true;
    }

    //method that return how many free spaces are left in a Column of a given index
    public int freeSpaceCol(int col){
        return 6-bookshelf.get(col).getSize();
    }

    private String tileColor(int x,int y){
        if (bookshelf.get(x).getSize()<y){
            return "out_of_limit";
        }
        return bookshelf.get(x).getColor(y);
    }

    private int markTile(int x,int y){
        if (bookshelf.get(x).getSize()<y){
            return -1;
        }
        return bookshelf.get(x).getMark(y);
    }

    private boolean validPosition(int x, int y){
        if(x<0 || x >= bookshelf.size() || y<0 || y >= bookshelf.get(x).getSize()){
            return false;
        }
        return true;
    }

    private int checkMark(int x, int y, String color){
        if(!(validPosition(x,y)) || tileColor(x,y)!=color || markTile(x,y)==1){
            return 0;
        }
        bookshelf.get(x).setMark(y);
        return 1+ checkMark(x+1,y,color) + checkMark(x,y+1, color) + checkMark(x-1,y,color) + checkMark(x,y-1,color);
    }
    public int calcScore(){
        int result=0;
        int numMark;
        String colorTmp;
        for(int i=0; i<5;i++){
            for(int j=0; j<bookshelf.get(i).getSize();j++){
                numMark=0;
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
}
