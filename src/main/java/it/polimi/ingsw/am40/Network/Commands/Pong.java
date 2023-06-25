package it.polimi.ingsw.am40.Network.Commands;

import it.polimi.ingsw.am40.Network.Handlers;
import it.polimi.ingsw.am40.Network.ICommand;

import java.io.IOException;
import java.util.ArrayList;

/**
 * The class used to handle the Pong message, sent by the client in response to a Ping message sent by the server
 */
public class Pong implements ICommand {

    /**
     * It handles the Pong message calling a specific method of the handler
     * @param c handler of the player
     * @param comm parameters of the command; in this case, no parameters
     * @throws IOException
     */
    @Override
    public void execute(Handlers c, ArrayList<String> comm) throws IOException {
        c.handlePong();
    }
}
