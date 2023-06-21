package it.polimi.ingsw.am40.GUI;

import javafx.scene.SnapshotParameters;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class PersonalGoal extends Label {

	/**
	 * TODO
	 * @param index
	 * @param primaryStage
	 */
	public PersonalGoal(int index, Stage primaryStage) {
		super();
		Image image = Resources.personalGoal(index);
		ImageView view = new ImageView(image);
		view.setPreserveRatio(true);
//		double screenHeight = Screen.getPrimary().getVisualBounds().getHeight() * 0.20;
		//view.setFitWidth(100);
		view.setFitWidth(Metrics.dim_x_pers*primaryStage.getWidth());
		view.setFitHeight(Metrics.dim_y_pers*primaryStage.getHeight());
		view.setPreserveRatio(true);
		setGraphic(view);

		Rectangle clip = new Rectangle(view.getFitWidth(), view.getFitHeight());
		clip.setArcWidth(20);
		clip.setArcHeight(20);
		view.setClip(clip);

		/* snapshot the rounded image */
		SnapshotParameters parameters = new SnapshotParameters();
		parameters.setFill(Color.TRANSPARENT);
		WritableImage img = view.snapshot(parameters, null);

		/* remove the rounding clip so that our effect can show through */
		view.setClip(null);

		view.setImage(img);
		view.setEffect(new DropShadow(20, Color.WHITE));
	}
}
