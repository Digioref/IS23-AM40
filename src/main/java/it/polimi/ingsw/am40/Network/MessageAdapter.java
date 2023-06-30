package it.polimi.ingsw.am40.Network;

import it.polimi.ingsw.am40.JSONConversion.JSONConverterCtoS;
import it.polimi.ingsw.am40.JSONConversion.JSONConverterStoC;
import it.polimi.ingsw.am40.JSONConversion.ParsingJSONManager;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * The class that takes in input a message from the client and parses it, decomposing it in the command and the parameters
 */
public class MessageAdapter {
    private final Map<String, ICommand> commands;
    private final ParsingJSONManager pJsonm;

    /**
     * Constructor that initializes the features of the class
     */
    public MessageAdapter() {
        commands = new HashMap<>();
        pJsonm = new ParsingJSONManager();
    }

    /**
     * It configures the commands, linking the name of the command to the class representing that command
     */
    public void configure() {
        pJsonm.configureCommands(commands);
    }

    /**
     * It parses the message from the client
     * @param c handler of the player
     * @param message message from the client
     * @throws IOException
     * @throws ParseException
     */
    public void parserMessage (Handlers c, String message) throws IOException, ParseException {
        JSONConverterCtoS jconv = new JSONConverterCtoS();
        System.out.println(message + "  -------  " + c.getNickname());
        jconv.fromJSON(message);
        if (commands.containsKey(jconv.getCommand().toLowerCase())) {
            ICommand cmd = commands.get(jconv.getCommand().toLowerCase());
            cmd.execute(c, jconv.getPar());
        }
        else {
            c.sendMessage(JSONConverterStoC.normalMessage("Unknown command"));
        }
    }

    /**
     * It sets a welcome message to the client
     * @param c handler of the player
     * @throws IOException
     */
    public void startMessage(Handlers c) throws IOException {
        c.sendMessage((JSONConverterStoC.normalMessage("""

                 ____    ____  ____  ____    ______   ____  ____  ________  _____     ________  _____  ________ \s
                |_   \\  /   _||_  _||_  _| .' ____ \\ |_   ||   _||_   __  ||_   _|   |_   __  ||_   _||_   __  |\s
                  |   \\/   |    \\ \\  / /   | (___ \\_|  | |__| |    | |_ \\_|  | |       | |_ \\_|  | |    | |_ \\_|\s
                  | |\\  /| |     \\ \\/ /     _.____`.   |  __  |    |  _| _   | |   _   |  _|     | |    |  _| _ \s
                 _| |_\\/_| |_    _|  |_    | \\____) | _| |  | |_  _| |__/ | _| |__/ | _| |_     _| |_  _| |__/ |\s
                |_____||_____|  |______|    \\______.'|____||____||________||________||_____|   |_____||________|\s
                                                                                                                \s
                """)));
        c.sendMessage(JSONConverterStoC.normalMessage("Welcome to MyShelfie game! (type 'help' for available commands)"));
    }

    /**
     * It returns the map of the command
     * @return the commands list as a map (the keys are the names of the commands)
     */
    public Map<String, ICommand> getCommands() {
        return commands;
    }

}
