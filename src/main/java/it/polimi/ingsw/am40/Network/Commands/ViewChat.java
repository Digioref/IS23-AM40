package it.polimi.ingsw.am40.Network.Commands;

import it.polimi.ingsw.am40.JSONConversion.JSONConverterStoC;
import it.polimi.ingsw.am40.Network.Handlers;

import java.io.IOException;
import java.util.ArrayList;
import it.polimi.ingsw.am40.Network.*;

/**
 * The class representing the ViewChat command, used to view the chat and the messages inside it
 */
public class ViewChat implements ICommand {
    /**
     * It executes the command calling a method of the handler
     * @param c handler of the player
     * @param comm parameters of the command; in this case, no parameters
     * @throws IOException
     */
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
