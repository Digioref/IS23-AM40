package it.polimi.ingsw.am40.Model;

import java.util.ArrayList;

/**
 * Class that can generate one of the 12 commonGoals
 */
public class CommonGoal {

    /**
     * The number of the commonGoal selected
     */
    private final int num;

    /**
     * The token of the specific commonGoal, it keeps track of the points to give to the players
     */
    private final CommonGoalToken commgoaltok;

    /**
     * number of rows of the bookshelf
     */
    private final int NROW = 6;

    /**
     * number of columns of the bookshelf
     */
    private final int NCOL = 5;

    /**
     * constructor
     * @param num the number of the commonGoal choosen, goes 1 to 12
     * @param numPlayer number of player of the match
     */
    public CommonGoal(int num, int numPlayer) {
        this.num = num;
        this.commgoaltok = new CommonGoalToken(numPlayer);
    }

    /**
     * It returns a number identifying the common goal
     * @return the number identifying the common goal
     */
    public int getNum() {
        return num;
    }

    /**
     * checks if the commonGoal is completed in a bookshelf
     * @param bookshelf the bookshelf of the player
     * @return the points for the player
     */
    public int check(Bookshelf bookshelf) {
        switch (num) {
            case 1 -> {
                return check1(bookshelf);
            }
            case 2 -> {
                return check2(bookshelf);
            }
            case 3 -> {
                return check3(bookshelf);
            }
            case 4 -> {
                return check4(bookshelf);
            }
            case 5 -> {
                return check5(bookshelf);
            }
            case 6 -> {
                return check6(bookshelf);
            }
            case 7 -> {
                return check7(bookshelf);
            }
            case 8 -> {
                return check8(bookshelf);
            }
            case 9 -> {
                return check9(bookshelf);
            }
            case 10 -> {
                return check10(bookshelf);
            }
            case 11 -> {
                return check11(bookshelf);
            }
            case 12 -> {
                return check12(bookshelf);
            }
        }
        return 0;
    }

    private int check1 (Bookshelf b) {

        ArrayList<Tile> fullSquares = new ArrayList<>();

        for (int i = 0; i < NCOL - 1; i++) {
            for (int j = 0; j < NROW - 1; j++) {
                if (sameColors(i,j,b)) {
                    fullSquares.add(b.getTile(i,j));
                }
            }
        }

        for (int i = 0; i < fullSquares.size() - 1; i++) {
            for (int j = i + 1 ; j < fullSquares.size(); j++) {
                //if (fullSquares.get(i).equals(fullSquares.get(j))) {  check if they are the same colors
                    if (!overlaps(fullSquares.get(i).getPos(), fullSquares.get(j).getPos())) {
                        return commgoaltok.updateScore();
                    }
                //}
            }
        }

        return 0;

    }

    /**
     * checks if the tile with coordinates x,y is at the bottom left of a sqare made of tiles of the same color
     * @param x coordinate of the tile
     * @param y coordinate of the tile
     * @param b bookshelf reference
     * @return true if there is a square, else false
     */
    private boolean sameColors(int x, int y, Bookshelf b) {
        if (b.getTile(x,y) != null) {
            return b.getTile(x, y).equals(b.getTile(x + 1, y)) && b.getTile(x, y).equals(b.getTile(x, y + 1)) && b.getTile(x, y).equals(b.getTile(x + 1, y + 1));
        }
        return false;
    }


    private boolean overlaps(Position pos1, Position pos2) { // return true if the two squares do overlap
        int x1, x2, y1, y2;
        x1 = pos1.getX();
        y1 = pos1.getY();
        x2 = pos2.getX();
        y2 = pos2.getY();
        return x1 <= x2 + 1 && x2 <= x1 + 1 && y1 <= y2 + 1 && y1 + 1 >= y2;
    }

    /**
     * Checks if there are two columns full of all different tiles
     * @param b bookshelf of the player
     * @return the score if the condition is verified, else it returns 0
     */
    private int check2 (Bookshelf b) {

        final int TARGET = 2;
        int count = 0;
        int equal;

        for (int i = 0; i < NCOL && count < TARGET; i++) {
            if (b.getBookshelf().get(i).getColumn().size() == NROW) {
                equal = 0;
                for (int j = 1; j < NROW && equal == 0; j++) {
                    for (int k = 0; k < j && equal == 0; k++) {
                        if (b.getTile(i, j).equals(b.getTile(i, k))) { // se trovo due celle uguali esco
                            equal = 1;
                        }
                    }
                }
                if (equal == 0) {
                    count++;
                }
            }
        }
        if (count == TARGET) {
            return commgoaltok.updateScore();
        } else {
            return 0;
        }
    }

