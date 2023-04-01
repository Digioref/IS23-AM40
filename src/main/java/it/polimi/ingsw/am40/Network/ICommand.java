package it.polimi.ingsw.am40.Network;

import java.io.IOException;

public interface ICommand {
    public void execute(ClientHandler c, String[] comm) throws IOException;
}
