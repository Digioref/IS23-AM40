package it.polimi.ingsw.am40.Network;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Scanner;

import java.net.BindException;

/**
 * The class that launches the server
 */
public class LaunchServer {

    /**
     * The main method that starts the server
     * @param args arguments
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        String hostName;
        int portNumber = Integer.parseInt(args[1]);

        hostName = InetAddress.getLocalHost().getHostName();
        InetAddress ip = InetAddress.getLocalHost();
        String IPAddress = ip.getHostAddress();
        System.out.println("HostName: " + hostName);
        System.out.println( "Exposed Address: " + IPAddress);
        Scanner scanner = new Scanner(System.in);
        GameServer server = null;
        boolean retry;
        do{
            try{
                server = GameServer.get();
                server.connect(portNumber, IPAddress);
                retry = false;
            }catch(BindException e)
            {
                System.out.println("There is a server instance already running, please close it and retry.");
                System.out.println("Do you want to retry?[Y/N]");
                String choice = scanner.nextLine();
                retry = !choice.equalsIgnoreCase("N");
            }
        }while(retry);

        try {
            server.run();
        } finally {
            server.close();
        }
    }


}
