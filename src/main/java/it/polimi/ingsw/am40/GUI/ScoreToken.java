package it.polimi.ingsw.am40.GUI;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class ScoreToken extends Label {

    private static final int FIRST_GOAL = 1;
    private static final int SECOND_GOAL = 2;
    private int objective;
    private Stage primaryStage;

    public ScoreToken(int order,int goal, Stage primaryStage) {
        super();
        Image score = null;
        objective=goal;
        this.primaryStage=primaryStage;

        switch (order) {
            case (1):
                score = Resources.score(4);
                break;
            case (2):
                score = Resources.score(8);
                break;
            case (3):
                score = Resources.score(6);
                break;
            case (4):
                score = Resources.score(2);
            default:
                break;
        }
        ImageView scoreView = new ImageView(score);
        scoreView.setPreserveRatio(true);
        scoreView.setFitWidth(Metrics.dim_x_comm*primaryStage.getWidth());
        scoreView.setFitHeight(Metrics.dim_y_comm*primaryStage.getHeight());
        setGraphic(scoreView);
        setToken(objective,scoreView);
    }

    private void setToken(int obj,ImageView scoreview){
        if(obj==FIRST_GOAL){
            Place(Metrics.d_y_comm1, scoreview);
        }
        else if(obj==SECOND_GOAL){
            Place(Metrics.d_y_comm2,scoreview);
        }
    }

    private void Place(Double y, ImageView scoreView){
        System.out.println("SC X: "+ Metrics.d_x_comm*primaryStage.getWidth() + "  SC Y: "+ y*primaryStage.getHeight());
        AnchorPane.setLeftAnchor(scoreView,Metrics.d_x_comm*primaryStage.getWidth());
        //AnchorPane.setLeftAnchor(scoreView, 200.0);
        AnchorPane.setTopAnchor(scoreView,1.2*y*primaryStage.getHeight());
    }

}


