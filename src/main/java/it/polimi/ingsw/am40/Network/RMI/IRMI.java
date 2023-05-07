package it.polimi.ingsw.am40.Network.RMI;

import java.rmi.*;
import java.util.ArrayList;

public interface IRMI extends Remote {

    String login(String s) throws RemoteException;
    String setPlayers(int n) throws RemoteException;
    String help() throws RemoteException;
    String insert(int c) throws RemoteException;
    String order(ArrayList<Integer> n) throws RemoteException;
    String pick() throws RemoteException;
    String quit() throws RemoteException;
    String remove() throws RemoteException;
    String select(ArrayList<Integer> n) throws RemoteException;
    String getBoard() throws RemoteException;
    String getBook() throws RemoteException;
    String getBookAll() throws RemoteException;
    String getCommGoals() throws RemoteException;
    String getCurrent() throws RemoteException;
    String getCurScore() throws RemoteException;
    String getHiddenScore() throws RemoteException;
    String getPersGoal() throws RemoteException;
    String getPlayers() throws RemoteException;
}
