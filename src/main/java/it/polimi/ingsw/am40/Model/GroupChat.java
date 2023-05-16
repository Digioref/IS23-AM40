package it.polimi.ingsw.am40.Model;

import java.security.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GroupChat {
    private ArrayList<String> publisher;
    private ArrayList<String> toplayer;
    private ArrayList<String> message;

    public GroupChat() {
        publisher = new ArrayList<>();
        toplayer = new ArrayList<>();
        message = new ArrayList<>();
    }
    public void addMessage(String name, String message, String from) {
        publisher.add(from);
        toplayer.add(name);
        this.message.add(message);
    }

    public ArrayList<String> getPublisher() {
        return publisher;
    }

    public ArrayList<String> getToplayer() {
        return toplayer;
    }

    public ArrayList<String> getMessage() {
        return message;
    }
}
