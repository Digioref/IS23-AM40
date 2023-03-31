
package it.polimi.ingsw.am40.Model;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileReader;
import java.io.IOException;
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



    /**
     * The constructor which builds the instance of the class according to the positions in the json file
     * @param num number of players
     */
    public Board(int num) {
        grid = new HashMap<>();
        pickableTiles = new ArrayList<>();
/*
        JSONParser jsonParser = new JSONParser();
        FileReader reader;
        try {
            reader = new FileReader("PositionsBoard.json");
            JSONObject configs = (JSONObject) jsonParser.parse(reader);
            JSONArray posArray = (JSONArray) configs.get("Positions");
            JSONObject o = (JSONObject) posArray.get(num - 2);
            JSONArray obj1 = (JSONArray) o.get("Players" + Integer.valueOf(num).toString());
            for (int i = 0; i < obj1.size(); i++) {
                JSONObject t = (JSONObject) obj1.get(i);
                String t1 = t.get("x").toString();
                String t2 = t.get("y").toString();
                Position p = new Position(Integer.parseInt(t1), Integer.parseInt(t2));
                Tile tile = new Tile(TileColor.NOCOLOR, TileType.EMPTY);
                tile.setPos(p);
                grid.put(p.getKey(), tile);
                System.out.println(p.getKey());


            }

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
 */
    }

    /**
     * Picks a tile from the bag b and puts it in a position on the board
     * @param b bag
     */
    public void config(Bag b) {
        for (String pos : grid.keySet()) {
          //  Position p = new Position(-20, -20);
          //  p.convertKey(pos);
          //  grid.put(pos, b.pick());
          //  grid.get(pos).setPos(p);

            Tile t = b.pick();
            t.setPos(grid.get(pos).getPos());
            grid.put(pos, t);
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
            grid.put(pos, new Tile(TileColor.NOCOLOR, TileType.EMPTY));
            return t;
        }
        return null;
    }

    /**
     * Removes the remaining tiles from the board in order to reconfig it later
     * @param b bag
     */
    public void remove(Bag b) {
        for (String pos : grid.keySet()) {
            if (grid.get(pos).getType() != TileType.EMPTY) {
                b.insert(grid.get(pos));
                grid.put(pos, new Tile(TileColor.NOCOLOR, TileType.EMPTY));
            }
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
    public void setGrid(Map<String, Tile> grid) {
        this.grid = grid;
    }

    /**
     * add the tiles with at least one free side to the array of the tile eligible to be picked
     *
     */
    public void setSideFreeTile(){
        for(Tile tile : grid.values()){
            if(checkFreeSide(tile.getPos())>0){
                pickableTiles.add(tile.getPos());
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
        for(int i=1;i<5;i++){
            next.setNext(pos,i);
            if(isFree(next)){
                res=res+1;
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
        if(!(grid.containsKey(pos.getKey()))){
            return false;
        }
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
    public void updatePickable(Position pos){
        for(Position t : pickableTiles){
            if(t!=null){
                if(!(isPickable(pos,t))){
                    pickableTiles.remove(t);
                }
            }
        }
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

    /**
     *
     * clear the arrayList of the available tiles at the end of the round
     */
    public void clearPickable(){
        pickableTiles.clear();
    }

    /**
     * check if at least one tile in the board have not all of its side free
     * @return a boolean that states if a refill of the board is necessary
     */
    public boolean needRefill(){
        for(Tile tile : grid.values()){
            if(checkFreeSide(tile.getPos())>0){
                return false;
            }
        }
        return true;
    }

    public ArrayList<Position> getPickableTiles() {
        return pickableTiles;
    }

}
