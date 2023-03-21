package it.polimi.ingsw.am40.model;

public class CommonGoal10 extends CommonGoal {

    public CommonGoal10(int numPlayer) {
        commgoaltok = new CommonGoalToken(numPlayer);
    }

    public int check (Bookshelf bookshelf){
        for(int i=0; i<3;i++) {
            for (int j = 0; j < 4; j++) {
                if (bookshelf.getTile(i,j) != null) {
                    if (bookshelf.getTile(i, j).equals(bookshelf.getTile(i, j + 2))) {
                        if (bookshelf.getTile(i, j).equals(bookshelf.getTile(i + 1, j + 1))) {
                            if (bookshelf.getTile(i, j).equals(bookshelf.getTile(i + 2, j))
                                    && bookshelf.getTile(i, j).equals(bookshelf.getTile(i + 2, j + 2))) {
                                return commgoaltok.updateScore();
                            }
                        }
                    }
                }
            }
        }
        return 0;
    }


}