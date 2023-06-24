package it.polimi.ingsw.am40.Network;

import it.polimi.ingsw.am40.Controller.Controller;
import it.polimi.ingsw.am40.JSONConversion.JSONConverterStoC;
import it.polimi.ingsw.am40.Model.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class VirtualView implements  IGameObserver, IGameErrorTurn{

    private String nickname;
    private Handlers clientHandler;
    private Controller controller;

    /**
     * TODO
     * @param nickname
     * @param clientHandler
     * @param controller
     */
    public VirtualView(String nickname, Handlers clientHandler, Controller controller) {
        this.nickname = nickname;
        this.clientHandler = clientHandler;
        this.controller = controller;
    }

    /**
     * TODO
     * @param numPlayers
     */
    @Override
    public void receiveNumPlayers(int numPlayers) {
        try {
            if (clientHandler != null) {
                clientHandler.sendMessage(JSONConverterStoC.normalMessage("Number of players in this game: " + numPlayers));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * TODO
     * Method to receive the list of the players
     * @param players
     */
    @Override
    public void receiveListPlayers(ArrayList<Player> players) {
        try {
            if (clientHandler != null) {
                clientHandler.sendMessage(JSONConverterStoC.createJSONPlayers(players));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void receiveCommonGoals(ArrayList<CommonGoal> commonGoals) {
        int num1 = commonGoals.get(0).getNum();
        int score1 = commonGoals.get(0).getCommgoaltok().getScore();
        int num2 = commonGoals.get(1).getNum();
        int score2 = commonGoals.get(1).getCommgoaltok().getScore();
        try {
            if (clientHandler != null) {
                clientHandler.sendMessage(JSONConverterStoC.createJSONCommonGoals(num1, score1, num2, score2));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void receivePersonalGoal(PersonalGoal personalGoal) {
        try {
            if (clientHandler != null) {
                clientHandler.sendMessage(JSONConverterStoC.createJSONPersGoal(personalGoal));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void receiveListBookshelves(ArrayList<Player> players) {
        try {
            if (clientHandler != null) {
                clientHandler.sendMessage(JSONConverterStoC.createJSONBookAll(players));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void receiveAllowedPositions(ArrayList<Position> positions, Board board) {
        try {
            if (clientHandler != null) {
                clientHandler.sendMessage(JSONConverterStoC.createJSONBoardPickable(board, positions));
            }
//            System.out.println("qui");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void receiveAvailableColumns(ArrayList<Integer> columns) {

    }

    @Override
    public void receiveBoard(Board board) {
        try {
            if (clientHandler != null) {
                clientHandler.sendMessage(JSONConverterStoC.createJSONBoard(board));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void receiveCurrentPlayer(Player player) {
        try {
            if (clientHandler != null) {
                clientHandler.sendMessage(JSONConverterStoC.createJSONCurrentPlayer(player.getNickname()));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void receiveHiddenScore(int hiddenScore) {
        try {
            if (clientHandler != null) {
                clientHandler.sendMessage(JSONConverterStoC.createJSONHiddenScore(hiddenScore));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void receiveDoneOrder(ArrayList<Tile> array) {
        try {
            if (clientHandler != null) {
                clientHandler.sendMessage(JSONConverterStoC.normalMessage("Order set!"));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void receiveFinalScore(ArrayList<Player> players, Player winner) {
        try {
            if (clientHandler != null) {
                clientHandler.sendMessage(JSONConverterStoC.createJSONFinalScore(players, winner));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        clientHandler.closeGame();
    }

    @Override
    public void receivePickedTiles(Player player) {
        try {
            if (clientHandler != null) {
                clientHandler.sendMessage(JSONConverterStoC.createJSONPickedTiles(player));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void receiveSelectedTiles(Player player) {
        try {
            if (clientHandler != null) {
                clientHandler.sendMessage(JSONConverterStoC.createJSONSelectedTiles(player));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void receiveTimer() {
        try {
            if (clientHandler != null) {
                clientHandler.sendMessage(JSONConverterStoC.normalMessage("The timer in game for disconnection is started!"));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void receiveDisconnection(String s) {
        if (clientHandler != null) {
            try {
                clientHandler.sendMessage(JSONConverterStoC.normalMessage("Player " + s + " disconnected!"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void receiveCurrentScore(Map<String, Integer> map) {
        if (clientHandler != null) {
            try {
                clientHandler.sendMessage(JSONConverterStoC.createJSONCurrentScore(map));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void receiveFirstPlayer(Player p) {
        if (clientHandler != null) {
            try {
                clientHandler.sendMessage(JSONConverterStoC.createJSONFirstPlayer(p.getNickname()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public String getNickname() {
        return nickname;
    }

    public Handlers getClientHandler() {
        return clientHandler;
    }

    @Override
    public void selectionTurnError() {
        try {
            clientHandler.sendMessage(JSONConverterStoC.createJSONError("It's not the SELECTION phase!"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void selectionError() {
        try {
            clientHandler.sendMessage(JSONConverterStoC.createJSONError("This tile can't be selected!"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void removingTurnError() {
        try {
            clientHandler.sendMessage(JSONConverterStoC.createJSONError("It's not the REMOVE phase, you have already picked up the tiles you selected!"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void pickingTurnError() {
        try {
            clientHandler.sendMessage(JSONConverterStoC.createJSONError("It's not the PICK phase!"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void orderingTurnError() {
        try {
            clientHandler.sendMessage(JSONConverterStoC.createJSONError("It's not the ORDER phase!"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void orderingError() {
        try {
            clientHandler.sendMessage(JSONConverterStoC.createJSONError("The order specified is not compatible with the number of tiles picked!"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void insertTurnError() {
        try {
            clientHandler.sendMessage(JSONConverterStoC.createJSONError("It's not the INSERT phase!"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void turnError() {
        try {
            clientHandler.sendMessage(JSONConverterStoC.createJSONError("It's not your turn!"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void insertError() {
        try {
            clientHandler.sendMessage(JSONConverterStoC.createJSONError("The column you selected is full!"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void chatError() {
        try {
            clientHandler.sendMessage(JSONConverterStoC.createJSONError("The player you want to send the message is not in this game!"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void receiveChat(GroupChat groupChat) {
        clientHandler.sendChat(JSONConverterStoC.createJSONChat(groupChat.getPublisher(), groupChat.getToplayer(), groupChat.getMessage()));
    }

    public void setClientHandler(Handlers clientHandler) {
        this.clientHandler = clientHandler;
    }
}
