package it.polimi.ingsw.am40.Network.Commands;

import it.polimi.ingsw.am40.JSONConversion.JSONConverterStoC;
import it.polimi.ingsw.am40.Model.Player;
import it.polimi.ingsw.am40.Network.Handlers;
import it.polimi.ingsw.am40.Network.ICommand;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GetCurScore implements ICommand {
    @Override
    public void execute(Handlers c, ArrayList<String> comm) throws IOException {
        if (comm.size() == 0) {
            Map<String, Integer> map = new HashMap<>();
            for (Player p: c.getController().getGame().getPlayers()) {
                map.put(p.getNickname(), p.getCurrentScore());
            }
            c.sendMessage(JSONConverterStoC.createJSONCurrentScore(map));
        } else {
            c.sendMessage(JSONConverterStoC.normalMessage("The command getcurscore doesn't want arguments!"));
        }
    }
}
