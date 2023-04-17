package it.polimi.ingsw.am40.Network.Commands;

import it.polimi.ingsw.am40.JSONConversion.JSONConverterStoC;
import it.polimi.ingsw.am40.Network.ClientHandler;
import it.polimi.ingsw.am40.Network.ICommand;
import it.polimi.ingsw.am40.Network.LoggingPhase;

import java.io.IOException;
import java.util.ArrayList;

public class SetPlayers implements ICommand {

    @Override
    public void execute(ClientHandler c, ArrayList<String> comm) throws IOException {
//        c.setNumPlayers(Integer.parseInt(comm.get(0)));
        if (comm.size() == 1) {
            if (LoggingPhase.SETPLAYERS && c.getLogphase().equals(LoggingPhase.WAITING)) {
                c.getLobby().setNumPlayers(Integer.parseInt(comm.get(0)));
            } else if (!LoggingPhase.SETPLAYERS) {
                c.sendMessage(JSONConverterStoC.normalMessage("You can not set the number of players, the game has been already created!"));
            }
            else {
                c.sendMessage(JSONConverterStoC.normalMessage("You are not logged in yet!"));
            }
        } else {
            c.sendMessage(JSONConverterStoC.normalMessage("Incomplete command"));
        }
    }
}
