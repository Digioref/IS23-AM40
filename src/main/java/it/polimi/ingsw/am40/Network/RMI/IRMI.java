package it.polimi.ingsw.am40.Network.RMI;

import java.rmi.*;

public interface IRMI extends Remote {

    String login(String s) throws RemoteException;
    String setPlayers(int n) throws RemoteException;


}
