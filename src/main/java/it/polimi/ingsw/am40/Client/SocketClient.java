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
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;
import java.util.Queue;
import java.util.ArrayDeque;

/**
 * The class representing the Client handler from client side using the Socket communication. It extends from the Client abstract class.
 */
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
    private boolean quitchat;
    private Queue<String> message;

    /**
     * <p> The public constructor which takes in input the socket which will be used for the communication with the Server.</p>
     * <p> Moreover, It creates, from the socket, a BufferedReader for the input communication, a PrintWriter for the output communication and
     *  a BufferedReader for the communication with the user through standard input</p>
     *
     * @param socket Socket used for the communication with the server
     */
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
        inChat = false;
        state = new ClientState(this);
        message = new ArrayDeque<>();
    }

    /**
     * It creates a thread for the communication with the user, another thread for the communication with the server, finally it starts the Ping
     * and the parsing of the messages in the queue
     */
    public void init() {
        createThreadFU();
        createThreadFS();
        startPing();
        startParsing();
    }

    /**
     * It sends a Pong message
     */
    public void sendPong() {
        sendMessage("Pong");
    }

    /**
     * It sends the message converted to a JSON String
     * @param s the message to be sent
     */
    public synchronized void sendMessage(String s) {
        JSONConverterCtoS jconv = new JSONConverterCtoS();
        jconv.toJSON(s);
        if (jconv.getObj().get("Command").toString().equals("insert")) {
            state.setSelectedtiles(null);
            state.setPickedtiles(null);
        }
        out.println(jconv.toString());
        out.flush();
    }

    /**
     * It closes the timer of the Ping, the parsing messages thread, the user communication thread, the server communication server and the socket
     */
    public void close() {
        ping.shutdownNow();
        parse.interrupt();
        stop = true;
        quitchat = true;
        try {
            if (fromUser != null) {
                fromUser.interrupt();
            }
            fromServer.interrupt();
            if (!socket.isClosed()) {
                socket.shutdownInput();
                socket.shutdownOutput();
                socket.close();
            }
            stdIn.close();
            System.exit(0);
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

    /**
     * It creates the thread used to communicate with the user. It takes in input what the user writes and, according to the message, it sends it to the server or does specific actions
     */
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
                                inChat = true;
                                LaunchClient.getView().chat(this);
                                inChat = false;
                                state.refresh();
                            } else {
                                sendMessage(userInput);
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

    /**
     * It creates the thread used to communicate with server. It receives the messages form the server and adds them to the message queue
     */
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

                message.add(line);
//                print(line);
                /*
                try {
                    parseMessage(line);
                } catch (ParseException e) {
                    System.out.println("Error in parsing!");
                    break;
//                    throw new RuntimeException(e);
                }
                */
            } while (!stop);
        });

        fromServer.setName("READ FROM SERVER");
        fromServer.start();
    }

    /**
     * It creates a thread which polls the messages in the queue and parses them
     */
    private void startParsing(){
        parse= new Thread( ()-> {
            do{
                synchronized (message){
                    if(!message.isEmpty()){
                        try {
                            parseMessage(message.poll());
                        } catch (ParseException e) {
                            System.out.println("Error in parsing");
                            break;
                        }
                    }
                }
            }while (!stop);
        });
        parse.setName("PARSING MESSAGE");
        parse.start();
    }

    /**
     * It starts a timer waiting for Ping messages from the Server. If it misses 5 Pings, it closes the Client
     */
    public void startPing() {
        Runnable task = () -> {
            numPing++;
            if (numPing == NUMPINGLOST) {
                close();
            }
        };
        ping = Executors.newSingleThreadScheduledExecutor();
        ping.scheduleAtFixedRate(task, WAIT_PING, WAIT_PING, TimeUnit.MILLISECONDS);
    }

    public void setQuitchat(boolean quitchat) {
        this.quitchat = quitchat;
    }

    public boolean isQuitchat() {
        return quitchat;
    }
}
