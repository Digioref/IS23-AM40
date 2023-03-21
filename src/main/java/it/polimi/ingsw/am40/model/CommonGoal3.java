package it.polimi.ingsw.am40.model;

public class CommonGoal3 extends CommonGoal {
    public CommonGoal3(int numPlayer) {
        super();
    }

    /**
     * Checks if there are 4 groups of 4 tiles of the same color with one side in common (not only vertical or orizontal)
     * @param bookshelf bookshelf of the player
     * @return the score if the condition is verified, else it returns 0
     */
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
}