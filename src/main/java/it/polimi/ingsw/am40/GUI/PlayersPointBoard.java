package it.polimi.ingsw.am40.GUI;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Map;

public class PlayersPointBoard extends AnchorPane {
    private VBox vbox;
    private ArrayList<HBox> hboxes;
    public PlayersPointBoard(Stage primaryStage) {
        super();
        setPrefSize(Metrics.dim_x_ppb* primaryStage.getWidth(), Metrics.dim_y_ppb*primaryStage.getHeight());

        Image image = Resources.background();

        BackgroundImage boardImg = new BackgroundImage(image, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(1, 1, true, true, false, false));

        setBackground(new Background(boardImg));
        setEffect(new DropShadow(20, Color.BLACK));
        setVisible(true);
        vbox = new VBox();
        vbox.setSpacing(12);
        vbox.setPadding(new Insets(8, 8, 8, 8));
        getChildren().add(vbox);
        hboxes = new ArrayList<>();

    }

    public void addPlayer(String name) {
        HBox hBox = new HBox();
        hBox.setSpacing(18);
        Label l = new Label(name);
        l.setTextFill(Color.BLACK);
        l.setFont(Font.font(20));
        hBox.getChildren().add(l);
        hboxes.add(hBox);
        vbox.getChildren().add(hBox);
    }

    public void addScores(Map<String, Integer> map) {
        for (HBox h: hboxes) {
            Label x = (Label) h.getChildren().get(0);
            Label l = new Label("Score: " + map.get(x.getText()));
            l.setFont(Font.font(20));
            if (h.getChildren().size() == 2) {
                h.getChildren().remove(1);
            }
            h.getChildren().add(l);
        }
    }

    public ArrayList<HBox> getHboxes() {
        return hboxes;
    }

    public void setCurrentPlayer(String s) {
        for (HBox h: hboxes) {
            Label l = (Label) h.getChildren().get(0);
            l.setTextFill(Color.BLACK);
            if (l.getText().equals(s)) {
                l.setTextFill(Color.GREEN);
            }
        }
    }
}
