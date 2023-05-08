package it.polimi.ingsw.am40.Network.Commands;

import it.polimi.ingsw.am40.JSONConversion.JSONConverterStoC;
import it.polimi.ingsw.am40.Network.Handlers;

import java.io.IOException;
import java.util.ArrayList;
import it.polimi.ingsw.am40.Network.*;

public class ChatAll implements ICommand {
    @Override
    public void execute(Handlers c, ArrayList<String> comm) throws IOException {
        if (!c.isLogged()) {
            c.sendMessage(JSONConverterStoC.normalMessage("You are not logged in!"));
        }
        else if (!c.getLogphase().equals(LoggingPhase.INGAME)) {
            c.sendMessage(JSONConverterStoC.normalMessage("You are not in any game yet!"));
        }
        else if (comm.size() != 1) {
            c.sendMessage(JSONConverterStoC.normalMessage("Incomplete command!"));
        }
        else {
            c.chat(comm.get(0), null);
        }

    }
}
