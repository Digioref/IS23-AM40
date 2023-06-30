package it.polimi.ingsw.am40.Network;

import it.polimi.ingsw.am40.Controller.Lobby;
import it.polimi.ingsw.am40.JSONConversion.JSONConverterStoC;
import it.polimi.ingsw.am40.Network.RMI.RMIServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.AlreadyBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * The class representing the server of the game
 */
public class GameServer implements Runnable {

    private int portNumber;
    private ServerSocket serverSocket;
    private final Lobby lobby;
    private static GameServer instance;
    private final ExecutorService pool;
    private boolean close;
    private RMIServer rmiserver;
    private final List<ClientHandler> clientHandlers;

    /**
     * Constructor of the server
     */
    public GameServer() {
        pool = Executors.newCachedThreadPool();
        lobby = new Lobby();
        clientHandlers = new ArrayList<>();
        close = false;
    }

    /**
     * It returns the instance of the server
     * @return the instance of the server, if null it creates a new one
     */
    public static synchronized GameServer get() {
        if (instance == null) {
            instance = new GameServer();
        }
        return instance;
    }

    /**
     * The run method because the game server is runnable
     */
    @Override
    public void run() {
        System.out.println("GameServer started on: " + portNumber);
        pool.submit(lobby);
        while (!close) {
            System.out.println("Accepting...");
            Socket clientSocket;
            try {
                clientSocket = serverSocket.accept();
                System.out.println("Accepted!");
                ClientHandler c = new ClientHandler(clientSocket, instance);
                clientHandlers.add(c);
                c.setLobby(lobby);
                c.setLogphase(LoggingPhase.LOGGING);
                pool.submit(c);
//                lobby.addQueue(c);
            } catch (IOException e) {
                System.out.println("Error in accepting client");
                break;
            }
        }
    }

    /**
     * It connects the server to the provided ip address and the number of the port, setting also what is necessary for RMI communication
     * @param port the number of the port
     * @param IPAddress the ip address of the server
     * @throws IOException
     */
    public void connect(int port, String IPAddress) throws IOException {
        portNumber = port;
        System.setProperty("java.rmi.server.hostname", IPAddress);
        rmiserver = new RMIServer();
        rmiserver.setLobby(lobby);
        Registry registry = LocateRegistry.createRegistry(5000);
        try {
            registry.bind("RMIRegistry", rmiserver);
        } catch (AlreadyBoundException e) {
            System.out.println("Bind of the registry failed");
            close();
        }
        System.out.println("GameServer bound on RMI registry");
        serverSocket = new ServerSocket(port);
        System.out.println("GameServer listening on port " + portNumber);
    }

    /**
     * It closes the game server
     * @throws IOException
     */
    public void close() throws IOException {
        close = true;
        serverSocket.close();
        pool.shutdown();
        for (ClientHandler h: clientHandlers) {
            h.sendMessage(JSONConverterStoC.normalMessage("Quit"));
            h.close();
        }
        for (String s: rmiserver.getClientHandlers().keySet()) {
            rmiserver.getClientHandlers().get(s).sendMessage(JSONConverterStoC.normalMessage("Quit"));
        }
    }

    /**
     * It removes the provided handler because it disconnected
     * @param clientHandler client handler
     */
    public void shutdownHandler(ClientHandler clientHandler) {
        clientHandlers.remove(clientHandler);
    }


}
