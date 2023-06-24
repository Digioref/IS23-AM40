package it.polimi.ingsw.am40.Network.Commands;

import it.polimi.ingsw.am40.JSONConversion.JSONConverterStoC;
import it.polimi.ingsw.am40.Network.Handlers;
import it.polimi.ingsw.am40.Network.ICommand;

import java.io.IOException;
import java.util.ArrayList;

/**
 * todo
 */
public class GetBookAll implements ICommand {
    @Override
    public void execute(Handlers c, ArrayList<String> comm) throws IOException {
        if (comm.size() == 0) {
            c.sendMessage(JSONConverterStoC.createJSONBookAll(c.getController().getGame().getPlayers()));
        } else {
            c.sendMessage(JSONConverterStoC.createJSONError("The command getbookall doesn't want arguments!"));
        }
    }
}
