package it.polimi.ingsw.am40.GUI;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 * It represents a rectangle used to store the picked tiles and to order them
 */
public class CommandBoard extends AnchorPane {
	private static final int TILES = 3;
	private static final double TILE_LEFT_OFFSET = 25.0/1536.0;
	private static final double TILE_TOP_OFFSET = 20.0/864.0;
	private static final double LABEL_LEFT_OFFSET = 39.0/1536.0;
	private static final double LABEL_TOP_OFFSET = 74.0/864.0;

	private int nextTilePos;
	private final int[] pickupOrder = { 1, 1, 1 };
	private int pickupIndex;

	private final TileRect[] tiles = new TileRect[TILES];
	private final LabelPos[] labels = new LabelPos[TILES];

	/**
	 * Constructor that creates the rectangle
	 * @param primaryStage stage where it is shown
	 */
	public CommandBoard(Stage primaryStage) {
		super();
		setPrefSize(Metrics.dim_x_comb* primaryStage.getWidth(), Metrics.dim_y_comb*primaryStage.getHeight());

		Image image = Resources.background();

		BackgroundImage boardImg = new BackgroundImage(image, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT,
				BackgroundPosition.DEFAULT,
				new BackgroundSize(1, 1, true, true, false, false));


		setBackground(new Background(boardImg));
		setEffect(new DropShadow(20, Color.BLACK));

		/* First position in tiles array */
		nextTilePos = 0;

		/* First index to get */
		pickupIndex = 1;

		for (int i = 0; i < TILES; i++) {
			tiles[i] = new TileRect(i,primaryStage.getWidth(), primaryStage.getHeight());
			//tiles[i].relocate(((TILE_LEFT_OFFSET + (i * (TILE_LEFT_OFFSET + Metrics.TILE_WIDTH)))/1536.0)* primaryStage.getWidth(), (TILE_TOP_OFFSET/864.0)*primaryStage.getHeight());
			AnchorPane.setLeftAnchor(tiles[i],(TILE_LEFT_OFFSET + (i *(TILE_LEFT_OFFSET + Metrics.TILE_WIDTH))) * primaryStage.getWidth());
			AnchorPane.setTopAnchor(tiles[i], (TILE_TOP_OFFSET * primaryStage.getHeight()));

			labels[i] = new LabelPos(primaryStage.getWidth(), primaryStage.getHeight());
			labels[i].setEmpty();
			//labels[i].relocate(((LABEL_LEFT_OFFSET + (i * (Metrics.TILE_WIDTH + LABEL_SIZE_WIDTH)))/1536.0)* primaryStage.getWidth(), (LABEL_TOP_OFFSET/864.0)* primaryStage.getHeight());
			AnchorPane.setLeftAnchor(labels[i], (LABEL_LEFT_OFFSET + (i* 0.0465)) * primaryStage.getWidth());
			AnchorPane.setTopAnchor(labels[i], (LABEL_TOP_OFFSET * primaryStage.getHeight()));

			getChildren().add(tiles[i]);
			getChildren().add(labels[i]);
		}

	}

	/**
	 * It adds a tile inside the rectangle
	 * @param node represents the tile
	 */
	public void addTile(Node node) {
		if (nextTilePos < TILES) {
			tiles[nextTilePos].getChildren().add(node);
			pickupOrder[nextTilePos] = nextTilePos + 1;
			labels[nextTilePos].setIndex(pickupOrder[nextTilePos]);
			nextTilePos++;
		}
	}

	/**
	 * It returns one of the tiles picked inside the command board
	 * @return a node representing the tile
	 */
	public Node getTile() {
		for (int i = 0; i < nextTilePos; i++) {
			if (pickupOrder[i] == pickupIndex) {
				Node node = tiles[i].getChildren().remove(0);
				labels[i].setEmpty();
				pickupIndex++;
				return node;
			}
		}

		for (int i = 0; i < TILES; i++) {
			pickupOrder[i] = 1;
		}
		pickupIndex = 1;
		nextTilePos = 0;

		return null;
	}

	/**
	 * It returns the number of tiles inside the command board, so the number of tiles picked
	 * @return number of tiles picked
	 */
	public int getNumTile() {
		return nextTilePos;
	}

	/**
	 * It checks if the order sequence contains two equals number
	 * @return false if the order sequence contains two equals number, true otherwise
	 */
	public boolean checkSequence() {
		for (int i = 0; i < nextTilePos; i++) {
			int k = pickupOrder[i];
			for (int j = i + 1; j < nextTilePos; j++) {
				if (pickupOrder[j] == k) {
					return false;
				}
			}
		}
		return true;
	}

	private class TileRect extends StackPane {
		private final int index;

		private TileRect(int index,double width, double height) {
			super();

			setPrefSize(width* Metrics.TILE_WIDTH,height* Metrics.TILE_HEIGHT);
			//setMinSize(Metrics.TILE_WIDTH, Metrics.TILE_HEIGHT);

			Rectangle clip = new Rectangle(width * Metrics.TILE_WIDTH,height* Metrics.TILE_HEIGHT);
			clip.setArcWidth(Metrics.TILE_ROUND_WIDTH);
			clip.setArcHeight(Metrics.TILE_ROUND_HEIGHT);
			setClip(clip);

			setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));

			this.index = index;

			setOnMouseClicked(event -> {
				if (getChildren().isEmpty()) {
					return;
				}

				int idx = getIndex();
				pickupOrder[idx]++;
				if (pickupOrder[idx] > nextTilePos) {
					pickupOrder[idx] = 1;
				}
				labels[idx].setIndex(pickupOrder[idx]);
			});

		}

		private int getIndex() {
			return index;
		}

	}

	private static class LabelPos extends Label {
		private static final double SIZE = 26.0/1536.0;
		private final ImageView[] view = { null, null, null };

		private LabelPos( double x, double y) {
			super();

			for (int i = 0; i < 3; i++) {

				Image image = Resources.number(i + 1);
				view[i] = new ImageView(image);
				view[i].setPreserveRatio(true);
				view[i].setFitWidth(SIZE * x);
				view[i].setFitHeight(SIZE * x);
			}

			setGraphic(null);
		}

		private void setIndex(int idx) {
			setGraphic(view[idx - 1]);
		}

		private void setEmpty() {
			setGraphic(null);
		}
	}

	/**
	 * It returns the number of tiles picked
	 * @return number of tiles picked
	 */
	public int getNextTilePos() {
		return nextTilePos;
	}

	/**
	 * It returns the order sequence
	 * @return order sequence
	 */
	public int[] getPickupOrder() {
		return pickupOrder;
	}
}
