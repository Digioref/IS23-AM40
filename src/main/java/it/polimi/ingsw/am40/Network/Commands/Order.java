package it.polimi.ingsw.am40.Network.Commands;

import it.polimi.ingsw.am40.JSONConversion.JSONConverterStoC;
import it.polimi.ingsw.am40.Network.ActionType;
import it.polimi.ingsw.am40.Network.Handlers;
import it.polimi.ingsw.am40.Network.ICommand;

import java.io.IOException;
import java.util.ArrayList;

/**
 * The class representing the Order command, used to give an order to the tiles picked
 */
public class Order implements ICommand {
    /**
     * It executes the command calling a method of the handler
     * @param c handler of the player
     * @param comm parameters of the command; in this case, a sequence of integer representing the order specified by the player
     * @throws IOException
     */
    @Override
    public void execute(Handlers c, ArrayList<String> comm) throws IOException {
            ArrayList<Integer> arr = new ArrayList<>();
            if (comm.size() > 0 && comm.size() < 4) {
                for (int i = 0; i < comm.size() ; i++) {
                    try {
                        Integer.parseInt(comm.get(i));
                    } catch (NumberFormatException e) {
                        c.sendMessage(JSONConverterStoC.createJSONError("You must type an int, not a string!"));
                        return;
                    }
                    if (Integer.parseInt(comm.get(i)) <= comm.size() && Integer.parseInt(comm.get(i)) > 0) {
                        arr.add(Integer.parseInt(comm.get(i)));
                    } else if (Integer.parseInt(comm.get(i)) > comm.size()) {
                        c.sendMessage(JSONConverterStoC.createJSONError("A number of the order is too high!"));
                        return;
                    } else {
                        c.sendMessage(JSONConverterStoC.createJSONError("A number of the order is too low!"));
                        return;
                    }
                }
                c.executeCommand(ActionType.ORDER, arr);
            } else {
                c.sendMessage(JSONConverterStoC.createJSONError("Incomplete command"));
            }
    }
}
