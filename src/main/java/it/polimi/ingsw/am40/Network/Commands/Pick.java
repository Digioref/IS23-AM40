package it.polimi.ingsw.am40.Network.Commands;

import it.polimi.ingsw.am40.JSONConversion.JSONConverterStoC;
import it.polimi.ingsw.am40.Network.ActionType;
import it.polimi.ingsw.am40.Network.ClientHandler;
import it.polimi.ingsw.am40.Network.ICommand;

import java.io.IOException;
import java.util.ArrayList;

public class Pick implements ICommand {

    @Override
    public void execute(ClientHandler c, ArrayList<String> comm) throws IOException {
        if (comm.size() == 0) {
            c.executeCommand(ActionType.PICK, null);
        } else {
            c.sendMessage(JSONConverterStoC.normalMessage("The command pick doesn't want arguments!"));
        }
    }
}
