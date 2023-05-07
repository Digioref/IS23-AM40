package it.polimi.ingsw.am40.Network;

import it.polimi.ingsw.am40.Controller.Lobby;

import java.net.Socket;

public class RMIClientHandler extends Handlers implements Runnable{
    public RMIClientHandler() {
        logged = false;
        lobby = new Lobby();
        setLogphase(LoggingPhase.LOGGING);
    }
    @Override
    public void run() {

    }
}
