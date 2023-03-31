package it.polimi.ingsw.am40.Model;

import java.util.ArrayList;

public class CommonGoalAll {

    private final int num;
    private final CommonGoalToken commgoaltok;

    public CommonGoalAll(int num, int numPlayer) {
        this.num = num;
        this.commgoaltok = new CommonGoalToken(numPlayer);
    }

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

    public int check1 (Bookshelf b) {

        ArrayList<Tile> fullSquares = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                if (sameColors(i,j,b)) {
                    fullSquares.add(b.getTile(i,j));
                }
            }
        }

        for (int i = 0; i < fullSquares.size() - 1; i++) {
            for (int j = i + 1 ; j < fullSquares.size(); j++) {
                if (fullSquares.get(i).equals(fullSquares.get(j))) {
                    if (!overlaps(fullSquares.get(i).getPos(), fullSquares.get(j).getPos())) {
                        return commgoaltok.updateScore();
                    }
                }
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
    public boolean sameColors(int x, int y, Bookshelf b) {
        if (b.getTile(x,y) != null) {
            return b.getTile(x, y).equals(b.getTile(x + 1, y)) && b.getTile(x, y).equals(b.getTile(x, y + 1)) && b.getTile(x, y).equals(b.getTile(x + 1, y + 1));
        }
        return false;
    }


    public boolean overlaps(Position pos1, Position pos2) { // return true if the two squares do overlap
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
    public int check2 (Bookshelf b) {

        int count = 0;
        int equal;
        for (int i = 0; i < 5 && count < 2; i++) {
            if (b.getBookshelf().get(i).getColumn().size() == 6) {
                equal = 0;
                for (int j = 1; j < 6 && equal == 0; j++) {
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
        if (count == 2) {
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
    public int check3(Bookshelf bookshelf) {

        possibleGroups.clear();

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
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

    public boolean contain(ArrayList<Tile> list, Tile tile) {
        for (Tile value : list) {
            if (value.equals(tile) && value.getPos().equals(tile.getPos())) {
                return true;
            }
        }
        return false;
    }
    public void groups(Bookshelf b, int x, int y, Tile prevTile) {

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
                if (x < 4 && b.getTile(x + 1, y) != null) {
                    groups(b, x + 1, y, b.getTile(x, y));
                }
                if (x > 0 && b.getTile(x - 1, y) != null) {
                    groups(b, x - 1, y, b.getTile(x, y));
                }
                if (y < 5 && b.getTile(x, y + 1) != null) {
                    groups(b, x, y + 1, b.getTile(x, y));
                }
                if (y > 0 && b.getTile(x, y - 1) != null) {
                    groups(b, x, y - 1, b.getTile(x, y));
                }
                count--;
                figure.remove(count);
            }
        } else if (prevTile.equals(tile) && contain(figure,tile) && count == 3) {
            if (x < 4) {
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
            if (y < 5) {
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
    public boolean overlap(ArrayList<Tile> figure1, ArrayList<Tile> figure2) {

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

    public boolean overlap(ArrayList<Tile> f1, ArrayList<Tile> f2, ArrayList<Tile> f3, ArrayList<Tile> f4) {

        return overlap(f1, f2) || overlap(f1, f3) || overlap(f1, f4) || overlap(f2, f3) || overlap(f2, f4) || overlap(f3, f4);

    }
    public boolean fourFigures() {

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
    public int check4(Bookshelf bookshelf) { // check due tiles vicini in qualunque posizione

        int count = 0;
        boolean[][] matrice = new boolean[5][6]; // matrice di boolean per tenere traccia di dove sono già  stato
        for (int i = 0; i < 5; i++) {               // la inizializzo tutta a true e metto a false dove passo
            for (int j = 0; j < 6; j++) {
                matrice[i][j] = true;
            }
        }

        for (int i = 0; i < 4 && count < 6; i++) {
            for (int j = 0; j < 5 && count < 6; j++) {
                if (matrice[i][j] && bookshelf.getTile(i, j) != null) {         // inizio solo se è una colonna su cui non sono ancora stato e non è vuota
                    if (bookshelf.getTile(i, j).equals(bookshelf.getTile(i + 1, j))) {
                        count++;
                        matrice[i][j] = false;
                        matrice[i + 1][j] = false;
                    } else if (bookshelf.getTile(i, j).equals(bookshelf.getTile(i, j + 1))) {
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
    public int check5(Bookshelf bookshelf) {
        int count = 0;
        ArrayList<Tile> differentTiles = new ArrayList<>(); // array where I save all the different Tiles

        for (int i = 0; i < 5 && count < 3; i++) {
            if (bookshelf.getBookshelf().get(i).getColumn().size() == 6) { // controllo solo quelli con la colonna piena
                differentTiles.clear();
                for (int j = 0; j < 6; j++) {
                    if (!differentTiles.contains(bookshelf.getTile(i,j))) { // aggiungo solo se non è già contrnuto ?? l'uguaglianza la controlla con .equals?? secondo me si
                        differentTiles.add(bookshelf.getTile(i,j));
                    }
                }
                if (differentTiles.size() < 4) {
                    count++;
                }
            }

        }
        if (count == 3) {
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
    public int check6(Bookshelf bookshelf) {
        int count = 0;
        int equal;
        for (int j = 0; j < 6 && count < 2; j++) {
            if (bookshelf.isFull(j)) {
                equal = 0;
                for (int i = 1; i < 5 && equal == 0; i++) {
                    if (bookshelf.getTile(i, j) != null) { // forse non serve ma controllo che la cella non sia vuota
                        for (int k = 0; k < i && equal == 0; k++) {
                            if (bookshelf.getTile(i, j).equals(bookshelf.getTile(k, j))) { // se trovo due celle uguali esco
                                equal = 1;
                            }
                        }
                    }
                }
                if (equal == 0) {
                    count++;
                }
            }
        }
        if (count == 2) {
            return commgoaltok.updateScore();
        } else {
            return 0;
        }
    }

    public int check7(Bookshelf bookshelf) {
        ArrayList<TileColor> differentColours = new ArrayList<>();

        // precheck che ci siano almeno 4 tile in ciascuna colonna, .get restituisce la colonna
        for (int i = 0; i < 5; i++) {
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
    public int check8(Bookshelf bookshelf) { // commongoal 8

        if (bookshelf.getTile(0,0) != null) {
            if (bookshelf.getTile(0, 0).equals(bookshelf.getTile(0, 5))) { // controllare se il tile è vuoto tile.getcolor cosa rotorna, fare bene il .equals
                if (bookshelf.getTile(0, 0).equals(bookshelf.getTile(4, 0))) {
                    if (bookshelf.getTile(0, 0).equals(bookshelf.getTile(4, 5))) {
                        return commgoaltok.updateScore();
                    }
                }
            }
        }
        return 0;
    }

    public int check9(Bookshelf bookshelf){
        int[] numcolour = {0,0,0,0,0,0};

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                if (bookshelf.getTile(i,j) != null) {
                    switch (bookshelf.getTile(i, j).getColor()) {
                        case YELLOW -> numcolour[0]++;
                        case CYAN -> numcolour[1]++;
                        case BLUE -> numcolour[2]++;
                        case WHITE -> numcolour[3]++;
                        case VIOLET -> numcolour[4]++;
                        case GREEN -> numcolour[5]++;
                    }
                }
            }
        }
        if (numcolour[0] > 5 || numcolour[1] > 5 || numcolour[2] > 5 || numcolour[3] > 5 || numcolour[4] > 5 || numcolour[5] > 5)
            return commgoaltok.updateScore();

        else{
            return 0;
        }
    }

    public int check10(Bookshelf bookshelf){
        for(int i=0; i<3;i++) {
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

    public int check11(Bookshelf bookshelf){

        int count = 1;

        for(int i=0; i<4;i++){
            if (bookshelf.getTile(i,i) != null) {
                if (bookshelf.getTile(i, i).equals(bookshelf.getTile(i + 1, i + 1)))
                    count++;
            }
        }
        if(count == 5)
            return commgoaltok.updateScore();

        count = 1;

        for(int i=0; i<4;i++){
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

    public int check12(Bookshelf bookshelf){

        int count = 0;
        if (bookshelf.getBookshelf().size() == 5) {
            for (int i = 0; i < 5; i++) {
                if (bookshelf.getBookshelf().get(i).getSize() == i + 1) {
                    count++;
                }
            }
            if (count == 5) {
                return commgoaltok.updateScore();
            }
            count = 0;
            for (int i = 0; i < 5; i++) {
                if (bookshelf.getBookshelf().get(i).getSize() == 5 - i) {
                    count++;
                }
            }
            if (count == 5) {
                return commgoaltok.updateScore();
            }
        }
        return 0;
    }



}
