package it.polimi.ingsw.am40.model;

import java.util.ArrayList;

public class CommonGoal5 extends CommonGoal {
    public CommonGoal5(int numPlayer) {
        super();
    }

    /**
     * Checks if there are 3 full columns with no more then 3 different types of tiles
     * @param bookshelf bookshelf of the player
     * @return the score if the condition is verified, else it returns 0
     */
    @Override
    public int check(Bookshelf bookshelf) {
        int count = 0;
        ArrayList<Tile> differentTiles = new ArrayList<Tile>(); // array dove salvo i tile diversi

        for (int i = 0; i < 5; i++) {
            if (bookshelf.getColumns().get(i).getColumn().size() == 6) { // controllo solo quelli con la colonna piena
                for (int j = 0; j < 6; j++) {
                    if (!differentTiles.contains(bookshelf.getTile(i,j))) { // aggiungo solo se non è già contrnuto ?? l'uguaglianza la controlla con .equals?? secondo me si
                        differentTiles.add(bookshelf.getTile(i,j));
                    }
                }
            }
            if (differentTiles.size() < 4) {
                count++;
            }
        }
        if (count == 3) {
            return commgoaltok.updateScore();
        } else {
            return 0;
        }
    }

}