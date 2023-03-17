package it.polimi.ingsw;
import java.util.ArrayList;

public class Column {
    private ArrayList<Tile> column;


    public Column() {
        column = new ArrayList<>();
    }

    public boolean addTile(Tile newTile){
        if(column == null || newTile == null || column.size() == 6){
            return false;
        }
        column.add(newTile);
        return true;
    }

    public ArrayList<Tile> getColumn() {
        return column;
    }

    public boolean isFull(){
        if (column.size() < 6){
            return false;
        }
        return true;
    }
}
