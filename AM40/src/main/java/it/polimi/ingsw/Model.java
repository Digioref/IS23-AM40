package it.polimi.ingsw;

import java.util.ArrayList;
import java.util.Random;

public class Model {
    private int numPlayer;
    private ArrayList<Player> players;
    private Player firstPlayer;
    private ArrayList<CommonGoal> commonGoals;
    private ArrayList<CommonGoal> currentComGoals;
    private Bag bag;
    private Board board;
    private EndToken endToken;

    public Model(int numPlayer, ArrayList<Player> players) {
        this.numPlayer = numPlayer;
        this.players = players;
    }


    public void configure() {
        Random rand = new Random();
        firstPlayer = players.get(rand.nextInt(players.size()));
        commonGoals = new ArrayList<CommonGoal>(12);
        currentComGoals = new ArrayList<CommonGoal>(2);
        bag = new Bag();
        board = new Board(numPlayer);
        endToken = new EndToken();

        createTiles();
        board.config(bag);
        for (int i = 0; i < players.size(); i++) {
            players.get(i).createBookshelf();
        }
        createComGoals();
        assignComGoal();
        assignPersonalGoal();
        firstPlayer.setTurn(true);
    }

    public void createTiles() {
        Tile t;
        for (int i = 0; i < 132; i++) {

            if(i < 22) {
                t = new Tile(TileColor.GREEN, TileType.CATS);
                bag.insert(t);
            }
            if(i >= 22 && i < 44) {
                t = new Tile(TileColor.WHITE, TileType.BOOKS);
                bag.insert(t);
            }
            if(i >= 44 && i < 66) {
                t = new Tile(TileColor.YELLOW, TileType.GAMES);
                bag.insert(t);
            }
            if(i >= 66 && i < 88) {
                t = new Tile(TileColor.BLUE, TileType.FRAMES);
                bag.insert(t);
            }
            if(i >= 88 && i < 110) {
                t = new Tile(TileColor.CYAN, TileType.TROPHIES);
                bag.insert(t);
            }
            if(i >= 110) {
                t = new Tile(TileColor.VIOLET, TileType.PLANTS);
                bag.insert(t);
            }
        }
    }

    public void createComGoals() {
        commonGoals.add(new CommonGoal1(numPlayer));
        commonGoals.add(new CommonGoal2(numPlayer));
        commonGoals.add(new CommonGoal3(numPlayer));
        commonGoals.add(new CommonGoal4(numPlayer));
        commonGoals.add(new CommonGoal5(numPlayer));
        commonGoals.add(new CommonGoal6(numPlayer));
        commonGoals.add(new CommonGoal7(numPlayer));
        commonGoals.add(new CommonGoal8(numPlayer));
        commonGoals.add(new CommonGoal9(numPlayer));
        commonGoals.add(new CommonGoal10(numPlayer));
        commonGoals.add(new CommonGoal11(numPlayer));
        commonGoals.add(new CommonGoal12(numPlayer));
    }

    public void assignPersonalGoal() {
        ArrayList<Integer> t = new ArrayList<>();
        Random rand = new Random();
        int x;
        for (int i = 0; i < players.size(); i++) {
            do {
                x = rand.nextInt(12);
                if (!t.contains(x)) {
                    players.get(i).setPersonalGoal(x);
                }
            } while (t.contains(x));
            t.add(x);
        }
    }

    public void assignComGoal() {
        Random rand = new Random();
        currentComGoals.add(commonGoals.get(rand.nextInt(commonGoals.size())));
        int x;
        do {
            x = rand.nextInt(commonGoals.size());
        } while (commonGoals.indexOf(currentComGoals.get(0)) == x);
        currentComGoals.add(commonGoals.get(x));
    }



    public int getNumPlayer() {
        return numPlayer;
    }

    public void setNumPlayer(int numPlayer) {
        this.numPlayer = numPlayer;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public Player getFirstPlayer() {
        return firstPlayer;
    }

    public void setFirstPlayer(Player firstPlayer) {
        this.firstPlayer = firstPlayer;
    }

    public ArrayList<CommonGoal> getCommonGoals() {
        return commonGoals;
    }

    public void setCommonGoals(ArrayList<CommonGoal> commonGoals) {
        this.commonGoals = commonGoals;
    }

    public ArrayList<CommonGoal> getCurrentComGoals() {
        return currentComGoals;
    }

    public void setCurrentComGoals(ArrayList<CommonGoal> currentComGoals) {
        this.currentComGoals = currentComGoals;
    }

    public Bag getBag() {
        return bag;
    }

    public void setBag(Bag bag) {
        this.bag = bag;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public EndToken getEndToken() {
        return endToken;
    }

    public void setEndToken(EndToken endToken) {
        this.endToken = endToken;
    }
}
