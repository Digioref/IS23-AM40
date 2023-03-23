package it.polimi.ingsw.am40.model;

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
    public int check (Bookshelf b) { // ok
        int count = 0;

        int x = 0; // coordinates of the bottom left tile of the first square find
        int y = 0;

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                if (b.getTile(i,j) != null) {

                    if (b.getTile(i, j).equals(b.getTile(i + 1, j)) && b.getTile(i, j).equals(b.getTile(i, j + 1)) && b.getTile(i, j).equals(b.getTile(i + 1, j + 1))) {
                        if (count == 0) {
                            count++;
                            x = i;
                            y = j;
                            j++;
                        } else {
                            if (b.getTile(x,y).equals(b.getTile(i,j))) { // controllo che il colore del secondo blocco si alo stesso di quello del primo
                                if (x + 1 != i || (y != j + 1 && y != j && y + 1 != j)) { // fa il controllo sulla sovrapposizione
                                    return commgoaltok.updateScore();
                                }
                            }
                        }
                    }
                } else {
                    j = 5;
                }
            }
        }
        return 0;
    }
}
