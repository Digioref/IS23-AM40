package it.polimi.ingsw.am40.Client;

import it.polimi.ingsw.am40.CLI.CliView;
import it.polimi.ingsw.am40.CLI.View;
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

public class LaunchClient {
    private static View view;
//    private static LaunchGui gui;
    public static void main(String[] args) {
        String choice = interfaceSelection();
        if (choice.equals("GUI")) {
            LaunchGui.main(args);
//            gui = new LaunchGui();
//            gui.main(args);
        } else if (choice.equals("CLI")){
            view = new CliView();
            view.chooseConnection();
        }
        

/*        System.out.println("Client started!");

        ExecutorService executor = Executors.newCachedThreadPool();
        System.out.println("Do you want to play with RMI or SOCKET?");
        String userin;
        do {
            try {
                userin = stdIn.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (!userin.equalsIgnoreCase("RMI") && !userin.equalsIgnoreCase("SOCKET")) {
                System.out.println("Wrong choice, you must type RMI or Socket! Try again");
            }
        } while (!userin.equalsIgnoreCase("RMI") && !userin.equalsIgnoreCase("SOCKET"));
        switch (userin.toUpperCase()) {
            case "RMI":
                System.out.println("You have chosen RMI!");
                try {
                    Registry registry = LocateRegistry.getRegistry("127.0.0.1",0);
                    RMIServerInterface stub = (RMIServerInterface) registry.lookup("RMIRegistry");
                    rmiAdapter = new RMIClient(stub);
                    executor.submit(rmiAdapter);

                } catch (RemoteException | NotBoundException e) {
                    throw new RuntimeException(e);
                }
            case "SOCKET":
                System.out.println("You have chosen SOCKET!");
                try {
                    Socket socket = new Socket(hostName, portNumber);
                    PrintWriter out = new PrintWriter(socket.getOutputStream());
                    socketClient = new SocketClient(socket);
                    executor.submit(socketClient);
                    String userInput;
                    while (true) {
                        userInput = stdIn.readLine();
                        JSONConverterCtoS jconv = new JSONConverterCtoS();
                        jconv.toJSON(userInput);
                        out.println(jconv.toString());
                        out.flush();
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
        }
*/
    }

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
            if(input.equals("G"))
                input = "GUI";
            else if(input.equals("C"))
                input = "CLI";
        }while(!input.equals("GUI") && !input.equals("CLI"));
        return input;
    }

    public static void startConnection(String choice, String serverIp) {
        if (choice.equals("RMI")) {
            RMIClient rmiClient = null;
            try {
                if (serverIp.equals("localhost")) {
                    setRMIHostname();
                } else {
//                    System.setProperty("java.rmi.server.hostname", serverIp);
                    System.out.println("Exposed address: " + serverIp);
                }
                rmiClient = new RMIClient(serverIp);
                UnicastRemoteObject.exportObject(rmiClient, 0);
            } catch (RemoteException e) {
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
            socketClient.init();

        }
    }

    public static View getView() {
        return view;
    }
    private static void setRMIHostname() {

        String localIP = getCorrectLocalIP();
        if(localIP == null)
        {
            Scanner scanner = new Scanner(System.in);
            InetAddress inetAddress;
            try {
                inetAddress = InetAddress.getLocalHost();
                System.out.println("Java host address: " + inetAddress.getHostAddress());
                System.out.println("Unable to verify the correct local ip address, insert Y if it is correct or otherwise insert the correct local ip:");
                localIP = scanner.nextLine();
                if(localIP.equalsIgnoreCase("Y"))
                    localIP = inetAddress.getHostAddress();
            } catch (UnknownHostException e) {
                System.out.println("Unable to find the local ip address, please provide it");
                localIP = scanner.nextLine();
            }


        }
        System.setProperty("java.rmi.server.hostname",localIP);
        System.out.println("Exposed address: " + localIP);
    }

    private static String getCorrectLocalIP()
    {
        String ip;
        try(final DatagramSocket socket = new DatagramSocket()){
            socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
            ip = socket.getLocalAddress().getHostAddress();
            if(ip.equals("0.0.0.0"))
                return null;
            return ip;
        }catch(Exception e)
        {
            return null;
        }
    }
}
