package it.polimi.ingsw.am40.model;

import java.util.ArrayList;
import java.util.Objects;

public class CommonGoal3 extends CommonGoal {
    public CommonGoal3(int numPlayer) {
        commgoaltok = new CommonGoalToken(numPlayer);
    }

    /**
     * Checks if there are 4 groups of 4 tiles of the same color with one side in common (not only vertical or orizontal)
     * @param bookshelf bookshelf of the player
     * @return the score if the condition is verified, else it returns 0
     */

    private int count = 0;
    private ArrayList<TileCoo> figure = new ArrayList<>();
    private ArrayList<ArrayList<TileCoo>> possibleGroups = new ArrayList<>();

    @Override
    public int check(Bookshelf bookshelf) {

        possibleGroups.clear();

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                if (bookshelf.getTile(i,j) != null) {
                    groups(bookshelf, i, j, bookshelf.getTile(i, j));
                }
            }
        }

        if (fourFigures()) {
            return commgoaltok.updateScore();
        }

        return 0;

    }
    public void groups(Bookshelf b, int x, int y, Tile prevTile) {

        TileCoo tile = new TileCoo(x,y,b.getTile(x,y));
        ArrayList<TileCoo> tmp;

        if (prevTile.equals(b.getTile(x,y)) && !figure.contains(tile)) {
            count++;
            figure.add(tile);
            if (count == 4) {
                tmp = new ArrayList<>(figure);
                possibleGroups.add(tmp);
                count--;
                figure.remove(count);
                return;
            } else {
                if (x < 4) {
                    groups(b, x + 1, y, b.getTile(x, y));
                }
                if (x > 0) {
                    groups(b, x - 1, y, b.getTile(x, y));
                }
                if (y < 5) {
                    groups(b, x, y + 1, b.getTile(x, y));
                }
                if (y > 0) {
                    groups(b, x, y - 1, b.getTile(x, y));
                }
                count--;
                figure.remove(count);
                return;
            }
        }
        return;
    }

    public boolean overlap(ArrayList<TileCoo> figure1, ArrayList<TileCoo> figure2) {

        //if (!figure1.isEmpty() && !figure2.isEmpty()) {
            if (!figure1.get(0).getTile().equals(figure2.get(0).getTile())) { // if they have different color they can not overlap
                return false;
            }

            for (int i = 0; i < figure1.size(); i++) {
                for (int j = 0; j < figure2.size(); j++) {
                    if (sameCoo(figure1.get(i), figure2.get(j))) {
                        return true;
                    }
                }
            }
        //}

        return false;

    }

    public boolean overlap(ArrayList<TileCoo> figure1, ArrayList<TileCoo> figure2, ArrayList<TileCoo> figure3, ArrayList<TileCoo> figure4) {

        if (!overlap(figure1, figure2) && !overlap(figure1, figure3) && !overlap(figure1, figure4) && !overlap(figure2, figure3) && !overlap(figure2, figure4) && !overlap(figure3, figure4)) {
            return false;
        }

        return true;

    }

    public boolean sameCoo(TileCoo t1, TileCoo t2) {

        return t1.getX() == t2.getX() && t1.getY() == t2.getY();

    }
    public boolean fourFigures() {

        if (possibleGroups.size() >= 4) {
            for (int i = 0; i < possibleGroups.size() - 3; i++) {
                for (int j = i + 1; j < possibleGroups.size() - 2; j++) {
                    for (int h = j + 1; h < possibleGroups.size() - 1; h++) {
                        for (int k = h + 1; k < possibleGroups.size(); k++) {
                            if (!overlap(possibleGroups.get(i), possibleGroups.get(j), possibleGroups.get(h), possibleGroups.get(k))) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return  false;
    }

}

class TileCoo {
    private int x;
    private int y;
    private Tile tile;

    public TileCoo(int x, int y, Tile tile) {
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

    @Override
    public String toString() {
        return "x = " + x +
                ", y = " + y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TileCoo tileCoo = (TileCoo) o;
        return x == tileCoo.x && y == tileCoo.y;
    }

}
