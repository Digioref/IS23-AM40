package it.polimi.ingsw.am40.GUI;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class RedCross extends Label {
    private ImageView view;

    public RedCross() {
        super();
        Image image = Resources.redCross();
        view = new ImageView(image);
        view.setPreserveRatio(true);
        view.setFitWidth(30);
        view.setFitHeight(30);
        setGraphic(view);
    }
    public void setSize(double w, double h) {
        view.setFitWidth(w);
        view.setFitHeight(h);
        setPrefSize(w, h);
        setMinSize(w, h);
    }
}
