package it.polimi.ingsw.am40.Network.Commands;

import it.polimi.ingsw.am40.JSONConversion.JSONConverterStoC;
import it.polimi.ingsw.am40.Model.Player;
import it.polimi.ingsw.am40.Network.Handlers;
import it.polimi.ingsw.am40.Network.ICommand;

import java.io.IOException;
import java.util.ArrayList;

/**
 * todo
 */
public class GetPersGoal implements ICommand {
    @Override
    public void execute(Handlers c, ArrayList<String> comm) throws IOException {
        if (comm.size() == 0) {
            for (Player p: c.getController().getGame().getPlayers()) {
                if (c.getNickname().equals(p.getNickname())) {
                    c.sendMessage(JSONConverterStoC.createJSONPersGoal(p.getPersonalGoal()));
                    break;
                }
            }
        } else {
            c.sendMessage(JSONConverterStoC.createJSONError("The command getpersgoal doesn't want arguments!"));
        }
    }
}
