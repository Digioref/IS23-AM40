package it.polimi.ingsw.am40.Network;

import java.io.IOException;
import java.util.ArrayList;

/**
 * TODO
 * ASSOLUTAMENTE
 */
public interface ICommand {

    /**
     * todo
     * @param c
     * @param comm
     * @throws IOException
     */
    public void execute(Handlers c, ArrayList<String> comm) throws IOException;
}
