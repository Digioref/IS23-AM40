package it.polimi.ingsw.am40.Network.Commands;

import it.polimi.ingsw.am40.JSONConversion.JSONConverterStoC;
import it.polimi.ingsw.am40.Network.Handlers;
import it.polimi.ingsw.am40.Network.ICommand;

import java.io.IOException;
import java.util.ArrayList;

public class Help implements ICommand {

    @Override
    public void execute(Handlers c, ArrayList<String> comm) throws IOException {
        if (comm.size() == 0) {
            for (String s: c.getMessAd().getCommands().keySet()) {
                switch(s) {
                    case "select":
                        c.sendMessage(JSONConverterStoC.normalMessage("- " + s + " [int] [int]"));
                        break;
                    case "order":
                        c.sendMessage(JSONConverterStoC.normalMessage("- " + s + " [int] (at least)"));
                        break;
                    case "setplayers", "insert":
                        c.sendMessage(JSONConverterStoC.normalMessage("- " + s + " [int]"));
                        break;
                    case "login":
                        c.sendMessage(JSONConverterStoC.normalMessage("- " + s + " [string]"));
                        break;
                    default:
                        c.sendMessage(JSONConverterStoC.normalMessage("- " + s));
                        break;
                }
            }
        } else {
            c.sendMessage(JSONConverterStoC.createJSONError("The command help doesn't want arguments!"));
        }
    }
}
