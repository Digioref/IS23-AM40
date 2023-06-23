package it.polimi.ingsw.am40.Network;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Objects;
import java.util.Scanner;

import java.net.BindException;


public class LaunchServer {
    private static String hostName;
    private static String IPAddress;
    private static int portNumber;

    /**
     * TODO
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
//        System.out.println("Server started!");
        if (args.length == 2) {
            hostName = args[0];
            portNumber = Integer.parseInt(args[1]);
        }
        else {
            hostName = ReadHostFromJSON();
            portNumber = ReadPortFromJSON();
        }
        hostName = InetAddress.getLocalHost().getHostName();
        InetAddress ip = InetAddress.getLocalHost();
        IPAddress = ip.getHostAddress();
        System.out.println("HostName: " + hostName);
        System.out.println( "Exposed Address: " + IPAddress);
//        setServerHostname();
        Scanner scanner = new Scanner(System.in);
        GameServer server = null;
        boolean retry;
        do{
            try{
                server = GameServer.get();
                server.connect(portNumber, IPAddress, hostName);
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
//            System.out.println("qui");
            server.close();
        }
    }


    /**
     * TODO
     * @return
     */
    public static int ReadPortFromJSON() {
//        JSONParser jsonParser = new JSONParser();
//        FileReader reader;
//        try {
////            ClassLoader classLoader = LaunchServer.class.getClassLoader();
////            File file = new File(Objects.requireNonNull(classLoader.getResource("Server.json")).getFile());
//            File file = new File("resources/Server.json");
//            reader = new FileReader(file);
//            JSONObject obj = (JSONObject) jsonParser.parse(reader);
//            JSONObject server = (JSONObject) obj.get("Server");
//            return Integer.parseInt(server.get("PortNumber").toString());
//        } catch (IOException | ParseException e) {
//            e.printStackTrace();
//        }
        return 0;
    }

    /**
     * TODO
     * @return
     */
    public static String ReadHostFromJSON() {
//        JSONParser jsonParser = new JSONParser();
//        FileReader reader;
//        try {
////            ClassLoader classLoader = LaunchServer.class.getClassLoader();
////            File file = new File(Objects.requireNonNull(classLoader.getResource("C:/Users/digio/OneDrive/Documents/Francesco/Universita'/Terzo Anno/Progetto di Ingegneria del SoftwareIS23-AM40 2/IS23-AM40/src/main/resources/Server.json")).getFile());
//               File file = new File("resources/Server.json");
//            reader = new FileReader(file);
//            JSONObject obj = (JSONObject) jsonParser.parse(reader);
//            JSONObject server = (JSONObject) obj.get("Server");
//            return server.get("HostName").toString();
//        } catch (IOException | ParseException e) {
//            e.printStackTrace();
//        }
        return null;
    }

}
