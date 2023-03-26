package it.polimi.ingsw.am40.Model;

import java.util.ArrayList;

public class CommonGoal7 extends CommonGoal {

    public CommonGoal7(int numPlayer) {
        commgoaltok = new CommonGoalToken(numPlayer);
    }

    public int check(Bookshelf bookshelf) {
        ArrayList<TileColor> differentColours = new ArrayList<>();

        // precheck che ci siano almeno 4 tile in ciascuna colonna, .get restituisce la colonna
        for (int i = 0; i < 5; i++) {
            if (bookshelf.getBookshelf().get(i).getColumn().size() < 4)
                return 0;
        }

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (!(differentColours.contains(bookshelf.getTile(i, j).getColor()))) {
                    differentColours.add(bookshelf.getTile(i, j).getColor());
                }
                if (differentColours.size() > 3)
                    return 0;
            }

        }
        return commgoaltok.updateScore();
    }

}