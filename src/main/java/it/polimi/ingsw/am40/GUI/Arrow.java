package it.polimi.ingsw.am40.GUI;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Class that represents the graphic element arrow
 */
public class Arrow extends Label {

	public static final int UP = 1;
	public static final int DOWN = 2;
	public static final int LEFT = 3;
	public static final int RIGHT = 4;

	private int index;
	private final int direction;

	/**
	 * Class representing arrow (graphic element)
	 * @param dir direction of the arrow
	 */
	public Arrow(int dir) {
		super();

		Image image = Resources.arrowDown();
		ImageView view = new ImageView(image);
		view.setPreserveRatio(true);
		view.setFitWidth(30);
		view.setFitHeight(30);

		switch (dir) {
			case UP -> view.setRotate(180);
			case LEFT -> view.setRotate(90);
			case RIGHT -> view.setRotate(270);
			default -> view.setRotate(0);
		}

		index = 0;
		direction = dir;

		setGraphic(view);
	}

	/**
	 * Sets the size (w = width, h = height) of the arrow
	 * @param w width
	 * @param h height
	 */
	public void setSize(double w, double h) {
		ImageView v;
		switch (direction) {
			case UP, DOWN -> {
				v = (ImageView) this.getGraphic();
				v.setFitWidth(w);
				v.setFitHeight(h);
				setPrefSize(w, h);
				setMinSize(w, h);
			}
			case LEFT, RIGHT -> {
				v = (ImageView) this.getGraphic();
				v.setFitWidth(h);
				v.setFitHeight(w);
				setPrefSize(h, w);
				setMinSize(h, w);
			}
			default -> {
			}
		}
	}

	/**
	 * It returns the index of the arrow
	 * @return the index of the Arrow
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * Sets the index of the arrow
	 * @param index index provided
	 */
	public void setIndex(int index) {
		this.index = index;
	}
}
