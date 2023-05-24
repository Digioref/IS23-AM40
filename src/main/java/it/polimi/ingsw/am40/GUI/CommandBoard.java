package it.polimi.ingsw.am40.GUI;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class CommandBoard extends AnchorPane {
	private static final int TILES = 3;
	private static final int TILE_LEFT_OFFSET = 25;
	private static final int TILE_TOP_OFFSET = 20;
	private static final int LABEL_SIZE_WIDTH = 20;
	private static final int LABEL_LEFT_OFFSET = 39;
	private static final int LABEL_TOP_OFFSET = 74;

	private int nextTilePos;
	private int[] pickupOrder = { 1, 1, 1 };
	private int pickupIndex;

	private TileRect[] tiles = new TileRect[TILES];
	private LabelPos[] labels = new LabelPos[TILES];

	public CommandBoard(Stage primaryStage) {
		super();
		setPrefSize(Metrics.dim_x_comb* primaryStage.getWidth(), Metrics.dim_y_comb*primaryStage.getHeight());

		Image image = Resources.background();

		BackgroundImage boardImg = new BackgroundImage(image, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT,
				BackgroundPosition.DEFAULT,
				new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false));

		setBackground(new Background(boardImg));
		setEffect(new DropShadow(20, Color.BLACK));

		/* First position in tiles array */
		nextTilePos = 0;

		/* First index to get */
		pickupIndex = 1;

		for (int i = 0; i < TILES; i++) {
			tiles[i] = new TileRect(i);
			tiles[i].relocate(((TILE_LEFT_OFFSET + (i * (TILE_LEFT_OFFSET + Metrics.TILE_WIDTH)))/1536.0)* primaryStage.getWidth(), (TILE_TOP_OFFSET/864.0)*primaryStage.getHeight());

			labels[i] = new LabelPos();
			labels[i].setEmpty();
			labels[i].relocate(((LABEL_LEFT_OFFSET + (i * (Metrics.TILE_WIDTH + LABEL_SIZE_WIDTH)))/1536.0)* primaryStage.getWidth(), (LABEL_TOP_OFFSET/864.0)* primaryStage.getHeight());

			getChildren().add(tiles[i]);
			getChildren().add(labels[i]);
		}

	}

	void addTile(Node node) {
		if (nextTilePos < TILES) {
			tiles[nextTilePos].getChildren().add(node);
			pickupOrder[nextTilePos] = nextTilePos + 1;
			labels[nextTilePos].setIndex(pickupOrder[nextTilePos]);
			nextTilePos++;
		}
	}

	Node getTile() {
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

	int getNumTile() {
		return nextTilePos;
	}

	boolean checkSequence() {
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

		public TileRect(int index) {
			super();

			setPrefSize(Metrics.TILE_WIDTH, Metrics.TILE_HEIGHT);
			setMinSize(Metrics.TILE_WIDTH, Metrics.TILE_HEIGHT);

			Rectangle clip = new Rectangle(Metrics.TILE_WIDTH, Metrics.TILE_HEIGHT);
			clip.setArcWidth(Metrics.TILE_ROUND_WIDTH);
			clip.setArcHeight(Metrics.TILE_ROUND_HEIGHT);
			setClip(clip);

			setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));

			this.index = index;

			setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					if (getChildren().isEmpty()) {
						return;
					}

					int idx = getIndex();
					pickupOrder[idx]++;
					if (pickupOrder[idx] > nextTilePos) {
						pickupOrder[idx] = 1;
					}
					labels[idx].setIndex(pickupOrder[idx]);
				}
			});

		}

		int getIndex() {
			return index;
		}

	}

	private class LabelPos extends Label {
		private static final int SIZE = 26;
		private final ImageView[] view = { null, null, null };

		public LabelPos() {
			super();

			for (int i = 0; i < 3; i++) {

				Image image = Resources.number(i + 1);
				view[i] = new ImageView(image);
				view[i].setPreserveRatio(true);
				view[i].setFitWidth(SIZE);
				view[i].setFitHeight(SIZE);
			}

			setGraphic(null);
		}

		void setIndex(int idx) {
			setGraphic(view[idx - 1]);
		}

		void setEmpty() {
			setGraphic(null);
		}
	}

	public int getNextTilePos() {
		return nextTilePos;
	}

	public int[] getPickupOrder() {
		return pickupOrder;
	}
}
