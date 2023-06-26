package it.polimi.ingsw.am40.CLI;

import it.polimi.ingsw.am40.Client.LaunchClient;
import it.polimi.ingsw.am40.Client.SocketClient;
import it.polimi.ingsw.am40.JSONConversion.JSONConverterCtoS;
import it.polimi.ingsw.am40.Model.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.channels.Pipe;
import java.util.ArrayList;
import java.util.Map;
import java.util.regex.Pattern;

public class CliView implements View{

    /**
     * CliView is the class used to print into the terminal
     */
    private Colors color = new Colors();


    /**
     * Print the '=' symbol
     */
    private void printEqual() {
        System.out.print(color.whiteBg() + color.black() + " = " + color.rst());
    }

    /**
     *
     */
    private void printNotEqual() {
        int ch = 8800;
        char notEqual = (char) ch;
        System.out.print(color.whiteBg() + color.black() + " " + notEqual + " " + color.rst());
    }

    /**
     *
     */
    private void printEmpty() {
        System.out.print(color.whiteBg() + "   " + color.rst());
    }

    /**
     * Prints the list of the plauers
     * @param names
     */
    public void showPlayers(ArrayList<String> names) {
        System.out.println(color.blackBg() + " Players " + color.rst());
        for (String s: names) {
            System.out.println(color.greenBg() + s + color.rst());
        }
        System.out.println("\n");
    }

    /**
     * @param s is the color of the tile to be printed
     * @return a string (color value to be printed)
     */
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

    /**
     * @param s, is the name of the current player
     * The method prints the name of the current player (passed by parameter)
     */
    public void showCurrentPlayer(String s) {
        System.out.println(color.blackBg() + "Current player:" + color.rst() + " " + s + "\n");
    }

    /**
     * Shows the current player
     * @param map player name and points
     */
    public void showCurrentScore(Map<String, Integer> map) {
        System.out.println(color.blackBg() + " Current Score " + color.rst());
        for (String s : map.keySet()) {
            System.out.println(color.greenBg() + color.black() + " " + s + " " + map.get(s) + " " + color.rst());
        }
        System.out.println();
    }

    /**
     * Shows the hidden total score of the player
     * @param score
     */
    public void showHiddenScore(int score) {
        System.out.println(color.blackBg() + " This is your actual score:" + color.blueBg() + color.black() + " " + score + " " + color.rst() + "\n");
    }

    /**
     * Prints the common goals of the game
     * @param map the number of the commonGoal to the points you get if completed
     * (updated based on how many players have completed it)
     */
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

