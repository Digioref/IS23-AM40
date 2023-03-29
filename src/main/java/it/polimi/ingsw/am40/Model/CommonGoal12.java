package it.polimi.ingsw.am40.Model;

public class CommonGoal12 extends CommonGoal { // NON FUNZIONA METTERE CONTROLLI SU NULL E CONTROLLARE

    public CommonGoal12(int numPlayer) {
        commgoaltok = new CommonGoalToken(numPlayer);
    }

    public int check (Bookshelf bookshelf){

        int count = 0;
        if (bookshelf.getBookshelf().size() == 5) {
            for (int i = 0; i < 5; i++) {
                if (bookshelf.getBookshelf().get(i).getSize() == i + 1) {
                    count++;
                }
            }
            if (count == 5) {
                return commgoaltok.updateScore();
            }
            count = 0;
            for (int i = 0; i < 5; i++) {
                if (bookshelf.getBookshelf().get(i).getSize() == 5 - i) {
                    count++;
                }
            }
            if (count == 5) {
                return commgoaltok.updateScore();
            }
        }
        return 0;
    }


}