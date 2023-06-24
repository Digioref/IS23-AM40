package it.polimi.ingsw.am40.Client;

import it.polimi.ingsw.am40.Network.Handlers;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * todo
 */
public interface RMIClientInterface extends Remote {

    /**
     * todo
     * @param s
     * @throws RemoteException
     */
    void receive(String s) throws RemoteException;

    /**
     * todo
     * @param s
     * @throws RemoteException
     */
    void receiveNickname(String s) throws RemoteException;

    /**
     * todo
     * @param s
     * @throws RemoteException
     */
    void receiveChat(String s) throws RemoteException;
}
