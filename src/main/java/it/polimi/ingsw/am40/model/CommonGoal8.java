package it.polimi.ingsw.am40.model;
public class CommonGoal8 extends CommonGoal {
    public CommonGoal8(int numPlayer) {
        commgoaltok = new CommonGoalToken(numPlayer);
    }

    /**
     * Checks if the four tiles in the edges of the bookshelf are the same type
     * @param bookshelf bookshelf of the player
     * @return the score if the condition is verified, else it returns 0
     */
    @Override
    public int check(Bookshelf bookshelf) { // commongoal 8

        if (bookshelf.getTile(0,0) != null) {
            if (bookshelf.getTile(0, 0).equals(bookshelf.getTile(0, 5))) { // controllare se il tile è vuoto tile.getcolor cosa rotorna, fare bene il .equals
                if (bookshelf.getTile(0, 0).equals(bookshelf.getTile(4, 0))) {
                    if (bookshelf.getTile(0, 0).equals(bookshelf.getTile(4, 5))) {
                        return commgoaltok.updateScore();
                    }
                }
            }
        }
        return 0;
    }
}