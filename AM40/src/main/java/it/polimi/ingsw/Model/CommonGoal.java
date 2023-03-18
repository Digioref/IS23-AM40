package it.polimi.ingsw.Model;

public abstract class CommonGoal {
    protected CommonGoalToken commgoaltok;

    public abstract int check (Bookshelf b);

    public CommonGoalToken getCommgoaltok() {
        return commgoaltok;
    }

    public void setCommgoaltok(CommonGoalToken commgoaltok) {
        this.commgoaltok = commgoaltok;
    }
}
