package it.polimi.ingsw.am40.GUI;

import it.polimi.ingsw.am40.Model.Position;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Tile extends Label {

	private final String border_selected = "-fx-border-style: solid; -fx-border-color: black; -fx-border-width: 3;";
	private final String border_pickable = "-fx-border-style: dotted; -fx-border-color: #008002; -fx-border-width: 4;";
	//private final String border_none = "-fx-border-width: 0;";
	private final String border_none = "-fx-border-style: dotted; -fx-border-color: transparent; -fx-border-width: 4;";

	private Point2D pos;

	private boolean selected = false;
	private boolean pickable = false;
	private boolean nocolor = false;

	public Tile(String type, Stage primaryStage) {
		super();

		int index = (int) (Math.random() * 3);
		Image image = Resources.tile(type, index);
		ImageView view = new ImageView(image);
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


		setOnMouseClicked(new EventHandler<MouseEvent>() {
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

	void setPosition(int x, int y) {
		this.pos = new Point2D(x, y);
	}
	public void setPosition(String s) {
		Position p = new Position(0,0);
		p.convertKey(s);
		pos = new Point2D(p.getX(), p.getY());
	}

	Point2D getPosition() {
		return this.pos;
	}

	void setPickable(boolean flag) {
		if (flag) {
			pickable = true;
			setStyle(border_pickable);
		} else {
			pickable = false;
			setStyle(border_none);
		}
	}
	public void setSelected() {
		setStyle(border_selected);
	}

	public boolean isNocolor() {
		return nocolor;
	}
}
