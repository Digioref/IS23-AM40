package it.polimi.ingsw.am40.Network;

import it.polimi.ingsw.am40.Model.*;

import java.util.ArrayList;

public interface IGameObserver {
    public void update();

    public void receiveNumPlayers(int numPlayers);
    public void receiveListPlayers(ArrayList<Player> players);
    public void receiveCommonGoals(ArrayList<CommonGoal> commonGoals);
    public void receivePersonalGoal(PersonalGoal personalGoal);
    public void receiveListBookshelves(ArrayList<Bookshelf> bookshelves);
    public void receiveAllowedPositions(ArrayList<Position> positions);
    public void receiveAvailableColumns(ArrayList<Integer> columns);
    public void receiveBoard(Board board);
    public void receiveCurrentPlayer(Player player);
    public void receiveCurrentScore(int currentScore);
    public void receiveHiddenScore(int hiddenScore);
    public void receiveDoneOrder();
    public void receiveFinalScore(int finalScore);

}
