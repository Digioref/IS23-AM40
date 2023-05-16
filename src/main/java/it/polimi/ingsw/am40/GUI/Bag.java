package it.polimi.ingsw.am40.GUI;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Bag extends Label {

	public Bag() {
		super();
		Image image = Resources.bag();
		ImageView view = new ImageView(image);
		view.setPreserveRatio(true);
		view.setFitWidth(Metrics.BAG_WIDTH);
		view.setFitHeight(Metrics.BAG_HEIGHT);
		setGraphic(view);
	}
}
