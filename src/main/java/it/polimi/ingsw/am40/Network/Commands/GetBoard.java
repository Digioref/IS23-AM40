package it.polimi.ingsw.am40.Network.Commands;

import it.polimi.ingsw.am40.JSONConversion.JSONConverterStoC;
import it.polimi.ingsw.am40.Model.Player;
import it.polimi.ingsw.am40.Network.Handlers;
import it.polimi.ingsw.am40.Network.ICommand;

import java.io.IOException;
import java.util.ArrayList;

/**
 * The class representing the GetBoard command, used to get the board of the game
 */
public class GetBoard implements ICommand {
    /**
     * It executes the command with the call of a specific method of the handler
     * @param c the handler of the player
     * @param comm the parameters of the command; in this case, no parameters
     * @throws IOException
     */
    @Override
    public void execute(Handlers c, ArrayList<String> comm) throws IOException {
        if (comm.size() == 0) {
            for (Player p: c.getController().getGame().getPlayers()) {
                if (c.getNickname().equals(p.getNickname())) {
                    c.sendMessage(JSONConverterStoC.createJSONBoard(p.getBoard()));
                    break;
                }
            }
        } else {
            c.sendMessage(JSONConverterStoC.createJSONError("The command getboard doesn't want arguments!"));
        }
    }
}
