package it.polimi.ingsw.am40.Model;

/**
 * It keeps track of the score associated with a specific common goal
 */
public class CommonGoalToken {
    /**
     * Current score of the goal that can be obtained
     */
    private int score;
    /**
     * Number of players playing
     */
    private int numPlayer;

    /**
     * Constructor which sets the initial score to 8 and the numPlayer to the provided one
     * @param numPlayer number of players
     */
    public CommonGoalToken(int numPlayer) {
        this.numPlayer = numPlayer;
        this.score = 8;
    }

    /**
     * Updates the score of the common goal
     * @return score of the goal
     */
    public int updateScore() {
        int t = score;
        if (numPlayer == 2) {
            score -= 4;
        }
        else {
            score -= 2;
            if (numPlayer == 3 && score == 2) {
                return 0;
            }
        }
        return t;
    }

    /**
     * Returns the score
     * @return score of the goal
     */
    public int getScore() {
        return score;
    }

    /**
     * Sets the score to the provided one
     * @param score the value of the score
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Returns the number of players
     * @return number of players
     */
    public int getNumPlayer() {
        return numPlayer;
    }

}