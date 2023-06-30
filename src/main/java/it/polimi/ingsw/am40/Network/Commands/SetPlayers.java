package it.polimi.ingsw.am40.Network.Commands;

import it.polimi.ingsw.am40.JSONConversion.JSONConverterStoC;
import it.polimi.ingsw.am40.Network.Handlers;
import it.polimi.ingsw.am40.Network.ICommand;
import it.polimi.ingsw.am40.Network.LoggingPhase;

import java.io.IOException;
import java.util.ArrayList;

/**
 * The class representing the SetPlayers command, used to set the number of players to play with
 */
public class SetPlayers implements ICommand {

    /**
     * It executes the command calling a method of the handler
     * @param c handler of the player
     * @param comm parameters of the command; in this case, an integer
     * @throws IOException
     */
    @Override
    public void execute(Handlers c, ArrayList<String> comm) throws IOException {
//        c.setNumPlayers(Integer.parseInt(comm.get(0)));
        if (comm.size() == 1) {
            if (LoggingPhase.SETPLAYERS && c.getLogphase().equals(LoggingPhase.SETTING)) {
                c.setLogphase(LoggingPhase.WAITING);
                int i;
                try {
                    i = Integer.parseInt(comm.get(0));
                } catch (NumberFormatException e) {
                    c.sendMessage(JSONConverterStoC.createJSONError("You must type an int, not a string!"));
                    return;
                }
                if (i < 2 || i > 4) {
                    c.sendMessage(JSONConverterStoC.createJSONError("The number of players must be between 2 and 4!"));
                    return;
                }
                c.getLobby().setNumPlayers(i);
                c.sendMessage(JSONConverterStoC.normalMessage("Waiting"));
            } else if (c.getLogphase().equals(LoggingPhase.INGAME)) {
                c.sendMessage(JSONConverterStoC.createJSONError("You can not set the number of players, the game has been already created!"));
            } else if (c.getLogphase().equals(LoggingPhase.LOGGING)) {
                c.sendMessage(JSONConverterStoC.createJSONError("You can not set the number of players, you have not yet logged in!"));
            } else if (c.getLogphase().equals(LoggingPhase.WAITING)) {
                c.sendMessage(JSONConverterStoC.createJSONError("You can not set the number of players, someone already did it!"));
            } else {
                c.sendMessage(JSONConverterStoC.createJSONError("You are not logged in yet!"));
            }
        } else {
            c.sendMessage(JSONConverterStoC.createJSONError("Incomplete command"));
        }
    }
}
