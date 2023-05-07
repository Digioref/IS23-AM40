package it.polimi.ingsw.am40.Client;

import it.polimi.ingsw.am40.CLI.CliView;
import it.polimi.ingsw.am40.CLI.View;
import it.polimi.ingsw.am40.Network.LaunchServer;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class LaunchClient {
    private static View view;
    public static void main(String[] args) {
        String choice = interfaceSelection();
        if (choice.equals("GUI")) {

        } else {
            view = new CliView();
        }
        view.chooseConnection();

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
                    IRMI stub = (IRMI) registry.lookup("RMIRegistry");
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
        String input;
        Scanner in = new Scanner(System.in);
        do {
            System.out.println("CLI[C] or GUI[G]?");
            input = in.nextLine();
            input = input.toUpperCase();
            if(input.equals("G"))
                input = "GUI";
            else if(input.equals("C"))
                input = "CLI";
        }while(!input.equals("GUI") && !input.equals("CLI"));
        return input;
    }

    public static void startConnection(String choice) {
        if (choice.equals("RMI")) {
            System.setProperty("java.rmi.server.hostname", "localhost");
            RMIClient rmiClient = new RMIClient();
            rmiClient.connect();
        } else {
            Socket socket;
            try {
                socket = new Socket(LaunchServer.ReadHostFromJSON(), LaunchServer.ReadPortFromJSON());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            SocketClient socketClient = new SocketClient(socket);
            socketClient.init();

        }
    }

    public static View getView() {
        return view;
    }
}
