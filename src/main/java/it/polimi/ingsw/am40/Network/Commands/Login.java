package it.polimi.ingsw.am40.Network.Commands;

import it.polimi.ingsw.am40.JSONConversion.JSONConverterStoC;
import it.polimi.ingsw.am40.Network.ClientHandler;
import it.polimi.ingsw.am40.Network.ICommand;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Login implements ICommand {

    @Override
    public void execute(ClientHandler c, ArrayList<String> comm) throws IOException {
        Scanner in = new Scanner(c.getSocket().getInputStream());
        if (!c.isLogged()) {
            if (c.checkNickname(comm.get(0))) {
                c.sendMessage("Nickname already used!");
                c.sendMessage("What about these nicknames:");
                c.suggestNickname(comm.get(0));
            }
            else {
                c.setLogged(true);
                c.setNickname(comm.get(0));
                c.getLobby().addQueue(c);
            }
            if (c.getLobby().getActivePlayers().size() == 0) {
                c.sendMessage(JSONConverterStoC.normalMessage("The number of players you want to play with: "));
            }
        }
        else {
            c.sendMessage("You are already logged in!");
        }
    }
}
