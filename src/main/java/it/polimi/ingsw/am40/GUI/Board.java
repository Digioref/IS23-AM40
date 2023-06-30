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

	/**
	 * Class that represent the graphic element board (to be shown on the primary stage)
	 * @param primaryStage stage where the board is displayed
	 */
	public Board(Stage primaryStage) {
		super();
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
	 * It places the tile in the parameter in the board
	 * @param tile a tile
	 */
	public void place(Tile tile) {
		Point2D pos = tile.getPosition();
		int x = (int) pos.getX();
		int y = (int) pos.getY();
		String key = hashkey(x, y);


		AnchorPane.setLeftAnchor(tile, ORIGIN_X + x*STEP_X);
		AnchorPane.setTopAnchor(tile, ORIGIN_Y - (y*STEP_Y));

		/* Store into the hash-map */
		tiles.put(key, tile);

		/* Add to the view */
		getChildren().add(tile);
	}


	/**
	 * It returns true if the position is selectable
	 * @param x x coordinate
	 * @param y y coordinate
	 * @return true if the tile in the position is selectable, false otherwise
	 */
	public boolean select(int x, int y) {
		String key = hashkey(x, y);
			selected.add(key);
		return (selected.size() == MAX_SELECTABLE);
	}

	/**
	 * It returns true if there are tiles already selected
	 * @return true if some tiles have been already selected, false otherwise
	 */
	public boolean isSelectedNotEmpty() {
		return (selected.size() != 0);
	}

	/**
	 * It removes from the board one of the tiles selected
	 * @return a tile, as a Node
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
	 * It is called when a player has just selected a tile, so it refreshes the board and the selectable tiles from the board
	 * @param map a map containing the selectable tiles
	 * @param selected positions of the tiles already selected
	 * @param board map representing the board
	 */
	public void clearUpdate(Map<String, String> map, ArrayList<Position> selected, Map<String, String> board) {
		getChildren().clear();
		for (String s: board.keySet()) {
				Tile t = (Tile) tiles.get(s);
				if (!board.get(s).equals("NOCOLOR")) {
					t.setPickable(map.containsKey(s));
					Position p = new Position();
					p.convertKey(s);
					if (selected.contains(p)) {
						t.setSelected();
						String key = hashkey(p.getX(), p.getY());
						if (!this.selected.contains(key)) {
							this.selected.add(key);
						}
					}
					getChildren().add(t);
				}
		}
		System.out.println(this.selected);

	}


	private String hashkey(int x, int y) {
		return "(" + x + "," + y + ")";
	}

	/**
	 * Sets parameter originX to the parameter passed
	 * @param originX x coordinate of the origin of the board
	 */
	public static void setOriginX(double originX) {
		ORIGIN_X = originX;
	}

	/**
	 * Sets parameter originY to the parameter passed
	 * @param originY y coordinate of the origin of the board
	 */
	public static void setOriginY(double originY) {
		ORIGIN_Y = originY;
	}

	/**
	 * Sets parameter stepX to the parameter passed
	 * @param stepX x distance between the origin of each tile
	 */
	public static void setStepX(double stepX) {
		STEP_X = stepX;
	}

	/**
	 * Sets parameter stepY to the parameter passed
	 * @param stepY y distance between the origin of each tile
	 */
	public static void setStepY(double stepY) {
		STEP_Y = stepY;
	}
	public void clearSelected() {
		for (String s: selected) {
			Tile t = (Tile) tiles.get(s);
			t.setSelected(false);
		}
		this.selected.clear();
	}

	public HashMap<String, Node> getTiles() {
		return tiles;
	}

}
