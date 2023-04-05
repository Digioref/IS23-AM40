package it.polimi.ingsw.am40.Network.Commands;

import it.polimi.ingsw.am40.Network.ClientHandler;
import it.polimi.ingsw.am40.Network.ICommand;

import java.io.IOException;

public class Quit implements ICommand {

    @Override
    public void execute(ClientHandler c, String[] comm) throws IOException {
        c.sendMessage("Bye Bye");
    }
}
