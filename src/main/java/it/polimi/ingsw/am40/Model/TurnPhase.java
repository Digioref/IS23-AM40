package it.polimi.ingsw.am40.Model;

/**
 * <p>An enumeration of the phases of the turn:</p>
 * <ul>
 *     <li> START: the beginning of the turn</li>
 *     <li> SELECTION: a tile can be selected from the board</li>
 *     <li> PICK: the tiles selected can be picked from the board (they are removed from the board)</li>
 *     <li> ORDER: the desired order of the tiles picked can be specified</li>
 *     <li> INSERT: the tiles picked and ordered can be inserted in the bookshelf</li>
 *     <li> ENDTURN: the end of the turn</li>
 *     <li> ENDGAME: the game has ended and the score of each player is calculated</li>
 * </ul>
 */
public enum TurnPhase {
    START, SELECTION, PICK, ORDER, INSERT, ENDTURN, ENDGAME;
}
