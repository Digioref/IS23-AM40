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
    private static int portNumber;

    public static void main(String[] args) throws IOException {
//        System.out.println("Server started!");
        setServerHostname();

//        if (args.length == 2) {
//            hostName = args[0];
//            portNumber = Integer.parseInt(args[1]);
//        }
//        else {
//            hostName = ReadHostFromJSON();
//            portNumber = ReadPortFromJSON();
//        }

        Scanner scanner = new Scanner(System.in);
        GameServer server = null;
        boolean retry;
        do{
            try{
                server = GameServer.get();
                server.connect(portNumber, hostName);
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



    public static int ReadPortFromJSON() {
        JSONParser jsonParser = new JSONParser();
        FileReader reader;
        try {
//            ClassLoader classLoader = LaunchServer.class.getClassLoader();
//            File file = new File(Objects.requireNonNull(classLoader.getResource("Server.json")).getFile());
            File file = new File("resources/Server.json");
            reader = new FileReader(file);
            JSONObject obj = (JSONObject) jsonParser.parse(reader);
            JSONObject server = (JSONObject) obj.get("Server");
            return Integer.parseInt(server.get("PortNumber").toString());
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static String ReadHostFromJSON() {
        JSONParser jsonParser = new JSONParser();
        FileReader reader;
        try {
//            ClassLoader classLoader = LaunchServer.class.getClassLoader();
//            File file = new File(Objects.requireNonNull(classLoader.getResource("C:/Users/digio/OneDrive/Documents/Francesco/Universita'/Terzo Anno/Progetto di Ingegneria del SoftwareIS23-AM40 2/IS23-AM40/src/main/resources/Server.json")).getFile());
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

    private static void setServerHostname() {

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
//        System.setProperty("java.rmi.server.hostname",localIP);
        hostName = localIP;
        System.out.println("Exposed address: " + localIP);
    }

//    /**
//     * Return che correct local ip address
//     * It is necessary because the rmi system property java.rmi.server.hostname it's automatically set to InetAddress.getLocalHost().getHostAddress() which can be an incorrect address in same cases (eg: in presence of Wireshark or Virtualbox it seems to take the address of their interfaces, which are not the ones used to communicate in the lan)
//     * @return the correct ip address, null in case of error
//     */
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
