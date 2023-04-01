package it.polimi.ingsw.am40.Network.Commands;

import it.polimi.ingsw.am40.Network.ActionType;
import it.polimi.ingsw.am40.Network.ClientHandler;
import it.polimi.ingsw.am40.Network.ICommand;

import java.io.IOException;
import java.util.ArrayList;

public class Order implements ICommand {
    @Override
    public void execute(ClientHandler c, String[] comm) throws IOException {
        if (comm.length == 1) {
            c.sendMessage("Incomplete command");
        }
        else {
            ArrayList<Integer> arr = new ArrayList<>();
            for (int i = 1; i < comm.length ; i++) {
                arr.add(Integer.parseInt(comm[i]));
            }
            c.executeComannd(ActionType.ORDER, arr);
        }
    }
}
