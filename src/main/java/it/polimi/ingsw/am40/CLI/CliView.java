package it.polimi.ingsw.am40.CLI;

import it.polimi.ingsw.am40.Model.Bookshelf;
import it.polimi.ingsw.am40.Model.Game;
import it.polimi.ingsw.am40.Model.Position;
import it.polimi.ingsw.am40.Model.Tile;

public class CliView {

    private Colors color = new Colors();


    public void showBoard(Game game) {
        for (int row = 4; row > -5; row--) {
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
        System.out.println();
    }

    public void showBoardPickable(Game game) {
        for (int row = 4; row > -5; row--) {
            for (int col = -4; col < 5; col++) {
                Position pos = new Position(col, row);
                Tile tile = game.getBoard().getGrid().get(pos.getKey());
                if (tile != null) {
                    if (game.getBoard().getPickableTiles().contains(tile.getPos()) ) {
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
        System.out.println();
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
}
