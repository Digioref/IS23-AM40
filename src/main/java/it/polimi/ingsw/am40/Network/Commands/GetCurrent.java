package it.polimi.ingsw.am40.Network.Commands;

import it.polimi.ingsw.am40.JSONConversion.JSONConverterStoC;
import it.polimi.ingsw.am40.Network.Handlers;
import it.polimi.ingsw.am40.Network.ICommand;

import java.io.IOException;
import java.util.ArrayList;

public class GetCurrent implements ICommand {
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