    private int count = 0;

    private ArrayList<Tile> figure = new ArrayList<>();
    private ArrayList<ArrayList<Tile>> possibleGroups = new ArrayList<>();
    /**
     * Checks if there are 4 groups of 4 tiles of the same color with one side in common (not only vertical or orizontal)
     * @param bookshelf bookshelf of the player
     * @return the score if the condition is verified, else it returns 0
     */
    private int check3(Bookshelf bookshelf) {

        //possibleGroups.clear();

        for (int i = 0; i < NCOL - 1; i++) {
            for (int j = 0; j < NROW - 1; j++) {
                if (bookshelf.getTile(i,j) != null) {
                    groups(bookshelf, i, j, bookshelf.getTile(i, j));
                }
            }
        }

        if (fourFigures()) {
            return commgoaltok.updateScore();
        }

        return 0;

    }

    private boolean contain(ArrayList<Tile> list, Tile tile) {
        for (Tile value : list) {
            if (value.equals(tile) && value.getPos().equals(tile.getPos())) {
                return true;
            }
        }
        return false;
    }
    private void groups(Bookshelf b, int x, int y, Tile prevTile) {

        Tile var = b.getTile(x,y);
        Tile tile = new Tile(var.getColor(), var.getType(), var.getPos());
        Tile nextTile;
        ArrayList<Tile> tmp;

        if (prevTile.equals(tile) && !contain(figure,tile)) {
            count++;
            figure.add(tile);
            if (count == 4) {
                tmp = new ArrayList<>(figure);
                possibleGroups.add(tmp);
                count--;
                figure.remove(count);
            } else {
                if (x < NCOL-1 && b.getTile(x + 1, y) != null) {
                    groups(b, x + 1, y, b.getTile(x, y));
                }
                if (x > 0 && b.getTile(x - 1, y) != null) {
                    groups(b, x - 1, y, b.getTile(x, y));
                }
                if (y < NROW-1 && b.getTile(x, y + 1) != null) {
                    groups(b, x, y + 1, b.getTile(x, y));
                }
                if (y > 0 && b.getTile(x, y - 1) != null) {
                    groups(b, x, y - 1, b.getTile(x, y));
                }
                count--;
                figure.remove(count);
            }
        } else if (prevTile.equals(tile) && contain(figure,tile) && count == 3) {
            if (x < NCOL-1) {
                nextTile = b.getTile(x+1,y);
                if (tile.equals(nextTile) && !contain(figure,nextTile)) {
                    figure.add(nextTile);
                    tmp = new ArrayList<>(figure);
                    possibleGroups.add(tmp);
                    figure.remove(count);
                }
            }
            if (x > 0) {
                nextTile = b.getTile(x - 1,y);
                if (tile.equals(nextTile) && !contain(figure,nextTile)) {
                    figure.add(nextTile);
                    tmp = new ArrayList<>(figure);
                    possibleGroups.add(tmp);
                    figure.remove(count);
                }
            }
            if (y < NROW-1) {
                nextTile = b.getTile(x,y + 1);
                if (tile.equals(nextTile) && !contain(figure,nextTile)) {
                    figure.add(nextTile);
                    tmp = new ArrayList<>(figure);
                    possibleGroups.add(tmp);
                    figure.remove(count);
                }
            }
            if (y > 0) {
                nextTile = b.getTile(x,y - 1);
                if (tile.equals(nextTile) && !contain(figure,nextTile)) {
                    figure.add(nextTile);
                    tmp = new ArrayList<>(figure);
                    possibleGroups.add(tmp);
                    figure.remove(count);
                }
            }
        }
    }
    private boolean overlap(ArrayList<Tile> figure1, ArrayList<Tile> figure2) {

        if (!figure1.get(0).equals(figure2.get(0))) { // if they have different color they can not overlap
            return false;
        }

        for (Tile value : figure1) {
            for (Tile tile : figure2) {
                if (value.getPos().equals(tile.getPos())) {
                    return true;
                }
            }
        }

        return false;

    }

