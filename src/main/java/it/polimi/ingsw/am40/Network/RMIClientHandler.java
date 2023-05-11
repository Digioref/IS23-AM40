package it.polimi.ingsw.am40.Network;

import it.polimi.ingsw.am40.Client.RMIClientInterface;
import it.polimi.ingsw.am40.Controller.Controller;
import it.polimi.ingsw.am40.Controller.Lobby;
import it.polimi.ingsw.am40.JSONConversion.JSONConverterStoC;
import it.polimi.ingsw.am40.Model.Position;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Random;

public class RMIClientHandler extends Handlers {
    private RMIClientInterface rmiClient;
    public RMIClientHandler() {
        logged = false;
        lobby = new Lobby();
        setLogphase(LoggingPhase.LOGGING);
//        commands = new ArrayList<>();
//        ParsingJSONManager.commands(commands);
        messAd= new MessageAdapter();
        messAd.configure();
    }

    @Override
    public void sendMessage(String s) {
        try {
            rmiClient.receive(s);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void suggestNickname(String s) {
        Random random = new Random();
        for (int i = 0; i < NSUGGEST; i++) {
            int x = random.nextInt(10);
            int y = random.nextInt(10);
            int z = random.nextInt(10);
            try {
                rmiClient.receive(JSONConverterStoC.normalMessage(nickname + x + y + z));
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void executeCommand(ActionType at, ArrayList<Integer> arr) {
        if (logphase.equals(LoggingPhase.INGAME)) {
            switch(at) {
                case SELECT:
                    Position p = new Position(arr.get(0), arr.get(1));
                    controller.getGameController().selectTile(virtualView, p);
                    break;
                case REMOVE:
                    controller.getGameController().notConfirmSelection(virtualView);
                    break;
                case PICK:
                    controller.getGameController().pickTiles(virtualView);
                    break;
                case ORDER:
                    controller.getGameController().order(virtualView, arr);
                    break;
                case INSERT:
                    controller.getGameController().insert(virtualView, arr.get(0));
                    break;

            }
        } else {
            sendMessage(JSONConverterStoC.normalMessage("You are not playing in any game yet!"));
        }
    }

    @Override
    public void chat(String message, String name) {
        controller.getGameController().chat(name, message, nickname);
    }

    @Override
    public void getChat() {
        controller.getGameController().getChat(nickname);
    }

    @Override
    public void sendChat(String s) {
        try {
            rmiClient.receiveChat(s);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public void setRmiClient(RMIClientInterface rmiClient) {
        this.rmiClient = rmiClient;
    }


    public RMIClientInterface getRmiClient() {
        return rmiClient;
    }
}
