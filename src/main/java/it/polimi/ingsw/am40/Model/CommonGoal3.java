package it.polimi.ingsw.am40.Model;

import java.util.ArrayList;

public class CommonGoal3 extends CommonGoal {
    public CommonGoal3(int numPlayer) {
        commgoaltok = new CommonGoalToken(numPlayer);
    }

    private int count = 0;
    private ArrayList<Tile> figure = new ArrayList<>();
    private ArrayList<ArrayList<Tile>> possibleGroups = new ArrayList<>();

    /**
     * Checks if there are 4 groups of 4 tiles of the same color with one side in common (not only vertical or orizontal)
     * @param bookshelf bookshelf of the player
     * @return the score if the condition is verified, else it returns 0
     */
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

    public boolean contain(ArrayList<Tile> list, Tile tile) {
        for (Tile value : list) {
            if (value.equals(tile) && value.getPos().equals(tile.getPos())) {
                return true;
            }
        }
        return false;
    }
    public void groups(Bookshelf b, int x, int y, Tile prevTile) {

        Tile var = b.getTile(x,y);
        Tile tile = new Tile(var.getColor(), var.getType(), var.getPos());
        Tile nextTile;
        ArrayList<Tile> tmp;

        if (prevTile.equals(tile) && !contain(figure,tile)) {
            count++;
            figure.add(tile);
            if (count == 4) {
                tmp = new ArrayList<>(figure);
                possibleGroups.add(tmp);
                count--;
                figure.remove(count);
            } else {
                if (x < 4 && b.getTile(x + 1, y) != null) {
                    groups(b, x + 1, y, b.getTile(x, y));
                }
                if (x > 0 && b.getTile(x - 1, y) != null) {
                    groups(b, x - 1, y, b.getTile(x, y));
                }
                if (y < 5 && b.getTile(x, y + 1) != null) {
                    groups(b, x, y + 1, b.getTile(x, y));
                }
                if (y > 0 && b.getTile(x, y - 1) != null) {
                    groups(b, x, y - 1, b.getTile(x, y));
                }
                count--;
                figure.remove(count);
            }
        } else if (prevTile.equals(tile) && contain(figure,tile) && count == 3) {
            if (x < 4) {
                nextTile = b.getTile(x+1,y);
                if (tile.equals(nextTile) && !contain(figure,nextTile)) {
                    figure.add(nextTile);
                    tmp = new ArrayList<>(figure);
                    possibleGroups.add(tmp);
                    figure.remove(count);
                }
            }
            if (x > 0) {
                nextTile = b.getTile(x - 1,y);
                if (tile.equals(nextTile) && !contain(figure,nextTile)) {
                    figure.add(nextTile);
                    tmp = new ArrayList<>(figure);
                    possibleGroups.add(tmp);
                    figure.remove(count);
                }
            }
            if (y < 5) {
                nextTile = b.getTile(x,y + 1);
                if (tile.equals(nextTile) && !contain(figure,nextTile)) {
                    figure.add(nextTile);
                    tmp = new ArrayList<>(figure);
                    possibleGroups.add(tmp);
                    figure.remove(count);
                }
            }
            if (y > 0) {
                nextTile = b.getTile(x,y - 1);
                if (tile.equals(nextTile) && !contain(figure,nextTile)) {
                    figure.add(nextTile);
                    tmp = new ArrayList<>(figure);
                    possibleGroups.add(tmp);
                    figure.remove(count);
                }
            }
        }
    }
    public boolean overlap(ArrayList<Tile> figure1, ArrayList<Tile> figure2) {

        if (!figure1.get(0).equals(figure2.get(0))) { // if they have different color they can not overlap
            return false;
        }

        for (Tile value : figure1) {
            for (Tile tile : figure2) {
                if (value.getPos().equals(tile.getPos())) {
                    return true;
                }
            }
        }

        return false;

    }

    public boolean overlap(ArrayList<Tile> f1, ArrayList<Tile> f2, ArrayList<Tile> f3, ArrayList<Tile> f4) {

        return overlap(f1, f2) || overlap(f1, f3) || overlap(f1, f4) || overlap(f2, f3) || overlap(f2, f4) || overlap(f3, f4);

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