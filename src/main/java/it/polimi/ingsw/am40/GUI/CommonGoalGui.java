package it.polimi.ingsw.am40.GUI;

import javafx.scene.SnapshotParameters;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class CommonGoalGui extends Label {
	private ImageView view;
	public CommonGoalGui(int index) {
		super();
		Image image = Resources.commonGoal(index);
		view = new ImageView(image);
		view.setPreserveRatio(true);
		view.setFitWidth(Metrics.COMMON_GOAL_WIDTH);
		view.setFitHeight(Metrics.COMMON_GOAL_HEIGHT);
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

	public void resize(double width, double height) {
		if (width != 0) {
			view.setFitWidth(Metrics.dim_x_comm*width);
		}
		if (height != 0) {
			view.setFitHeight(Metrics.dim_y_comm*height);
		}
	}
}
