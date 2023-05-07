package it.polimi.ingsw.am40.CLI;

import it.polimi.ingsw.am40.Model.Position;

import java.util.ArrayList;
import java.util.Map;

public interface View {
    public void chooseConnection();
    public void showCurrentPlayer(String s);
    public void showCurrentScore(Map<String, Integer> map);
    public void showHiddenScore(int score);
    public void showCommonGoals(Map<Integer, Integer> map);
    public void showPersonalGoal(Map<String, String> map);
    public void showBoard(Map<String, String> map);
    public void showCurrentBookshelf(Map<String, String> map);
    public void showAllBookshelves(Map<String, Map<String, String>> map);
    public void showBookshelf(Map<String, String> map);
    public void showBoardPickable(Map<String, String> map, ArrayList<Position> arr, Map<String, String> board);
    public void showSelectedTiles(Map<String, String> map, String s);
    public void showPickedTiles(Map<String, String> map, String s);
    public void showFinalScore(Map<String, Integer> map, String winner);
    public void showPlayers(ArrayList<String> names);
    public void printMessage(String s);

}
