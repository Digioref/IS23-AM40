package it.polimi.ingsw.am40;

public abstract class CommonGoal {
    private String description;
    private CommonGoalToken commgoaltok;

    public abstract int check (Bookshelf b);
}
