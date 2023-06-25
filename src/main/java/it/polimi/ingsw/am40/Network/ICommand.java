package it.polimi.ingsw.am40.Network;

import java.io.IOException;
import java.util.ArrayList;

/**
 * The interface implemented by each command
 */
public interface ICommand {

    /**
     * It executes the command, performing an action on the game
     * @param c handler of the player
     * @param comm parameters of the command
     * @throws IOException
     */
    void execute(Handlers c, ArrayList<String> comm) throws IOException;
}
