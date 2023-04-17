package it.polimi.ingsw.am40.Network.Commands;

import it.polimi.ingsw.am40.JSONConversion.JSONConverterStoC;
import it.polimi.ingsw.am40.Network.ClientHandler;
import it.polimi.ingsw.am40.Network.ICommand;
import it.polimi.ingsw.am40.Network.LoggingPhase;

import java.io.IOException;
import java.util.ArrayList;

public class Login implements ICommand {

    @Override
    public void execute(ClientHandler c, ArrayList<String> comm) throws IOException {
        if (!c.isLogged() && c.getLogphase().equals(LoggingPhase.LOGGING) && comm.size() == 1) {
            if (c.checkNickname(comm.get(0))) {
                c.sendMessage(JSONConverterStoC.normalMessage("Nickname already used!"));
                c.sendMessage(JSONConverterStoC.normalMessage("What about these nicknames:"));
                c.suggestNickname(comm.get(0));
            }
            else {
                c.setLogged(true);
                c.setNickname(comm.get(0));
                c.getLobby().addQueue(c);
                c.getLobby().getNicknameInGame().add(comm.get(0));
                c.setLogphase(LoggingPhase.WAITING);
                c.sendMessage(JSONConverterStoC.normalMessage("You are logged in!"));
            }
            if (!LoggingPhase.SETPLAYERS) {
                LoggingPhase.setSETPLAYERS(true);
                c.sendMessage(JSONConverterStoC.normalMessage("The number of players you want to play with: "));
            }
        } else if (comm.size() != 1) {
            c.sendMessage(JSONConverterStoC.normalMessage("Incomplete command"));
        } else {
            c.sendMessage(JSONConverterStoC.normalMessage("You are already logged in!"));
        }
    }
}
