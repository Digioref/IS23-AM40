package it.polimi.ingsw.am40.GUI;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Chair extends Label {
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
