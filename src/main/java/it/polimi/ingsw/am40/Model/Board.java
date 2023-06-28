
package it.polimi.ingsw.am40.Model;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents the game board form which the players pick the Tiles
 */
public class Board {
    /**
     * A map that maps the positions in the board to a tile
     */
    private Map<String, Tile> grid;
    private ArrayList<Position> pickableTiles;
    private ArrayList<Position> startingPickable;
    private static int MAX_TILE = 3;



    /**
     * The constructor which builds the instance of the class according to the positions in the json file
     * @param num number of players
     */
    public Board(int num) {
        grid = new HashMap<>();
        pickableTiles = new ArrayList<>();
        startingPickable = new ArrayList<>();
    }

    /**
     * Picks a tile from the bag b and puts it in a position on the board
     * @param b bag
     */
    public void config(Bag b) {
        int k=0;
        for (String pos : grid.keySet()) {
          //  Position p = new Position(-20, -20);
          //  p.convertKey(pos);
          //  grid.put(pos, b.pick());
          //  grid.get(pos).setPos(p);
            //System.out.println(k+": "+pos); x DEBUG
            Tile t = b.pick();
            t.setPos(grid.get(pos).getPos());
            grid.put(pos, t);
            k++;
        }
    }

    /**
     * A tile is picked form the board (it's removed form the board)
     * @param pos a position in the board
     * @return the tile picked
     */
    public Tile pick(String pos) {
        if (grid.containsKey(pos) && grid.get(pos).getType() != TileType.EMPTY) {
            Tile t = grid.get(pos);
            Position p = new Position(-20, -20);
            p.convertKey(pos);
            grid.put(pos, new Tile(TileColor.NOCOLOR, TileType.EMPTY, p));
            return t;
        }
        return null;
    }

    /**
     * Removes the remaining tiles from the board in order to reconfig it later
     * @param b bag
     */
    public void remove(Bag b) {
        int k=0;
        for (String pos : grid.keySet()) {
            //System.out.println(("k: "+ k)); x DEBUG
            if (grid.get(pos).getType() != TileType.EMPTY) {
                b.insert(grid.get(pos));
                Position p = new Position(-20, -20);
                p.convertKey(pos);
                grid.put(pos, new Tile(TileColor.NOCOLOR, TileType.EMPTY, p));
            }
            k++;
        }
    }

    /**
     * Returns the map representing the board
     * @return map representing the board
     */
    public Map<String, Tile> getGrid() {
        return grid;
    }

    /**
     * Sets the map according to the parameter
     * @param grid a map representing a board
     */
    /*
    public void setGrid(Map<String, Tile> grid) {
        this.grid = grid;
    }
    */

    /**
     * add the tiles with at least one free side to the array of the tile eligible to be picked
     */
    public void setSideFreeTile(){
        for(Tile tile : grid.values()){
            if(checkFreeSide(tile.getPos())>0 && !(tile.getColor().equals(TileColor.NOCOLOR)) ){
                pickableTiles.add(tile.getPos());
                startingPickable.add(tile.getPos());
                //System.out.println(tile);
            }
        }
    }

    /**
     * return the number of free side of a tile in a given position
     * @param pos a parameter stating the position of a tile in the board
     * @return an int representing how many free side the specific tile have, it can only be a value between 0 and 4
     */
    public int checkFreeSide(Position pos){
        Position next = new Position();
        int res = 0;
        for (int i = 1; i < 5; i++) {
            next.setNext(pos,i);
            if(isFree(next)){
                res = res + 1;
            }
        }
        return res;
    }


    /**
     *
     * @param pos a parameter stating the position of a tile in the board
     * @return a boolean stating if at the position given the board is free (true) or contains a tile (false)
     */
    private boolean isFree(Position pos) {
        //if the key obtained from the position
        /*
        if(!(grid.containsKey(pos.getKey())))
            return true;
         */
        if (grid.containsKey(pos.getKey())) {
            if (grid.get(pos.getKey()).getColor() != TileColor.NOCOLOR && grid.get(pos.getKey()).getType() != TileType.EMPTY) {
                return false;
            }
        }
        return true;
    }

    /**
     * method that can be call after the selection of one tile, it updates the array of
     * tiles that can be picked with only the adjacent one and removes all the tiles in
     * the array that are not eligible to be picked after
     * @param pos position of the tile picked
     */
    public void updatePickable(Position pos, Player pl){
        System.out.println(pickableTiles);
        if(pickableTiles.size() == startingPickable.size()){
            startingPickable.remove(pos);
            for (int i = 0; i < pickableTiles.size();) {
//            if (pickableTiles.get(i) != null) {
                if ((!(isPickable(pos, pickableTiles.get(i)))) || pos.equals(pickableTiles.get(i))) {
                    pickableTiles.remove(pickableTiles.get(i));
                    //System.out.println(pickableTiles.remove(pickableTiles.get(i)));
//                    i--;
                }
                else {
                    i++;
                }
                //           }
            }

        }
        else {
            System.out.println(startingPickable.size());
            System.out.println(pickableTiles.size());
            startingPickable.remove(pos);
            pickableTiles.remove(pos);
            if(pl.getSelectedPositions().size()<MAX_TILE){
                for(int j=0;j<startingPickable.size();j++){
                    if(!pickableTiles.contains(startingPickable.get(j))){
                        if(isPickable(pos, startingPickable.get(j)) && isInLine(pos,startingPickable.get(j),pl.getSelectedPositions())){
                            pickableTiles.add(startingPickable.get(j));
                        }
                    }
                    else {
                        if(!isInLine(pos,startingPickable.get(j),pl.getSelectedPositions())){
                            pickableTiles.remove(startingPickable.get(j));
                        }
                    }
                }
            }
            else{
                clearPickable();
            }

        }
        System.out.println(pickableTiles);
    }

