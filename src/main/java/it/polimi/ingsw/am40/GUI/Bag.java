package it.polimi.ingsw.am40.GUI;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class Bag extends Label {

	public Bag(Stage primaryStage) {
		super();
		Image image = Resources.bag();
		ImageView view = new ImageView(image);
		view.setPreserveRatio(true);
		view.setFitWidth(Metrics.dim_x_bag*primaryStage.getWidth());
		view.setFitHeight(Metrics.dim_y_bag*primaryStage.getHeight());
		setGraphic(view);
	}

}
