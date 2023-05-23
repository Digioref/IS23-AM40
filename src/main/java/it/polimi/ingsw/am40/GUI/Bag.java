package it.polimi.ingsw.am40.GUI;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Bag extends Label {
	private ImageView view;

	public Bag() {
		super();
		Image image = Resources.bag();
		view = new ImageView(image);
		view.setPreserveRatio(true);
		view.setFitWidth(Metrics.BAG_WIDTH);
		view.setFitHeight(Metrics.BAG_HEIGHT);
		setGraphic(view);
	}

	public ImageView getView() {
		return view;
	}
	public void resize(double width, double height) {
		if (width != 0) {
			view.setFitWidth(width * Metrics.dim_x_bag);
		}
		if (height != 0) {
			view.setFitHeight(height * Metrics.dim_y_bag);
		}
	}
}
