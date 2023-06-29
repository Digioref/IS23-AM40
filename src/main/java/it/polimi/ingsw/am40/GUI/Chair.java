package it.polimi.ingsw.am40.GUI;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Class that represents the graphic element chair
 */
public class Chair extends Label {
    /**
     * Constructor setting the width and the height of the chair
     * @param w width
     * @param h height
     */
    public Chair(double w, double h) {
        Image image = Resources.chair();
        ImageView view = new ImageView(image);
        view.setPreserveRatio(true);
        view.setFitWidth(w*Metrics.dim_x_chair);
        view.setFitHeight(h*Metrics.dim_y_chair);
        view.setPreserveRatio(true);
        setGraphic(view);
    }
}
