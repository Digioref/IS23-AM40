package it.polimi.ingsw.Model;
import java.util.ArrayList;

public class Column {
    private ArrayList<Tile> column;
    private ArrayList<Integer> mark;

    public Column(){
        this.column = new ArrayList<>();
        this.mark = new ArrayList<>();
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
        return column.size() >= 6;
    }
    public void setMark(int pos){
        mark.set(pos,1);
    }
    public int getMark(int pos){
        return mark.get(pos);
    }

    public TileColor getColor(int pos){
        return (column.get(pos).getColor());
    }


    public int getSize(){
        return column.size();
    }

    public Tile getTile (int i) {
        return column.get(i);
    }

}
