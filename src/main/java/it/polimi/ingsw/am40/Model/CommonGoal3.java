package it.polimi.ingsw.am40.Model;

import java.util.ArrayList;

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
    private ArrayList<Tile> figure = new ArrayList<>();
    private ArrayList<ArrayList<Tile>> possibleGroups = new ArrayList<>();
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
        ArrayList<Tile> tmp;

        if (prevTile.equals(tile) && !contain(figure,tile)) {
            count++;
            figure.add(tile);
            if (count == 4) {
                tmp = new ArrayList<>(figure);
                possibleGroups.add(tmp);
                count--;
                figure.remove(count);
                return;
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
                return;
            }
        } else if (prevTile.equals(b.getTile(x,y)) && contain(figure,tile) && count == 3) {
            if (x < 4) {
                tile = b.getTile(x+1,y);
                if (b.getTile(x,y).equals(tile) && !contain(figure,tile)) {
                    figure.add(tile);
                    tmp = new ArrayList<>(figure);
                    possibleGroups.add(tmp);
                    figure.remove(count);
                }
            }
            if (x > 0) {
                tile = b.getTile(x - 1,y);
                if (b.getTile(x,y).equals(tile) && !contain(figure,tile)) {
                    figure.add(tile);
                    tmp = new ArrayList<>(figure);
                    possibleGroups.add(tmp);
                    figure.remove(count);
                }
            }
            if (y < 5) {
                tile = b.getTile(x,y + 1);
                if (b.getTile(x,y).equals(tile) && !contain(figure,tile)) {
                    figure.add(tile);
                    tmp = new ArrayList<>(figure);
                    possibleGroups.add(tmp);
                    figure.remove(count);
                }
            }
            if (y > 0) {
                tile = b.getTile(x,y - 1);
                if (b.getTile(x,y).equals(tile) && !contain(figure,tile)) {
                    figure.add(tile);
                    tmp = new ArrayList<>(figure);
                    possibleGroups.add(tmp);
                    figure.remove(count);
                }
            }
        }
    }
    public boolean overlap(ArrayList<Tile> figure1, ArrayList<Tile> figure2) {

        //if (!figure1.isEmpty() && !figure2.isEmpty()) {
        if (!figure1.get(0).equals(figure2.get(0))) { // if they have different color they can not overlap
            return false;
        }

        for (int i = 0; i < figure1.size(); i++) {
            for (int j = 0; j < figure2.size(); j++) {
                if (figure1.get(i).getPos().equals(figure2.get(j).getPos())) {
                    return true;
                }
            }
        }
        //}

        return false;

    }

    public boolean overlap(ArrayList<Tile> figure1, ArrayList<Tile> figure2, ArrayList<Tile> figure3, ArrayList<Tile> figure4) {

        if (!overlap(figure1, figure2) && !overlap(figure1, figure3) && !overlap(figure1, figure4) && !overlap(figure2, figure3) && !overlap(figure2, figure4) && !overlap(figure3, figure4)) {
            return false;
        }

        return true;

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