package it.polimi.ingsw.am40.Network.Commands;

import it.polimi.ingsw.am40.Network.ClientHandler;
import it.polimi.ingsw.am40.Network.ICommand;

import java.io.IOException;
import java.util.ArrayList;

public class Help implements ICommand {

    @Override
    public void execute(ClientHandler c, ArrayList<String> comm) throws IOException {
        for (String s: c.getMessAd().getCommands().keySet()) {
            switch(s) {
                case "select":
                    c.sendMessage("- " + s + " [int] [int]");
                    break;
                case "order":
                    c.sendMessage("- " + s + " [int] (at least)");
                    break;
                case "insert":
                    c.sendMessage("- " + s + " [int]");
                    break;
                default:
                    c.sendMessage("- " + s);
                    break;
            }
        }
    }
}
