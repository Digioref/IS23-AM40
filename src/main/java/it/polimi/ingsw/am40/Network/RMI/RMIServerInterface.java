package it.polimi.ingsw.am40.Network.RMI;

import it.polimi.ingsw.am40.Client.RMIClientInterface;

import java.rmi.*;

public interface RMIServerInterface extends Remote {

    void login(String s, RMIClientInterface client) throws RemoteException;
    void setPlayers(String s, int n) throws RemoteException;
    void help(String s) throws RemoteException;
//    String insert(int c) throws RemoteException;
//    String order(ArrayList<Integer> n) throws RemoteException;
//    String pick() throws RemoteException;
//    String quit() throws RemoteException;
//    String remove() throws RemoteException;
//    String select(ArrayList<Integer> n) throws RemoteException;
//    String getBoard() throws RemoteException;
//    String getBook() throws RemoteException;
//    String getBookAll() throws RemoteException;
//    String getCommGoals() throws RemoteException;
//    String getCurrent() throws RemoteException;
//    String getCurScore() throws RemoteException;
//    String getHiddenScore() throws RemoteException;
//    String getPersGoal() throws RemoteException;
//    String getPlayers() throws RemoteException;
}