    private boolean overlap(ArrayList<Tile> f1, ArrayList<Tile> f2, ArrayList<Tile> f3, ArrayList<Tile> f4) {

        return overlap(f1, f2) || overlap(f1, f3) || overlap(f1, f4) || overlap(f2, f3) || overlap(f2, f4) || overlap(f3, f4);

    }
    private boolean fourFigures() {

        if (possibleGroups.size() >= 4) {
            for (int i = 0; i < possibleGroups.size() - 3; i++) {
                for (int j = i + 1; j < possibleGroups.size() - 2; j++) {
                    for (int h = j + 1; h < possibleGroups.size() - 1; h++) {
                        for (int k = h + 1; k < possibleGroups.size(); k++) {
                            if (!overlap(possibleGroups.get(i), possibleGroups.get(j), possibleGroups.get(h), possibleGroups.get(k))) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return  false;
    }

    /**
     * Checks if there are 6 groups of the tiles aside of the same color
     * @param bookshelf bookshelf of the player
     * @return the score if the condition is verified, else it returns 0
     */
    private int check4(Bookshelf bookshelf) { // check due tiles vicini in qualunque posizione

        int count = 0;
        final int TARGET = 6;
        boolean[][] matrice = new boolean[NCOL][NROW]; // matrice di boolean per tenere traccia di dove sono già  stato
        for (int i = 0; i < NCOL; i++) {               // la inizializzo tutta a true e metto a false dove passo
            for (int j = 0; j < NROW; j++) {
                matrice[i][j] = true;
            }
        }

        for (int i = 0; i < NCOL && count < TARGET; i++) {
            for (int j = 0; j < NROW && count < TARGET; j++) {
                if (matrice[i][j] && bookshelf.getTile(i, j) != null) {         // inizio solo se è una colonna su cui non sono ancora stato e non è vuota
                    if (i < NCOL-1 &&bookshelf.getTile(i, j).equals(bookshelf.getTile(i + 1, j))) {
                        count++;
                        matrice[i][j] = false;
                        matrice[i + 1][j] = false;
                    } else if (j < NROW-1 && bookshelf.getTile(i, j).equals(bookshelf.getTile(i, j + 1))) {
                        count++;
                        matrice[i][j] = false;
                        matrice[i][j + 1] = false;
                    }
                }
            }
        }
        if (count == 6) {
            return commgoaltok.updateScore();
        } else {
            return 0;
        }
    }

    /**
     * Checks if there are 3 full columns with no more then 3 different types of tiles
     * @param bookshelf bookshelf of the player
     * @return the score if the condition is verified, else it returns 0
     */
    private int check5(Bookshelf bookshelf) {
        int count = 0;
        final int TARGET = 3;
        ArrayList<Tile> differentTiles = new ArrayList<Tile>(); // array where I save all the different Tiles

        for (int i = 0; i < NCOL && count < TARGET; i++) {
            if (bookshelf.getBookshelf().get(i).getColumn().size() == NROW) { // controllo solo quelli con la colonna piena
                differentTiles.clear();
                for (int j = 0; j < NROW; j++) {
                    if (!differentTiles.contains(bookshelf.getTile(i,j))) { // aggiungo solo se non è già contrnuto ?? l'uguaglianza la controlla con .equals?? secondo me si
                        differentTiles.add(bookshelf.getTile(i,j));
                    }
                }
                if (differentTiles.size() < 4) {
                    count++;
                }
            }

        }
        if (count == TARGET) {
            return commgoaltok.updateScore();
        } else {
            return 0;
        }
    }

    /**
     *  Checks if there are 2 rows full of different tiles
     * @param bookshelf bookshelf of the player
     * @return the score if the condition is verified, else it returns 0
     */
    private int check6(Bookshelf bookshelf) {
        int count = 0;
        int equal;
        final int TARGET = 2;
        for (int j = 0; j < NROW && count < TARGET; j++) {
            equal = 0;
            for (int i = 1; i < NCOL && equal == 0; i++) {
                if (bookshelf.getTile(i, j) != null) {
                    for (int k = 0; k < i && equal == 0; k++) {
                        if (bookshelf.getTile(i, j).equals(bookshelf.getTile(k, j))) { // se trovo due celle uguali esco
                            equal = 1;
                        }
                    }
                } else {
                    equal = 1;
                }
            }
            if (equal == 0) {
                count++;
            }

        }
        if (count == TARGET) {
            return commgoaltok.updateScore();
        } else {
            return 0;
        }
    }

    private int check7(Bookshelf bookshelf) {
        ArrayList<TileColor> differentColours = new ArrayList<>();

        // precheck che ci siano almeno 4 tile in ciascuna colonna, .get restituisce la colonna
        for (int i = 0; i < NCOL; i++) {
            if (bookshelf.getBookshelf().get(i).getColumn().size() < 4)
                return 0;
        }

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (!(differentColours.contains(bookshelf.getTile(i, j).getColor()))) {
                    differentColours.add(bookshelf.getTile(i, j).getColor());
                }
                if (differentColours.size() > 3)
                    return 0;
            }

        }
        return commgoaltok.updateScore();
    }

    /**
     * Checks if the four tiles in the edges of the bookshelf are the same type
     * @param bookshelf bookshelf of the player
     * @return the score if the condition is verified, else it returns 0
     */
    private int check8(Bookshelf bookshelf) { // commongoal 8

        if (bookshelf.getTile(0,0) != null) {
            if (bookshelf.getTile(0, 0).equals(bookshelf.getTile(0, NROW-1))) { // controllare se il tile è vuoto tile.getcolor cosa rotorna, fare bene il .equals
                if (bookshelf.getTile(0, 0).equals(bookshelf.getTile(NCOL-1, 0))) {
                    if (bookshelf.getTile(0, 0).equals(bookshelf.getTile(NCOL-1, NROW-1))) {
                        return commgoaltok.updateScore();
                    }
                }
            }
        }
        return 0;
    }

    private int check9(Bookshelf bookshelf){
        int[] numcolour = {0,0,0,0,0,0};
        int maxColors = 0;
        final int TARGET = 7; //before it was 5

        for (int i = 0; i < NCOL; i++) { //before NCOL-1
            for (int j = 0; j < NROW; j++) { //before   NROWS-1
                if (bookshelf.getTile(i,j) != null) {
                    switch (bookshelf.getTile(i, j).getColor()) {
                        case YELLOW -> maxColors = numcolour[0]++;
                        case CYAN -> maxColors = numcolour[1]++;
                        case BLUE -> maxColors = numcolour[2]++;
                        case WHITE -> maxColors = numcolour[3]++;
                        case VIOLET -> maxColors = numcolour[4]++;
                        case GREEN -> maxColors = numcolour[5]++;
                    }
                    if (maxColors == TARGET) {
                        System.out.println("colori tiles: " + numcolour);
                        return commgoaltok.updateScore();
                    }
                }
            }
        }
        return 0;
    }

    private int check10(Bookshelf bookshelf){
        for(int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                if (bookshelf.getTile(i,j) != null) {
                    if (bookshelf.getTile(i, j).equals(bookshelf.getTile(i, j + 2))) {
                        if (bookshelf.getTile(i, j).equals(bookshelf.getTile(i + 1, j + 1))) {
                            if (bookshelf.getTile(i, j).equals(bookshelf.getTile(i + 2, j))
                                    && bookshelf.getTile(i, j).equals(bookshelf.getTile(i + 2, j + 2))) {
                                return commgoaltok.updateScore();
                            }
                        }
                    }
                }
            }
        }
        return 0;
    }

    private int check11(Bookshelf bookshelf){

        int count = 1;

        for(int i = 0; i < 4; i++){
            if (bookshelf.getTile(i,i) != null) {
                if (bookshelf.getTile(i, i).equals(bookshelf.getTile(i + 1, i + 1)))
                    count++;
            }
        }
        if (count == 5)
            return commgoaltok.updateScore();

        count = 1;

        for(int i = 0; i < 4; i++){
            if (bookshelf.getTile(i,i+1) != null) {
                if (bookshelf.getTile(i, i + 1).equals(bookshelf.getTile(i + 1, i + 2)))
                    count++;
            }
        }
        if(count == 5)
            return commgoaltok.updateScore();

        count = 1;

        for(int i=0; i<4;i++){
            if (bookshelf.getTile(i, 4 - i) != null) {
                if (bookshelf.getTile(i, 4 - i).equals(bookshelf.getTile(i + 1, 3 - i)))
                    count++;
            }
        }
        if (count == 5)
            return commgoaltok.updateScore();

        count = 1;

        for (int i = 0; i < 4; i++) {
            if (bookshelf.getTile(i, 5 - i) != null) {
                if (bookshelf.getTile(i, 5 - i).equals(bookshelf.getTile(i + 1, 4 - i))) {
                    count++;
                }
            }
        }
        if (count == 5)
            return commgoaltok.updateScore();

        return 0;
    }

    private int check12(Bookshelf bookshelf){

        int count = 0;
        final int TARGET = 5;
        if (bookshelf.getBookshelf().size() == NCOL) {
            for (int i = 0; i < NCOL; i++) {
                if (bookshelf.getBookshelf().get(i).getSize() == i + 1) {
                    count++;
                }
            }
            if (count == TARGET) {
                return commgoaltok.updateScore();
            }
            count = 0;
            for (int i = 0; i < NCOL; i++) {
                if (bookshelf.getBookshelf().get(i).getSize() == NROW - 1 - i) {
                    count++;
                }
            }
            if (count == TARGET) {
                return commgoaltok.updateScore();
            }
        }
        return 0;
    }

    /**
     * It returns the token related to the common goal, so the remaining score achievable of the common goal
     * @return the score token of the common goal
     */
    public CommonGoalToken getCommgoaltok() {
        return commgoaltok;
    }
}
