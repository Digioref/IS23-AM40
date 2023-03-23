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
    /*
    @Override
    public int check (Bookshelf bookshelf) { // check quattro tiles vicini in qualunque posizione

        int ntiles;
        int count = 0;
        boolean[][] matrice = new boolean[5][6]; // matrice di boolean per tenere traccia di dove sono già  stato
        for (int i = 0; i < 5; i++) {               // la inizializzo tutta a true e metto a false dove passo
            for (int j = 0; j < 6; j++) {
                matrice[i][j] = true;
            }
        }

        for (int i = 0; i < 4 && count < 4; i++) {
            for (int j = 0; j< 5 && count < 4; j++) {
                if (matrice[i][j] && bookshelf.getTile(i,j) != null) {         // inizio solo se è una colonna su cui non sono ancora stato e non è vuota
                    ntiles = ricors(i, j, bookshelf, matrice); // metodo che conta le adiacenze di un tile in modo ricorsivo
                    count += ntiles/4;  // aggiungo a count i blocchi da 4 che riesco a fare con quelle caselle vicine
                }
            }
        }
        if (count >= 4) {
            return commgoaltok.updateScore();
        } else {
            return 0;
        }

    }

    public int ricors(int x, int y, Bookshelf bookshelf, boolean[][] matrice) { // metodo di check3

        int val = 0;
        matrice[x][y] = false; // quattro if per coprire tutte le direzioni, con i relativi controlli se sto uscendo dalla mappa

        if ( x < 4 && matrice[x+1][y] && bookshelf.getTile(x,y).equals(bookshelf.getTile(x+1,y)) ) {
            val = ricors(x+1, y, bookshelf, matrice);
        }
        if ( y < 5 && matrice[x][y+1] && bookshelf.getTile(x,y).equals(bookshelf.getTile(x,y+1)) ) {
            val = ricors(x, y+1, bookshelf, matrice);
        }
        if ( x > 0 && matrice[x-1][y] && bookshelf.getTile(x,y).equals(bookshelf.getTile(x-1,y)) ) {
            val = ricors(x-1, y, bookshelf, matrice);
        }
        if ( y > 0 && matrice[x][y-1] && bookshelf.getTile(x,y).equals(bookshelf.getTile(x,y-1)) ) {
            val = ricors(x, y-1, bookshelf, matrice);
        }
        return 1 + val;
    }

    */

    private int count = 0;
    private ArrayList<TileCoo> figure;
    private ArrayList<ArrayList<TileCoo>> possibleGroups = new ArrayList<>();

    @Override
    public int check(Bookshelf bookshelf) {

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                groups(bookshelf, i, j, bookshelf.getTile(i,j));
            }
        }

        for (int i = 0; i < possibleGroups.size() - 1; i++) {
            for (int j =  i + 1; j < possibleGroups.size(); j++) {
                if (fourFigures()) {
                    return commgoaltok.updateScore();
                }
            }
        }

        return 0;

    }
    public void groups(Bookshelf b, int x, int y, Tile prevTile) {

        TileCoo tile = new TileCoo(x,y,b.getTile(x,y));

        if (prevTile.equals(b.getTile(x,y)) && !figure.contains(tile)) {
            count++;
            figure.add(tile);
            if (count == 4) {
                if (!possibleGroups.contains(figure)) {
                    possibleGroups.add(figure);
                }
                count--;
                figure.remove(count);
                return;
            } else {
                groups(b, x + 1, y, b.getTile(x,y));
                groups(b, x - 1, y, b.getTile(x,y));
                groups(b, x, y + 1, b.getTile(x,y));
                groups(b, x, y - 1, b.getTile(x,y));
                count--;
                figure.remove(count);
                return;
            }
        }
        return;
    }

    public boolean overlap(ArrayList<TileCoo> figure1, ArrayList<TileCoo> figure2) {

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

    ArrayList<ArrayList<TileCoo>> finalGroups = new ArrayList<>();
    public boolean fourFigures() {

        if (finalGroups.size() >= 4) {
            for (int i = 0; i < possibleGroups.size() - 3; i++) {
                for (int j = i + 1; j < possibleGroups.size() - 2; j++) {
                    for (int h = j + 1; h < possibleGroups.size() - 1; h++) {
                        for (int k = h + 1; k < possibleGroups.size(); k++) {
                            if (!overlap(finalGroups.get(i), finalGroups.get(j), finalGroups.get(h), finalGroups.get(k))) {
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
}
