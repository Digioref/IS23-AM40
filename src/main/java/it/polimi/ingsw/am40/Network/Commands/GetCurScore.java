package it.polimi.ingsw.am40.Network.Commands;

import it.polimi.ingsw.am40.JSONConversion.JSONConverterStoC;
import it.polimi.ingsw.am40.Model.Player;
import it.polimi.ingsw.am40.Network.Handlers;
import it.polimi.ingsw.am40.Network.ICommand;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * The class used to represent the GetCurrentScore command, used to get the current score of the player
 */
public class GetCurScore implements ICommand {
    /**
     * It executes the command calling a method of the handler
     * @param c handler of the player
     * @param comm the parameters of the command; in this case, no parameters
     * @throws IOException
     */
    @Override
    public void execute(Handlers c, ArrayList<String> comm) throws IOException {
        if (comm.size() == 0) {
            Map<String, Integer> map = new HashMap<>();
            for (Player p: c.getController().getGame().getPlayers()) {
                map.put(p.getNickname(), p.getCurrentScore());
            }
            c.sendMessage(JSONConverterStoC.createJSONCurrentScore(map));
        } else {
            c.sendMessage(JSONConverterStoC.createJSONError("The command getcurscore doesn't want arguments!"));
        }
    }
}
