package it.polimi.ingsw.am40.Client;

import it.polimi.ingsw.am40.JSONConversion.JSONConverterCtoS;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientMain {
    final static String hostName = "localhost";
    final static int portNumber = 1234;
    static ClientAdapter clientAdapter = null;
    public static void main(String[] args) {
        System.out.println("Client started!");
        try {
            Socket socket = new Socket(hostName, portNumber);
            PrintWriter out = new PrintWriter(socket.getOutputStream());
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
            ExecutorService executor = Executors.newCachedThreadPool();
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
