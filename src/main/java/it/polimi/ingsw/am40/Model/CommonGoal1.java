package it.polimi.ingsw.am40.Model;

import java.util.ArrayList;

public class CommonGoal1 extends CommonGoal { // private static final CONST = 6;

    public CommonGoal1(int numPlayer) {
        commgoaltok = new CommonGoalToken(numPlayer);
    }

    /**
     * Checks if there are 2 squares of 2x2 sixe with all 8 tiles with the same color
     * @param b bookshelf of the player
     * @return the score if it finds the squares, else it returns 0
     */
    @Override
    public int check (Bookshelf b) {

        ArrayList<Tile> fullSquares = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                if (sameColors(i,j,b)) {
                    fullSquares.add(b.getTile(i,j));
                }
            }
        }

        for (int i = 0; i < fullSquares.size() - 1; i++) {
            for (int j = i + 1 ; j < fullSquares.size(); j++) {
                if (fullSquares.get(i).equals(fullSquares.get(j))) {
                    if (!overlaps(fullSquares.get(i).getPos(), fullSquares.get(j).getPos())) {
                        return commgoaltok.updateScore();
                    }
                }
            }
        }

        return 0;

    }

    /**
     * checks if the tile with coordinates x,y is at the bottom left of a sqare made of tiles of the same color
     * @param x coordinate of the tile
     * @param y coordinate of the tile
     * @param b bookshelf reference
     * @return true if there is a square, else false
     */
    public boolean sameColors(int x, int y, Bookshelf b) {
        if (b.getTile(x,y) != null) {
            return b.getTile(x, y).equals(b.getTile(x + 1, y)) && b.getTile(x, y).equals(b.getTile(x, y + 1)) && b.getTile(x, y).equals(b.getTile(x + 1, y + 1));
        }
        return false;
    }


    public boolean overlaps(Position pos1, Position pos2) { // return true if the two squares do overlap
        int x1, x2, y1, y2;
        x1 = pos1.getX();
        y1 = pos1.getY();
        x2 = pos2.getX();
        y2 = pos2.getY();
        return x1 <= x2 + 1 && x2 <= x1 + 1 && y1 <= y2 + 1 && y1 + 1 >= y2;
    }

}
