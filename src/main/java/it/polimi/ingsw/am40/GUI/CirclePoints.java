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
 * Class that represents the graphic elements circle-points
 */
public class CirclePoints extends AnchorPane {
    private ImageView view;
    private Pane image;
    private Label score;
    public CirclePoints() {
        super();
        Image image = Resources.pointsCircle();
        this.image = new StackPane();
        view = new ImageView(image);
        view.setPreserveRatio(true);
        view.setFitWidth(120);
        view.setFitHeight(120);
        this.image.getChildren().add(view);
        score = new Label("0");
        this.image.getChildren().add(score);
        score.setAlignment(Pos.CENTER);
        score.setFont(Font.font(30));
        score.setVisible(true);
        score.setTextFill(Color.BLACK);
        getChildren().add(this.image);
    }

    public void setScore(int x) {
        score.setText(Integer.toString(x));
    }
}
