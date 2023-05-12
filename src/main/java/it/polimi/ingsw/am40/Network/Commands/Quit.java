package it.polimi.ingsw.am40.Network.Commands;

import it.polimi.ingsw.am40.Network.Handlers;
import it.polimi.ingsw.am40.Network.ICommand;

import java.io.IOException;
import java.util.ArrayList;

public class Quit implements ICommand {

    @Override
    public void execute(Handlers c, ArrayList<String> comm) throws IOException {
        c.close();
    }
}
