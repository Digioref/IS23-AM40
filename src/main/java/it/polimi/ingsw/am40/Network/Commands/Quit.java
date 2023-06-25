package it.polimi.ingsw.am40.Network.Commands;

import it.polimi.ingsw.am40.Network.Handlers;
import it.polimi.ingsw.am40.Network.ICommand;

import java.io.IOException;
import java.util.ArrayList;

/**
 * The class representing the Quit command, used when the user wants to quit from the game
 */
public class Quit implements ICommand {

    /**
     * It executes the command calling a method of the handler
     * @param c handler of the player
     * @param comm parameters of the command; in this case, no parameters
     * @throws IOException
     */
    @Override
    public void execute(Handlers c, ArrayList<String> comm) throws IOException {
        c.close();
    }
}
