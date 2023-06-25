package it.polimi.ingsw.am40.Network.RMI;

import it.polimi.ingsw.am40.Client.RMIClientInterface;

import java.rmi.*;

/**
 * It's the remote interface implemented by the rmi server. It defines the methods that can be called by the client using RMI
 */
public interface RMIServerInterface extends Remote {
    /**
     * The method that the player uses to log in
     * @param s nickname of the player
     * @param client remote client
     * @throws RemoteException
     */
    void login(String s, RMIClientInterface client) throws RemoteException;

    /**
     * It sets the number of player
     * @param s nickname of the player
     * @param n number of players in string format
     * @throws RemoteException
     */
    void setPlayers(String s, String n) throws RemoteException;

    /**
     * It sends to the client a list of command, with the type of parameters requested by each command
     * @param client remote client
     * @param s nickname of the player
     * @throws RemoteException
     */
    void help(RMIClientInterface client, String s) throws RemoteException;

    /**
     * It is used to request specific information about the game, such as the bookshelves, the current players, etc...
     * @param s nickname of the player
     * @param command the command representing the specific request
     * @throws RemoteException
     */
    void gameInfoRequest(String s, String command) throws RemoteException;

    /**
     * It is used to perform an action that changes the game state, such as insert, order, pick, etc...
     * @param s nickname of the player
     * @param command command that represents the action
     * @throws RemoteException
     */
    void gameUpdate(String s, String command) throws RemoteException;

    /**
     * It is used to send a message to other players
     * @param s nickname of the player who sends the message
     * @param command the command to send the message
     * @throws RemoteException
     */
    void chat(String s, String command) throws RemoteException;

    /**
     * It is used to get the chat from the game
     * @param s nickname of the player requesting the chat
     * @param command the command to get the chat
     * @throws RemoteException
     */
    void getChat(String s, String command) throws RemoteException;

    /**
     * It is used to close the handler
     * @param s nickname of the player
     * @throws RemoteException
     */
    void close(String s) throws RemoteException;

    /**
     * It is used to receive and handle the Pong message
     * @param client remote client that has sent the Pong message
     * @throws RemoteException
     */
    void receivePong(RMIClientInterface client) throws RemoteException;

    /**
     * It is used to initialize the RMI connection between the remote client and its handler
     * @param client remote client
     * @throws RemoteException
     */
    void init(RMIClientInterface client) throws RemoteException;

}
