package it.polimi.ingsw.am40.Network.Commands;

import it.polimi.ingsw.am40.JSONConversion.JSONConverterStoC;
import it.polimi.ingsw.am40.Network.ActionType;
import it.polimi.ingsw.am40.Network.Handlers;
import it.polimi.ingsw.am40.Network.ICommand;

import java.io.IOException;
import java.util.ArrayList;

/**
 * The class representing the Select command, used to select a specific position on the board, where there is a tile, in order to select that tile
 */
public class Select implements ICommand {

    /**
     * It executes the command calling a method of the handler
     * @param c handler of the player
     * @param comm parameters of the command; in this case, two integers representing the desired position on the board
     * @throws IOException
     */
    @Override
    public void execute(Handlers c, ArrayList<String> comm) throws IOException {
        if (comm.size() != 2) {
            c.sendMessage(JSONConverterStoC.createJSONError("Incomplete command"));
        }
        else {
            int x, y;
            try {
                x = Integer.parseInt(comm.get(0));
                y = Integer.parseInt(comm.get(1));
            } catch (NumberFormatException e) {
                c.sendMessage(JSONConverterStoC.createJSONError("You must type an int, not a string!"));
                return;
            }
            ArrayList<Integer> arr = new ArrayList<>();
            arr.add(x);
            arr.add(y);
            c.executeCommand(ActionType.SELECT, arr);
        }
    }
}