    /**
     *
     * @param pos position of the tile picked
     * @param t position of another tile present in th arraylist tilesPickable
     * @return a boolean that states if a tile in a specific position can be picked (only up,down,right and left at distance one)
     */
    private boolean isPickable(Position pos, Position t){
        int diffX = pos.getX()-t.getX();
        int diffY = pos.getY()-t.getY();
        if(diffY>1 || diffY <-1){
            return false;
        }
        if(diffX>1 || diffX <-1){
            return false;
        }
        if(diffX!=0 && diffY !=0){
            return false;
        }
        return true;
    }

    private boolean isInLine(Position pos, Position t, ArrayList<Position> tilesSelected){
        if(tilesSelected.size()<2){
            return true;
        }
        int diffX = pos.getX()-t.getX();
        int diffY = pos.getY()-t.getY();
        if (tilesSelected.get(0).getX() - tilesSelected.get(1).getX() != 0) {
            if(diffY!=0){
                return false;
            }
        }
        else if (tilesSelected.get(0).getY() - tilesSelected.get(1).getY() != 0){
            if(diffX!=0){
                return false;
            }
        }
        return true;
    }

    /**
     *
     * clear the arrayList of the available tiles at the end of the round
     */
    public void clearPickable(){
        pickableTiles.clear();
        startingPickable.clear();
    }

    /**
     * check if at least one tile in the board have not all of its side free
     * @return a boolean that states if a refill of the board is necessary
     */
    public boolean needRefill(){
        //int h=0;
        /*
        for(String pos : grid.keySet()){
            System.out.println(h+ ": "+ pos);
            h++;
        }
         */
        //h=0;
        for(String value : grid.keySet()){
            //System.out.println(h+ ": "+ value + " =>"+ grid.get(value));
            if(!(grid.get(value).getType().equals(TileType.EMPTY)) && checkFreeSide(grid.get(value).getPos())<4){
                return false;
            }
        }
        /*
        for(Tile tile : grid.values()){
            System.out.println(tile);
            if(!(tile.getType().equals(TileType.EMPTY)) && checkFreeSide(tile.getPos())>0){
                return false;
            }
        }
         */
        return true;
    }

    /**
     * Returns the tiles that can be selected from the board
     * @return the feature pickableTiles, which contains the tiles selectable
     */

    public ArrayList<Position> getPickableTiles() {
        return pickableTiles;
    }

     /*
    public void updateAfterSelect(Position p, Player pl) {
        Position pos = new Position(-10, -10);

        if (pl.getSelectedPositions().size() < 2) {
            pos.setXY(p.getX(), (p.getY()-1));
            if (isPickable(p, pos) && checkFreeSide(pos) > 0 && !pl.getSelectedPositions().contains(pos) && !pickableTiles.contains(pos)){
                //pickableTiles.add(new Position(p.getX(), p.getY()-1));
            }
            pos.setXY(p.getX(), p.getY()+1);
            if (isPickable(pos, p) && checkFreeSide(pos) > 0 && !pl.getSelectedPositions().contains(pos) && !pickableTiles.contains(pos)){
                //pickableTiles.add(new Position(p.getX(), p.getY()+1));
            }
            pos.setXY(p.getX()-1, p.getY());
            if (isPickable(pos, p) && checkFreeSide(pos) > 0 && !pl.getSelectedPositions().contains(pos) && !pickableTiles.contains(pos)){
                //pickableTiles.add(new Position(p.getX()-1, p.getY()));
            }
            pos.setXY(p.getX()+1, p.getY());
            if (isPickable(pos, p) && checkFreeSide(pos) > 0 && !pl.getSelectedPositions().contains(pos) && !pickableTiles.contains(pos)){
                //pickableTiles.add(new Position(p.getX()+1, p.getY()));
            }
        }
        else {
            if (pl.getSelectedPositions().get(0).getX() == pl.getSelectedPositions().get(1).getX()) {
                pos.setXY(p.getX(), p.getY()+1);
                if (isPickable(pos, p) && checkFreeSide(pos) > 0 && !pl.getSelectedPositions().contains(pos) && !pickableTiles.contains(pos)){
                    //pickableTiles.add(new Position(p.getX(), p.getY()+1));
                }
                pos.setXY(p.getX(), p.getY()-1);
                if (isPickable(pos, p) && checkFreeSide(pos) > 0 && !pl.getSelectedPositions().contains(pos) && !pickableTiles.contains(pos)){
                    //pickableTiles.add(new Position(p.getX(), p.getY()-1));
                }
            }
            if (pl.getSelectedPositions().get(0).getY() == pl.getSelectedPositions().get(1).getY()) {
                pos.setXY(p.getX()+1, p.getY());
                if (isPickable(pos, p) && checkFreeSide(pos) > 0 && !pl.getSelectedPositions().contains(pos) && !pickableTiles.contains(pos)){
                    //pickableTiles.add(new Position(p.getX()+1, p.getY()));
                }
                pos.setXY(p.getX()-1, p.getY());
                if (isPickable(pos, p) && checkFreeSide(pos) > 0 && !pl.getSelectedPositions().contains(pos) && !pickableTiles.contains(pos)){
                    //pickableTiles.add(new Position(p.getX()-1, p.getY()));
                }
            }
        }

    }
    */
}
