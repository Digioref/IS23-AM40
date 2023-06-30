package it.polimi.ingsw.am40.GUI;

import it.polimi.ingsw.am40.Model.Position;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 * It represents the graphical element Tile
 */
public class Tile extends Label {

	private final String border_selected = "-fx-border-style: solid; -fx-border-color: black; -fx-border-width: 3;";
	private final String border_none = "-fx-border-style: dotted; -fx-border-color: transparent; -fx-border-width: 4;";

	private Point2D pos;
	private String type;

	private boolean selected = false;
	private boolean pickable = false;

	private ImageView tileView;

	/**
	 * Constructor which creates the tile according to the specified color
	 * @param type color of the tile
	 * @param primaryStage stage where iot is shown
	 */
	public Tile(String type, Stage primaryStage) {
		super();
		if(!type.equals("NOCOLOR")){
			int index = (int) (Math.random() * 3);
			this.type = type;
			Image image = Resources.tile(type, index);
			ImageView view = new ImageView(image);
			tileView = view;
			view.setPreserveRatio(true);
			view.setFitWidth(Metrics.dim_x_tile*primaryStage.getWidth());
			view.setFitHeight(Metrics.dim_y_tile*primaryStage.getHeight());
			setGraphic(view);

			Rectangle clip = new Rectangle(view.getFitWidth(), view.getFitHeight());
			clip.setArcWidth(Metrics.TILE_ROUND_WIDTH);
			clip.setArcHeight(Metrics.TILE_ROUND_HEIGHT);
			view.setClip(clip);

			/* snapshot the rounded image */
			SnapshotParameters parameters = new SnapshotParameters();
			parameters.setFill(Color.TRANSPARENT);
			WritableImage img = view.snapshot(parameters, null);
			view.setClip(null);

			view.setImage(img);
			setStyle(border_none);

			setUserData(this);


			setOnMouseClicked(new EventHandler<>() {
				/**
				 * When a tile is clicked by the mouse, it triggers this event, which changes the border of the tile
				 *
				 * @param event a mouse event
				 */
				@Override
				public void handle(MouseEvent event) {
					if (pickable) {
						Tile tile = (Tile) event.getSource();
						if (!selected) {
							setStyle(border_selected);
						}
						fireEvent(new CustomEvent(CustomEvent.TILE_SELECTED, tile, selected));
						selected = true;
					}
				}
			});
		}


	}

	/**
	 * It sets the position of the tile
	 * @param s string representing the position of the tile
	 */
	public void setPosition(String s) {
		Position p = new Position(0,0);
		p.convertKey(s);
		pos = new Point2D(p.getX(), p.getY());
	}

	/**
	 * It returns the position of the tile
	 * @return the position of the tile
	 */
	public Point2D getPosition() {
		return this.pos;
	}

	/**
	 * It sets the border of the tile according to the flag
	 * @param flag boolean value
	 */
	public void setPickable(boolean flag) {
		if (flag) {
			pickable = true;
			String border_pickable = "-fx-border-style: dotted; -fx-border-color: #008002; -fx-border-width: 4;";
			setStyle(border_pickable);
		} else {
			pickable = false;
			setStyle(border_none);
		}
	}

	/**
	 * It sets the border of the tile to a specific border
	 */
	public void setSelected() {
		setStyle(border_selected);
	}

	/**
	 * It returns the view of the tile
	 * @return view of the tile
	 */
	public ImageView getTileView(){
		return tileView;
	}
	/**
	 * It sets the border of the tile according to the flag
	 * @param selected boolean value
	 */
	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	/**
	 * It returns the color of the tile
	 * @return color of the tile
	 */
	public String getType() {
		return type;
	}
}
