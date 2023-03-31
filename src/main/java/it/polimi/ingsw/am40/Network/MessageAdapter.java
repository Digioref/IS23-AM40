package it.polimi.ingsw.am40.Network;

import it.polimi.ingsw.am40.Model.ParsingJSONManager;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessageAdapter {
    private Map<String, ICommand> commands;
    private ParsingJSONManager pJsonm;

    public MessageAdapter() {
        commands = new HashMap<>();
        pJsonm = new ParsingJSONManager();
    }
    public void configure() {
        pJsonm.configureCommands(commands);
    }

    public void parserMessage (ClientHandler c, String message) throws IOException {
        Pattern pattern = Pattern.compile("^([a-zA-Z]+)||([a-zA-Z]+)( +([0-9]+)*)$");
        Matcher match = pattern.matcher(message);
        match.find();
        String command = match.group(1);
        if (commands.containsKey(command.toLowerCase())) {
            ICommand cmd = commands.get(command.toLowerCase());
            cmd.execute(c);
        }
        else {
            c.sendMessage("Unknown command");
        }
    }
    public void startMessage(ClientHandler c) throws IOException {
        c.sendMessage("Welcome to MyShelfie game! (type 'help' for available commands)");
    }

    public Map<String, ICommand> getCommands() {
        return commands;
    }
}
