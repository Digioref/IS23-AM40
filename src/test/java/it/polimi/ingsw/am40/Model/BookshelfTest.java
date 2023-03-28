package it.polimi.ingsw.am40.Model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.Assert.*;

class BookshelfTest {
    @Test
    public void Test(){
        Bookshelf bookshelf = new Bookshelf();
        Tile g = new Tile(TileColor.GREEN, TileType.CATS); // colors: GREEN, WHITE, YELLOW, BLUE, CYAN, VIOLET, NOCOLOR;
        Tile w = new Tile(TileColor.WHITE, TileType.BOOKS);
        Tile y = new Tile(TileColor.YELLOW, TileType.CATS);
        Tile b = new Tile(TileColor.BLUE, TileType.FRAMES);
        Tile c = new Tile(TileColor.CYAN, TileType.TROPHIES);
        Tile v = new Tile(TileColor.VIOLET, TileType.PLANTS);

        ArrayList<Tile> col1 = new ArrayList<>(List.of(c,b,b,b,v,v));
        ArrayList<Tile> col2 = new ArrayList<>(List.of(c,c,y,b,v,v));
        ArrayList<Tile> col3 = new ArrayList<>(List.of(c,g,y,v,v));
        ArrayList<Tile> col4 = new ArrayList<>(List.of(g,g,y,v,v));
        ArrayList<Tile> col5 = new ArrayList<>(List.of(g,g,w,w));

        ArrayList<ArrayList<Tile>> structure = new ArrayList<>(List.of(col1,col2,col3,col4,col5));
        for(int i=0; i<5;i++){
            for(int j=0; j<structure.get(i).size();j++){
              bookshelf.addTile(structure.get(i).get(j),i);
            }
        }

        int result = 0;
        result= bookshelf.calcScore();

        assertEquals(21,result);
        assertEquals(b,structure.get(0).get(1));

    }


}