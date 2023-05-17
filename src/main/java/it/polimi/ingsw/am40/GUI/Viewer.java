package it.polimi.ingsw.am40.GUI;

import it.polimi.ingsw.am40.Model.CommonGoal;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;

import java.util.Random;

public class Viewer extends Application {

	/*
	 * This variable is needed only for the test methods. Remove it when removing
	 * the test methods.
	 */
	private String connectionType;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) {

		// -----------  setup page  -----------

		Pane rootBox = new Pane();

		//stage.setMaximized(true);
		stage.setFullScreen(true);
		stage.setTitle("MyShelfie");
		stage.getIcons().add(Resources.icon());
		stage.setResizable(true);

		newScene(stage, rootBox);

		stage.show();

		setBackground(stage, rootBox);

		VBox vbox = createVbox(rootBox);

		setTitle(vbox);

		Text t1 = addDescription(vbox, "Inserisci l'indirizzo ip, L per localHost");

		TextField tf = addTextField(vbox);

		Text t2 = addDescription(vbox, "scegli un tipo di connessione");

		RadioButton r1 = addToggle(vbox,"RMI", true);
		RadioButton r2 = addToggle(vbox,"SOCKET", false);
		setToggles(r1,r2);

		Button b1 = addButton(vbox, "Ready?", true);
		Button b2 = addButton(vbox, "CONTINUA", false);
		b1.setOnAction(e -> {
			setConnection(vbox, tf, t1, t2, r1, r2, b1, b2);
		});

		Button b3 = addButton(vbox, "Let's goooo", false);

		b2.setOnAction(e -> {
			setUsername(vbox, tf, t1, b2, b3);
		});

		// -----------  From here you go to the play scene  -----------

		Pane newRoot = new Pane();
		b3.setOnAction(e -> {
			newScene(stage, newRoot);
		});

		//newScene(stage, newRoot);
		setBackground(stage, newRoot);

		BorderPane home = new BorderPane();

		//ScrollPane sp = new ScrollPane();   --- Come mai lo scroll pane non va??????

		newRoot.getChildren().add(home);

		VBox vLeft = new VBox();
		VBox vRight = new VBox();
		VBox v = new VBox();



		Bag b = new Bag();
		Bookshelf bs1 = new Bookshelf();
		Bookshelf bs2 = new Bookshelf();
		Bookshelf bs3 = new Bookshelf();
		Bookshelf bs4 = new Bookshelf();
		CommonGoalGui cg1 = new CommonGoalGui(1);
		CommonGoalGui cg2 = new CommonGoalGui(2);

		vLeft.getChildren().addAll(bs1,bs2,bs3, bs4);
		vRight.getChildren().addAll(cg1, cg2);
		Board board = new Board();

		home.setRight(vRight);
		home.setLeft(vLeft);
		home.setCenter(board);
		home.setBottom(v);







		//ScrollPane scrollPane = new ScrollPane(rootBox);

		//scene = new Scene(scrollPane);





		/* Test: populate the board */
		//populateBoard();
	}

	public void setBackground(Stage stage, Pane pane) {
		double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
		double screenHeight = Screen.getPrimary().getVisualBounds().getHeight();
		pane.setPrefSize(screenWidth, screenHeight);

		Image background = Resources.background();
		BackgroundImage bgImg = new BackgroundImage(background, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT,
				BackgroundPosition.DEFAULT, new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false));
		pane.setBackground(new Background(bgImg));

		MenuBar topMenu = MenuObj.createMenuBar(stage);
		pane.getChildren().add(topMenu);

	}

	public VBox createVbox(Pane pane) {
		VBox vbox = new VBox();
		vbox.setSpacing(10);
		vbox.setAlignment(Pos.CENTER);
		pane.getChildren().add(vbox);
		return vbox;
	}

	public void setTitle(Pane pane) {
		double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
		Image title = Resources.title();
		ImageView imageView = new ImageView(title);
		imageView.setFitWidth(screenWidth);
		imageView.setPreserveRatio(true);
		pane.getChildren().addAll(imageView);
	}

	public Text addDescription(Pane pane, String tmp) {
		Text text = new Text(tmp);
		text.setFont(Font.font(25));
		pane.getChildren().add(text);
		return text;
	}

	public TextField addTextField(Pane pane) {
		TextField textField = new TextField();
		textField.setMaxWidth(200);
		pane.getChildren().add(textField);
		return textField;
	}

	public RadioButton addToggle(Pane pane, String tmp, Boolean isSelected) {
		RadioButton button = new RadioButton(tmp);
		button.setFont(Font.font(20));
		button.setSelected(isSelected);
		pane.getChildren().add(button);
		return button;
	}

	public void setToggles(RadioButton rmi, RadioButton socket) {
		ToggleGroup tg = new ToggleGroup();

		connectionType = "SOCKET";

		socket.setOnAction(e -> {
			connectionType = "SOCKET";
		});
		rmi.setOnAction(e -> {
			connectionType = "RMI";
		});

		socket.setToggleGroup(tg);
		rmi.setToggleGroup(tg);

	}

	public Button addButton(Pane pane, String tmp, Boolean isVisible) {
		Image background_per_pulsanti = new Image("colore_pulsanti.jpg");
		BackgroundImage bgImg_per_pulsanti = new BackgroundImage(background_per_pulsanti, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT,
				BackgroundPosition.DEFAULT, new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false));

		Background bg_conferma_pulsante = new Background(bgImg_per_pulsanti);

		Button button = new Button(tmp);
		button.setFont(Font.font(20));
		button.setBackground(bg_conferma_pulsante);
		button.setVisible(isVisible);
		pane.getChildren().add(button);

		return button;

	}

	public void setConnection(Pane pane, TextField tf, Text t1, Text t2, RadioButton socket, RadioButton rmi, Button oldB, Button newB) {
		String connectionIp = tf.getText();
		if (!connectionIp.equals("")) {
			if (connectionIp.equalsIgnoreCase("L")) {
				connectionIp = "localhost";
			}
			//LaunchClient.startConnection(connectionType, connectionIp);
			t1.setText("Scegli uno username");
			tf.clear();

			pane.getChildren().remove(t2);
			pane.getChildren().remove(socket);
			pane.getChildren().remove(rmi);
			pane.getChildren().remove(oldB);

			newB.setVisible(true);
		} else {
			t1.setText("Devi selezionare un indirizzo ip se no non ti mando avanti");
			//////////////////////////////////////////////////////////////////////////////////////////////////////// da rendere rosso con FONT
		}
	}

	public void setUsername(Pane pane, TextField tf, Text t1, Button oldB, Button newB) {
		if (!tf.getText().equals("")) {
			String tmp = "Welcome " + tf.getText();
			t1.setText(tmp);

			pane.getChildren().remove(oldB);
			pane.getChildren().remove(tf);

			newB.setVisible(true);

			waitingAnimation(pane);

		} else {
			String tmp = t1.getText();
			tmp = tmp.concat("!");
			t1.setText(tmp);
		}
	}

	public void waitingAnimation(Pane pane) {
		Image im = Resources.tile(1,0);
		ImageView loadImage = new ImageView(im);
		loadImage.setFitWidth(100);
		loadImage.setPreserveRatio(true);

		RotateTransition rotateTransition = new RotateTransition(Duration.seconds(2), loadImage);
		rotateTransition.setByAngle(360); // Rotate by 360 degrees
		rotateTransition.setCycleCount(Animation.INDEFINITE); // Repeat indefinitely
		rotateTransition.setAutoReverse(false); // Do not reverse the animation

		Timeline timeline = new Timeline(
				new KeyFrame(Duration.ZERO, event -> {
					Random random = new Random();
					int type = random.nextInt(5) + 2;
					int index = random.nextInt(4);
					Image tmp = Resources.tile(type,index);
					loadImage.setImage(tmp);
				}),
				new KeyFrame(Duration.seconds(2), event -> {

				})
		);
		timeline.setCycleCount(Timeline.INDEFINITE); // Repeat indefinitely

		pane.getChildren().add(loadImage);
		rotateTransition.play();
		timeline.play();
	}

	public void newScene(Stage stage, Pane pane) {
		final Scene scene = new Scene(pane);
		stage.setScene(scene);
		stage.setFullScreen(true);
	}



	/* Test methods */
