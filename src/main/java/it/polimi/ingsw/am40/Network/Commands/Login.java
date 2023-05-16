package it.polimi.ingsw.am40.Network.Commands;

import it.polimi.ingsw.am40.JSONConversion.JSONConverterStoC;
import it.polimi.ingsw.am40.Network.Handlers;
import it.polimi.ingsw.am40.Network.ICommand;
import it.polimi.ingsw.am40.Network.LoggingPhase;

import java.io.IOException;
import java.util.ArrayList;

public class Login implements ICommand {

    @Override
    public void execute(Handlers c, ArrayList<String> comm) throws IOException {
        if (!c.isLogged() && c.getLogphase().equals(LoggingPhase.LOGGING) && comm.size() >= 1) {
            if (c.checkNickname(comm.get(0))) {
                c.sendMessage(JSONConverterStoC.normalMessage("Nickname already used!"));
                c.sendMessage(JSONConverterStoC.normalMessage("What about these nicknames:"));
                c.suggestNickname(comm.get(0));
            }
            else {
                c.setLogged(true);
                String s = "";
                for (int i = 0; i < comm.size(); i++) {
                    if (s.equals("")) {
                        s = comm.get(i);
                    } else {
                        s = s + " " + comm.get(i);
                    }
                }
                c.setNickname(s);
                c.getLobby().addNickname(s);
                c.setLogphase(LoggingPhase.WAITING);
                c.getLobby().addQueue(c);
                c.sendMessage(JSONConverterStoC.normalMessage("You are logged in!"));
                c.sendMessage(JSONConverterStoC.createJSONNickname(comm.get(0)));
            }
            if (!LoggingPhase.SETPLAYERS) {
                LoggingPhase.setSETPLAYERS(true);
                c.sendMessage(JSONConverterStoC.normalMessage("The number of players you want to play with: "));
            }
        } else if (comm.size() < 1) {
            c.sendMessage(JSONConverterStoC.normalMessage("Incomplete command"));
        } else {
            c.sendMessage(JSONConverterStoC.normalMessage("You are already logged in!"));
        }
    }
}
