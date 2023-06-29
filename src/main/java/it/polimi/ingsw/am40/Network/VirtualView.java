package it.polimi.ingsw.am40.Network;

import it.polimi.ingsw.am40.Controller.Controller;
import it.polimi.ingsw.am40.JSONConversion.JSONConverterStoC;
import it.polimi.ingsw.am40.Model.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

/**
 * <p>This class receives all the updates of the game state and all the errors cause by wrong actions of the player and sends everything to the client</p>
 * <p>It is an observer registered to the game, which is the observable</p>
 */
public class VirtualView implements  IGameObserver, IGameErrorTurn{

    private String nickname;
    private Handlers clientHandler;
    private Controller controller;

    /**
     * Constructor that initializes the features according to the parameters
     * @param nickname nickname of the player to whom the virtual view is associated
     * @param clientHandler handler of the player
     * @param controller controller of the game to which the virtual view is registered
     */
    public VirtualView(String nickname, Handlers clientHandler, Controller controller) {
        this.nickname = nickname;
        this.clientHandler = clientHandler;
        this.controller = controller;
    }

    /**
     * It receives the number of players from the game and sends it to the client
     * @param numPlayers number of players
     */
    @Override
    public void receiveNumPlayers(int numPlayers) {
        try {
            if (clientHandler != null) {
                clientHandler.sendMessage(JSONConverterStoC.normalMessage("Number of players in this game: " + numPlayers));
            }
        } catch (IOException e) {
            System.out.println("Socket not available");
            clientHandler.close();
        }
    }

    /**
     * It receives the players from the game and sends their nicknames to the client
     * @param players players of the game
     */
    @Override
    public void receiveListPlayers(ArrayList<Player> players) {
        try {
            if (clientHandler != null) {
                clientHandler.sendMessage(JSONConverterStoC.createJSONPlayers(players));
            }
        } catch (IOException e) {
            System.out.println("Socket not available");
            clientHandler.close();
        }
    }

