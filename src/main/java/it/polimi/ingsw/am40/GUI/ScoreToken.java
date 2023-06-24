package it.polimi.ingsw.am40.GUI;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;


public class ScoreToken extends Label {
    private Stage primaryStage;

    public ScoreToken(int point, Stage primaryStage) {
        super();
        Image score = Resources.score(point);
        ImageView scoreView = new ImageView(score);
        scoreView.setPreserveRatio(true);
        scoreView.setFitWidth(0.34*Metrics.dim_x_comm*primaryStage.getWidth());
        scoreView.setFitHeight(0.34*Metrics.dim_y_comm*primaryStage.getHeight());
        // Rotate the ImageView by 45 degrees
        double rotationAngle = -7.5;
        Rotate rotate = new Rotate(rotationAngle, scoreView.getFitWidth() / 2, scoreView.getFitHeight() / 2);
        scoreView.getTransforms().add(rotate);
        setGraphic(scoreView);
    }

}


