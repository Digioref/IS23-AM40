package it.polimi.ingsw.am40.Client;

import it.polimi.ingsw.am40.CLI.CliView;
import it.polimi.ingsw.am40.JSONConversion.JSONConverterCtoS;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.Queue;
import java.util.ArrayDeque;

/**
 * The class representing the Client handler from client side using the Socket communication. It extends from the Client abstract class.
 */
public class SocketClient extends Client {

    private BufferedReader stdIn;
    private BufferedReader in;
    private PrintWriter out;
    private Thread fromUser;
    private Thread fromServer;

    private boolean stop;
    private Socket socket;
    private boolean quitchat;
    private final Queue<String> message;

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
            System.out.println("Socket is unavailable!");
            System.exit(0);
        }
        stop = false;
        inChat = false;
        state = new ClientState();
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
        out.println(jconv);
        out.flush();
        if (s.equals("quit")) {
            close();
        }
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
            if (LaunchClient.getView() != null) {
                LaunchClient.getView().quit(nickname);
            }
            System.exit(0);
//            LaunchClient.getView().quit(nickname);
        } catch (IOException e) {
            System.exit(0);
        }
    }

    /**
     * It returns the Buffered Reader used to read form standard input
     * @return buffered reader
     */
    public BufferedReader getStdIn() {
        return stdIn;
    }

    /**
     * It returns the Buffered Reader used to read form the socket
     * @return buffered reader
     */
    public BufferedReader getIn() {
        return in;
    }

    /**
     * It returns the Print Writer used to write to the socket
     * @return print writer
     */

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
                            String userInput;
                            try {
                                userInput = stdIn.readLine();
                            } catch (IOException e ) {
                                break;
                            }
                            if (userInput.equals("quit")) {
                                sendMessage(userInput);
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
                        System.out.println("Standard input not available");
                        close();
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
                String line;
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
                }

                message.add(line);
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

    /**
     * it sets a boolean which specifies if the player exited from the chat
     * @param quitchat boolean
     */
    public void setQuitchat(boolean quitchat) {
        this.quitchat = quitchat;
    }

    /**
     * It specifies if the player exited from the chat
     * @return a boolean, true if the player is outside the chat, false otherwise
     */
    public boolean isQuitchat() {
        return quitchat;
    }

    /**
     * It sends a chat message using the socket
     * @param command chat message to be sent
     */
    public void chat(String command) {
        out.println(command);
        out.flush();
    }
}
