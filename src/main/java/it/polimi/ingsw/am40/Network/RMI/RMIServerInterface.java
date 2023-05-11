package it.polimi.ingsw.am40.Network.RMI;

import it.polimi.ingsw.am40.Client.RMIClientInterface;

import java.rmi.*;

public interface RMIServerInterface extends Remote {

    void login(String s, RMIClientInterface client) throws RemoteException;
    void setPlayers(String s, String n) throws RemoteException;
    void help(RMIClientInterface client, String s) throws RemoteException;
    void gameInfoRequest(String s, String command) throws RemoteException;
    void gameUpdate(String s, String command) throws RemoteException;
    void chat(String s, String command) throws RemoteException;
    void getChat(String s, String command) throws RemoteException;

}
