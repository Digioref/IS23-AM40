package it.polimi.ingsw.am40.CLI;

import it.polimi.ingsw.am40.Model.*;

import java.util.ArrayList;

public class CliView {

    private Colors color = new Colors();


    public void showBoard(Game game) {
        for (int row = 4; row > -5; row--) {
            if (row >= 0) {
                System.out.printf(" %d ", row);
            } else {
                System.out.printf("%d ", row);
            }
            for (int col = -4; col < 5; col++) {
                Position pos = new Position(col, row);
                Tile tile = game.getBoard().getGrid().get(pos.getKey());
                if (tile != null) {
                    System.out.printf(color.blackBg());
                    System.out.printf(tile.print());
                } else {
                    System.out.printf(color.blackBg() + "  " + color.rst());
                }
            }
            System.out.printf("\n");
        }
        System.out.printf("  ");
        for (int i = -4; i < 5; i++) {
            if (i < 0) {
                System.out.printf("%d", i);
            } else {
                System.out.printf(" %d", i);
            }
        }
        System.out.println("\n");
    }

    public void showBoardPickable(Game game) {
        System.out.println("You can choose only the tiles with red background");
        for (int row = 4; row > -5; row--) {
            if (row >= 0) {
                System.out.printf(" %d ", row);
            } else {
                System.out.printf("%d ", row);
            }
            for (int col = -4; col < 5; col++) {
                Position pos = new Position(col, row);
                Tile tile = game.getBoard().getGrid().get(pos.getKey());
                if (tile != null) {
                    if (game.getBoard().getPickableTiles().contains(tile.getPos()) ) {
                        System.out.printf(color.rst());
                    } else if (game.getCurrentPlayer().getSelectedPositions().contains(tile.getPos())) {
                        System.out.printf(color.redBg());
                    } else {
                        System.out.printf(color.blackBg());
                    }
                    System.out.printf(tile.print());
                } else {
                    System.out.printf(color.blackBg() + "  " + color.rst());
                }
            }
            System.out.printf("\n");
        }
        System.out.printf("  ");
        for (int i = -4; i < 5; i++) {
            if (i < 0) {
                System.out.printf("%d", i);
            } else {
                System.out.printf(" %d", i);
            }
        }
        System.out.println("\n");
    }

    public void showCurrentBookshelf(Game game) {
        System.out.println(game.getCurrentPlayer().getNickname() + "'s bookshelf");
        Bookshelf b = game.getCurrentPlayer().getBookshelf();
        for (int row = 5; row >= 0; row--) {
            for (int col = 0; col < 5; col++) {
                System.out.printf(color.blackBg() + " " + color.rst());
                if (b.getTile(col, row) != null) {
                    System.out.printf(color.blackBg());
                    System.out.printf(b.getTile(col, row).print());
                } else {
                    System.out.printf(color.blackBg() + "  " + color.rst());
                }
            }
            System.out.printf("\n");
        }
        System.out.println();
    }

    public void showAllBookshelfs(Game game) {
        System.out.println("Here you can see all the bookshelfs\n");
        showCurrentBookshelf(game);
        for (Player p : game.getPlayers()) {
            if (!p.equals(game.getCurrentPlayer())) {
                System.out.println(p.getNickname() + "'s bookshelf");
                Bookshelf b = p.getBookshelf();
                for (int row = 5; row >= 0; row--) {
                    for (int col = 0; col < 5; col++) {
                        System.out.printf(color.blackBg() + " " + color.rst());
                        if (b.getTile(col, row) != null) {
                            System.out.printf(color.blackBg());
                            System.out.printf(b.getTile(col, row).print());
                        } else {
                            System.out.printf(color.blackBg() + "  " + color.rst());
                        }
                    }
                    System.out.printf("\n");
                }
                System.out.println();
            }
        }
    }

    public void showCurrentPlayer(Game game) {

        System.out.println(game.getCurrentPlayer().getNickname() + "\n");

    }

    public void showSelectedTiles(Game game) {
        System.out.println(game.getCurrentPlayer().getNickname() + " has selected the following Tiles");
        ArrayList<Tile> selectedTiles = game.getCurrentPlayer().getTilesPicked();
        if (selectedTiles.size() == 0) {
            System.out.println("No Tiles selected");
        }
        for (Tile tile : selectedTiles) {
            System.out.printf(tile.print() + tile.getPos().toString() + " ");
        }
        System.out.println("\n");
    }

