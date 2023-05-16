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
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SocketClient extends Client {

    final static String hostName = "localhost";
    final static int portNumber = 1234;
    private BufferedReader stdIn;
    private BufferedReader in;
    private PrintWriter out;
    private Thread fromUser;
    private Thread fromServer;
    private boolean stop;
    private Socket socket;


    public SocketClient(Socket socket) {
        try {
            this.socket = socket;
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());
            stdIn = new BufferedReader(new InputStreamReader(System.in));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stop = false;
    }
    public void init() {
        createThreadFU();
        createThreadFS();
        startPing();
    }


    public void sendPong() {
        JSONConverterCtoS jconv = new JSONConverterCtoS();
        jconv.toJSON("Pong");
        sendMessage(jconv.toString());
    }
    public synchronized void sendMessage(String s) {
        out.println(s);
        out.flush();
    }

    public void close() {
        ping.shutdownNow();
        stop = true;
        try {
            fromUser.interrupt();
            fromServer.interrupt();
            socket.shutdownInput();
            socket.shutdownOutput();
            socket.close();
            stdIn.close();
//            LaunchClient.getView().quit(nickname);
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

    private void createThreadFU() {
        if (LaunchClient.getView() instanceof CliView) {
            fromUser = new Thread(() -> {
                do {
                    try {
                        if (stdIn.ready()) {
                            String userInput = null;
                            try {
                                userInput = stdIn.readLine();
                            } catch (IOException e ) {
                                break;
                                //                        throw new RuntimeException(e);
                            }
                            if (userInput.equals("quit")) {
                                JSONConverterCtoS jconv = new JSONConverterCtoS();
                                jconv.toJSON(userInput);
                                sendMessage(jconv.toString());
                                break;
                            }
                            if (userInput.equals("chat")) {
                                LaunchClient.getView().chat(this);
                            } else {
                                JSONConverterCtoS jconv = new JSONConverterCtoS();
                                jconv.toJSON(userInput);
                                sendMessage(jconv.toString());
                            }
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                } while (!stop);
            });
            fromUser.setName("READ FROM USER");
            fromUser.start();
        }
    }
    private void createThreadFS() {
        fromServer = new Thread(() -> {
            do {
                String line = null;
                try {
                    line = in.readLine();
                    if (line == null) {
                        close();
                        break;
                    }
                } catch (IOException e) {
                    System.out.println("Server crashed! Closing client...");
                    close();
                    break;
//                    throw new RuntimeException(e);
                }
//                print(line);
                try {
                    parseMessage(line);
                } catch (ParseException e) {
                    System.out.println("Error in parsing!");
                    break;
//                    throw new RuntimeException(e);
                }
            } while (!stop);
        });

        fromServer.setName("READ FROM SERVER");
        fromServer.start();
    }
}
