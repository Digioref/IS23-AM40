package it.polimi.ingsw.am40.GUI;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * It represents the graphical element Red cross, which after pressed removes all the selected tiles
 */

public class RedCross extends Label {
    private final ImageView view;

    /**
     * Constructor of the class
     */

    public RedCross() {
        super();
        Image image = Resources.redCross();
        view = new ImageView(image);
        view.setPreserveRatio(true);
        view.setFitWidth(30);
        view.setFitHeight(30);
        setGraphic(view);
    }

    /**
     * It sets the size of the image
     * @param w width
     * @param h height
     */
    public void setSize(double w, double h) {
        view.setFitWidth(w);
        view.setFitHeight(h);
        setPrefSize(w, h);
        setMinSize(w, h);
    }
}