    public void showPersonalGoal(Game game) {
        System.out.println(game.getCurrentPlayer().getNickname() + " here you can see your personalGoal");
        PersonalGoal pg = game.getCurrentPlayer().getPersonalGoal();
        Position pos;
        TileColor tmp;
        Bookshelf b = game.getCurrentPlayer().getBookshelf();
        int index;
        for (int row = 5; row >= 0; row--) {
            for (int col = 0; col < 5; col++) {
                System.out.printf(color.blackBg() + " ");
                pos = new Position(col, row);
                index = pg.getPos().indexOf(pos);
                if (b.getTile(col,row) != null) {
                    if (pg.getPos().contains(pos) && pg.getColor().get(index).equals(b.getTile(col, row).getColor())) {
                        System.out.printf(color.redBg());
                    } else if (pg.getPos().contains(pos) && !pg.getColor().get(index).equals(b.getTile(col, row).getColor())) {
                        System.out.printf(color.rst());
                    } else {
                        System.out.printf(color.blackBg());
                    }
                }
                if (pg.getPos().contains(pos)) {
                    tmp = pg.getColor().get(index);
                    if (tmp.equals(TileColor.GREEN)) {
                        System.out.printf(color.green() + "G " + color.rst());
                    } else if (tmp.equals(TileColor.WHITE)) {
                        System.out.printf(color.white() + "W " + color.rst());
                    } else if (tmp.equals(TileColor.YELLOW)) {
                        System.out.printf(color.yellow() + "Y " + color.rst());
                    } else if (tmp.equals(TileColor.BLUE)) {
                        System.out.printf(color.blue() + "B " + color.rst());
                    } else if (tmp.equals(TileColor.CYAN)) {
                        System.out.printf(color.cyan() + "C " + color.rst());
                    } else if (tmp.equals(TileColor.VIOLET)) {
                        System.out.printf(color.purple() + "V " + color.rst());
                    } else System.out.printf(color.black() + "X " + color.rst());
                } else {
                    System.out.printf(color.blackBg() + "  " + color.rst());
                }
            }
            System.out.printf("\n");
        }
        System.out.println();
    }

    public void showCommonGoals(Game game) {

        int ch = 8800;
        char notEqual = (char) ch;

        int cg;

        for (CommonGoal x : game.getCurrentComGoals()) {

            cg = x.getNum();

            switch (cg) {
                case 1 -> {
                    // CG 1
                    System.out.println(color.whiteBg() + color.black() + " =  = " + color.rst() + color.red() + " x2" + color.rst());
                    System.out.println(color.whiteBg() + color.black() + " =  = " + color.rst() + "\n");
                }
                case 2 -> {
                    // CG 2
                    for (int i = 0; i < 6; i++) {
                        System.out.print(color.whiteBg() + color.black() + " " + notEqual + " " + color.rst());
                        if (i == 2) {
                            System.out.print(color.red() + " x2" + color.rst());
                        }
                        System.out.println();
                    }
                    System.out.println();
                }
                case 3 -> {
                    // CG 3
                    for (int i = 0; i < 4; i++) {
                        System.out.print(color.whiteBg() + color.black() + " = " + color.rst());
                        if (i == 1) {
                            System.out.print(color.red() + " x4" + color.rst());
                        }
                        System.out.println();
                    }
                    System.out.println();
                }
                case 4 -> {
                    // CG 4
                    System.out.println(color.whiteBg() + color.black() + " = " + color.rst() + color.red() + " x6" + color.rst());
                    System.out.println(color.whiteBg() + color.black() + " = " + color.rst() + "\n");
                }
                case 5 -> {
                    // CG 5
                    for (int i = 0; i < 6; i++) {
                        System.out.print(color.whiteBg() + "   " + color.rst());
                        if (i == 2) {
                            System.out.print(color.red() + " x3  MAX 3 " + color.whiteBg() + color.black() + " " + notEqual + " " + color.rst());
                        }
                        System.out.println();
                    }
                    System.out.println();
                }
                case 6 -> {
                    // CG 6
                    for (int i = 0; i < 5; i++) {
                        System.out.print(color.whiteBg() + color.black() + " " + notEqual + " " + color.rst());
                    }
                    System.out.println(color.red() + " x2" + color.rst() + "\n");
                }
                case 11 -> {
                    // CG 11
                    System.out.println(color.whiteBg() + color.black() + " = " + color.rst());
                    System.out.println("   " + color.whiteBg() + color.black() + " = " + color.rst());
                    System.out.println("      " + color.whiteBg() + color.black() + " = " + color.rst());
                    System.out.println("         " + color.whiteBg() + color.black() + " = " + color.rst());
                    System.out.println("            " + color.whiteBg() + color.black() + " = " + color.rst() + "\n");
                }
                default -> {
                    System.out.println("Still working on the commonGoal num " + cg + "\n");
                }
            }

        }



    }
}
