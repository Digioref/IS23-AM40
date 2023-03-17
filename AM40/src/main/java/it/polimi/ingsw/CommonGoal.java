package it.polimi.ingsw;

public abstract class CommonGoal {
    protected String description;
    protected CommonGoalToken commgoaltok;

    public abstract int check (Bookshelf b);

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CommonGoalToken getCommgoaltok() {
        return commgoaltok;
    }

    public void setCommgoaltok(CommonGoalToken commgoaltok) {
        this.commgoaltok = commgoaltok;
    }
}
