package it.polimi.ingsw;

public class CommonGoal9 extends CommonGoal {

    public CommonGoal9(int x, String name) {
        this.description = name;
        commgoaltok = new CommonGoalToken(x);
    }

    public int check (Bookshelf bookshelf){
        int[] numcolour = {0,0,0,0,0,0};

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                if (bookshelf.getTile(i,j) != null) {
                    switch (bookshelf.getTile(i, j).getColor()) {
                        case TileColor.YELLOW -> numcolour[0]++;
                        case TileColor.CYAN -> numcolour[1]++;
                        case TileColor.BLUE -> numcolour[2]++;
                        case TileColor.WHITE -> numcolour[3]++;
                        case TileColor.VIOLET -> numcolour[4]++;
                        case TileColor.GREEN -> numcolour[5]++;
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
