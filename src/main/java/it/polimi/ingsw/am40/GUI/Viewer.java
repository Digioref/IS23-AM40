package it.polimi.ingsw.am40.GUI;

import it.polimi.ingsw.am40.Client.LaunchClient;
import it.polimi.ingsw.am40.JSONConversion.JSONConverterCtoS;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.animation.*;

import java.util.Random;
import java.util.regex.Pattern;

public class Viewer extends Application {

	/*
	 * This variable is needed only for the test methods. Remove it when removing
	 * the test methods.
	 */
	private String connectionType;
	private static Viewer gui;
	private Stage primaryStage;
	private Scene scene;
	private Pane pane;

	public static void main(String[] args) {
		launch(args);
	}

	public static Viewer getGUI() {
		if (gui != null) {
			return gui;
		} else {
			(new Thread(() -> launch())).start();
			while(gui == null || !gui.isPrimaryStageOn())  //ensures that the gui has been launched before proceeding
			{
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		return gui;
	}

	private boolean isPrimaryStageOn() {
		if (primaryStage != null) {
			return true;
		}
		return false;
	}

	@Override
	public void start(Stage stage) {
		this.primaryStage = stage;
		gui = this;
//
//		// -----------  setup page  -----------
//		Pane rootBox = new Pane();
//
//		//stage.setMaximized(true);
//		stage.setFullScreen(true);
//		stage.setTitle("MyShelfie");
//		stage.getIcons().add(Resources.icon());
//		stage.setResizable(true);
//
//		//newScene(stage, rootBox);
//
//		stage.show();
//
//		setBackground(stage, rootBox);
//
//		VBox vbox = createVbox(rootBox);
//
//		setTitle(vbox);
//
//		Text t1 = addDescription(vbox, "Inserisci l'indirizzo ip, L per localHost");
//
//		TextField tf = addTextField(vbox);
//
//		Text t2 = addDescription(vbox, "Scegli un tipo di connessione");
//
//		RadioButton r1 = addToggle(vbox,"RMI", true);
//		RadioButton r2 = addToggle(vbox,"SOCKET", false);
//		setToggles(r1,r2);
//
//		Button b1 = addButton(vbox, "Ready?", true);
//		Button b2 = addButton(vbox, "CONTINUA", false);
//		b1.setOnAction(e -> {
//			setConnection(vbox, tf, t1, t2, r1, r2, b1, b2);
//		});
//
//		Button b3 = addButton(vbox, "Let's goooo", false);
//
//		b2.setOnAction(e -> {
//			setUsername(vbox, tf, t1, b2, b3);
//		});
//
//		// -----------  From here you go to the play scene  -----------
//
//		Pane newRoot = new Pane();
//		b3.setOnAction(e -> {
//			newScene(stage, newRoot);
//		});
//
//		newScene(stage, newRoot);
//		setBackground(stage, newRoot);
//
//		StackPane stack = new StackPane();
//
//		BorderPane home = new BorderPane();
//
//		stack.getChildren().add(home);
//
//
//		//ScrollPane sp = new ScrollPane();   --- Come mai lo scroll pane non va??????
//
//		newRoot.getChildren().add(stack);
//
//		VBox vLeft = new VBox();
//		vLeft.setSpacing(Screen.getPrimary().getVisualBounds().getHeight() * 0.05);
//		VBox vRight = new VBox();
//		vRight.setSpacing(40);
//		vRight.setAlignment(Pos.CENTER);
//		VBox v = new VBox();
//
//		///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//		Bag b = new Bag();
//
//		//aggiunto un metodo per settare board e pg
//		setBookshelvesAndPG(vLeft, stack);
//
//
//		CommonGoalGui cg1 = new CommonGoalGui(1);
//		CommonGoalGui cg2 = new CommonGoalGui(2);
//
//		CommonGoalGuiZOOMEDLabel cg1duplicate = new CommonGoalGuiZOOMEDLabel(1);
//		CommonGoalGuiZOOMEDLabel cg2duplicate = new CommonGoalGuiZOOMEDLabel(2);
//
//		activateZOOM(cg1, cg1duplicate, stack);
//		activateZOOM(cg2, cg2duplicate, stack);
//
//		vRight.getChildren().addAll(cg1, cg2);
//
//		Board board = new Board();
//
//		home.setRight(vRight);
//		home.setLeft(vLeft);
//		home.setCenter(board);
//		home.setBottom(v);



		//home.setBottom(v);

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

//		MenuBar topMenu = MenuObj.createMenuBar(stage);
//		pane.getChildren().add(topMenu);

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
//		Image background_per_pulsanti = new Image("colore_pulsanti.jpg");
//		BackgroundImage bgImg_per_pulsanti = new BackgroundImage(background_per_pulsanti, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT,
//				BackgroundPosition.DEFAULT, new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false));
//
//		Background bg_conferma_pulsante = new Background(bgImg_per_pulsanti);

//		Button button = new Button(tmp);
//		button.setFont(Font.font(20));
//		button.setBackground(bg_conferma_pulsante);
		Button button = new Button(tmp);
		button.setVisible(isVisible);
		button.setFont(Font.font(20));
		pane.getChildren().add(button);

		button.getStylesheets().add("Button.css");

		return button;

	}

	public void setConnection( TextField tf, Text t1) {
		String connectionIp = tf.getText();
		Pattern p = Pattern.compile("^(?:[0-9]{1,3}\\.){3}[0-9]{1,3}$");
		if (!p.matcher(connectionIp).matches() && !connectionIp.equalsIgnoreCase("L") && !connectionIp.equals("")) {
			t1.setText("Insert a valid ip address, please:");
		} else {
			if (connectionIp.equalsIgnoreCase("L") || connectionIp.equals("")) {
				connectionIp = "localhost";
			}
			LaunchClient.startConnection(connectionType, connectionIp);
			setUsername();
		}
////		t1.setText("Scegli uno username");
////		tf.clear();
//
//		pane.getChildren().remove(t2);
//		pane.getChildren().remove(socket);
//		pane.getChildren().remove(rmi);
//		pane.getChildren().remove(oldB);
//
//		newB.setVisible(true);			//////////////////////////////////////////////////////////////////////////////////////////////////////// da rendere rosso con FONT
	}

	public void setUsername() {
//		primaryStage.close();

		pane = new Pane();
		newScene(pane);
		setBackground(primaryStage, pane);

		VBox vbox = createVbox(pane);

		setTitle(vbox);

		Text t1 = addDescription(vbox, "Insert your username: ");

		TextField tf = addTextField(vbox);

		Button b1 = addButton(vbox, "Login", true);
		Text t2 = addDescription(vbox, "");
//		Button b2 = addButton(vbox, "CONTINUE", false);
//		primaryStage.setScene(scene);
//		primaryStage.setFullScreen(true);
		primaryStage.setTitle("MY SHELFIE LOGIN");
		primaryStage.getIcons().add(Resources.icon());
		primaryStage.setResizable(true);
		primaryStage.show();

		b1.setOnAction(e -> {
			if (tf.getText().equals("")) {
				t1.setText("Insert an username, please!");
			} else {
				JSONConverterCtoS jconv = new JSONConverterCtoS();
				jconv.toJSON("login " + tf.getText());
				LaunchClient.getClient().sendMessage(jconv.toString());
			}
		});
//		if (!tf.getText().equals("")) {
//			String tmp = "Welcome " + tf.getText();
//			t1.setText(tmp);
//
//			pane.getChildren().remove(oldB);
//			pane.getChildren().remove(tf);
//
//			newB.setVisible(true);
//
//			waitingAnimation(pane);
//
//		} else {
//			String tmp = t1.getText();
//			tmp = tmp.concat("!");
//			t1.setText(tmp);
//		}
	}

	public void waitingAnimation() {
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

//		pane.getChildren().add(loadImage);
		Pane x = (Pane) pane.getChildren().get(0);
		x.getChildren().add(loadImage);
		rotateTransition.play();
		timeline.play();
	}

	public void newScene(Pane pane) {
		scene = new Scene(pane, 500, 300);
		primaryStage.setScene(scene);
		primaryStage.setFullScreen(true);
	}



	public void setBookshelvesAndPG(VBox vLeft, StackPane stack){
	    int numPlayers = 4;
		for(int i=0; i<numPlayers;i++) {
			HBox row = new HBox();
			Bookshelf bs1 = new Bookshelf();
			BookshelfZOOMED bs1zoomed = new BookshelfZOOMED();
			activateZOOM(bs1,bs1zoomed,stack);
			PersonalGoal pg = new PersonalGoal(0);
			row.getChildren().addAll(bs1, pg);
			vLeft.getChildren().add(row);
		}

	}


	public void activateZOOM(Node notZOOMED, Node zoomedLabel, StackPane stack){
		notZOOMED.setOnMouseEntered(e -> {
			System.out.println("entro");
			stack.getChildren().add(zoomedLabel);
		});

		notZOOMED.setOnMouseExited(e -> {
			System.out.println("esco");
			stack.getChildren().remove(zoomedLabel);
		});
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
	public void chooseConnection() {
		pane = new Pane();
		newScene(pane);
		setBackground(primaryStage, pane);
		//stage.setMaximized(true);
//		scene.setFullScreen(true);
//		scene.setTitle("MyShelfie");
//		stage.getIcons().add(Resources.icon());
//		stage.setResizable(true);
//
//		//newScene(stage, rootBox);
//
//		stage.show();
//
//		setBackground(stage, rootBox);

		VBox vbox = createVbox(pane);

		setTitle(vbox);

		Text t1 = addDescription(vbox, "Insert the server IP (or localhost [L]):");

		TextField tf = addTextField(vbox);

		Text t2 = addDescription(vbox, "Choose a connection type: ");

		RadioButton r1 = addToggle(vbox,"RMI", true);
		RadioButton r2 = addToggle(vbox,"SOCKET", false);
		setToggles(r1,r2);

		Button b1 = addButton(vbox, "Connect", true);
		Text t3 = addDescription(vbox, "");
//		Button b2 = addButton(vbox, "CONTINUE", false);
//		primaryStage.setScene(scene);
//		primaryStage.setFullScreen(true);
		primaryStage.setTitle("MY SHELFIE CONNECTION");
		primaryStage.getIcons().add(Resources.icon());
		primaryStage.setResizable(true);
		primaryStage.show();

		b1.setOnAction(e -> {
			setConnection(tf, t1);
		});

//		Button b3 = addButton(vbox, "Let's goooo", false);
//
//		b2.setOnAction(e -> {
//			setUsername(vbox, tf, t1, b2, b3);
//		});
	}

	public void setplayers() {
		pane = new Pane();
		newScene(pane);
		setBackground(primaryStage, pane);
		VBox vbox = createVbox(pane);

		setTitle(vbox);

		Text t1 = addDescription(vbox, "Insert the number of Players: ");

		TextField tf = addTextField(vbox);

		Button b1 = addButton(vbox, "SetPlayers", true);
		Text t2 = addDescription(vbox, "");
//		Button b2 = addButton(vbox, "CONTINUE", false);
//		primaryStage.setScene(scene);
//		primaryStage.setFullScreen(true);
		primaryStage.setTitle("MY SHELFIE SETPLAYERS");
		primaryStage.getIcons().add(Resources.icon());
		primaryStage.setResizable(true);
		primaryStage.show();

		b1.setOnAction(e -> {
			if (tf.getText() != null) {
				try {
					Integer.parseInt(tf.getText());
				} catch (NumberFormatException ex) {
					t1.setText("Insert an integer, not a string!");
					return;
				}
				JSONConverterCtoS jconv = new JSONConverterCtoS();
				jconv.toJSON("setplayers " + tf.getText());
				LaunchClient.getClient().sendMessage(jconv.toString());
				b1.setDisable(true);
			}  else {
				t1.setText("Insert an integer, please!");
			}
		});
	}

	public void showMessage(String s) {
		System.out.println(s);
		Text text = new Text(s);
		text.setFont(Font.font(25));
		Pane x = (Pane) pane.getChildren().get(0);

//		x.getChildren().remove(x.getChildren().size()-1);
		Text y = (Text) x.getChildren().get(x.getChildren().size()-1);
		y.setText(s);
//		primaryStage.show();
	}
}
