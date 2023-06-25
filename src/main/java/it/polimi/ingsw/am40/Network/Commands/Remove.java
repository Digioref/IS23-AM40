package it.polimi.ingsw.am40.Network.Commands;

import it.polimi.ingsw.am40.JSONConversion.JSONConverterStoC;
import it.polimi.ingsw.am40.Network.ActionType;
import it.polimi.ingsw.am40.Network.Handlers;
import it.polimi.ingsw.am40.Network.ICommand;

import java.io.IOException;
import java.util.ArrayList;

/**
 * The class representing the Remove command, used to remove all the tiles already selected
 */
public class Remove implements ICommand {

    /**
     * It executes the command calling a method of the handler
     * @param c handler of the player
     * @param comm parameters of the command; in this case, no parameters
     * @throws IOException
     */
    @Override
    public void execute(Handlers c, ArrayList<String> comm) throws IOException {
        if (comm.size() == 0) {
            c.executeCommand(ActionType.REMOVE, null);
        } else {
            c.sendMessage(JSONConverterStoC.createJSONError("The command remove doesn't want arguments!"));
        }

    }
}
