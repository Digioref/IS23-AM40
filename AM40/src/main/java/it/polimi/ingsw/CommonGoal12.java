package it.polimi.ingsw;

public class CommonGoal12 extends CommonGoal {

    public CommonGoal12(int x, String name) {
        this.description = name;
        commgoaltok = new CommonGoalToken(x);
    }

    public int check (Bookshelf bookshelf){
        int count=0;

        for(int i=0; i<4;i++){
            if(bookshelf.getTile(i,i).equals(bookshelf.getTile(i+1,i+1)))
                count++;
        }
        if(count == 5)
            return commgoaltok.updateScore();

        count=0;

        for(int i=0; i<4;i++){
            if(bookshelf.getTile(i,i+1).equals(bookshelf.getTile(i+1,i+2)))
                count++;
        }
        if(count == 5)
            return commgoaltok.updateScore();

        count = 0;

        for(int i=5; i>1;i--){
            if(bookshelf.getTile(i,i).equals(bookshelf.getTile(i,i-1)))
                count++;
        }
        if(count == 5)
            return commgoaltok.updateScore();

        count = 0;

        for(int i=5; i>1;i--){
            if(bookshelf.getTile(i,i).equals(bookshelf.getTile(i-1,i-2)))
                count++;
        }
        if(count == 5)
            return commgoaltok.updateScore();

        return 0;
    }


}
