package it.polimi.ingsw.am40.Model;

import java.util.ArrayList;

public class CommonGoal5 extends CommonGoal {
    public CommonGoal5(int numPlayer) {
        commgoaltok = new CommonGoalToken(numPlayer);
    }

    /**
     * Checks if there are 3 full columns with no more then 3 different types of tiles
     * @param bookshelf bookshelf of the player
     * @return the score if the condition is verified, else it returns 0
     */
    @Override
    public int check(Bookshelf bookshelf) {
        int count = 0;
        ArrayList<Tile> differentTiles = new ArrayList<Tile>(); // array where I save all the different Tiles

        for (int i = 0; i < 5 && count < 3; i++) {
            if (bookshelf.getBookshelf().get(i).getColumn().size() == 6) { // controllo solo quelli con la colonna piena
                differentTiles.clear();
                for (int j = 0; j < 6; j++) {
                    if (!differentTiles.contains(bookshelf.getTile(i,j))) { // aggiungo solo se non è già contrnuto ?? l'uguaglianza la controlla con .equals?? secondo me si
                        differentTiles.add(bookshelf.getTile(i,j));
                    }
                }
                if (differentTiles.size() < 4) {
                    count++;
                }
            }

        }
        if (count == 3) {
            return commgoaltok.updateScore();
        } else {
            return 0;
        }
    }


}