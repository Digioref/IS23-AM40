package it.polimi.ingsw.am40.CLI;

import it.polimi.ingsw.am40.Client.LaunchClient;
import it.polimi.ingsw.am40.Client.SocketClient;
import it.polimi.ingsw.am40.JSONConversion.JSONConverterCtoS;
import it.polimi.ingsw.am40.Model.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Map;

public class CliView implements View{
//    private Game game;

    private Colors color = new Colors();

//    public CliView(Game game) {
//        this.game = game;
//    }

    private void printEqual() {
        System.out.print(color.whiteBg() + color.black() + " = " + color.rst());
    }

    private void printNotEqual() {
        int ch = 8800;
        char notEqual = (char) ch;
        System.out.print(color.whiteBg() + color.black() + " " + notEqual + " " + color.rst());
    }
    private void printEmpty() {
        System.out.print(color.whiteBg() + "   " + color.rst());
    }

    public void showPlayers(ArrayList<String> names) {
        System.out.println(color.blackBg() + " Players " + color.rst());
        for (String s: names) {
            System.out.println(color.greenBg() + s + color.rst());
        }
        System.out.println("\n");
    }

    public String printTile(String s) {
        switch(s) {
            case "GREEN":
                return color.greenBg() + " G " + color.rst();
            case "WHITE":
                return color.whiteBg() + " W " + color.rst();
            case "YELLOW":
                return color.yellowBg() + " Y " + color.rst();
            case "BLUE":
                return color.blueBg() + " B " + color.rst();
            case "CYAN":
                return color.cyanBg() + " C " + color.rst();
            case "VIOLET":
                return color.purpleBg() + " V " + color.rst();
            default:
                return color.blackBg() + "   " + color.rst();
        }
    }

    public void showCurrentPlayer(String s) {
        System.out.println(color.blackBg() + "Current player:" + color.rst() + " " + s + "\n");
    }

    public void showCurrentScore(Map<String, Integer> map) {
        System.out.println(color.blackBg() + " Current Score " + color.rst());
        for (String s : map.keySet()) {
            System.out.println(color.greenBg() + color.black() + " " + s + " " + map.get(s) + " " + color.rst());
        }
        System.out.println();
    }

    public void showHiddenScore(int score) {
        System.out.println(color.blackBg() + " This is your actual score:" + color.blueBg() + color.black() + " " + score + " " + color.rst() + "\n");
    }

