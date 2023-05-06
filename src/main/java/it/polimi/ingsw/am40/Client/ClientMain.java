package it.polimi.ingsw.am40.Client;

import it.polimi.ingsw.am40.JSONConversion.JSONConverterCtoS;
import it.polimi.ingsw.am40.Network.RMI.IRMI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientMain {
    final static String hostName = "localhost";
    final static int portNumber = 1234;
    static ClientAdapter clientAdapter = null;
    static RMIAdapter rmiAdapter = null;
    public static void main(String[] args) {
        System.out.println("Client started!");
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
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
                    Registry registry = LocateRegistry.getRegistry(hostName, portNumber);
                    IRMI stub = (IRMI) registry.lookup("RMIRegistry");
                    rmiAdapter = new RMIAdapter(stub);
                    executor.submit(rmiAdapter);

                } catch (RemoteException | NotBoundException e) {
                    throw new RuntimeException(e);
                }
            case "SOCKET":
                System.out.println("You have chosen RMI!");
                try {
                    Socket socket = new Socket(hostName, portNumber);
                    PrintWriter out = new PrintWriter(socket.getOutputStream());
                    clientAdapter = new ClientAdapter(socket);
                    executor.submit(clientAdapter);
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

    }
}
