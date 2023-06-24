package it.polimi.ingsw.am40.Network;

import it.polimi.ingsw.am40.Model.*;

import java.util.ArrayList;
import java.util.Map;

/**
 * todo
 */
public interface IGameObserver {

    public void receiveNumPlayers(int numPlayers);
    public void receiveListPlayers(ArrayList<Player> players);
    public void receiveCommonGoals(ArrayList<CommonGoal> commonGoals);
    public void receivePersonalGoal(PersonalGoal personalGoal);
    public void receiveListBookshelves(ArrayList<Player> players);
    public void receiveAllowedPositions(ArrayList<Position> positions, Board board);
    public void receiveBoard(Board board);
    public void receiveCurrentPlayer(Player player);
    public void receiveHiddenScore(int hiddenScore);
    public void receiveDoneOrder(ArrayList<Tile> array);
    public void receiveFinalScore(ArrayList<Player> players, Player winner);
    public void receivePickedTiles(Player player);
    public void receiveSelectedTiles(Player player);
    public void receiveTimer();
    public void receiveDisconnection(String s);
    public void receiveCurrentScore(Map<String, Integer> map);
    void receiveFirstPlayer(Player p);

}
