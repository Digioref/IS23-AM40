package it.polimi.ingsw.am40.Model;

/**
 * <p>An enumeration of the phases of the turn</p>
 */
public enum TurnPhase {
    /**
     * The beginning of the turn
     */
    START,
    /**
     *  A tile can be selected from the board
     */
    SELECTION,
    /**
     * The tiles selected can be picked from the board (they are removed from the board)
     */
    PICK,
    /**
     * The desired order of the tiles picked can be specified
     */
    ORDER,
    /**
     * The tiles picked and ordered can be inserted in the bookshelf
     */
    INSERT,
    /**
     * The end of the turn
     */
    ENDTURN,
    /**
     * The game has ended and the score of each player is calculated
     */
    ENDGAME;
}
