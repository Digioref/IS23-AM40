package it.polimi.ingsw.am40.Controller;

import it.polimi.ingsw.am40.Network.ClientHandler;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LobbyTest {

    @Test
    void removeFromQueue() {
        Lobby lobby = new Lobby();
        ClientHandler clientHandler = new ClientHandler();
        lobby.getQueue().add(clientHandler);
        lobby.removeFromQueue();

        lobby.setNumPlayers(2);
        lobby.getQueue().add(clientHandler);
        ClientHandler clientHandler1 = new ClientHandler();
        lobby.removeFromQueue();
    }

    @Test
    void run() {
    }

    @Test
    void findGame() {
    }

    @Test
    void addQueue() {
    }

    @Test
    void create() {
    }

    @Test
    void getNicknameInGame() {
    }

    @Test
    void getActivePlayers() {
    }

    @Test
    void setNumPlayers() {
    }

    @Test
    void getQueue() {
    }

    @Test
    void addNickname() {
    }

    @Test
    void removeQuit() {
    }

    @Test
    void getGames() {
    }

    @Test
    void closeGame() {
    }
}