package it.polimi.ingsw.am40.model;

public class CommonGoal9 extends CommonGoal {

    public CommonGoal9(int x) {
        commgoaltok = new CommonGoalToken(x);
    }

    public int check (Bookshelf bookshelf){
        int[] numcolour = {0,0,0,0,0,0};

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                if (bookshelf.getTile(i,j) != null) {
                    switch (bookshelf.getTile(i, j).getColor()) {
                        case YELLOW -> numcolour[0]++;
                        case CYAN -> numcolour[1]++;
                        case BLUE -> numcolour[2]++;
                        case WHITE -> numcolour[3]++;
                        case VIOLET -> numcolour[4]++;
                        case GREEN -> numcolour[5]++;
                    }
                }
            }
        }
        if (numcolour[0] > 5 || numcolour[1] > 5 || numcolour[2] > 5 || numcolour[3] > 5 || numcolour[4] > 5 || numcolour[5] > 5)
            return commgoaltok.updateScore();

        else{
            return 0;}
    }

}
