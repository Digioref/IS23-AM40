package it.polimi.ingsw.am40.Network.Commands;

import it.polimi.ingsw.am40.JSONConversion.JSONConverterStoC;
import it.polimi.ingsw.am40.Network.ActionType;
import it.polimi.ingsw.am40.Network.Handlers;
import it.polimi.ingsw.am40.Network.ICommand;

import java.io.IOException;
import java.util.ArrayList;

/**
 * The class representing the Insert command, used to insert the tiles picked and ordered into the bookshelf
 */
public class Insert implements ICommand {

    /**
     * It executes the command calling a method of the handler
     * @param c handler of the player
     * @param comm parameters of the command; in this case, it's an integer representing the index of the column of the bookshelf
     * @throws IOException
     */
    @Override
    public void execute(Handlers c, ArrayList<String> comm) throws IOException {
        if (comm.size() != 1) {
            c.sendMessage(JSONConverterStoC.createJSONError("Incomplete command"));
        }
        else {
            try {
                Integer.parseInt(comm.get(0));
            } catch (NumberFormatException e) {
                c.sendMessage(JSONConverterStoC.createJSONError("You must type an int, not a string!"));
                return;
            }
            if (Integer.parseInt(comm.get(0)) > 0 && Integer.parseInt(comm.get(0)) <= 5) {
                ArrayList<Integer> arr = new ArrayList<>();
                arr.add(Integer.parseInt(comm.get(0)));
                c.executeCommand(ActionType.INSERT, arr);
            } else if (Integer.parseInt(comm.get(0)) <= 0) {
                c.sendMessage(JSONConverterStoC.createJSONError("The index of the column is too low"));
            } else {
                c.sendMessage(JSONConverterStoC.createJSONError("The index of the column is too high"));
            }
        }
    }
}
