package it.polimi.ingsw.am40.Network.Commands;

import it.polimi.ingsw.am40.JSONConversion.JSONConverterStoC;
import it.polimi.ingsw.am40.Model.Player;
import it.polimi.ingsw.am40.Network.ClientHandler;
import it.polimi.ingsw.am40.Network.ICommand;

import java.io.IOException;
import java.util.ArrayList;

public class GetHiddenScore  implements ICommand {
    @Override
    public void execute(ClientHandler c, ArrayList<String> comm) throws IOException {
        for (Player p: c.getController().getGame().getPlayers()) {
            if (c.getNickname().equals(p.getNickname())) {
                c.sendMessage(JSONConverterStoC.createJSONHiddenScore(p.getHiddenScore()));
                break;
            }
        }
    }
}
