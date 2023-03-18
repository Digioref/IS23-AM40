package it.polimi.ingsw.Model;

public class CommonGoal10 extends CommonGoal {

    public CommonGoal10(int x) {
        commgoaltok = new CommonGoalToken(x);
    }

    public int check (Bookshelf bookshelf){
        for(int i=0; i<3;i++) {
            for (int j = 0; j < 4; j++) {
                if(bookshelf.getTile(i,j).getColor().equals(bookshelf.getTile(i,j+2).getColor()))
                {
                    if(bookshelf.getTile(i,j).getColor().equals(bookshelf.getTile(i+1,j+1).getColor()))
                    {
                        if(bookshelf.getTile(i,j).getColor().equals(bookshelf.getTile(i+2,j).getColor())
                                &&bookshelf.getTile(i,j).getColor().equals(bookshelf.getTile(i+2,j+2).getColor()))
                        {
                            return commgoaltok.updateScore();
                        }
                    }
                }
            }
        }
        return 0;
    }


}
