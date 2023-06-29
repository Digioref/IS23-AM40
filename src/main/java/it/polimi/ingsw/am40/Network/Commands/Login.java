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
            String s = "";
            for (int i = 0; i < comm.size(); i++) {
                if (s.equals("")) {
                    s = comm.get(i);
                } else {
                    s = s + " " + comm.get(i);
                }
            }
            if (c.checkNickname(s)) {
                if ((!c.getLobby().getGames().containsKey(s) && c.getLobby().getNicknameInGame().contains(s)) || (c.getLobby().getGames().containsKey(s) && !c.getLobby().getGames().get(s).getGame().getDiscPlayers().contains(s))) {
                    c.sendMessage(JSONConverterStoC.createJSONError("Nickname already used!"));
//                    c.sendMessage(JSONConverterStoC.normalMessage("What about these nicknames:"));
                    c.suggestNickname(s);
                } else if (c.getLobby().getGames().containsKey(s) && c.getLobby().getGames().get(s).getGame().getDiscPlayers().contains(s)) {
                    c.sendMessage(JSONConverterStoC.normalMessage("Welcome back " + s + "!\nReconnecting to the game..."));
                    c.setNickname(s);
                    c.setLogphase(LoggingPhase.INGAME);
                    c.sendMessage(JSONConverterStoC.createJSONNickname(s));
                    c.setController(c.getLobby().getGames().get(s).getController());
                    c.setLogged(true);
                    c.setNumPlayers(c.getLobby().getGames().get(s).getGame().getNumPlayers());
                    for (VirtualView v: c.getLobby().getGames().get(s).getGame().getObservers()) {
                        if (v.getNickname().equals(s)) {
                            v.setClientHandler(c);
                            c.setVirtualView(v);
                            break;
                        }
                    }
                    c.sendMessage(JSONConverterStoC.normalMessage("RECONNECT"));
                    c.getController().getGameController().reconnect(s);
                    return;
                }
            } else {
                c.setLogged(true);
                c.setNickname(s);
                c.getLobby().addNickname(s);
                c.setLogphase(LoggingPhase.WAITING);
                c.getLobby().addQueue(c);
                c.sendMessage(JSONConverterStoC.normalMessage("You are logged in!"));
                c.sendMessage(JSONConverterStoC.createJSONNickname(s));
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
