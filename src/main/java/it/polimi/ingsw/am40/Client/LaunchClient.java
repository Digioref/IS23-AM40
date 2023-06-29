package it.polimi.ingsw.am40.Client;

import it.polimi.ingsw.am40.CLI.CliView;
import it.polimi.ingsw.am40.CLI.View;
import it.polimi.ingsw.am40.GUI.ClientGUIController;
import it.polimi.ingsw.am40.GUI.LaunchGui;
import it.polimi.ingsw.am40.JSONConversion.ServerArgs;
import it.polimi.ingsw.am40.Network.LaunchServer;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * it launches the client
 */
public class LaunchClient {
    private static View view;
    private static Client client;
//    private static LaunchGui gui;
    /**
     * It is the main method, it starts the client
     * @param args arguments of the method
     */
    public static void main(String[] args) {
        String choice = interfaceSelection();
        if (choice.equals("GUI")) {
            view = new ClientGUIController();
        } else if (choice.equals("CLI")){
            view = new CliView();
        }
        if (view != null) {
            view.chooseConnection();
        }
    }

    /**
     * It manages the selection of the UI
     * @return it returns the choice of the user
     */
    public static String interfaceSelection() {
        String input = null;
        Scanner in = new Scanner(System.in);
        do {
            System.out.println("CLI[C] or GUI[G]?");
            try{
                input = in.nextLine();
            } catch (NoSuchElementException e) {
                input = "";
                break;
            }
            input = input.toUpperCase();
            if(input.equals("G") || input.equals(""))
                input = "GUI";
            else if(input.equals("C"))
                input = "CLI";
        }while(!input.equals("GUI") && !input.equals("CLI"));
        return input;
    }

    /**
     * It starts the connection to the type chosen by the user
     * @param choice the choice of the connection type made by the user
     * @param serverIp ip address of the server
     */
    public static void startConnection(String choice, String serverIp) {
        if (choice.equals("RMI")) {
            RMIClient rmiClient = null;
            try {
                if (serverIp.equals("localhost")) {
                    InetAddress ip = InetAddress.getLocalHost();
                    System.out.println( "Exposed Address: " + ip.getHostAddress());
                } else {
//                    System.setProperty("java.rmi.server.hostname", serverIp);
                    System.out.println("Exposed address: " + serverIp);
                }
                rmiClient = new RMIClient(serverIp);
                client = rmiClient;
                UnicastRemoteObject.exportObject(rmiClient, 0);
            } catch (RemoteException | UnknownHostException e) {
                System.out.println("Error in connecting to RMI");
                System.exit(0);
            }
            rmiClient.connect();
        } else {
            Socket socket;
            try {
//                String[] args = ServerArgs.ReadServerConfigFromJSON();
//                socket = new Socket(args[0], Integer.parseInt(args[1]));
                socket = new Socket(serverIp, 1234);
//                socket = new Socket(LaunchServer.ReadHostFromJSON(), LaunchServer.ReadPortFromJSON());
            } catch (IOException e) {
                System.out.println("Server not reachable. Closing...");
                LaunchClient.getView().quit("");
                return;
            }
            SocketClient socketClient = new SocketClient(socket);
            client = socketClient;
            socketClient.init();

        }
    }

    /**
     * It returns the UI chosen by the user
     * @return UI (CLI or GUI)
     */
    public static View getView() {
        return view;
    }

    /**
     * The client launched by the class
     * @return client
     */
    public static Client getClient() {
        return client;
    }
}
