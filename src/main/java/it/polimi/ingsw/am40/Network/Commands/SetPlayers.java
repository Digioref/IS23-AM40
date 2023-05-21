package it.polimi.ingsw.am40.Network.Commands;

import it.polimi.ingsw.am40.JSONConversion.JSONConverterStoC;
import it.polimi.ingsw.am40.Network.Handlers;
import it.polimi.ingsw.am40.Network.ICommand;
import it.polimi.ingsw.am40.Network.LoggingPhase;

import java.io.IOException;
import java.util.ArrayList;

public class SetPlayers implements ICommand {

    @Override
    public void execute(Handlers c, ArrayList<String> comm) throws IOException {
//        c.setNumPlayers(Integer.parseInt(comm.get(0)));
        if (comm.size() == 1) {
            if (LoggingPhase.SETPLAYERS && c.getLogphase().equals(LoggingPhase.SETTING)) {
                c.setLogphase(LoggingPhase.WAITING);
                try {
                    c.getLobby().setNumPlayers(Integer.parseInt(comm.get(0)));
                } catch (NumberFormatException e) {
                    c.sendMessage(JSONConverterStoC.createJSONError("You must type an int, not a string!"));
                    return;
                }
//                c.sendMessage(JSONConverterStoC.normalMessage("Number of players set!"));
//                c.sendMessage(JSONConverterStoC.normalMessage("You are playing with " + comm.get(0) + " players!"));
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
