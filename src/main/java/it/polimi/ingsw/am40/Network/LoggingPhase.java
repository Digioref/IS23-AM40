package it.polimi.ingsw.am40.Network;

public enum LoggingPhase {
    LOGGING, WAITING, INGAME, SETTING;
    public static boolean SETPLAYERS = false;

    public static void setSETPLAYERS(boolean SETPLAYERS) {
        LoggingPhase.SETPLAYERS = SETPLAYERS;
    }

}
