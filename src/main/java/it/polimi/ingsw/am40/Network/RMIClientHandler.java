package it.polimi.ingsw.am40.Network;

import it.polimi.ingsw.am40.Client.RMIClientInterface;
import it.polimi.ingsw.am40.Controller.Lobby;

import java.io.IOException;
import java.net.Socket;

public class RMIClientHandler extends Handlers {
    private RMIClientInterface rmiClient;
    public RMIClientHandler() {
        logged = false;
        lobby = new Lobby();
        setLogphase(LoggingPhase.LOGGING);
    }

    @Override
    public void sendMessage(String s) throws IOException {

    }

    public void setRmiClient(RMIClientInterface rmiClient) {
        this.rmiClient = rmiClient;
    }
}
