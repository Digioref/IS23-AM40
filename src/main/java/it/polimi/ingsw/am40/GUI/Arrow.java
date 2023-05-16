package it.polimi.ingsw.am40.GUI;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Arrow extends Label {

	public static final int UP = 1;
	public static final int DOWN = 2;
	public static final int LEFT = 3;
	public static final int RIGHT = 4;

	private int index;
	private int direction;

	public Arrow(int dir) {
		super();

		Image image = Resources.arrowDown();
		ImageView view = new ImageView(image);
		view.setPreserveRatio(true);
		view.setFitWidth(30);
		view.setFitHeight(30);

		switch (dir) {
		case UP:
			view.setRotate(180);
			break;
		case DOWN:
			view.setRotate(0);
			break;
		case LEFT:
			view.setRotate(90);
			break;
		case RIGHT:
			view.setRotate(270);
			break;
		default:
			view.setRotate(0);
			break;
		}

		index = 0;
		direction = dir;

		setGraphic(view);
	}

	public void setSize(double w, double h) {
		ImageView v;
		switch (direction) {
		case UP:
		case DOWN:
			v = (ImageView) this.getGraphic();
			v.setFitWidth(w);
			v.setFitHeight(h);
			setPrefSize(w, h);
			setMinSize(w, h);
			break;
		case LEFT:
		case RIGHT:
			v = (ImageView) this.getGraphic();
			v.setFitWidth(h);
			v.setFitHeight(w);
			setPrefSize(h, w);
			setMinSize(h, w);
			break;
		default:
			break;
		}
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
}
