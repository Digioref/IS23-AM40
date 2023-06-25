package it.polimi.ingsw.am40.Network;

/**
 * <p>An enumeration of the possible action for the player:</p>
 * <ul>
 *     <li>SELECT: select a tile from the board</li>
 *     <li>PICK: pick the tiles selected</li>
 *     <li>ORDER: order the tiles picked</li>
 *     <li>INSERT: insert the tiles picked and ordered into the bookshelf</li>
 *     <li>REMOVE: remove the tiles selected</li>
 *     <li>CHAT: send a message to a specific player or to everyone</li>
 * </ul>
 */
public enum ActionType {
    SELECT, PICK, ORDER, INSERT, REMOVE, CHAT;
}
