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

}
