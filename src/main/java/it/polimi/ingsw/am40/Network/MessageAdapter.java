package it.polimi.ingsw.am40.Network;

import it.polimi.ingsw.am40.JSONConversion.JSONConverterCtoS;
import it.polimi.ingsw.am40.JSONConversion.JSONConverterStoC;
import it.polimi.ingsw.am40.Model.ParsingJSONManager;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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

    public void parserMessage (ClientHandler c, String message) throws IOException, ParseException {
//        Pattern pattern = Pattern.compile("^([a-zA-Z]+)|(([a-zA-Z]+)( +([0-9]+)*))$");
//        Pattern pattern = Pattern.compile("[a-zA-Z0-9]+");
//        Matcher match = pattern.matcher(message);
//        match.find();
//        String command = match.group(0);
        JSONConverterCtoS jconv = new JSONConverterCtoS();
        jconv.fromJSON(message);
//        String[] command = message.split("\\s");
//        if (commands.containsKey(command[0].toLowerCase())) {
//            ICommand cmd = commands.get(command[0].toLowerCase());
//            cmd.execute(c, command);
//        }
//        else {
//            c.sendMessage("Unknown command");
//        }
        if (commands.containsKey(jconv.getCommand().toLowerCase())) {
            ICommand cmd = commands.get(jconv.getCommand().toLowerCase());
            cmd.execute(c, jconv.getPar());
        }
        else {
            c.sendMessage("Unknown command");
        }
    }
    public void startMessage(ClientHandler c) throws IOException {
        c.sendMessage(JSONConverterStoC.normalMessage("Welcome to MyShelfie game! (type 'help' for available commands)"));
    }

    public Map<String, ICommand> getCommands() {
        return commands;
    }
}
