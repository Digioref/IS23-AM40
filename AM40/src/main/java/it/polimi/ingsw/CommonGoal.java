package it.polimi.ingsw;

public abstract class CommonGoal {
    private String description;
    private CommonGoalToken commgoaltok;

    public abstract int check (Bookshelf b);
}
