package it.polimi.ingsw.Model;

import java.util.ArrayList;
public class Player {
    private String nickname;
    private ArrayList<Tile> tilesPicked;
    private int currentScore;
    private int finalScore;
    private Bookshelf bookshelf;
    private Board board;
    private PersonalGoal personalGoal;
    private  Player next;

    public Player(String nickname) {
        this.nickname = nickname;
        currentScore = 0;
        finalScore = 0;
    }

    public void pickTile(Position pos) {
        if (board.getGrid().containsKey(pos)) {
            tilesPicked.add(board.pick(pos));
        }
    }

    public void clearTilesPicked() {
        tilesPicked.clear();
    }

    public void createBookshelf() {
        bookshelf = new Bookshelf();
    }

    public void placeInBookshelf(int col) {
        for (Tile tile : tilesPicked) {
            bookshelf.addTile(tile, col);
        }
    }

    public void selectOrder(ArrayList<Tile> at) {
        for (int i = 0; i < tilesPicked.size(); i++) {
            tilesPicked.set(i, at.get(i));
        }
    }

    public void calculateScore() {
        finalScore = personalGoal.calcScore(bookshelf) + bookshelf.calcScore() + currentScore;
    }

    public void updateCurrScore (ArrayList<CommonGoal> commgoal) {
        for (CommonGoal commonGoal : commgoal) {
            currentScore += commonGoal.check(bookshelf);
        }
    }


    public String getNickname() {
        return nickname;
    }

    public Player getNext() {
        return next;
    }

    public void setNext(Player next) {
        this.next = next;
    }

    public void addCurrentScore(int s) {
        currentScore += s;
    }

    public ArrayList<Tile> getTilesPicked() {
        return tilesPicked;
    }

    public void setTilesPicked(ArrayList<Tile> tilesPicked) {
        this.tilesPicked = tilesPicked;
    }

    public int getCurrentScore() {
        return currentScore;
    }

    public void setCurrentScore(int currentScore) {
        this.currentScore = currentScore;
    }

    public int getFinalScore() {
        return finalScore;
    }

    public void setFinalScore(int finalScore) {
        this.finalScore = finalScore;
    }

    public Bookshelf getBookshelf() {
        return bookshelf;
    }

    public void setBookshelf(Bookshelf bookshelf) {
        this.bookshelf = bookshelf;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public PersonalGoal getPersonalGoal() {
        return personalGoal;
    }

    public void setPersonalGoal(int i) {
        this.personalGoal = new PersonalGoal(i);
    }

}