    public void showCommonGoals(Map<Integer, Integer> map) {
        int c = 0;
        for (Integer x : map.keySet()) {

            if (c == 0) {
                System.out.println("First CommonGoal:");
            } else {
                System.out.println("Second CommonGoal: ");
            }

            switch (x) {
                case 1 -> {
                    // CG 1
                    for (int i = 0; i < 4; i++) {
                        printEqual();
                        if (i == 1) {
                            System.out.println(color.red() + " x2" + color.rst());
                        }
                    }
                    System.out.println();
                    System.out.println("Two groups each containing 4 tiles of the same type in a 2x2 square.\n" +
                            "The tiles of one square can be different from those of the other square.\n");
                    System.out.println("The score obtainable is: " + map.get(x) + "\n");
                }
                case 2 -> {
                    // CG 2
                    for (int i = 0; i < 6; i++) {
                        printNotEqual();
                        if (i == 2) {
                            System.out.print(color.red() + " x2" + color.rst());
                        }
                        System.out.println();
                    }
                    System.out.println("Two columns each formed by 6 different types of tiles.\n");
                    System.out.println("The score obtainable is: " + map.get(x) + "\n");
                }
                case 3 -> {
                    // CG 3
                    for (int i = 0; i < 4; i++) {
                        printEqual();
                        if (i == 1) {
                            System.out.print(color.red() + " x4" + color.rst());
                        }
                        System.out.println();
                    }
                    System.out.println("Four groups each containing at least\n" +
                            "4 tiles of the same type (not necessarily in the depicted shape).\n" +
                            "The tiles of one group can be different from those of another group.\n");
                    System.out.println("The score obtainable is: " + map.get(x) + "\n");
                }
                case 4 -> {
                    // CG 4
                    printEqual();
                    System.out.println(color.red() + " x6" + color.rst());
                    printEqual();
                    System.out.println();
                    System.out.println("Six groups each containing at least\n" +
                            "2 tiles of the same type (not necessarily in the depicted shape).\n" +
                            "The tiles of one group can be different from those of another group.\n");
                    System.out.println("The score obtainable is: " + map.get(x) + "\n");
                }
                case 5 -> {
                    // CG 5
                    for (int i = 0; i < 6; i++) {
                        printEmpty();
                        if (i == 2) {
                            System.out.print(color.red() + " x3  MAX 3 ");
                            printNotEqual();
                        }
                        System.out.println();
                    }
                    System.out.println("Three columns each formed by 6 tiles of maximum three different types. \n" +
                            "One column can show the same or a different combination of another column.");
                    System.out.println();
                    System.out.println("The score obtainable is: " + map.get(x) + "\n");
                }
                case 6 -> {
                    // CG 6
                    for (int i = 0; i < 5; i++) {
                        printNotEqual();
                    }
                    System.out.println(color.red() + " x2" + color.rst() + "\n");
                    System.out.println("Two lines each formed by 5 different types of tiles." +
                            "One line can show the same or a different combination of the other line.\n");
                    System.out.println("The score obtainable is: " + map.get(x) + "\n");
                }
                case 7 -> {
                    for (int i = 0; i < 5; i++) {
                        printEmpty();
                    }
                    System.out.print(color.red() + " x4  MAX 3 ");
                    printNotEqual();
                    System.out.println();
                    System.out.println("Four lines each formed by 5 tiles of maximum three different types. \n" +
                            "One line can show the same or a different combination of another line.\n");
                    System.out.println("The score obtainable is: " + map.get(x) + "\n");
                }
                case 8 -> {
                    printEqual();
                    System.out.print("         ");
                    printEqual();
                    System.out.print("\n\n\n\n\n");
                    printEqual();
                    System.out.print("         ");
                    printEqual();
                    System.out.println();
                    System.out.println("Four tiles of the same type in the four corners of the bookshelf.\n");
                    System.out.println("The score obtainable is: " + map.get(x) + "\n");

                }
                case 9 -> {
                    System.out.print("  ");
                    for (int i = 0; i < 8; i++) {
                        printEqual();
                        System.out.print("  ");
                        if (i == 1 || i == 4 || i == 7) {
                            System.out.println("\n");
                        }
                    }
                    System.out.println("Eight tiles of the same type. \n" +
                            "There’s no restriction about the position of these tiles.");
                    System.out.println();
                    System.out.println("The score obtainable is: " + map.get(x) + "\n");
                }
                case 10 -> {
                    for (int i = 0; i < 9; i++) {
                        if (i%2 == 0) {
                            printEqual();
                        } else {
                            System.out.print("   ");
                        }
                        if (i%3 == 2) {
                            System.out.println();
                        }
                    }
                    System.out.println("Five tiles of the same type forming an X.");
                    System.out.println();
                    System.out.println("The score obtainable is: " + map.get(x) + "\n");
                }
                case 11 -> {
                    // CG 11
                    for (int i = 0; i < 5; i++) {
                        for (int j = 0; j < i; j++) {
                            System.out.print("   ");
                        }
                        printEqual();
                        System.out.println();
                    }
                    System.out.println("Five tiles of the same type forming a diagonal.");
                    System.out.println();
                    System.out.println("The score obtainable is: " + map.get(x) + "\n");
                }
                case 12 -> {
                    for (int i = 0; i < 5; i++) {
                        for (int j = 0; j < i+1; j++) {
                            printEmpty();
                        }
                        System.out.println();
                    }
                    System.out.println("Five columns of increasing or decreasing height. \n" +
                            "Starting from the first column on the left or on the right, \n" +
                            "each next column must be made of exactly one more tile. \n" +
                            "Tiles can be of any type.");
                    System.out.println();
                    System.out.println("The score obtainable is: " + map.get(x) + "\n");
                }
                default -> {
                    System.out.println(color.red() + "You choose the common goal number " + x + " but you should have a number from 1 to 12...\n" + color.rst());
                }
            }
            c++;
        }
    }

