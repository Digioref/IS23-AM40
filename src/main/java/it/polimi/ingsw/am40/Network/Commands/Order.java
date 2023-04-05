package it.polimi.ingsw.am40.Network.Commands;

import it.polimi.ingsw.am40.Network.ActionType;
import it.polimi.ingsw.am40.Network.ClientHandler;
import it.polimi.ingsw.am40.Network.ICommand;

import java.io.IOException;
import java.util.ArrayList;

public class Order implements ICommand {
    @Override
    public void execute(ClientHandler c, ArrayList<String> comm) throws IOException {
            ArrayList<Integer> arr = new ArrayList<>();
            for (int i = 0; i < comm.size() ; i++) {
                if (Integer.parseInt(comm.get(i)) <= comm.size()) {
                    arr.add(Integer.parseInt(comm.get(i)));
                    c.executeCommand(ActionType.ORDER, arr);
                } else {
                    c.sendMessage("A number of the order is too high!");
                    return;
                }
            }
    }
}
