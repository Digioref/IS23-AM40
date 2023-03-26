package it.polimi.ingsw.am40.Model;

import java.util.ArrayList;

public class CommonGoal1 extends CommonGoal {
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
        ArrayList<Square> fullSquares = new ArrayList<>();
        int x1, y1, x2, y2;

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                if (sameColors(i,j,b)) {
                    fullSquares.add(new Square(i,j,b.getTile(i,j)));
                }
            }
        }

        for (int i = 0; i < fullSquares.size() - 1; i++) {
            for (int j = i + 1 ; j < fullSquares.size(); j++) {
                if (fullSquares.get(i).getTile().equals(fullSquares.get(j).getTile())) {
                    x1 = fullSquares.get(i).getX();
                    y1 = fullSquares.get(i).getY();
                    x2 = fullSquares.get(j).getX();
                    y2 = fullSquares.get(j).getY();
                    if (!overlaps(x1, y1, x2, y2)) {
                        return commgoaltok.updateScore();
                    }
                }
            }
        }

        return 0;

    }

    public boolean sameColors(int x, int y, Bookshelf b) {
        if (b.getTile(x,y) != null) {
            return b.getTile(x, y).equals(b.getTile(x + 1, y)) && b.getTile(x, y).equals(b.getTile(x, y + 1)) && b.getTile(x, y).equals(b.getTile(x + 1, y + 1));
        }
        return false;
    }

    public boolean overlaps(int x1, int y1, int x2, int y2) { // return true if the two squares do overlap
        return x1 <= x2 + 1 && x2 <= x1 + 1 && y1 <= y2 + 1 && y1 + 1 >= y2;
    }

}

class Square {
    private int x;
    private int y;
    private Tile tile;

    public Square(int x, int y, Tile tile) {
        this.x = x;
        this.y = y;
        this.tile = tile;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Tile getTile() {
        return tile;
    }

}
