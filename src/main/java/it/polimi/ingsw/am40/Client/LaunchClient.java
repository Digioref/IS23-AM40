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
 * todo
 */
public class LaunchClient {
    private static View view;
    private static Client client;
//    private static LaunchGui gui;
    /**
     * todo
     * @param args
     */
    public static void main(String[] args) {
        String choice = interfaceSelection();
        if (choice.equals("GUI")) {
            view = new ClientGUIController();
        } else if (choice.equals("CLI")){
            view = new CliView();
        }
        view.chooseConnection();
    }

    /**
     * todo
     * @return
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
     * todo
     * @param choice
     * @param serverIp
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
                throw new RuntimeException(e);
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
                return;
//                throw new RuntimeException(e);
            }
            SocketClient socketClient = new SocketClient(socket);
            client = socketClient;
            socketClient.init();

        }
    }

    /**
     * todo
     * @return
     */
    public static View getView() {
        return view;
    }

    /**
     * todo
     * @return
     */
    public static Client getClient() {
        return client;
    }
}
