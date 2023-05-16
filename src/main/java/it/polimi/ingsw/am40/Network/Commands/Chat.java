package it.polimi.ingsw.am40.Network.Commands;

import it.polimi.ingsw.am40.Network.Handlers;

import java.io.IOException;
import java.util.ArrayList;
import it.polimi.ingsw.am40.Network.*;

public class Chat implements ICommand {
    @Override
    public void execute(Handlers c, ArrayList<String> comm) throws IOException {
        if (comm.get(0).equals("all")) {
            c.chat(comm.get(1), comm.get(0));
        } else {
            c.chat(comm.get(1), comm.get(0));
        }
    }
}
