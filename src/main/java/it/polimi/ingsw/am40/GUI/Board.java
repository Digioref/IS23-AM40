package it.polimi.ingsw.am40.GUI;

import java.util.ArrayList;
import java.util.HashMap;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.paint.Color;
import javafx.stage.Screen;

public class Board extends AnchorPane {

	private static final int MAX_SELECTABLE = 3;

	private static final double ORIGIN_X = 244.0/1536.0;
	private static final double ORIGIN_Y = 247.0/864.0	;
	private static final double STEP_X = 55.0/1536.0;
	private static final double STEP_Y = 55.0/864.0;

	private final HashMap<String, Node> tiles = new HashMap<String, Node>();

	private final ArrayList<String> selected = new ArrayList<String>();

	public Board() {
		super();

//		double screenHeight = Screen.getPrimary().getVisualBounds().getHeight() * 0.63;
//		setPrefSize(screenHeight, screenHeight);
//		setPrefSize(Metrics.BOARD_WIDTH, Metrics.BOARD_HEIGHT);
		setWidth(Metrics.BOARD_WIDTH);
		setHeight(Metrics.BOARD_HEIGHT);
		Image image = Resources.board();

		BackgroundImage boardImg = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
				BackgroundPosition.DEFAULT,
				new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false));

		setBackground(new Background(boardImg));
		setEffect(new DropShadow(20, Color.WHITE));
	}

	void place(Tile tile) {
		Point2D pos = tile.getPosition();
		int x = (int) pos.getX();
		int y = (int) pos.getY();
		String key = hashkey(x, y);

		/* Relocate the tile on the grid */
		tile.relocate(ORIGIN_X + (x * STEP_X), ORIGIN_Y - (y * STEP_Y));

		/* Store into the hash-map */
		tiles.put(key, tile);

		/* Add to the view */
		getChildren().add(tile);
	}

	void pickable(int x, int y) {
		Tile node;
		String key = hashkey(x, y);
		node = (Tile) tiles.get(key);
		if (node != null) {
			node.setPickable(true);
		}
	}

	boolean select(boolean flag, int x, int y) {
		String key = hashkey(x, y);
		if (flag) {
			selected.add(key);
		} else {
			selected.remove(key);
		}

		return (selected.size() == MAX_SELECTABLE);
	}

	boolean isSelectedEmpty() {
		return (selected.size() == 0);
	}

	Node getSelected() {
		Node node = null;
		String key;

		if (selected.size() > 0) {
			key = selected.remove(0);
			node = tiles.remove(key);
			if (node != null) {
				getChildren().remove(node);
			}
		}
		return node;
	}

	private String hashkey(int x, int y) {
		return new String("tile_" + x + "_" + y);
	}

	@Override
	public void resize(double width, double height) {
		if (width != 0) {
			setWidth(width);
			if (!tiles.isEmpty()) {
				for (String s: tiles.keySet()) {
					tiles.get(s).resize(width, 0);
				}
			}
		}
		if (height != 0) {
			setHeight(height);
			if (!tiles.isEmpty()) {
				for (String s: tiles.keySet()) {
					tiles.get(s).resize(0, height);
				}
			}
		}

	}
}
