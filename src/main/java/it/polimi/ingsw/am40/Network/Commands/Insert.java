package it.polimi.ingsw.am40.Network.Commands;

import it.polimi.ingsw.am40.Network.ActionType;
import it.polimi.ingsw.am40.Network.ClientHandler;
import it.polimi.ingsw.am40.Network.ICommand;

import java.io.IOException;
import java.util.ArrayList;

public class Insert implements ICommand {
    @Override
    public void execute(ClientHandler c, ArrayList<String> comm) throws IOException {
        if (comm.size() != 1) {
            c.sendMessage("Incomplete command");
        }
        else {
            ArrayList<Integer> arr = new ArrayList<>();
            arr.add(Integer.parseInt(comm.get(0)));
            c.executeCommand(ActionType.INSERT, arr);
        }
    }
}
