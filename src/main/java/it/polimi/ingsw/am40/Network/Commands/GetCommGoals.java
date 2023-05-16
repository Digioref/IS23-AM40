package it.polimi.ingsw.am40.Network.Commands;

import it.polimi.ingsw.am40.JSONConversion.JSONConverterStoC;
import it.polimi.ingsw.am40.Model.Player;
import it.polimi.ingsw.am40.Network.Handlers;
import it.polimi.ingsw.am40.Network.ICommand;

import java.io.IOException;
import java.util.ArrayList;

public class GetCommGoals implements ICommand {
    @Override
    public void execute(Handlers c, ArrayList<String> comm) throws IOException {
        if (comm.size() == 0) {
            for (Player p: c.getController().getGame().getPlayers()) {
                if (c.getNickname().equals(p.getNickname())) {
                    int num1 = c.getController().getGame().getCurrentComGoals().get(0).getNum();
                    int num2 = c.getController().getGame().getCurrentComGoals().get(1).getNum();
                    int score1 = c.getController().getGame().getCurrentComGoals().get(0).getCommgoaltok().getScore();
                    int score2 = c.getController().getGame().getCurrentComGoals().get(1).getCommgoaltok().getScore();
                    c.sendMessage(JSONConverterStoC.createJSONCommonGoals(num1, score1, num2, score2));
                    break;
                }
            }
        } else {
            c.sendMessage(JSONConverterStoC.normalMessage("The command getcommgoals doesn't want arguments!"));
        }
    }
}
