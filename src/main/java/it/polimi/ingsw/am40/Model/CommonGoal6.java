package it.polimi.ingsw.am40.Model;

public class CommonGoal6 extends CommonGoal {
    public CommonGoal6(int numPlayer) {
        commgoaltok = new CommonGoalToken(numPlayer);
    }

    /**
     *  Checks if there are 2 rows full of different tiles
     * @param bookshelf bookshelf of the player
     * @return the score if the condition is verified, else it returns 0
     */
    @Override
    public int check(Bookshelf bookshelf) {
        int count = 0;
        int equal;
        for (int j = 0; j < 6 && count < 2; j++) {
            equal = 0;
            for (int i = 1; i < 5 && equal == 0; i++) {
                if (bookshelf.getTile(i,j) != null) { // forse non serve ma controllo che la cella non sia vuota
                    for (int k = 0; k < i && equal == 0; k++) {
                        if (bookshelf.getTile(i, j).equals(bookshelf.getTile(k, j))) { // se trovo due celle uguali esco
                            equal = 1;
                        }
                    }
                }
            }
            if (equal == 0) {
                count++;
            }
        }
        if (count == 2) {
            return commgoaltok.updateScore();
        } else {
            return 0;
        }
    }
}