    public void showPersonalGoal(Map<String, String> map) {
        System.out.println(" Here you can see your Personal Goal\n");
        for (int i = 5; i >= 0 ; i--) {
            for (int j = 0; j < 5; j++) {
                Position pr = new Position(j, i);
                if (map.containsKey(pr.getKey())) {
                    System.out.print(printTile(map.get(pr.getKey())));
                }
                else {
                    System.out.printf(color.blackBg() + "   " + color.rst());
                }
            }
            System.out.print("\n");
        }
        System.out.println();
    }

    public void showBoard(Map<String, String> map) {
        for (int row = 4; row > -5; row--) {
            if (row >= 0) {
                System.out.printf(" %d ", row);
            } else {
                System.out.printf("%d ", row);
            }
            for (int col = -4; col < 5; col++) {
                Position pos = new Position(col, row);
                if ((map.containsKey(pos.getKey())) && !(map.get(pos.getKey()).equals("NOCOLOR"))) {
                    System.out.printf(color.blackBg());
                    System.out.printf(printTile(map.get(pos.getKey())));
                } else {
                    System.out.printf(color.blackBg() + "   " + color.rst());
                }
            }
            System.out.printf("\n");
        }
        System.out.printf("   ");
        for (int i = -4; i < 5; i++) {
            if (i < 0) {
                System.out.printf("%d ", i);
            } else {
                System.out.printf(" %d ", i);
            }
        }
        System.out.println("\n");
    }

    public void showCurrentBookshelf(Map<String, String> map) {
        System.out.println("Your bookshelf:\n");
        showBookshelf(map);
    }

    public void showAllBookshelves(Map<String, Map<String, String>> map) {
        System.out.println("Here you can see all the bookshelfs\n");
        for (String s: map.keySet()) {
            System.out.println(s + "'s bookshelf:");
            showBookshelf(map.get(s));
            System.out.printf("\n");
        }
        System.out.println();
    }

    public void showBookshelf(Map<String, String> map) {
        for (int row = 5; row >= 0; row--) {
            for (int col = 0; col < 5; col++) {
                Position p = new Position(col, row);
                if (map.containsKey(p.getKey()) && !(map.get(p.getKey()).equals("NOCOLOR"))) {
                    System.out.printf(color.blackBg());
                    System.out.printf(printTile(map.get(p.getKey())));
                } else {
                    System.out.printf(color.blackBg() + "   " + color.rst());
                }
            }
            System.out.printf("\n");
        }
        System.out.println();
    }

    public void showBoardPickable(Map<String, String> map, ArrayList<Position> arr, Map<String, String> board) {
        System.out.println("You can choose only the tiles with the black letter");
        for (int row = 4; row > -5; row--) {
            if (row >= 0) {
                System.out.printf(" %d ", row);
            } else {
                System.out.printf("%d ", row);
            }
            for (int col = -4; col < 5; col++) {
                Position pos = new Position(col, row);
                if (board.containsKey(pos.getKey())) {
                    if (map.containsKey(pos.getKey()))  {
                        System.out.printf(color.black());
                        System.out.printf(printTile(map.get(pos.getKey())));
                    } else if (arr.contains(pos)) {
                        System.out.printf(color.rst() + "   ");
                    } else {
                        System.out.printf(printTile(board.get(pos.getKey())));
                    }
                } else {
                    System.out.printf(color.blackBg() + "   " + color.rst());
                }
            }
            System.out.printf("\n");
        }
        System.out.printf("   ");
        for (int i = -4; i < 5; i++) {
            if (i < 0) {
                System.out.printf("%d ", i);
            } else {
                System.out.printf(" %d ", i);
            }
        }
        System.out.println("\n");
    }

    public void showSelectedTiles(Map<String, String> map, String s) {
        if (map.isEmpty()) {
            System.out.println("You haven't selected any Tile\n");
        } else {
            System.out.println( "Player " + s + " has selected the following Tiles");
            for (String s1 : map.keySet()) {
                System.out.printf(printTile(map.get(s1)) + s1 + " ");
            }
            System.out.println("\n");
        }
    }

