package it.polimi.ingsw.am40.Client;

import it.polimi.ingsw.am40.Network.RMI.RMIServerInterface;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMIClient implements RMIClientInterface {
    private BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
    private RMIServerInterface stub;
    private Thread rmiThread;
    private boolean stop;
    private String nickname;

    public RMIClient() {
        stop = false;
    }

    public void connect() {
        Registry registry;
        try {
            registry = LocateRegistry.getRegistry("localhost",5000);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        try {
            stub = (RMIServerInterface) registry.lookup("RMIRegistry");
        } catch (RemoteException | NotBoundException e) {
            throw new RuntimeException(e);
        }
        rmiThread = new Thread(() -> {
            do {
                String line;
                try {
                    line = stdIn.readLine();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                parseMessage(line);
            } while(!stop);
        });
        rmiThread.start();
    }

    private void parseMessage(String line) {
        String[] command = line.split("\\s");
        switch (command[0]) {
            case "login":
                try {
                    nickname = command[1];
                    SocketClient.parseMessage(stub.login(command[1], this));
                } catch (RemoteException | ParseException e) {
                    throw new RuntimeException(e);
                };
            case "setplayers":
                try {
                    SocketClient.parseMessage(stub.setPlayers(nickname, Integer.parseInt(command[1])));
                } catch (RemoteException | ParseException e) {
                    throw new RuntimeException(e);
                };
        }
    }

}
