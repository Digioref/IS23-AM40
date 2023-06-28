package it.polimi.ingsw.am40.Model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GroupChatTest {

    @Test
    void addMessage() {
        GroupChat chat = new GroupChat();
        chat.addMessage("pippo", "ciao", "dani");
    }

    @Test
    void getPublisher() {
        GroupChat chat = new GroupChat();
        chat.addMessage("pippo", "ciao", "dani");
        assertEquals("dani", chat.getPublisher().get(0));
    }

    @Test
    void getToplayer() {
        GroupChat chat = new GroupChat();
        chat.addMessage("pippo", "ciao", "dani");
        assertEquals("pippo", chat.getToplayer().get(0));
    }

    @Test
    void getMessage() {
        GroupChat chat = new GroupChat();
        chat.addMessage("pippo", "ciao", "dani");
        assertEquals("ciao", chat.getMessage().get(0));
    }
}