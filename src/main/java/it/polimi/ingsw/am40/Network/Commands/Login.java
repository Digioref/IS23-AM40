package it.polimi.ingsw.am40.Network.Commands;

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
                do{
                    c.sendMessage("The number of players you want to play with: ");
                    line = in.nextLine();
                    if (Integer.parseInt(line) < 2 || Integer.parseInt(line) > 4) {
                        c.sendMessage("The number of players must be 2, 3 or 4! Retype please...");
                    }
                } while (Integer.parseInt(line) < 2 || Integer.parseInt(line) > 4 );
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
