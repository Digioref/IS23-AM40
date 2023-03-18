package it.polimi.ingsw.Model;

import java.util.ArrayList;
import java.util.Random;

public class Model {
    private int numPlayer;
    private ArrayList<Player> players;
    private ArrayList<CommonGoal> commonGoals;
    private ArrayList<CommonGoal> currentComGoals;
    private Player firstPlayer;
    private Bag bag;
    private Board board;
    private EndToken endToken;
    private boolean hasStarted;
    private boolean hasEnded;
    private Player currentPlayer;

    public Model(int numPlayer) {
        this.numPlayer = numPlayer;
        players = new ArrayList<>();
    }

    public void addPlayer (Player p) {
        players.get(players.size() - 1).setNext(p);
        players.add(p);
        if (players.size() == numPlayer) {
            players.get(numPlayer - 1).setNext(players.get(0));
        }
    }
    public void configureGame() {
        commonGoals = new ArrayList<>(12);
        currentComGoals = new ArrayList<>(2);
        bag = new Bag();
        board = new Board(numPlayer);
        endToken = new EndToken();

        createTiles();
        for (Player player : players) {
            player.createBookshelf();
        }
        createComGoals();
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
        for (Player player : players) {
            do {
                x = rand.nextInt(12);
                if (!t.contains(x)) {
                    player.setPersonalGoal(x);
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

    public void startGame() {
        Random rand = new Random();
        firstPlayer = players.get(rand.nextInt(players.size()));
        currentPlayer = firstPlayer;
        board.config(bag);
        assignComGoal();
        assignPersonalGoal();
        setHasStarted(true);
        setHasEnded(false);
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }
    public boolean nextPlayer() {
        if (endToken.isEnd()) {
            if (currentPlayer.getNext().equals(firstPlayer)) {
                return false;
            }
        }
        else {
            currentPlayer = currentPlayer.getNext();
            return true;
        }
        return false;

    }

    public void selectTiles(Player p, Position pos) {
        if (players.contains(p)) {
            p.pickTile(pos);
        }
    }
    public void removeTiles(Player p) {
        if (players.contains(p)) {
            p.clearTilesPicked();
        }
    }

    public void setOrder (Player p, ArrayList<Tile> t) {
        if (players.contains(p)) {
            p.selectOrder(t);
        }
    }

    public void insertInBookshelf (Player p, int c) {
        if (players.contains(p)) {
            p.placeInBookshelf(c);
            endToken.updateScore(p);
            nextPlayer();
        }
    }

    public int endGame(Player p) {
        p.calculateScore();
        return p.getFinalScore();
    }

    public boolean HasStarted() {
        return hasStarted;
    }

    public void setHasStarted(boolean hasStarted) {
        this.hasStarted = hasStarted;
    }

    public boolean HasEnded() {
        return hasEnded;
    }

    public void setHasEnded(boolean hasEnded) {
        this.hasEnded = hasEnded;
    }


}
