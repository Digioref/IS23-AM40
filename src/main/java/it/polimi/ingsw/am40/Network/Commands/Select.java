package it.polimi.ingsw.am40.Network.Commands;

import it.polimi.ingsw.am40.Network.ActionType;
import it.polimi.ingsw.am40.Network.ClientHandler;
import it.polimi.ingsw.am40.Network.ICommand;

import java.io.IOException;
import java.util.ArrayList;

public class Select implements ICommand {

    @Override
    public void execute(ClientHandler c, ArrayList<String> comm) throws IOException {
        if (comm.size() != 2) {
            c.sendMessage("Incomplete command");
        }
        else {
//            System.out.println(("qui"));
            int x = Integer.parseInt(comm.get(0));
            int y = Integer.parseInt(comm.get(1));
            ArrayList<Integer> arr = new ArrayList<>();
            arr.add(x);
            arr.add(y);
            c.executeCommand(ActionType.SELECT, arr);
        }
    }
}
