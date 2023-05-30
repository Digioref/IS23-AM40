package it.polimi.ingsw.am40.Network.Commands;

import it.polimi.ingsw.am40.JSONConversion.JSONConverterStoC;
import it.polimi.ingsw.am40.Network.Handlers;

import java.io.IOException;
import java.util.ArrayList;
import it.polimi.ingsw.am40.Network.*;

public class ViewChat implements ICommand {
    @Override
    public void execute(Handlers c, ArrayList<String> comm) throws IOException {
        if (!c.isLogged()) {
            c.sendMessage(JSONConverterStoC.createJSONError("You are not logged in!"));
        }
        else if (!c.getLogphase().equals(LoggingPhase.INGAME)) {
            c.sendMessage(JSONConverterStoC.createJSONError("You are not in any game yet!"));
        }
        else if (comm.size() != 0) {
            c.sendMessage(JSONConverterStoC.createJSONError("Incomplete command!"));
        }
        else {
            c.getChat();
        }

    }
}
