package it.polimi.ingsw.am40;

public class CommonGoalToken {
    private int score;
    private int numPlayer;

    public CommonGoalToken(int numPlayer) {
        this.numPlayer = numPlayer;
        this.score = 8;
    }

    public int updateScore() {
        int t = score;
        if (numPlayer == 2) {
            score -= 4;
        }
        else {
            score -= 2;
        }
        return t;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getNumPlayer() {
        return numPlayer;
    }

    public void setNumPlayer(int numPlayer) {
        this.numPlayer = numPlayer;
    }
}
