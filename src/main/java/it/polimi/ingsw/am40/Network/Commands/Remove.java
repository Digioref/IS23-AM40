package it.polimi.ingsw.am40.Network.Commands;

import it.polimi.ingsw.am40.JSONConversion.JSONConverterStoC;
import it.polimi.ingsw.am40.Network.ActionType;
import it.polimi.ingsw.am40.Network.Handlers;
import it.polimi.ingsw.am40.Network.ICommand;

import java.io.IOException;
import java.util.ArrayList;

public class Remove implements ICommand {

    @Override
    public void execute(Handlers c, ArrayList<String> comm) throws IOException {
        if (comm.size() == 0) {
            c.executeCommand(ActionType.REMOVE, null);
        } else {
            c.sendMessage(JSONConverterStoC.createJSONError("The command remove doesn't want arguments!"));
        }

    }
}
