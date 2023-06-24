package it.polimi.ingsw.am40.Network.Commands;

import it.polimi.ingsw.am40.JSONConversion.JSONConverterStoC;
import it.polimi.ingsw.am40.Network.ActionType;
import it.polimi.ingsw.am40.Network.Handlers;
import it.polimi.ingsw.am40.Network.ICommand;

import java.io.IOException;
import java.lang.invoke.TypeDescriptor;
import java.util.ArrayList;

/**
 * todo
 */
public class Insert implements ICommand {

    /**
     * todo
     * @param c
     * @param comm
     * @throws IOException
     */
    @Override
    public void execute(Handlers c, ArrayList<String> comm) throws IOException {
        if (comm.size() != 1) {
            c.sendMessage(JSONConverterStoC.createJSONError("Incomplete command"));
        }
        else {
            try {
                Integer i = Integer.parseInt(comm.get(0));
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