    /**
     * TODO
     * Key (made by the position) and type of the tile
     * @param map
     * @param number
     */
    public void showPersonalGoal(Map<String, String> map, int number) {
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

    /**
     * TODO
     * @param map
     */
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

    /**
     * TODO
     * @param map
     */
    public void showCurrentBookshelf(Map<String, String> map) {
        System.out.println("Your bookshelf:\n");
        showBookshelf(map);
    }

    /**
     * TODO
     * @param map
     */
    public void showAllBookshelves(Map<String, Map<String, String>> map) {
        System.out.println("Here you can see all the bookshelfs\n");
        for (String s: map.keySet()) {
            System.out.println(s + "'s bookshelf:");
            showBookshelf(map.get(s));
            System.out.printf("\n");
        }
        System.out.println();
    }

    /**
     * TODO
     * @param map
     */
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

    /**
     * TODO
     * @param map
     * @param arr
     * @param board
     */
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

    /**
     * TODO
     * @param map
     * @param s
     * @param selected
     */
    public void showSelectedTiles(Map<String, String> map, String s,ArrayList<ArrayList<String>> selected) {
        if (map.isEmpty()) {
            System.out.println("You haven't selected any Tile\n");
        } else {
            System.out.println( "Player " + s + " has selected the following Tiles");


           // System.out.println("size of selected: " + selected.size() +"\n");
            //System.out.println("tile: "+ selected.get(0).get(0) + "pos: " + selected.get(0).get(1));
            for(ArrayList<String> tmp : selected){
                //System.out.println("tile: "+ tmp.get(0) + " pos: " + tmp.get(1));
                System.out.printf(printTile(tmp.get(0))+ tmp.get(1) + " ");
            }


            /*
            for (String s1 : map.keySet()) {
                System.out.println("tile: " + map.get(s1) + " pos "+ s1);
                System.out.printf(printTile(map.get(s1)) + s1 + " ");
            }

             */




            System.out.println("\n");
        }
    }


    /**
     * TODO
     * @param map
     * @param s
     * @param picked
     */
    public void showPickedTiles(Map<String, String> map, String s,ArrayList<ArrayList<String>> picked) {
        if (map.isEmpty()) {
            System.out.println("You haven't picked Tiles yet\n");
        } else {
            System.out.println("Player " + s + " has picked the following Tiles");

            for(ArrayList<String> tmp : picked){
                System.out.printf(printTile(tmp.get(0))+ tmp.get(1) + " ");
            }
/*
            for (String s1: map.keySet()) {
                System.out.printf(printTile(map.get(s1)) + s1 + " ");
            }

 */

            System.out.println("\n");
        }
    }

    /**
     * TODO
     * @param map
     * @param winner
     */
    public void showFinalScore(Map<String, Integer> map, String winner) { // different color for the higher score, but if two have the same score I show both as winners, I should do the control on the winner, do I have this information?
        System.out.println(color.blackBg() + " Final Scores " + color.rst());

        for (String s : map.keySet()) {
            if (s.equals(winner)) {
                System.out.println(color.greenBg() + color.black() + " " + s + " " + map.get(s) + " " + color.rst());
            } else {
                System.out.println(color.yellowBg() + color.black() + " " + s + " " + map.get(s) + " " + color.rst());
            }
        }
        System.out.println(color.blackBg() + " The winner is: " + color.rst() + color.greenBg() + color.black() + winner + color.rst());
        System.out.println();
        LaunchClient.getClient().close();
    }

    /**
     * Prints the message passed as argument
     * @param s
     */
    public void printMessage(String s) {
        System.out.println(s);
    }

    /**
     * TODO
     * E' solo socket?
     * Opens the chat for a player
     * @param socketClient
     */
    @Override
    public void chat(SocketClient socketClient) {
        socketClient.setQuitchat(false);
        System.out.println(color.cyanBg() + " You are in the Chat!" + color.rst());
        System.out.println("Write the message (-q to quit): ");
        while (!socketClient.isQuitchat()) {
            try {
                String message = null;
                if (socketClient.getStdIn().ready()) {
                    message = socketClient.getStdIn().readLine();
                    if (message.toLowerCase().equals("-q")) {
                        socketClient.setQuitchat(true);
                    } else {
                        System.out.println("to [playerName] (leave it blank if it is a broadcast message): ");
                        String receiver = socketClient.getStdIn().readLine();
                        if (receiver.length() == 0)
                            receiver = null;
                        JSONConverterCtoS jconv = new JSONConverterCtoS();
                        jconv.toJSONChat(receiver, message);
                        socketClient.getOut().println(jconv.toString());
                        socketClient.getOut().flush();
                        System.out.println(color.cyanBg() + " You are in the Chat!" + color.rst());
                        System.out.println("Write the message (-q to quit): ");
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }

    /**
     * TODO
     * @param array1
     * @param array2
     * @param array3
     * @param nickname
     */
    @Override
    public void showChat(ArrayList<String> array1, ArrayList<String> array2, ArrayList<String> array3, String nickname) {
        System.out.println(color.cyanBg() + " Chat " + color.rst() + "\n");
        for (int i = 0; i < array1.size(); i++) {
            if(nickname.equals(array2.get(i)) || array2.get(i).equals("all") || nickname.equals(array1.get(i))) {
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
            if (choice == null) {
                return;
            }
            choice = choice.toUpperCase();
            if(choice.equals("S"))
                choice = "SOCKET";
            else if(choice.equals("R"))
                choice = "RMI";
        }while(!choice.equals("RMI") && !choice.equals("SOCKET"));
        printMessage("Insert the server IP (or localhost [L]):");
        Pattern p = Pattern.compile("^(?:[0-9]{1,3}\\.){3}[0-9]{1,3}$");
        String ip;
        do {
            try {
                ip = stdIn.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (ip == null) {
                return;
            }
            if (!p.matcher(ip).matches() && !ip.equalsIgnoreCase("L") && !ip.equals("")) {
                printMessage("Insert a valid ip address, please:");
            }
        } while (!p.matcher(ip).matches() && !ip.equalsIgnoreCase("L") && !ip.equals(""));

        if(ip.equalsIgnoreCase("L") || ip.equals("")) {
            ip = "localhost";
        }
        LaunchClient.startConnection(choice, ip);
    }

    /**
     * Method to print that the player with the nickname passed has quitted the game
     * @param nickname
     */
    @Override
    public void quit(String nickname) {
        if (nickname != null) {
            printMessage("Client " + nickname + " closed!");
        } else {
            printMessage("Client closed!");
        }
    }

    /**
     * Method used to ask the first player with how many people he wants to play
     */
    @Override
    public void setplayers() {
        printMessage("The number of players you want to play with: ");
    }


    /**
     * Prints "Waiting for the lobby..."
     */
    @Override
    public void waitLobby() {
        printMessage("Waiting in the lobby.....");
    }

    /**
     * Prints the name already used (to be substituted) and some other alternatives
     * @param to_be_replaced
     * @param suggested
     */
    @Override
    public void showSuggestedNicknames(String to_be_replaced, ArrayList<String> suggested) {
        printMessage(to_be_replaced);
        for (String t: suggested) {
            printMessage(t);
        }
    }

    /**
     * TODO
     * @param error
     */
    @Override
    public void showError(String error) {
        System.out.println(color.red() + error + color.rst());
    }

    /**
     * todo
     */
    @Override
    public void showGame() {
        System.out.println(color.green() + "Game is being created" + color.rst());
    }

    @Override
    public void showFirstPlayer(String nickname) {
        System.out.println(color.blackBg() + "First player:" + color.rst() + " " + nickname + "\n");
    }

    @Override
    public void showCGDone(String nickname, int num, int score) {
        System.out.println("The player " + nickname + "has done a common goal obtaining " + score + "points!" + "\n");
    }


}
