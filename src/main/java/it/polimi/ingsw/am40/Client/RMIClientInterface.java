package it.polimi.ingsw.am40.Client;

import it.polimi.ingsw.am40.Network.Handlers;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Remote Interface implemented by the RMI client. It defines the methods that can be called remotely by the server
 */
public interface RMIClientInterface extends Remote {

    /**
     * It receives a message from the server
     * @param s message received
     * @throws RemoteException
     */
    void receive(String s) throws RemoteException;

    /**
     * It receives the nickname of the player
     * @param s nickname of the player
     * @throws RemoteException
     */
    void receiveNickname(String s) throws RemoteException;

    /**
     * It receives the chat
     * @param s string which contains the chat, it's a JSON string
     * @throws RemoteException
     */
    void receiveChat(String s) throws RemoteException;
}
