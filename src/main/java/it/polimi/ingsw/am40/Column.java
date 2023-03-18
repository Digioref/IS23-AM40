package it.polimi.ingsw.am40;
import java.util.ArrayList;

public class Column {
    private ArrayList<Tile> column;
    private ArrayList<Integer> mark;

    public Column(){
        this.column = new ArrayList<Tile>();
        this.mark = new ArrayList<Integer>();
    }

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


    public ArrayList<Tile> getColumn() {
        return column;
    }

    public boolean isFull(){
        if(column.size()<6){
            return false;
        }
        return true;
    }
    public void setMark(int pos){
        mark.set(pos,1);
    }
    public int getMark(int pos){
        return mark.get(pos);
    }

    public String getColor(int pos){
        return (column.get(pos).getColor().name());
    }

    public int getSize(){
        return column.size();
    }
}
