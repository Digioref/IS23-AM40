package it.polimi.ingsw.am40.Network;

/**
 * The interface implemented by the virtual view. It defines methods used to receive errors from the game
 */
public interface IGameErrorTurn {
    /**
     * It receives the selection turn error because it isn't the selection phase but the player has typed the select command
     */
    void selectionTurnError();

    /**
     * It receives the error related to the selection of a tile that can't be selected
     */
    void selectionError();

    /**
     * It receives the remove turn error because it isn't the remove phase but the player has typed the remove command
     */
    void removingTurnError();

    /**
     * It receives the pick turn error because it isn't the pick phase but the player has typed the pick command
     */
    void pickingTurnError();

    /**
     * It receives the order turn error because it isn't the ordering phase but the player has typed the order command
     */
    void orderingTurnError();

    /**
     * It receives the order error because the order specified by the user is not compatible with the number of selected tiles
     */
    void orderingError();

    /**
     * It receives the insert turn error because it isn't the insert phase but the player has typed the insert command
     */
    void insertTurnError();

    /**
     * It receives the turn error because it isn't the turn of the player, but he has typed a command
     */
    void turnError();

    /**
     * It receives the insert error because the column specified by the player is full
     */
    void insertError();

    /**
     * It receives the chat error because the nickname of the receiver is not in that game
     */
    void chatError();

    void pickColumnFullError();
}
