package it.polimi.ingsw.am40.Controller;

import it.polimi.ingsw.am40.JSONConversion.JSONConverterStoC;
import it.polimi.ingsw.am40.Network.ClientHandler;
import it.polimi.ingsw.am40.Network.GameServer;
import it.polimi.ingsw.am40.Network.LoggingPhase;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class LobbyTest {
    @Test
    void removeFromQueue() throws IOException {
        ServerSocket ss = new ServerSocket(1234);
        Socket s = new Socket();
        s.connect(ss.getLocalSocketAddress());
        ClientHandler c1 = new ClientHandler(s, new GameServer());
        c1.setNickname("fra");
        Lobby lobby = new Lobby();
        lobby.setNumPlayers(2);
        lobby.addQueue(c1);
        lobby.removeFromQueue();
        ClientHandler c2 = new ClientHandler(s, new GameServer());
        lobby.addQueue(c2);
        lobby.removeFromQueue();
        ss.close();
    }

    @Test
    void run() {
        Lobby lobby = new Lobby();
        lobby.setNumPlayers(2);
        LoggingPhase.SETPLAYERS = true;

        ClientHandler clientHandler1 = new ClientHandler();
        ClientHandler clientHandler2 = new ClientHandler();

        lobby.getActivePlayers().add(clientHandler1);
        lobby.getActivePlayers().add(clientHandler2);

        lobby.getActivePlayers().add(clientHandler1);
        lobby.getActivePlayers().add(clientHandler2);

        lobby.setNumPlayers(2);
        ThreadPoolExecutor executors = new ScheduledThreadPoolExecutor(2);
        executors.execute(lobby);


        Lobby lobby2 = new Lobby();
        lobby2.setNumPlayers(2);
        LoggingPhase.SETPLAYERS = true;

        ClientHandler clientHandler3 = new ClientHandler();
        ClientHandler clientHandler4 = new ClientHandler();

        lobby2.getActivePlayers().add(clientHandler3);
        lobby2.getActivePlayers().add(clientHandler4);

        lobby2.getQueue().add(clientHandler3);
        lobby2.getQueue().add(clientHandler4);

        lobby2.getActivePlayers().add(clientHandler3);
        lobby2.getActivePlayers().add(clientHandler4);
        ThreadPoolExecutor executors2 = new ScheduledThreadPoolExecutor(2);
        executors2.execute(lobby2);


        Lobby lobby3 = new Lobby();
        lobby3.setNumPlayers(2);
        LoggingPhase.SETPLAYERS = true;

        ClientHandler clientHandler5 = new ClientHandler();
        ClientHandler clientHandler6 = new ClientHandler();

        lobby3.getActivePlayers().add(clientHandler5);
        lobby3.getActivePlayers().add(clientHandler6);

        lobby3.setNumPlayers(2);
        ThreadPoolExecutor executors3 = new ScheduledThreadPoolExecutor(2);
        executors3.execute(lobby3);

    }

    @Test
    void addQueue() {
    Lobby lobby = new Lobby();
    ClientHandler clientHandler = new ClientHandler();
    lobby.addQueue(clientHandler);
    }

    @Test
    void create() throws IOException {
        ServerSocket ss = new ServerSocket(1234);
        Socket s1 = new Socket();
        s1.connect(ss.getLocalSocketAddress());
        Lobby lobby = new Lobby();
        lobby.setNumPlayers(2);
        ClientHandler c1 = new ClientHandler(s1, new GameServer());
        c1.setNickname("fra");
        lobby.getActivePlayers().add(c1);
        ClientHandler c2 = new ClientHandler(s1, new GameServer());
        c2.setNickname("ale");
        lobby.getActivePlayers().add(c2);
        ClientHandler c3 = new ClientHandler(s1, new GameServer());
        c3.setNickname("fra");
        lobby.addQueue(c3);
        lobby.create();
        ss.close();
    }

    @Test
    void getNicknameInGame() {
        Lobby lobby = new Lobby();
        assertTrue(lobby.getNicknameInGame().isEmpty());
        lobby.addNickname("pippo");
        assertTrue(lobby.getNicknameInGame().contains("pippo"));
    }

    @Test
    void getActivePlayers() {
        Lobby lobby = new Lobby();
        ClientHandler clientHandler = new ClientHandler();
        lobby.getActivePlayers().add(clientHandler);
        assertTrue(lobby.getActivePlayers().contains(clientHandler));

    }

    @Test
    void setNumPlayers() {
        Lobby lobby = new Lobby();
        lobby.setNumPlayers(2);
        assertEquals(lobby.getNumPlayers(), 2);
    }

    @Test
    void getQueue() {
        Lobby lobby = new Lobby();
        lobby.getQueue();
    }

    @Test
    void addNickname() {
        Lobby lobby = new Lobby();
        lobby.addNickname("gigi");
    }

    @Test
    void removeQuit() {
        Lobby lobby = new Lobby();
        ClientHandler clientHandler = new ClientHandler();
        ClientHandler clientHandler1 = new ClientHandler();
        ClientHandler clientHandler2 = new ClientHandler();

        lobby.getQueue().add(clientHandler);
        lobby.getActivePlayers().add(clientHandler);
        Writer out = new Writer() {
            @Override
            public void write(char[] cbuf, int off, int len) throws IOException {}
            @Override
            public void flush() throws IOException {}
            @Override
            public void close() throws IOException {}
        };
        PrintWriter out1 = new PrintWriter(out);
        clientHandler.setOut(out1);
        lobby.removeQuit(clientHandler);


        lobby.getActivePlayers().add(clientHandler1);
        lobby.getActivePlayers().add(clientHandler2);


        clientHandler1.setOut(out1);
        clientHandler2.setOut(out1);

        lobby.removeQuit(clientHandler1);
    }

    @Test
    void getGames() {
        Lobby lobby = new Lobby();
        lobby.getGames();
    }

    @Test
    void closeGame() {
        Lobby lobby = new Lobby();
        ClientHandler clientHandler = new ClientHandler();
        lobby.closeGame(clientHandler);
    }
}