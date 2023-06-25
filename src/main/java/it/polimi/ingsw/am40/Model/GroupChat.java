package it.polimi.ingsw.am40.Model;

import java.security.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * The class representing the chat of the game
 */
public class GroupChat {
    private ArrayList<String> publisher;
    private ArrayList<String> toplayer;
    private ArrayList<String> message;

    /**
     * The constructor of the class, it creates all the necessary data structure
     */
    public GroupChat() {
        publisher = new ArrayList<>();
        toplayer = new ArrayList<>();
        message = new ArrayList<>();
    }

    /**
     * It adds the provided message to the chat, with the name of the sender and the name oif the receiver
     * @param name the receiver of the message
     * @param message the message
     * @param from the sender of the message
     */
    public void addMessage(String name, String message, String from) {
        publisher.add(from);
        toplayer.add(name);
        this.message.add(message);
    }

    /**
     * It returns the publisher of each message
     * @return an array containing the names of each publisher of each message
     */
    public ArrayList<String> getPublisher() {
        return publisher;
    }

    /**
     * It returns the receiver of each message
     * @return an array containing the names of each receiver of each message
     */
    public ArrayList<String> getToplayer() {
        return toplayer;
    }

    /**
     * It returns each message
     * @return an array containing all the messages exchanged
     */
    public ArrayList<String> getMessage() {
        return message;
    }
}
