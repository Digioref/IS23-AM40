package it.polimi.ingsw.am40.Network.Commands;

import it.polimi.ingsw.am40.Network.ClientHandler;
import it.polimi.ingsw.am40.Network.ICommand;

import java.io.IOException;
import java.util.ArrayList;

public class SetPlayers implements ICommand {

    @Override
    public void execute(ClientHandler c, ArrayList<String> comm) throws IOException {
        c.setNumPlayers(Integer.parseInt(comm.get(0)));
        c.getLobby().addQueue(c);
        c.sendMessage("You are logged in");
    }
}
