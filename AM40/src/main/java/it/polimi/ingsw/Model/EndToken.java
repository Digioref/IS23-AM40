package it.polimi.ingsw.Model;

/**
 * Represents the Token given to the first player who completes his bookshelf
 */
public class EndToken {
    /**
     * A boolean which is true if someone has already finished his bookshelf and obtained 1 point
     */
    private boolean end;
    /**
     * The first player to complete his bookshelf
     */
    private Player player;

    /**
     * Constructor which sets end to false and player to null
     */
    public EndToken() {
        end = false;
        player = null;
    }

    /**
     * Verifies if the player p has completed his bookshelf
     * @param p player whose bookshelf has to be verified
     * @return true if the player has completed his bookshelf, false otherwise
     */
    public boolean check (Player p) {
        return p.getBookshelf().isFull();
    }

    /**
     * Updates the score of the player p adding 1 point if he is the first player to complete the bookshelf
     * @param p player
     */
    public void updateScore (Player p) {
        if (player == null && !end && check(p)) {
            p.addCurrentScore(1);
            player = p;
            end = true;
        }
    }

    /**
     * Returns true if a player has already completed his bookshelf
     * @return the feature end
     */
    public boolean isEnd() {
        return end;
    }

    /**
     * Sets the feature end to the provided one
     * @param end a boolean
     */
    public void setEnd(boolean end) {
        this.end = end;
    }

    /**
     * Returns the first player who has completed his bookshelf
     * @return the feature player
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Sets the feature player to the provided one
     * @param player a player
     */
    public void setPlayer(Player player) {
        this.player = player;
    }
}
