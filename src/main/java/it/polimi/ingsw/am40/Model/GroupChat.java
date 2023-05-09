package it.polimi.ingsw.am40.Model;

import java.security.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GroupChat {
    private ArrayList<String> publisher;
    private ArrayList<String> toplayer;
    private Map<String, Long> message;

    public GroupChat() {
        publisher = new ArrayList<>();
        toplayer = new ArrayList<>();
        message = new HashMap<>();
    }
    public void addMessage(String name, String message, String from, Long time) {

    }
}
