package it.polimi.ingsw.am40.Network.Commands;

import it.polimi.ingsw.am40.Network.ClientHandler;
import it.polimi.ingsw.am40.Network.ICommand;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Login implements ICommand {

    @Override
    public void execute(ClientHandler c) throws IOException {
        Scanner in = new Scanner(c.getSocket().getInputStream());
        if (!c.isLogged()) {

            String line;
            do {
                c.sendMessage("Your nickname: ");
                line = in.nextLine();
                if (c.checkNickname(line)) {
                    c.sendMessage("Nickname already used!");
                    c.sendMessage("What about these nicknames:");
                    c.suggestNickname(line);
                }
            } while (c.checkNickname(line));
            c.setLogged(true);
            c.setNickname(line);
            if(c.getLobby().getActivePlayers().size() == 0) {
                c.sendMessage("The number of players you want to play with: ");
                line = in.nextLine();
                c.setNumPlayers(Integer.parseInt(line));
            }
            c.getLobby().addQueue(c);
            c.sendMessage("You are logged in");

        }
        else {
            c.sendMessage("You are already logged in!");
        }
    }
}
