package it.polimi.ingsw.am40.model;

public class CommonGoal11 extends CommonGoal {

    public CommonGoal11(int numPlayer) {
        commgoaltok = new CommonGoalToken(numPlayer);
    }

    public int check (Bookshelf bookshelf){
        int count=0;

        for(int i=0; i<4;i++){
            if (bookshelf.getTile(i,i) != null) {
                if (bookshelf.getTile(i, i).equals(bookshelf.getTile(i + 1, i + 1)))
                    count++;
            }
        }
        if(count == 5)
            return commgoaltok.updateScore();

        count = 0;

        for(int i=0; i<4;i++){
            if (bookshelf.getTile(i,i+1) != null) {
                if (bookshelf.getTile(i, i + 1).equals(bookshelf.getTile(i + 1, i + 2)))
                    count++;
            }
        }
        if(count == 5)
            return commgoaltok.updateScore();

        count = 0;

        for(int i=0; i<4;i++){
            if (bookshelf.getTile(i, 4 - i) != null) {
                if (bookshelf.getTile(i, 4 - i).equals(bookshelf.getTile(i + 1, 3 - i)))
                    count++;
            }
        }
        if(count == 5)
            return commgoaltok.updateScore();

        count = 0;

        for(int i=0; i<4;i++){
            if (bookshelf.getTile(i, 5 - i) != null) {
                if (bookshelf.getTile(i, 5 - i).equals(bookshelf.getTile(i + 1, i - 2)))
                    count++;
            }
        }
        if(count == 5)
            return commgoaltok.updateScore();

        return 0;
    }


}