/*
	void populateBoard() {
		addTile(Resources.TILE_TYPE_CAT, 0, 0);
		addTile(Resources.TILE_TYPE_FRAME, 0, 1);
		addTile(Resources.TILE_TYPE_FLOWER, 0, 2);
		addTile(Resources.TILE_TYPE_TROPHY, 0, 3);
		addTile(Resources.TILE_TYPE_CAT, 0, -1);
		addTile(Resources.TILE_TYPE_GAME, 0, -2);
		addTile(Resources.TILE_TYPE_BOOK, 0, -3);

		addTile(Resources.TILE_TYPE_CAT, -1, 0);
		addTile(Resources.TILE_TYPE_FRAME, -1, 1);
		addTile(Resources.TILE_TYPE_CAT, -1, 2);
		addTile(Resources.TILE_TYPE_BOOK, -1, 3);
		addTile(Resources.TILE_TYPE_GAME, -1, -1);
		addTile(Resources.TILE_TYPE_TROPHY, -1, -2);

		addTile(Resources.TILE_TYPE_TROPHY, -2, 0);
		addTile(Resources.TILE_TYPE_BOOK, -2, 1);
		addTile(Resources.TILE_TYPE_CAT, -2, -1);
		addTile(Resources.TILE_TYPE_FRAME, -3, 0);
		addTile(Resources.TILE_TYPE_CAT, -3, -1);

		addTile(Resources.TILE_TYPE_FRAME, 1, 0);
		addTile(Resources.TILE_TYPE_TROPHY, 1, 1);
		addTile(Resources.TILE_TYPE_GAME, 1, 2);
		addTile(Resources.TILE_TYPE_TROPHY, 1, -1);
		addTile(Resources.TILE_TYPE_CAT, 1, -2);
		addTile(Resources.TILE_TYPE_FRAME, 1, -3);

		addTile(Resources.TILE_TYPE_CAT, 2, 0);
		addTile(Resources.TILE_TYPE_BOOK, 2, 1);
		addTile(Resources.TILE_TYPE_GAME, 2, -1);

		addTile(Resources.TILE_TYPE_FLOWER, 3, 0);
		addTile(Resources.TILE_TYPE_BOOK, 3, 1);

		setTilePickable(3, 1);
		setTilePickable(3, 0);
		setTilePickable(2, 1);
		setTilePickable(1, 2);
		setTilePickable(1, -2);
		setTilePickable(0, 3);
		setTilePickable(-1, 3);
		setTilePickable(-1, 2);
		setTilePickable(-2, 1);
		setTilePickable(-3, 0);
		setTilePickable(-3, -1);
		setTilePickable(-2, -1);
		setTilePickable(-1, -2);
		setTilePickable(0, -3);
		setTilePickable(1, -3);
		setTilePickable(1, -3);
		setTilePickable(2, -1);
	}

	void addTile(int type, int x, int y) {
		Tile tile;

		tile = new Tile(type);
		tile.setPosition(x, y);
		viewController.fireEvent(new CustomEvent(CustomEvent.BOARD_ADD_TILE, tile));
	}

	void setTilePickable(int x, int y) {
		viewController.fireEvent(new CustomEvent(CustomEvent.BOARD_TILE_PICKABLE, x, y));
	}
	*/

}
