package it.polimi.ingsw.am40.Network;

/**
 * <p>An enumeration of the logging phases:</p>
 * <ul>
 *     <li>LOGGING: the player is logging in with the chosen nickname</li>
 *     <li>WAITING: the player is waiting in the lobby</li>
 *     <li>INGAME: the player is playing</li>
 *     <li>SETTING: the player is setting the number of players of the game</li>
 * </ul>
 */
public enum LoggingPhase {
    LOGGING, WAITING, INGAME, SETTING;
    /**
     * If it is false, the number of players cannot be set, while if it is true, the player who is in the phase SETTING, can set the number of players
     */
    public static boolean SETPLAYERS = false;

    /**
     * Sets the static attribute SETPLAYERS to the parameter passed (enum)
     * @param SETPLAYERS a boolean
     */
    public static void setSETPLAYERS(boolean SETPLAYERS) {
        LoggingPhase.SETPLAYERS = SETPLAYERS;
    }

}
