package it.polimi.ingsw.am40.Network;

import java.io.IOException;
import java.util.ArrayList;

public interface ICommand {
    public void execute(Handlers c, ArrayList<String> comm) throws IOException;
}
