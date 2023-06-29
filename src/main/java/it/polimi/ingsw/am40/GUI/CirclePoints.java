package it.polimi.ingsw.am40.GUI;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * Class that represents the graphic elements circle-points, where inside there is the hidden score
 */
public class CirclePoints extends AnchorPane {
    private final Label score;

    /**
     * Constructor setting the image
     */
    public CirclePoints() {
        super();
        Image image = Resources.pointsCircle();
        Pane image1 = new StackPane();
        ImageView view = new ImageView(image);
        view.setPreserveRatio(true);
        view.setFitWidth(120);
        view.setFitHeight(120);
        image1.getChildren().add(view);
        score = new Label("0");
        image1.getChildren().add(score);
        score.setAlignment(Pos.CENTER);
        score.setFont(Font.font(30));
        score.setVisible(true);
        score.setTextFill(Color.BLACK);
        getChildren().add(image1);
    }

    /**
     * It sets the score inside the image
     * @param x score to be set
     */
    public void setScore(int x) {
        score.setText(Integer.toString(x));
    }
}
