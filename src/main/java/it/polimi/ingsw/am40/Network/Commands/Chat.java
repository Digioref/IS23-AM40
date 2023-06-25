package it.polimi.ingsw.am40.Network.Commands;

import it.polimi.ingsw.am40.Network.Handlers;

import java.io.IOException;
import java.util.ArrayList;
import it.polimi.ingsw.am40.Network.*;

/**
 * The class representing the chat command, used to send a message to a specific player or in broadcast
 */
public class Chat implements ICommand {
    /**
     * It's the method that allows to execute the command chat, with the call of a specific method of the handler of the player
     * @param c handler of the player
     * @param comm the parameters of the command; in this case, the message and the receiver
     * @throws IOException
     */
    @Override
    public void execute(Handlers c, ArrayList<String> comm) throws IOException {
        if (comm.get(0).equals("all")) {
            c.chat(comm.get(1), comm.get(0));
        } else {
            c.chat(comm.get(1), comm.get(0));
        }
    }
}
