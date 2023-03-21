package it.polimi.ingsw.am40.model;

public class CommonGoal4 extends CommonGoal {
    public CommonGoal4(int numPlayer) {
        super();
    }

    /**
     * Checks if there are 6 groups of the tiles aside of the same color
     * @param bookshelf bookshelf of the player
     * @return the score if the condition is verified, else it returns 0
     */
    @Override
    public int check(Bookshelf bookshelf) { // check due tiles vicini in qualunque posizione

        int count = 0;
        boolean[][] matrice = new boolean[5][6]; // matrice di boolean per tenere traccia di dove sono già  stato
        for (int i = 0; i < 5; i++) {               // la inizializzo tutta a true e metto a false dove passo
            for (int j = 0; j < 6; j++) {
                matrice[i][j] = true;
            }
        }

        for (int i = 0; i < 4 && count < 6; i++) {
            for (int j = 0; j < 5 && count < 6; j++) {
                if (matrice[i][j] && bookshelf.getTile(i, j) != null) {         // inizio solo se è una colonna su cui non sono ancora stato e non è vuota
                    if (bookshelf.getTile(i, j).equals(bookshelf.getTile(i + 1, j))) {
                        count++;
                        matrice[i][j] = false;
                        matrice[i + 1][j] = false;
                    } else if (bookshelf.getTile(i, j).equals(bookshelf.getTile(i, j + 1))) {
                        count++;
                        matrice[i][j] = false;
                        matrice[i][j + 1] = false;
                    }
                }
            }
        }
        if (count == 6) {
            return commgoaltok.updateScore();
        } else {
            return 0;
        }
    }
}
