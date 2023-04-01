package it.polimi.ingsw.am40.Network.Commands;

import it.polimi.ingsw.am40.Network.ClientHandler;
import it.polimi.ingsw.am40.Network.ICommand;

import java.io.IOException;

public class Help implements ICommand {

    @Override
    public void execute(ClientHandler c) throws IOException {
        for (String s: c.getMessAd().getCommands().keySet()) {
            c.sendMessage("- " + s);
        }
    }
}
