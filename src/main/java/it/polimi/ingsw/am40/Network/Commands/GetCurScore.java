package it.polimi.ingsw.am40.Network.Commands;

import it.polimi.ingsw.am40.JSONConversion.JSONConverterStoC;
import it.polimi.ingsw.am40.Model.Player;
import it.polimi.ingsw.am40.Network.ClientHandler;
import it.polimi.ingsw.am40.Network.ICommand;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GetCurScore implements ICommand {
    @Override
    public void execute(ClientHandler c, ArrayList<String> comm) throws IOException {
        Map<String, Integer> map = new HashMap<>();
        for (Player p: c.getController().getGame().getPlayers()) {
            map.put(p.getNickname(), p.getCurrentScore());
        }
        c.sendMessage(JSONConverterStoC.createJSONCurrentScore(map));
    }
}
