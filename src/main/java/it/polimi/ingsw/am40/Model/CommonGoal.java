package it.polimi.ingsw.am40.Model;

/**
 * An abstract class representing the common goal of the game
 */
public abstract class CommonGoal {

    /**
     * The token of the common goal, It represents the score of the goal
     */
    protected CommonGoalToken commgoaltok;

    /**
     * Returns the score obtained if the goal is achieved
     * @param b bookshelf of the player
     * @return score achieved by the player (0 if the goal is not achieved)
     */
    public abstract int check (Bookshelf b);

    /**
     * Returns the Token connected to the goal
     * @return
     */

    public CommonGoalToken getCommgoaltok() {
        return commgoaltok;
    }

    /**
     * Sets the token to the provided one
     * @param commgoaltok
     */
    public void setCommgoaltok(CommonGoalToken commgoaltok) {
        this.commgoaltok = commgoaltok;
    }
}
