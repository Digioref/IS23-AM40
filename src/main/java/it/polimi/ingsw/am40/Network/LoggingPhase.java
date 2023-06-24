package it.polimi.ingsw.am40.Network;

/**
 * todo
 */
public enum LoggingPhase {
    LOGGING, WAITING, INGAME, SETTING;
    public static boolean SETPLAYERS = false;

    /**Sets the static attribute SETPLAYERS to the parameter passed (enum)
     * @param SETPLAYERS
     */
    public static void setSETPLAYERS(boolean SETPLAYERS) {
        LoggingPhase.SETPLAYERS = SETPLAYERS;
    }

}
