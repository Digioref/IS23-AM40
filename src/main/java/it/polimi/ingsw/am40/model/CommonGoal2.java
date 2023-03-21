package it.polimi.ingsw.am40.model;

public class CommonGoal2 extends CommonGoal {
    public CommonGoal2(int numPlayer) {
        commgoaltok = new CommonGoalToken(numPlayer);
    }
    /**
     * Checks if there are two columns full of all different tiles
     * @param bookshelf bookshelf of the player
     * @return the score if the condition is verified, else it returns 0
     */
    @Override
    public int check (Bookshelf bookshelf) {

        int count = 0;
        int equal;
        for (int i = 0; i < 5 && count < 2; i++) {
            if (bookshelf.getBookshelf().get(i).getColumn().size() == 6) {
                equal = 0;
                for (int j = 1; j < 6 && equal == 0; j++) {
                    for (int k = 0; k < j && equal == 0; k++) {
                        if (bookshelf.getTile(i, j).equals(bookshelf.getTile(i, k))) { // se trovo due celle uguali esco
                            equal = 1;
                        }
                    }
                }
                if (equal == 0) {
                    count++;
                }
            }
        }
        if (count == 2) {
            return commgoaltok.updateScore();
        } else {
            return 0;
        }
    }
}
