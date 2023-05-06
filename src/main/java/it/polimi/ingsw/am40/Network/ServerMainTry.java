package it.polimi.ingsw.am40.Network;

import it.polimi.ingsw.am40.Controller.Lobby;
import it.polimi.ingsw.am40.Network.RMI.IRMI;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class ServerMainTry implements IRMI {
    private static String hostName;
    private static int portNumber;
    private static ServerSocket serverSocket;
    private static Lobby lobby;
    public static void main(String[] args) {
        System.out.println("Server started!");
        if (args.length == 2) {
            hostName = args[0];
            portNumber = Integer.parseInt(args[1]);
        }
        else {
            hostName = ReadHostFromJSON();
            portNumber = ReadPortFromJSON();
        }
        try {
            serverSocket = new ServerSocket(portNumber);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        lobby = new Lobby();
        ExecutorService executor = Executors.newCachedThreadPool();
        executor.submit(lobby);
        ServerMainTry obj = new ServerMainTry();
        try {
            IRMI stub = (IRMI) UnicastRemoteObject.exportObject(obj, portNumber);
            Registry registry = LocateRegistry.createRegistry(portNumber);
            registry.bind("RMIRegistry", stub);
        } catch (RemoteException | AlreadyBoundException e) {
            throw new RuntimeException(e);
        }
        while (true) {
            System.out.println("Accepting...");
            Socket clientSocket;
            try {
                clientSocket = serverSocket.accept();
                System.out.println("Accepted!");
                ClientHandler c = new ClientHandler(clientSocket);
//                PrintWriter out = new PrintWriter(clientSocket.getOutputStream());
//                out.println("Give nickname: ");
//                out.flush();
//                Scanner in = new Scanner(clientSocket.getInputStream());
//                c.setNickname(in.nextLine());
                c.setLobby(lobby);
                executor.submit(c);
//                lobby.addQueue(c);
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
        executor.shutdown();
    }

    private static int ReadPortFromJSON() {
        JSONParser jsonParser = new JSONParser();
        FileReader reader;
        try {
            ClassLoader classLoader = ServerMainTry.class.getClassLoader();
            File file = new File(Objects.requireNonNull(classLoader.getResource("Server.json")).getFile());
            reader = new FileReader(file);
            JSONObject obj = (JSONObject) jsonParser.parse(reader);
            JSONObject server = (JSONObject) obj.get("Server");
            return Integer.parseInt(server.get("PortNumber").toString());
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private static String ReadHostFromJSON() {
        JSONParser jsonParser = new JSONParser();
        FileReader reader;
        try {
//            ClassLoader classLoader = ServerMainTry.class.getClassLoader();
//            File file = new File(Objects.requireNonNull(classLoader.getResource("Server.json")).getFile());
            File file = new File("resources/Server.json");
            reader = new FileReader(file);
            JSONObject obj = (JSONObject) jsonParser.parse(reader);
            JSONObject server = (JSONObject) obj.get("Server");
            return server.get("HostName").toString();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String login(String s) {
        return "OK";
    }
}
