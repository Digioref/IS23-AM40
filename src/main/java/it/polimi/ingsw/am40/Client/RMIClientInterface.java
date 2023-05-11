package it.polimi.ingsw.am40.Client;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIClientInterface extends Remote {
    void receive(String s) throws RemoteException;
    void receiveNickname(String s) throws RemoteException;
    void receiveChat(String s) throws RemoteException;
}
