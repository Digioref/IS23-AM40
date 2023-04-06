package it.polimi.ingsw.am40.Client;

import it.polimi.ingsw.am40.JSONConversion.JSONConverterCtoS;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientMain {
    final static String hostName = "localhost";
    final static int portNumber = 1234;
    public static void main(String[] args) {
        System.out.println("Client started!");
        try {
            Socket socket = new Socket(hostName, portNumber);
            PrintWriter out = new PrintWriter(socket.getOutputStream());
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
            String userInput;
            String line1 = in.readLine();
            System.out.println("Received: " + line1);
            while (true) {
                String line = in.readLine();
                System.out.println("Received: " + line);
                System.out.flush();
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