    /**
     * It receives the common goals from the game and sends them to the client
     * @param commonGoals common goals of the game
     */
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
            System.out.println("Socket not available");
            clientHandler.close();
        }
    }

    /**
     * It receives the personal goal from the game and sends it to the client
     * @param personalGoal personal goal
     */
    @Override
    public void receivePersonalGoal(PersonalGoal personalGoal) {
        try {
            if (clientHandler != null) {
                clientHandler.sendMessage(JSONConverterStoC.createJSONPersGoal(personalGoal));
            }
        } catch (IOException e) {
            System.out.println("Socket not available");
            clientHandler.close();
        }
    }

    /**
     * It receives the bookshelves from the game and sends them to the client
     * @param players players of the game; from the players, their bookshelves are obtained
     */
    @Override
    public void receiveListBookshelves(ArrayList<Player> players) {
        try {
            if (clientHandler != null) {
                clientHandler.sendMessage(JSONConverterStoC.createJSONBookAll(players));
            }
        } catch (IOException e) {
            System.out.println("Socket not available");
            clientHandler.close();
        }
    }

    /**
     * It receives the positions of the tiles that can be selected from the game and sends them to the client
     * @param positions the positions of the tile already selected
     * @param board the board of the game
     */
    @Override
    public void receiveAllowedPositions(ArrayList<Position> positions, Board board) {
        try {
            if (clientHandler != null) {
                clientHandler.sendMessage(JSONConverterStoC.createJSONBoardPickable(board, positions));
            }
//            System.out.println("qui");
        } catch (IOException e) {
            System.out.println("Socket not available");
            clientHandler.close();
        }
    }



    /**
     * It receives the board from the game and sends it to the client
     * @param board the board
     */
    @Override
    public void receiveBoard(Board board) {
        try {
            if (clientHandler != null) {
                clientHandler.sendMessage(JSONConverterStoC.createJSONBoard(board));
            }
        } catch (IOException e) {
            System.out.println("Socket not available");
            clientHandler.close();
        }
    }

    /**
     * It receives the current player from the game and sends it to the client
     * @param player current player
     */
    @Override
    public void receiveCurrentPlayer(Player player) {
        try {
            if (clientHandler != null) {
                clientHandler.sendMessage(JSONConverterStoC.createJSONCurrentPlayer(player.getNickname()));
            }
        } catch (IOException e) {
            System.out.println("Socket not available");
            clientHandler.close();
        }
    }

    /**
     * It receives the hidden score of the player from the game and sends it to the client
     * @param hiddenScore the hidden score of the player
     */
    @Override
    public void receiveHiddenScore(int hiddenScore) {
        try {
            if (clientHandler != null) {
                clientHandler.sendMessage(JSONConverterStoC.createJSONHiddenScore(hiddenScore));
            }
        } catch (IOException e) {
            System.out.println("Socket not available");
            clientHandler.close();
        }
    }

    /**
     * It receives the order of the tiles picked from the game and sends that the ordering went well to the client
     * @param array the array of the tiles in the order specified by the player
     */
    @Override
    public void receiveDoneOrder(ArrayList<Tile> array) {
        try {
            if (clientHandler != null) {
                clientHandler.sendMessage(JSONConverterStoC.normalMessage("Order set!"));
            }
        } catch (IOException e) {
            System.out.println("Socket not available");
            clientHandler.close();
        }
    }

    /**
     * It receives the final score of each player from the game and sends it to the client
     * @param players players of the game
     * @param winner winner
     */
    @Override
    public void receiveFinalScore(ArrayList<Player> players, Player winner) {
        try {
            if (clientHandler != null) {
                clientHandler.sendMessage(JSONConverterStoC.createJSONFinalScore(players, winner));
            }
        } catch (IOException e) {
            System.out.println("Socket not available");
            clientHandler.close();
        }
        clientHandler.closeGame();
    }

    /**
     * It receives the picked tiles from the game and sends them to the client
     * @param player player who has picked the tiles
     */
    @Override
    public void receivePickedTiles(Player player) {
        try {
            if (clientHandler != null) {
                clientHandler.sendMessage(JSONConverterStoC.createJSONPickedTiles(player));
            }
        } catch (IOException e) {
            System.out.println("Socket not available");
            clientHandler.close();
        }
    }

    /**
     * It receives the tiles selected by the players from the game and sends them to the client
     * @param player player who has selected the tiles
     */
    @Override
    public void receiveSelectedTiles(Player player) {
        try {
            if (clientHandler != null) {
                clientHandler.sendMessage(JSONConverterStoC.createJSONSelectedTiles(player));
            }
        } catch (IOException e) {
            System.out.println("Socket not available");
            clientHandler.close();
        }
    }

    /**
     * It receives the starting of the timer from the game and sends a message, specifying that the disconnection timer has started, to the client
     */
    @Override
    public void receiveTimer() {
        try {
            if (clientHandler != null) {
                clientHandler.sendMessage(JSONConverterStoC.normalMessage("The timer in game for disconnection is started!"));
            }
        } catch (IOException e) {
            System.out.println("Socket not available");
            clientHandler.close();
        }
    }

    /**
     * It receives the nickname of the player who disconnected from the game and sends it to the client
     * @param s nickname of the disconnected player
     */
    @Override
    public void receiveDisconnection(String s) {
        if (clientHandler != null) {
            try {
                clientHandler.sendMessage(JSONConverterStoC.normalMessage("Player " + s + " disconnected!"));
            } catch (IOException e) {
                System.out.println("Socket not available");
                clientHandler.close();
            }
        }
    }
    /**
     * It receives the current score of each player from the game and sends it to the client
     * @param map a map between the nicknames of the players and theirs current scores
     */
    @Override
    public void receiveCurrentScore(Map<String, Integer> map) {
        if (clientHandler != null) {
            try {
                clientHandler.sendMessage(JSONConverterStoC.createJSONCurrentScore(map));
            } catch (IOException e) {
                System.out.println("Socket not available");
                clientHandler.close();
            }
        }
    }
    /**
     * It receives the first player from the game and sends his nickname to the client
     * @param p first player
     */
    @Override
    public void receiveFirstPlayer(Player p) {
        if (clientHandler != null) {
            try {
                clientHandler.sendMessage(JSONConverterStoC.createJSONFirstPlayer(p.getNickname()));
            } catch (IOException e) {
                System.out.println("Socket not available");
                clientHandler.close();
            }
        }
    }

    @Override
    public void receiveCommonGoalDone(String name, int index, int score) {
        if (clientHandler != null) {
            try {
                clientHandler.sendMessage(JSONConverterStoC.createJSONCGDone(name, index, score));
            } catch (IOException e) {
                System.out.println("Socket not available");
                clientHandler.close();
            }
        }
    }

    @Override
    public void receiveReconnection(String s) {
        try {
            clientHandler.sendMessage(JSONConverterStoC.normalMessage("Player "+ s + " reconnected!"));
        } catch (IOException e) {
            System.out.println("Socket not available");
            clientHandler.close();
        }
    }

    @Override
    public void receiveBookshelf(Bookshelf bookshelf) {
        try {
            clientHandler.sendMessage(JSONConverterStoC.createJSONBook(bookshelf));
        } catch (IOException e) {
            System.out.println("Socket not available");
            clientHandler.close();
        }
    }

    /**
     * It returns the nickname
     * @return the attribute nickname
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * It returns the handler
     * @return the attribute clientHandler
     */
    public Handlers getClientHandler() {
        return clientHandler;
    }

    /**
     * It receives the selection turn error because it isn't the selection phase but the player has typed the select command
     */
    @Override
    public void selectionTurnError() {
        try {
            clientHandler.sendMessage(JSONConverterStoC.createJSONError("It's not the SELECTION phase!"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * It receives the error related to the selection of a tile that can't be selected
     */
    @Override
    public void selectionError() {
        try {
            clientHandler.sendMessage(JSONConverterStoC.createJSONError("This tile can't be selected!"));
        } catch (IOException e) {
            System.out.println("Socket not available");
            clientHandler.close();
        }
    }

    /**
     * It receives the remove turn error because it isn't the remove phase but the player has typed the remove command
     */
    @Override
    public void removingTurnError() {
        try {
            clientHandler.sendMessage(JSONConverterStoC.createJSONError("It's not the REMOVE phase, you have already picked up the tiles you selected!"));
        } catch (IOException e) {
            System.out.println("Socket not available");
            clientHandler.close();
        }
    }

    /**
     * It receives the pick turn error because it isn't the pick phase but the player has typed the pick command
     */
    @Override
    public void pickingTurnError() {
        try {
            clientHandler.sendMessage(JSONConverterStoC.createJSONError("It's not the PICK phase!"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * It receives the order turn error because it isn't the ordering phase but the player has typed the order command
     */
    @Override
    public void orderingTurnError() {
        try {
            clientHandler.sendMessage(JSONConverterStoC.createJSONError("It's not the ORDER phase!"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * It receives the order error because the order specified by the user is not compatible with the number of selected tiles
     */
    @Override
    public void orderingError() {
        try {
            clientHandler.sendMessage(JSONConverterStoC.createJSONError("The order specified is not compatible with the number of tiles picked!"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * It receives the insert turn error because it isn't the insert phase but the player has typed the insert command
     */
    @Override
    public void insertTurnError() {
        try {
            clientHandler.sendMessage(JSONConverterStoC.createJSONError("It's not the INSERT phase!"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * It receives the turn error because it isn't the turn of the player, but he has typed a command
     */
    @Override
    public void turnError() {
        try {
            if(clientHandler != null)
              clientHandler.sendMessage(JSONConverterStoC.createJSONError("It's not your turn!"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * It receives the insert error because the column specified by the player is full
     */
    @Override
    public void insertError() {
        try {
            clientHandler.sendMessage(JSONConverterStoC.createJSONError("The column you selected is full!"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * It receives the chat error because the nickname of the receiver is not in that game
     */
    @Override
    public void chatError() {
        try {
            clientHandler.sendMessage(JSONConverterStoC.createJSONError("The player you want to send the message is not in this game!"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void pickColumnFullError() {
        try {
            clientHandler.sendMessage(JSONConverterStoC.createJSONError("There is no column with available space equal to the number of tiles picked!"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * It receives the chat from the game and sends it to the client
     * @param groupChat the game chat
     */
    public void receiveChat(GroupChat groupChat) {
        clientHandler.sendChat(JSONConverterStoC.createJSONChat(groupChat.getPublisher(), groupChat.getToplayer(), groupChat.getMessage()));
    }

    /**
     * Sets the parameter clientHandler to the parameter passed
     * @param clientHandler handler of the player
     */
    public void setClientHandler(Handlers clientHandler) {
        this.clientHandler = clientHandler;
    }


}
