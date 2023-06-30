package it.polimi.ingsw.am40.Network.Commands;

import it.polimi.ingsw.am40.JSONConversion.JSONConverterStoC;
import it.polimi.ingsw.am40.Network.Handlers;
import it.polimi.ingsw.am40.Network.ICommand;
import it.polimi.ingsw.am40.Network.LoggingPhase;
import it.polimi.ingsw.am40.Network.VirtualView;

import java.io.IOException;
import java.util.ArrayList;

/**
 * The class representing the Login command, used to log into the game with a specific nickname
 */
public class Login implements ICommand {

    /**
     * It executes the command calling a method of the handler
     * @param c handler of the player
     * @param comm parameters of the command; in this case, the nickname of the player
     * @throws IOException
     */
    @Override
    public void execute(Handlers c, ArrayList<String> comm) throws IOException {
        if (!c.isLogged() && c.getLogphase().equals(LoggingPhase.LOGGING) && comm.size() >= 1) {
            StringBuilder s = new StringBuilder();
            for (String value : comm) {
                if (s.toString().equals("")) {
                    s = new StringBuilder(value);
                } else {
                    s.append(" ").append(value);
                }
            }
            if (c.checkNickname(s.toString())) {
                if ((!c.getLobby().getGames().containsKey(s.toString()) && c.getLobby().getNicknameInGame().contains(s.toString())) || (c.getLobby().getGames().containsKey(s.toString()) && !c.getLobby().getGames().get(s.toString()).getGame().getDiscPlayers().contains(s.toString()))) {
                    c.sendMessage(JSONConverterStoC.createJSONError("Nickname already used!"));
                    c.suggestNickname(s.toString());
                } else if (c.getLobby().getGames().containsKey(s.toString()) && c.getLobby().getGames().get(s.toString()).getGame().getDiscPlayers().contains(s.toString())) {
                    c.sendMessage(JSONConverterStoC.normalMessage("Welcome back " + s + "!\nReconnecting to the game..."));
                    c.setNickname(s.toString());
                    c.setLogphase(LoggingPhase.INGAME);
                    c.sendMessage(JSONConverterStoC.createJSONNickname(s.toString()));
                    c.setController(c.getLobby().getGames().get(s.toString()).getController());
                    c.setLogged(true);
                    c.setNumPlayers(c.getLobby().getGames().get(s.toString()).getGame().getNumPlayers());
                    for (VirtualView v: c.getLobby().getGames().get(s.toString()).getGame().getObservers()) {
                        if (v.getNickname().equals(s.toString())) {
                            v.setClientHandler(c);
                            c.setVirtualView(v);
                            break;
                        }
                    }
                    c.sendMessage(JSONConverterStoC.normalMessage("RECONNECT"));
                    c.getController().getGameController().reconnect(s.toString());
                    return;
                }
            } else {
                c.setLogged(true);
                c.setNickname(s.toString());
                c.getLobby().addNickname(s.toString());
                c.setLogphase(LoggingPhase.WAITING);
                c.getLobby().addQueue(c);
                c.sendMessage(JSONConverterStoC.normalMessage("You are logged in!"));
                c.sendMessage(JSONConverterStoC.createJSONNickname(s.toString()));
                c.sendMessage(JSONConverterStoC.normalMessage("Waiting"));
            }
            if (!LoggingPhase.SETPLAYERS) {
                LoggingPhase.setSETPLAYERS(true);
                c.sendMessage(JSONConverterStoC.normalMessage("Setplayers"));
            }
        } else if (comm.size() < 1) {
            c.sendMessage(JSONConverterStoC.createJSONError("Incomplete command"));
        } else {
            c.sendMessage(JSONConverterStoC.createJSONError("You are already logged in!"));
        }
    }
}
