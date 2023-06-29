package it.polimi.ingsw.am40.GUI;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

/**
 * It represents the graphical element Score Token
 */

public class ScoreToken extends Label {
    private final ImageView view;

    /**
     * Constructor which chooses the token according to the score
     * @param point score of the token
     * @param primaryStage stage where it is shown
     */
    public ScoreToken(int point, Stage primaryStage) {
        super();
        Image score = Resources.score(point);
        ImageView scoreView = new ImageView(score);
        this.view= scoreView;
        scoreView.setPreserveRatio(true);
        scoreView.setFitWidth(0.34*Metrics.dim_x_comm*primaryStage.getWidth());
        scoreView.setFitHeight(0.34*Metrics.dim_y_comm*primaryStage.getHeight());
        // Rotate the ImageView by 45 degrees
        double rotationAngle = -7.5;
        Rotate rotate = new Rotate(rotationAngle, scoreView.getFitWidth() / 2, scoreView.getFitHeight() / 2);
        scoreView.getTransforms().add(rotate);
        setGraphic(scoreView);
    }

    /**
     * It returns the image view of the token
     * @return image view of the token
     */
    public ImageView getImageview(){
        return this.view;
    }


}


