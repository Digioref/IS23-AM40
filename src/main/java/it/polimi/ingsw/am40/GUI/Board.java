package it.polimi.ingsw.am40.GUI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import it.polimi.ingsw.am40.Model.Position;
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
import javafx.stage.Stage;

/**
 * Class that represents the graphic element board
 */
public class Board extends AnchorPane {

	private static final int MAX_SELECTABLE = 3;

	private static double ORIGIN_X = 244.0;
	private static double ORIGIN_Y = 246.0;
	private static double STEP_X = 55.0;
	private static double STEP_Y = 55.0;

	private final HashMap<String, Node> tiles = new HashMap<>();

	private final ArrayList<String> selected = new ArrayList<>();
	private Stage primaryStage;

	/**
	 * Class that represent the graphic element board (to be shown on the primary stage)
	 * @param primaryStage
	 */
	public Board(Stage primaryStage) {
		super();
		this.primaryStage = primaryStage;
//		double screenHeight = Screen.getPrimary().getVisualBounds().getHeight() * 0.63;
//		setPrefSize(screenHeight, screenHeight);
		setPrefSize(Metrics.dim_x_board*primaryStage.getWidth(), Metrics.dim_y_board*primaryStage.getHeight());

		Image image = Resources.board();

		BackgroundImage boardImg = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
				BackgroundPosition.DEFAULT,
				new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false));

		setBackground(new Background(boardImg));
		setEffect(new DropShadow(20, Color.WHITE));
		setOriginX((ORIGIN_X/1536.0)* primaryStage.getWidth());
		setOriginY((ORIGIN_Y/864.0)*primaryStage.getHeight());
		setStepX((STEP_X/1536)* primaryStage.getWidth());
		setStepY((STEP_Y/864.0)* primaryStage.getHeight());
	}

	/**
	 * @param tile
	 */
	void place(Tile tile) {
		Point2D pos = tile.getPosition();
		int x = (int) pos.getX();
		int y = (int) pos.getY();
		String key = hashkey(x, y);

		/* Relocate the tile on the grid */
		//tile.relocate(ORIGIN_X + (x * STEP_X), ORIGIN_Y - (y * STEP_Y));
		AnchorPane.setLeftAnchor(tile, ORIGIN_X + x*STEP_X);
		AnchorPane.setTopAnchor(tile, ORIGIN_Y - (y*STEP_Y));

		/* Store into the hash-map */
		tiles.put(key, tile);

		/* Add to the view */
		getChildren().add(tile);
	}

	/**
	 * TODO
	 * @param x
	 * @param y
	 */
	void pickable(int x, int y) {
		Tile node;
		String key = hashkey(x, y);
		node = (Tile) tiles.get(key);
		if (node != null) {
			node.setPickable(true);
		}
	}

	/**
	 * TODO
	 * @param x
	 * @param y
	 * @return
	 */
	boolean select(int x, int y) {
		String key = hashkey(x, y);
			selected.add(key);
		return (selected.size() == MAX_SELECTABLE);
	}

	/**
	 * TODO
	 * @return
	 */
	boolean isSelectedEmpty() {
		return (selected.size() == 0);
	}

	/**
	 * TODO
	 * @return
	 */
	public Node getSelected() {
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

	/**
	 * TODO
	 * @param map
	 * @param selected
	 * @param board
	 */
	public void clearUpdate(Map<String, String> map, ArrayList<Position> selected, Map<String, String> board) {
		getChildren().clear();
		tiles.clear();
//		this.selected.clear();
		for (String s: board.keySet()) {
				Tile t = new Tile(board.get(s), primaryStage);
				if (!board.get(s).equals("NOCOLOR")) {
					t.setPosition(s);
					if (!map.containsKey(s)) {
						t.setPickable(false);
					} else {
						t.setPickable(true);
					}
					Position p = new Position();
					p.convertKey(s);
					if (selected.contains(p)) {
						t.setSelected();
						String key = hashkey(p.getX(), p.getY());
						if (!this.selected.contains(key)) {
							this.selected.add(key);
						}
					}
					place(t);
				}
//			Tile t = (Tile) tiles.get(s);
//			if (!board.get(s).equals("NOCOLOR")) {
//				if (!map.containsKey(s)) {
//						t.setPickable(false);
//					} else {
//						t.setPickable(true);
//					}
//				Position p = new Position();
//				p.convertKey(s);
//				if (selected.contains(p)) {
//					t.setSelected();
//					String key = hashkey(p.getX(), p.getY());
//					if (!this.selected.contains(key)) {
//						this.selected.add(key);
//					}
//				}
//			}

		}
		System.out.println(this.selected);

	}

	/**
	 * TODO
	 * @param x
	 * @param y
	 * @return
	 */
	private String hashkey(int x, int y) {
		return "(" + x + "," + y + ")";
	}

	/**
	 * Sets parameter originX to the parameter passed
	 * @param originX
	 */
	public static void setOriginX(double originX) {
		ORIGIN_X = originX;
	}

	/**
	 * Sets parameter originY to the parameter passed
	 * @param originY
	 */
	public static void setOriginY(double originY) {
		ORIGIN_Y = originY;
	}

	/**
	 * Sets parameter stepX to the parameter passed
	 * @param stepX
	 */
	public static void setStepX(double stepX) {
		STEP_X = stepX;
	}

	/**
	 * Sets parameter stepY to the parameter passed
	 * @param stepY
	 */
	public static void setStepY(double stepY) {
		STEP_Y = stepY;
	}
	public void clearSelected() {
//		for (String s: selected) {
//			Tile t = (Tile) tiles.get(s);
//			t.setSelected(false);
//		}
		this.selected.clear();
	}

	public HashMap<String, Node> getTiles() {
		return tiles;
	}

}
