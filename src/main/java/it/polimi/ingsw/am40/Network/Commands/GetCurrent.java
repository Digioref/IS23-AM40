package it.polimi.ingsw.am40.Network.Commands;

import it.polimi.ingsw.am40.JSONConversion.JSONConverterStoC;
import it.polimi.ingsw.am40.Network.Handlers;
import it.polimi.ingsw.am40.Network.ICommand;

import java.io.IOException;
import java.util.ArrayList;

/**
 * The class representing the GetCurrent command, used to get the nickname of the current player
 */
public class GetCurrent implements ICommand {
    /**
     * It executes the command calling a method of the handler
     * @param c the handler of the player
     * @param comm the parameters of the command; in this case, no parameters
     * @throws IOException
     */
    @Override
    public void execute(Handlers c, ArrayList<String> comm) throws IOException {
        if (comm.size() == 0) {
            String s = c.getController().getGame().getCurrentPlayer().getNickname();
            c.sendMessage(JSONConverterStoC.createJSONCurrentPlayer(s));
        } else {
            c.sendMessage(JSONConverterStoC.createJSONError("The command getcurrent doesn't want arguments!"));
        }
    }
}
