package it.polimi.ingsw.am40.Client;

import it.polimi.ingsw.am40.CLI.CliView;
import it.polimi.ingsw.am40.JSONConversion.JSONConverterCtoS;
import it.polimi.ingsw.am40.Model.Position;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SocketClient extends Client {

    final static String hostName = "localhost";
    final static int portNumber = 1234;
    private BufferedReader stdIn;
    private BufferedReader in;
    private PrintWriter out;
    private Thread fromUser;
    private Thread fromServer;
    private boolean stop;

    public SocketClient(Socket socket) {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());
            stdIn = new BufferedReader(new InputStreamReader(System.in));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stop = false;
    }
    public void init() {
        if (LaunchClient.getView() instanceof CliView) {
            fromUser = new Thread(() -> {
                do {
                    String userInput;
                    try {
                        userInput = stdIn.readLine();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    if (userInput.equals("chat")) {
                        LaunchClient.getView().chat(this);
                    } else {
                        JSONConverterCtoS jconv = new JSONConverterCtoS();
                        jconv.toJSON(userInput);
                        out.println(jconv.toString());
                        out.flush();
                    }
                } while (!stop);
            });
            fromUser.setName("READ FROM USER");
            fromUser.start();
        }
        fromServer = new Thread(() -> {
            do {
                String line;
                try {
                    line = in.readLine();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                //print(line);
                try {
                    parseMessage(line);
                } catch (ParseException e) {
                    System.out.println("Error in parsing!");
                    throw new RuntimeException(e);
                }
            } while (!stop);
        });

        fromServer.setName("READ FROM SERVER");
        fromServer.start();
    }



    public void close() {
        stop = true;
        try {
            stdIn.close();
            in.close();
            out.close();
            fromUser.interrupt();
            fromServer.interrupt();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public BufferedReader getStdIn() {
        return stdIn;
    }

    public BufferedReader getIn() {
        return in;
    }

    public PrintWriter getOut() {
        return out;
    }

    public synchronized void print(String s) {
        System.out.println(s);
        System.out.flush();
    }
}
