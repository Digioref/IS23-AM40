package it.polimi.ingsw.am40.Network;

import it.polimi.ingsw.am40.Controller.Lobby;
import it.polimi.ingsw.am40.Network.RMI.RMIServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GameServer implements Runnable {

    private String hostName = "localhost";
    private int portNumber = 1234;
    private ServerSocket serverSocket;
    private Lobby lobby;
    private static GameServer instance;
    private ExecutorService pool;
    private boolean close;
    private RMIServer rmiserver;
    private List<ClientHandler> clientHandlers;

    public GameServer() {
        pool = Executors.newCachedThreadPool();
        lobby = new Lobby();
        clientHandlers = new ArrayList<>();
        close = false;
    }

    public static synchronized GameServer get() {
        if (instance == null) {
            instance = new GameServer();
        }
        return instance;
    }

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
                ClientHandler c = new ClientHandler(clientSocket);
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

    public void connect(int port) throws IOException {
        rmiserver = new RMIServer();
        rmiserver.setLobby(lobby);
        Registry registry = LocateRegistry.createRegistry(5000);
        registry.rebind("RMIRegistry", rmiserver);
        System.out.println("GameServer bound on RMI registry");
        serverSocket = new ServerSocket(port);
        System.out.println("GameServer listening on port 5000");
    }

    public void close() throws IOException {
        close = true;
        serverSocket.close();
        pool.shutdown();
    }
}
