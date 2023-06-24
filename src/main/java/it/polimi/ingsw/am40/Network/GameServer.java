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
 * todo
 */
public class GameServer implements Runnable {

    private String hostName;
    private int portNumber;
    private String IPAddress;
    private ServerSocket serverSocket;
    private Lobby lobby;
    private static GameServer instance;
    private ExecutorService pool;
    private boolean close;
    private RMIServer rmiserver;
    private List<ClientHandler> clientHandlers;

    /**
     * todo
     */
    public GameServer() {
        pool = Executors.newCachedThreadPool();
        lobby = new Lobby();
        clientHandlers = new ArrayList<>();
        close = false;
    }

    /**
     * @return the whole istance, if null creates a new one
     */
    public static synchronized GameServer get() {
        if (instance == null) {
            instance = new GameServer();
        }
        return instance;
    }

    /**
     * todo
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
//                PrintWriter out = new PrintWriter(clientSocket.getOutputStream());
//                out.println("Give nickname: ");
//                out.flush();
//                Scanner in = new Scanner(clientSocket.getInputStream());
//                c.setNickname(in.nextLine());
                c.setLobby(lobby);
                c.setLogphase(LoggingPhase.LOGGING);
                pool.submit(c);
//                lobby.addQueue(c);
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }

    /**
     * todo
     * @param port
     * @param IPAddress
     * @param hostName
     * @throws IOException
     */
    public void connect(int port, String IPAddress, String hostName) throws IOException {
        portNumber = port;
        this.hostName = hostName;
        this.IPAddress = IPAddress;
        System.setProperty("java.rmi.server.hostname", IPAddress);
        rmiserver = new RMIServer();
        rmiserver.setLobby(lobby);
        Registry registry = LocateRegistry.createRegistry(5000);
        try {
            registry.bind("RMIRegistry", rmiserver);
        } catch (AlreadyBoundException e) {
            throw new RuntimeException(e);
        }
        System.out.println("GameServer bound on RMI registry");
        serverSocket = new ServerSocket(port);
        System.out.println("GameServer listening on port " + portNumber);
    }

    /**
     * todo
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
     * todo
     * @param clientHandler
     */
    public void shutdownHandler(ClientHandler clientHandler) {
        clientHandlers.remove(clientHandler);
    }

    /**
     * todo
     * @return
     */
    public List<ClientHandler> getClientHandlers() {
        return clientHandlers;
    }
}