    public void showPickedTiles(Map<String, String> map, String s) {
        if (map.isEmpty()) {
            System.out.println("You haven't picked Tiles yet\n");
        } else {
            System.out.println("Player " + s + " has picked the following Tiles");
            for (String s1: map.keySet()) {
                System.out.printf(printTile(map.get(s1)) + s1 + " ");
            }
            System.out.println("\n");
        }
    }

    public void showFinalScore(Map<String, Integer> map, String winner) { // different color for the higher score, but if two have the same score I show both as winners, I should do the control on the winner, do I have this information?
        System.out.println(color.blackBg() + " Final Scores " + color.rst());

        for (String s : map.keySet()) {
            if (s.equals(winner)) {
                System.out.println(color.greenBg() + color.black() + " " + s + " " + map.get(s) + " " + color.rst());
            } else {
                System.out.println(color.yellowBg() + color.black() + " " + s + " " + map.get(s) + " " + color.rst());
            }
        }
        System.out.println();
    }

    public void printMessage(String s) {
        System.out.println(s);
    }

    @Override
    public void chat(SocketClient socketClient) {
        boolean quit = false;

        while (!quit) {
            System.out.println(color.blackBg() + " You are in the Chat!" + color.rst());
            System.out.println("Write the message: ");
            try {
                String message = socketClient.getStdIn().readLine();
                if (message.toLowerCase().equals("q")) {
                    quit = true;
                } else {
                    System.out.println("to [playerName] (leave it blank if it is a broadcast message): ");
                    String receiver = socketClient.getStdIn().readLine();
                    if (receiver.length() == 0)
                        receiver = null;
                    JSONConverterCtoS jconv = new JSONConverterCtoS();
                    jconv.toJSONChat(receiver, message);
                    socketClient.getOut().println(jconv.toString());
                    socketClient.getOut().flush();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }

    @Override
    public void showChat(ArrayList<String> array1, ArrayList<String> array2, ArrayList<String> array3, String nickname) {
        System.out.println(color.cyanBg() + " Chat " + color.rst() + "\n");
        for (int i = 0; i < array1.size(); i++) {
            if(nickname.equals(array2.get(i)) || array2.get(i).equals("all")) {
                System.out.println(color.greenBg() + array1.get(i) + color.rst() + "  -->  " + color.yellowBg() + array2.get(i) + color.rst() + " : " + array3.get(i) + "\n");
            }
        }
    }

    @Override
    public void chooseConnection() {
        String choice;
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        printMessage("Choose the connection type:");
        do{
            printMessage("Socket[S] or RMI[R] ?:");
            try {
                choice = stdIn.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            choice = choice.toUpperCase();
            if(choice.equals("S"))
                choice = "SOCKET";
            else if(choice.equals("R"))
                choice = "RMI";
        }while(!choice.equals("RMI") && !choice.equals("SOCKET"));
        printMessage("Insert the server IP (or localhost [L]):");
        String ip;
        try {
            ip = stdIn.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if(ip.equalsIgnoreCase("L"))
            ip = "localhost";
        LaunchClient.startConnection(choice, ip);
    }


//    public void showBoard() {
//        for (int row = 4; row > -5; row--) {
//            if (row >= 0) {
//                System.out.printf(" %d ", row);
//            } else {
//                System.out.printf("%d ", row);
//            }
//            for (int col = -4; col < 5; col++) {
//                Position pos = new Position(col, row);
//                Tile tile = game.getBoard().getGrid().get(pos.getKey());
//                if (tile != null) {
//                    System.out.printf(color.blackBg());
//                    System.out.printf(tile.print());
//                } else {
//                    System.out.printf(color.blackBg() + "   " + color.rst());
//                }
//            }
//            System.out.printf("\n");
//        }
//        System.out.printf("   ");
//        for (int i = -4; i < 5; i++) {
//            if (i < 0) {
//                System.out.printf("%d ", i);
//            } else {
//                System.out.printf(" %d ", i);
//            }
//        }
//        System.out.println("\n");

//    }
//    public void showBoardPickable() {
//        System.out.println("You can choose only the tiles with the black letter");
//        for (int row = 4; row > -5; row--) {
//            if (row >= 0) {
//                System.out.printf(" %d ", row);
//            } else {
//                System.out.printf("%d ", row);
//            }
//            for (int col = -4; col < 5; col++) {
//                Position pos = new Position(col, row);
//                Tile tile = game.getBoard().getGrid().get(pos.getKey());
//                if (tile != null) {
//                    if (game.getBoard().getPickableTiles().contains(tile.getPos()) ) {
//                        System.out.printf(color.black());
//                        System.out.printf(tile.print());
//                    } else if (game.getCurrentPlayer().getSelectedPositions().contains(tile.getPos())) {
//                        System.out.printf(color.rst() + "   ");
//                    } else {
//                        System.out.printf(tile.print());
//                    }
//                } else {
//                    System.out.printf(color.blackBg() + "   " + color.rst());
//                }
//            }
//            System.out.printf("\n");
//        }
//        System.out.printf("   ");
//        for (int i = -4; i < 5; i++) {
//            if (i < 0) {
//                System.out.printf("%d ", i);
//            } else {
//                System.out.printf(" %d ", i);
//            }
//        }
//        System.out.println("\n");

//    }
//    public void showCurrentBookshelf() {
//        System.out.println(game.getCurrentPlayer().getNickname() + "'s bookshelf");
//        Bookshelf b = game.getCurrentPlayer().getBookshelf();
//        for (int row = 5; row >= 0; row--) {
//            for (int col = 0; col < 5; col++) {
//                if (b.getTile(col, row) != null) {
//                    System.out.printf(color.blackBg());
//                    System.out.printf(b.getTile(col, row).print());
//                } else {
//                    System.out.printf(color.blackBg() + "   " + color.rst());
//                }
//            }
//            System.out.printf("\n");
//        }
//        System.out.println();

//    }
//    public void showAllBookshelves() {
//        System.out.println("Here you can see all the bookshelfs\n");
//        showCurrentBookshelf();
//        for (Player p : game.getPlayers()) {
//            if (!p.equals(game.getCurrentPlayer())) {
//                System.out.println(p.getNickname() + "'s bookshelf");
//                Bookshelf b = p.getBookshelf();
//                for (int row = 5; row >= 0; row--) {
//                    for (int col = 0; col < 5; col++) {
//                        if (b.getTile(col, row) != null) {
//                            System.out.printf(color.blackBg());
//                            System.out.printf(b.getTile(col, row).print());
//                        } else {
//                            System.out.printf(color.blackBg() + "   " + color.rst());
//                        }
//                    }
//                    System.out.printf("\n");
//                }
//                System.out.println();
//            }
//        }

//    }
//    public void showCurrentPlayer() {
//
//        System.out.println(game.getCurrentPlayer().getNickname() + "\n");
//

//    }
//    public void showPickedTiles() {
//        ArrayList<Tile> selectedTiles = game.getCurrentPlayer().getTilesPicked();
//        if (selectedTiles.size() == 0) {
//            System.out.println("You haven't picked Tiles yet\n");
//        } else {
//            System.out.println(game.getCurrentPlayer().getNickname() + " has picked the following Tiles");
//            for (Tile tile : selectedTiles) {
//                System.out.printf(tile.print() + tile.getPos().toString() + " ");
//            }
//            System.out.println("\n");
//        }
//

//    }
//    public void showSelectedTiles() {
//        ArrayList<Position> selectedTiles = game.getCurrentPlayer().getSelectedPositions();
//        if (selectedTiles.size() == 0) {
//            System.out.println("You haven't selected any Tile\n");
//        } else {
//            System.out.println(game.getCurrentPlayer().getNickname() + " has selected the following Tiles");
//            for (Position p : selectedTiles) {
//                Tile tile = game.getBoard().getGrid().get(p.getKey());
//                System.out.printf(tile.print() + tile.getPos().toString() + " ");
//            }
//            System.out.println("\n");
//        }

//    }
//    public void showPersonalGoal() {
//        System.out.println(game.getCurrentPlayer().getNickname() + " here you can see your personalGoal");
//        PersonalGoal pg = game.getCurrentPlayer().getPersonalGoal();
//        Position pos;
//        TileColor tmp;
//        Tile t;
//        Bookshelf b = game.getCurrentPlayer().getBookshelf();
//        int index;
//        for (int row = 5; row >= 0; row--) {
//            for (int col = 0; col < 5; col++) {
//                //System.out.printf(color.blackBg() + " ");
//                pos = new Position(col, row);
//                index = pg.getPos().indexOf(pos);
//                if (b.getTile(col,row) != null) {
//                    if (pg.getPos().contains(pos) && pg.getColor().get(index).equals(b.getTile(col, row).getColor())) {
//                        System.out.printf(color.black());
//                    } else if (pg.getPos().contains(pos) && !pg.getColor().get(index).equals(b.getTile(col, row).getColor())) {
//                        System.out.printf(color.red());
//                    }
//                }
//                if (pg.getPos().contains(pos)) {
//                    tmp = pg.getColor().get(index);
//                    t = new Tile(tmp, TileType.CATS);
//                    System.out.print(t.print());
//                } else {
//                    System.out.printf(color.blackBg() + "   " + color.rst());
//                }
//            }
//            System.out.printf("\n");
//        }
//        System.out.println();

//    }
//    public void showCommonGoals() {
//
//        int cg;
//
//        for (CommonGoal x : game.getCurrentComGoals()) {
//
//            cg = x.getNum();
//            if (game.getCurrentComGoals().indexOf(x) == 0) {
//                System.out.println("First CommonGoal:");
//            } else {
//                System.out.println("Second CommonGoal: ");
//            }
//
//            switch (cg) {
//                case 1 -> {
//                    // CG 1
//                    for (int i = 0; i < 4; i++) {
//                        printEqual();
//                        if (i == 1) {
//                            System.out.println(color.red() + " x2" + color.rst());
//                        }
//                    }
//                    System.out.println();
//                    System.out.println("Two groups each containing 4 tiles of the same type in a 2x2 square.\n" +
//                            "The tiles of one square can be different from those of the other square.\n");
//                }
//                case 2 -> {
//                    // CG 2
//                    for (int i = 0; i < 6; i++) {
//                        printNotEqual();
//                        if (i == 2) {
//                            System.out.print(color.red() + " x2" + color.rst());
//                        }
//                        System.out.println();
//                    }
//                    System.out.println("Two columns each formed by 6 different types of tiles.\n");
//                }
//                case 3 -> {
//                    // CG 3
//                    for (int i = 0; i < 4; i++) {
//                        printEqual();
//                        if (i == 1) {
//                            System.out.print(color.red() + " x4" + color.rst());
//                        }
//                        System.out.println();
//                    }
//                    System.out.println("Four groups each containing at least\n" +
//                            "4 tiles of the same type (not necessarily in the depicted shape).\n" +
//                            "The tiles of one group can be different from those of another group.\n");
//                }
//                case 4 -> {
//                    // CG 4
//                    printEqual();
//                    System.out.println(color.red() + " x6" + color.rst());
//                    printEqual();
//                    System.out.println();
//                    System.out.println("Six groups each containing at least\n" +
//                            "2 tiles of the same type (not necessarily in the depicted shape).\n" +
//                            "The tiles of one group can be different from those of another group.\n");
//                }
//                case 5 -> {
//                    // CG 5
//                    for (int i = 0; i < 6; i++) {
//                        printEmpty();
//                        if (i == 2) {
//                            System.out.print(color.red() + " x3  MAX 3 ");
//                            printNotEqual();
//                        }
//                        System.out.println();
//                    }
//                    System.out.println("Three columns each formed by 6 tiles of maximum three different types. \n" +
//                            "One column can show the same or a different combination of another column.");
//                    System.out.println();
//                }
//                case 6 -> {
//                    // CG 6
//                    for (int i = 0; i < 5; i++) {
//                        printNotEqual();
//                    }
//                    System.out.println(color.red() + " x2" + color.rst() + "\n");
//                    System.out.println("Two lines each formed by 5 different types of tiles." +
//                            "One line can show the same or a different combination of the other line.\n");
//                }
//                case 7 -> {
//                    for (int i = 0; i < 5; i++) {
//                        printEmpty();
//                    }
//                    System.out.print(color.red() + " x4  MAX 3 ");
//                    printNotEqual();
//                    System.out.println();
//                    System.out.println("Four lines each formed by 5 tiles of maximum three different types. \n" +
//                            "One line can show the same or a different combination of another line.\n");
//                }
//                case 8 -> {
//                    printEqual();
//                    System.out.print("         ");
//                    printEqual();
//                    System.out.print("\n\n\n\n\n");
//                    printEqual();
//                    System.out.print("         ");
//                    printEqual();
//                    System.out.println();
//                    System.out.println("Four tiles of the same type in the four corners of the bookshelf.\n");
//
//                }
//                case 9 -> {
//                    System.out.print("  ");
//                    for (int i = 0; i < 8; i++) {
//                        printEqual();
//                        System.out.print("  ");
//                        if (i == 1 || i == 4 || i == 7) {
//                            System.out.println("\n");
//                        }
//                    }
//                    System.out.println("Eight tiles of the same type. \n" +
//                            "There’s no restriction about the position of these tiles.");
//                    System.out.println();
//                }
//                case 10 -> {
//                    for (int i = 0; i < 9; i++) {
//                        if (i%2 == 0) {
//                            printEqual();
//                        } else {
//                            System.out.print("   ");
//                        }
//                        if (i%3 == 2) {
//                            System.out.println();
//                        }
//                    }
//                    System.out.println("Five tiles of the same type forming an X.");
//                    System.out.println();
//                }
//                case 11 -> {
//                    // CG 11
//                    for (int i = 0; i < 5; i++) {
//                        for (int j = 0; j < i; j++) {
//                            System.out.print("   ");
//                        }
//                        printEqual();
//                        System.out.println();
//                    }
//                    System.out.println("Five tiles of the same type forming a diagonal.");
//                    System.out.println();
//                }
//                case 12 -> {
//                    for (int i = 0; i < 5; i++) {
//                        for (int j = 0; j < i+1; j++) {
//                            printEmpty();
//                        }
//                        System.out.println();
//                    }
//                    System.out.println("Five columns of increasing or decreasing height. \n" +
//                            "Starting from the first column on the left or on the right, \n" +
//                            "each next column must be made of exactly one more tile. \n" +
//                            "Tiles can be of any type.");
//                    System.out.println();
//                }
//                default -> {
//                    System.out.println(color.red() + "You choose the common goal number " + cg + " but you should have a number from 1 to 12...\n" + color.rst());
//                }
//            }
//
//        }

//    }

//    public void showCurrentScore() {
//        int score;
//        System.out.println(color.blackBg() + " Current Score " + color.rst());
//        for (Player p : game.getPlayers()) {
//            score = p.getCurrentScore();
//            System.out.println(color.greenBg() + color.black() + " " + p.getNickname() + " " + score + " " + color.rst());
//        }
//        System.out.println();
//    }

//    public void showHiddenScore() {
//        int score;
//        score = game.getCurrentPlayer().getHiddenScore();
//        System.out.println(color.blackBg() + " " + game.getCurrentPlayer().getNickname() + " this is your score:" + color.blueBg() + color.black() + " " + score + " " + color.rst() + "\n");
//    }

//    public void showFinalScore() { // different color for the higher score, but if two have the same score I show both as winners, I should do the control on the winner, do I have this information?
//        int score;
//        int maxScore = 0;
//        System.out.println(color.blackBg() + " Final Score " + color.rst());
//        for (Player p : game.getPlayers()) {
//            score = p.getFinalScore();
//            if (score > maxScore) {
//                maxScore = score;
//            }
//        }
//        for (Player p : game.getPlayers()) {
//            score = p.getFinalScore();
//            if (score == maxScore) {
//                System.out.println(color.greenBg() + color.black() + " " + p.getNickname() + " " + score + " " + color.rst());
//            } else {
//                System.out.println(color.yellowBg() + color.black() + " " + p.getNickname() + " " + score + " " + color.rst());
//            }
//        }
//        System.out.println();
//    }

}
