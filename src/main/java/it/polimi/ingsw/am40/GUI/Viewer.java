package it.polimi.ingsw.am40.GUI;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Viewer extends Application {

	/*
	 * This variable is needed only for the test methods. Remove it when removing
	 * the test methods.
	 */
	private AnchorPane viewController;

	public static void main(String[] args) {launch(args);
	}

	@Override
	public void start(Stage stage) {

		VBox rootBox;
		final Scene scene;

		rootBox = new VBox();

		/* Add the menu */
		MenuBar topMenu = MenuObj.createMenuBar(stage);
		rootBox.getChildren().add(topMenu);

		viewController = new ViewController();
		rootBox.getChildren().add(viewController);

		ScrollPane scrollPane = new ScrollPane(rootBox);

		/* Create the main scene */
		scene = new Scene(scrollPane);

		/* Set stage properties */
		stage.setTitle("MyShelfie");
		stage.getIcons().add(Resources.icon());
		stage.setResizable(true);
		stage.setScene(scene);
		stage.show();

		/* Test: populate the board */
		populateBoard();
	}

	/* Test methods */

	void populateBoard() {
		addTile(Resources.TILE_TYPE_CAT, 0, 0);
		addTile(Resources.TILE_TYPE_FRAME, 0, 1);
		addTile(Resources.TILE_TYPE_FLOWER, 0, 2);
		addTile(Resources.TILE_TYPE_TROPHY, 0, 3);
		addTile(Resources.TILE_TYPE_CAT, 0, -1);
		addTile(Resources.TILE_TYPE_GAME, 0, -2);
		addTile(Resources.TILE_TYPE_BOOK, 0, -3);

		addTile(Resources.TILE_TYPE_CAT, -1, 0);
		addTile(Resources.TILE_TYPE_FRAME, -1, 1);
		addTile(Resources.TILE_TYPE_CAT, -1, 2);
		addTile(Resources.TILE_TYPE_BOOK, -1, 3);
		addTile(Resources.TILE_TYPE_GAME, -1, -1);
		addTile(Resources.TILE_TYPE_TROPHY, -1, -2);

		addTile(Resources.TILE_TYPE_TROPHY, -2, 0);
		addTile(Resources.TILE_TYPE_BOOK, -2, 1);
		addTile(Resources.TILE_TYPE_CAT, -2, -1);
		addTile(Resources.TILE_TYPE_FRAME, -3, 0);
		addTile(Resources.TILE_TYPE_CAT, -3, -1);

		addTile(Resources.TILE_TYPE_FRAME, 1, 0);
		addTile(Resources.TILE_TYPE_TROPHY, 1, 1);
		addTile(Resources.TILE_TYPE_GAME, 1, 2);
		addTile(Resources.TILE_TYPE_TROPHY, 1, -1);
		addTile(Resources.TILE_TYPE_CAT, 1, -2);
		addTile(Resources.TILE_TYPE_FRAME, 1, -3);

		addTile(Resources.TILE_TYPE_CAT, 2, 0);
		addTile(Resources.TILE_TYPE_BOOK, 2, 1);
		addTile(Resources.TILE_TYPE_GAME, 2, -1);

		addTile(Resources.TILE_TYPE_FLOWER, 3, 0);
		addTile(Resources.TILE_TYPE_BOOK, 3, 1);

		setTilePickable(3, 1);
		setTilePickable(3, 0);
		setTilePickable(2, 1);
		setTilePickable(1, 2);
		setTilePickable(1, -2);
		setTilePickable(0, 3);
		setTilePickable(-1, 3);
		setTilePickable(-1, 2);
		setTilePickable(-2, 1);
		setTilePickable(-3, 0);
		setTilePickable(-3, -1);
		setTilePickable(-2, -1);
		setTilePickable(-1, -2);
		setTilePickable(0, -3);
		setTilePickable(1, -3);
		setTilePickable(1, -3);
		setTilePickable(2, -1);
	}

	void addTile(int type, int x, int y) {
		Tile tile;

		tile = new Tile(type);
		tile.setPosition(x, y);
		viewController.fireEvent(new CustomEvent(CustomEvent.BOARD_ADD_TILE, tile));
	}

	void setTilePickable(int x, int y) {
		viewController.fireEvent(new CustomEvent(CustomEvent.BOARD_TILE_PICKABLE, x, y));
	}
}
