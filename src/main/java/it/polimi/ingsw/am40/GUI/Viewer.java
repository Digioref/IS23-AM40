package it.polimi.ingsw.am40.GUI;

import it.polimi.ingsw.am40.Client.LaunchClient;
import it.polimi.ingsw.am40.JSONConversion.JSONConverterCtoS;
import it.polimi.ingsw.am40.Model.GroupChat;
import it.polimi.ingsw.am40.Model.Position;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.regex.Pattern;

public class Viewer extends Application {
	private final Arrow arrowRight = new Arrow(Arrow.RIGHT);
	private final ArrayList<Arrow> arrowDownList = new ArrayList<>();
	private static final int ARROWS_DOWN = 5;
	private String connectionType;
	private static Viewer gui;
	private Stage primaryStage;
	private Scene scene;
	private Pane pane;
	private final RedCross redCross = new RedCross();

	private AnchorPane gameBoard;
	private String nickname;
	private int numPlayers;
	private Bag bag;
	private Board board;
	private CommonGoalGui c1;
	private CommonGoalGui c2;
	private PersonalGoal p;
	private CommandBoard commandBoard;
	private Bookshelf bookshelf;
	private ArrayList<Bookshelf> bookshelves;
	private ArrayList<String> names;
	private Alert alert;
	private Alert errorAlert;
	@FXML
	private Button ChatButton;
	private boolean isVisible;
	private boolean isNew;
	private VBox chatContainer = new VBox();
	private double aspectRatio = 16.0 / 9.0; // Desired aspect ratio
	private Circle dotIndicator;

	private VBox messages;

	private ArrayList<Integer> currentToken;

	private ArrayList<ScoreToken> scoringToken;

	private ImageView endToken;




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
		this.bookshelves = new ArrayList<>();
//		alert = new Alert(Alert.AlertType.INFORMATION);
//		errorAlert = new Alert(Alert.AlertType.ERROR);
//		alert.getDialogPane().getStylesheets().add("Alert.css");
//		errorAlert.getDialogPane().getStylesheets().add("Error.css");
		primaryStage.setResizable(true);

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
//		CommonGoalGuiZOOMED cg1duplicate = new CommonGoalGuiZOOMED(1);
//		CommonGoalGuiZOOMED cg2duplicate = new CommonGoalGuiZOOMED(2);
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
		//pane.setPrefSize(screenWidth, screenHeight);
		pane.setPrefSize(15.5,8.7);

		//setting the background
		Image backGroundImage = Resources.background();
		ImageView backGroundView = new ImageView(backGroundImage);
		pane.getChildren().add(backGroundView);

		//setting the background
		AnchorPane.setTopAnchor(backGroundView,0.0);
		AnchorPane.setRightAnchor(backGroundView,0.0);
		AnchorPane.setLeftAnchor(backGroundView,0.0);
		AnchorPane.setBottomAnchor(backGroundView,0.0);

		backGroundView.fitWidthProperty().bind(scene.widthProperty());
		backGroundView.fitHeightProperty().bind(scene.heightProperty());
	}


	public VBox createVbox(Pane pane) {
		VBox vbox = new VBox();
		vbox.setSpacing(10);
		vbox.setAlignment(Pos.CENTER);
		vbox.setMaxWidth(primaryStage.getWidth());
		primaryStage.widthProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				vbox.setMaxWidth((double) newValue);
			}
		});
//		vbox.autosize();
		pane.getChildren().add(vbox);
		return vbox;
	}


	public void setTitle(Pane pane) {
		double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
		//double sceneWidth = scene.getWindow().getWidth();
		Image title = Resources.title();
		ImageView imageView = new ImageView(title);
//  	imageView.setFitWidth(screenWidth);
//		imageView.setFitWidth(sceneWidth);
		imageView.setFitWidth(primaryStage.getWidth());
		imageView.setFitHeight(primaryStage.getHeight());
		imageView.setPreserveRatio(true);
		pane.getChildren().addAll(imageView);
		primaryStage.widthProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				imageView.setFitWidth((double)newValue);
			}
		});
		primaryStage.heightProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				imageView.setFitHeight((double) newValue);
			}
		});
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
		textField.setAlignment(Pos.CENTER);
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
		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
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
//		primaryStage.setResizable(true);
		primaryStage.show();

		b1.setOnAction(e -> {
			if (tf.getText().equals("")) {
				t1.setText("Insert an username, please!");
			} else {
				nickname = tf.getText();
				LaunchClient.getClient().sendMessage("login " + tf.getText());
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
		Pane x = (Pane) pane.getChildren().get(1);
		x.getChildren().add(loadImage);
		rotateTransition.play();
		timeline.play();
	}

	public void newScene(Pane pane) {
		scene = new Scene(pane, Screen.getPrimary().getVisualBounds().getWidth(), Screen.getPrimary().getVisualBounds().getHeight());
		primaryStage.setScene(scene);
		primaryStage.setFullScreen(true);
	}



	public void setBookshelvesAndPG(VBox vLeft, StackPane stack){
//	    int numPlayers = 4;
//		for(int i=0; i<numPlayers;i++) {
//			HBox row = new HBox();
//			Bookshelf bs1 = new Bookshelf();
//			BookshelfZOOMED bs1zoomed = new BookshelfZOOMED();
//			activateZOOM(bs1,bs1zoomed,stack);
//			PersonalGoal pg = new PersonalGoal(0);
//			row.getChildren().addAll(bs1, pg);
//			vLeft.getChildren().add(row);
//		}

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

	public void newMessage(ArrayList<String> array1, ArrayList<String> array2, ArrayList<String> array3, String receiver) {
		if (nickname.equals(receiver)) {
			if (!isVisible) {
				dotIndicator.setVisible(true);
			}
			NewMessage t = new NewMessage(array1.get(array1.size()-1), array2.get(array2.size()-1), array3.get(array3.size()-1));
			messages.getChildren().add(t);
		}
	}

	private void createChatContainer() {

		isVisible = false;
		ChatButton = new Button();
		ChatButton.getStylesheets().add("Button.css");
		ChatButton.setFont(Font.font(20));
		ChatButton.setVisible(true);
		ChatButton.setText("CHAT");

		gameBoard.getChildren().add(ChatButton);
		ChatButton.setOnAction(e -> showChat(ChatButton));

		// Red dot indicator for unread messages
		dotIndicator = new Circle(5);
		dotIndicator.setFill(Color.RED);
		dotIndicator.setVisible(true);

		StackPane chatButtonPane = new StackPane();
		chatButtonPane.getChildren().addAll(ChatButton, dotIndicator);
		StackPane.setAlignment(dotIndicator, Pos.TOP_RIGHT);
		AnchorPane.setLeftAnchor(chatButtonPane, gameBoard.getWidth()*(1340/1536.0));
		AnchorPane.setTopAnchor(chatButtonPane, gameBoard.getHeight()*(65/864.0));

		gameBoard.getChildren().add(chatButtonPane);

		chatContainer.setStyle("-fx-background-color: white; -fx-border-color: black; -fx-padding: 10px;");
		chatContainer.setMaxHeight(200);
		chatContainer.setVisible(isVisible);


		// scrollpane to see the messages
		ScrollPane chatScrollPane = new ScrollPane();
		chatScrollPane.setFitToWidth(true);
		chatScrollPane.setFitToHeight(true);
		chatContainer.getChildren().add(chatScrollPane);

		messages = new VBox();
		//messages.setSpacing(10);
		chatScrollPane.setContent(messages);

		// drop to select the receiver
		ComboBox<String> selectReceivers = new ComboBox<>();
		ArrayList<String> sendTo = new ArrayList<>();
		sendTo.add("everyOne");
		sendTo.add("pippo");
		sendTo.add("marco");
		selectReceivers.getItems().addAll(sendTo);
		selectReceivers.setValue("everyOne");
		chatContainer.getChildren().add(selectReceivers);

		// text field to write the message
		TextField messageInput = new TextField();
		Button sendButton = new Button("Send");
		sendButton.getStylesheets().add("Button.css");

		HBox inputBox = new HBox(messageInput, sendButton);
		inputBox.setSpacing(10);
		inputBox.setAlignment(Pos.CENTER_RIGHT);
		chatContainer.getChildren().add(inputBox);

		// update the full message list, I can add one message at a time if I am notified when I recive one
		GroupChat chatClass = new GroupChat();
		ArrayList<String> messagesList = chatClass.getMessage();
		ArrayList<String> sendersList = chatClass.getPublisher();
		ArrayList<String> receiversList = chatClass.getToplayer();
		for (int i = 0; i < messagesList.size(); i++) {
			String sender = sendersList.get(i);
			String m = messagesList.get(i);
			String receiver = receiversList.get(i);
			NewMessage newMessage = new NewMessage(sender, receiver, m);
			messages.getChildren().add(newMessage);

		}

		sendButton.setOnAction(e -> {
			String sender = nickname;
			String message = messageInput.getText();
			String receiver = selectReceivers.getValue();

			if (!message.isEmpty()) {
				NewMessage newMessage = new NewMessage(sender, receiver, message);
				messages.getChildren().add(newMessage);
				messageInput.clear();

				chatScrollPane.setVvalue(1.0);

			}
		});


		// test, this will be automatic
		NewMessage newMessage = new NewMessage("fil", "marc","funziona");
		messages.getChildren().add(newMessage);

		newMessage = new NewMessage("marc", "fil","bravo!");
		messages.getChildren().add(newMessage);

		// Add the chat container to the main pane
		gameBoard.getChildren().add(chatContainer);

		chatContainer.setTranslateX(100);
		chatContainer.setTranslateY(100);

	}

	private class NewMessage extends HBox {
		public NewMessage(String from, String to, String message) {
			Label senderLabel = new Label("from " + from + " to " + to + ": ");
			if (from == null || from.equals(nickname)) {
				senderLabel.setStyle("-fx-text-fill: red;");
			}
			Label messageLabel = new Label(message);
			messageLabel.setWrapText(true);
			//messageLabel.setMaxWidth(100);

			getChildren().addAll(senderLabel, messageLabel);
		}
	}

	private void showChat(Button button) {
		dotIndicator.setVisible(false);
		isVisible = !isVisible;
		chatContainer.setVisible(isVisible);
//		if (isVisible) {
//			button.setText("Hide chat");
//		} else {
//			button.setText("Show chat");
//		}
	}

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
//		primaryStage.setResizable(true);
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
		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
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
//		primaryStage.setResizable(true);
		primaryStage.show();

		b1.setOnAction(e -> {
			if (tf.getText() != null) {
				try {
					Integer.parseInt(tf.getText());
				} catch (NumberFormatException ex) {
					t1.setText("Insert an integer, not a string!");
					return;
				}
				if (Integer.parseInt(tf.getText()) < 1 || Integer.parseInt(tf.getText()) > 4) {
					t1.setText("The number must be between 2 and 4!");
					return;
				}
				LaunchClient.getClient().sendMessage("setplayers " + tf.getText());
				b1.setDisable(true);
				numPlayers = Integer.parseInt(tf.getText());
			}  else {
				t1.setText("Insert an integer, please!");
			}
		});
	}


	public void showMessage(String s) {
		alert = new Alert(Alert.AlertType.INFORMATION);
		alert.getDialogPane().getStylesheets().add("Alert.css");
		alert.initModality(Modality.APPLICATION_MODAL);
		alert.initOwner(primaryStage);
		alert.setTitle("Information Dialog");
		alert.setHeaderText(null);
		alert.setContentText(s);
		alert.showAndWait();
	}

	public void suggestNicknames(String s, ArrayList<String> array4) {
		alert = new Alert(Alert.AlertType.INFORMATION);
		alert.getDialogPane().getStylesheets().add("Alert.css");
		alert.initModality(Modality.APPLICATION_MODAL);
		alert.initOwner(primaryStage);
		alert.setTitle("Information Dialog");
		String r = s;
		for (String t: array4) {
			r = r + "\n"+ t;
		}
		alert.setContentText(r);
		alert.showAndWait();

	}

	public void showError(String error) {
		errorAlert = new Alert(Alert.AlertType.ERROR);
		errorAlert.getDialogPane().getStylesheets().add("Error.css");
		errorAlert.initModality(Modality.APPLICATION_MODAL);
		errorAlert.initOwner(primaryStage);
		errorAlert.setTitle("Error Dialog");
		errorAlert.setContentText(error);
		errorAlert.showAndWait();
	}



	public void startGame() {
		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		primaryStage.setTitle("MY SHELFIE");
		gameBoard = new AnchorPane();
		newScene(gameBoard);
		setBackground(primaryStage, gameBoard);

		handleEvent();

		////////////////////////////
		System.out.println("PRIMARY STAGE W: "+ primaryStage.getWidth());
		System.out.println("PRIMARY STAGE H: " +primaryStage.getHeight());
		////////////////////////////

		bag = new Bag(primaryStage);
		//System.out.println("BAG PERC: top: " + (Metrics.d_y_bag) + "  left: " + (Metrics.d_x_bag));
		AnchorPane.setTopAnchor(bag, gameBoard.getHeight() * Metrics.d_y_bag );
		AnchorPane.setLeftAnchor(bag, gameBoard.getWidth() * Metrics.d_x_bag);
		//bag.relocate(primaryStage.getWidth()*Metrics.d_x_bag, primaryStage.getHeight()*Metrics.d_y_bag);
		gameBoard.getChildren().add(bag);

		board = new Board(primaryStage);
		//System.out.println("BAG PERC: top: " + (Metrics.d_y_board) + "  left: " + (Metrics.d_x_board) );
		//board.relocate(primaryStage.getWidth()*Metrics.d_x_board, primaryStage.getHeight()*Metrics.d_y_board);
		AnchorPane.setTopAnchor(board, gameBoard.getHeight() * Metrics.d_y_board );
		AnchorPane.setLeftAnchor(board, gameBoard.getWidth() * Metrics.d_x_board);
		gameBoard.getChildren().add(board);

		commandBoard = new CommandBoard(primaryStage);
		AnchorPane.setTopAnchor(commandBoard, gameBoard.getHeight() * Metrics.d_y_comb );
		AnchorPane.setLeftAnchor(commandBoard, gameBoard.getWidth() * Metrics.d_x_comb);
		//commandBoard.relocate(primaryStage.getWidth()*Metrics.d_x_comb, primaryStage.getHeight()*Metrics.d_y_comb);
		gameBoard.getChildren().add(commandBoard);
		setEndToken();

		System.out.println("PRIMARYSTAGE W: " + primaryStage.getWidth() + " H: "+primaryStage.getHeight());
		bookshelf = new Bookshelf(Metrics.dim_x_bookpl*primaryStage.getWidth(), Metrics.dim_y_bookpl*primaryStage.getHeight(), primaryStage);
		//bookshelf.relocate(Metrics.d_x_bookpl*primaryStage.getWidth(), Metrics.d_y_bookpl*primaryStage.getHeight());
		AnchorPane.setTopAnchor(bookshelf, gameBoard.getHeight() * Metrics.d_y_bookpl );
		AnchorPane.setLeftAnchor(bookshelf, gameBoard.getWidth() * Metrics.d_x_bookpl);
		bookshelf.createLabelName(Metrics.dim_x_targetname*primaryStage.getWidth(),Metrics.dim_y_targetname*primaryStage.getHeight(), primaryStage.getWidth()*Metrics.dim_x_bookpl*Metrics.d_x_targetname, primaryStage.getHeight()*Metrics.dim_y_bookpl*Metrics.d_y_targetname);
		bookshelf.setName(nickname);
		gameBoard.getChildren().add(bookshelf);

		createChatContainer();

		for (int i = 0; i < ARROWS_DOWN; i++) {
			Arrow arrowDown = new Arrow(Arrow.DOWN);
			arrowDown.setUserData(arrowDown);
			arrowDown.setVisible(false);
			arrowDown.setIndex(i);
			arrowDown.setSize(gameBoard.getWidth() * Metrics.ARROW_DOWN_WIDTH,gameBoard.getHeight() * Metrics.ARROW_DOWN_HEIGHT);
			AnchorPane.setLeftAnchor(arrowDown, gameBoard.getWidth() * (1026.0/1536.0) + gameBoard.getWidth()* (i*60)/1536.0);
			AnchorPane.setTopAnchor(arrowDown, gameBoard.getHeight()* 168.0/864.0);
			arrowDown.setOnMouseClicked(event -> {
				Arrow ad = (Arrow) event.getSource();
				String s = "order";
				for (int j = 0; j < commandBoard.getNextTilePos(); j++) {
					s += " " + commandBoard.getPickupOrder()[j];
				}
				if (commandBoard.checkSequence()) {
					LaunchClient.getClient().sendMessage(s);
					handleArrowDown(event, ad.getIndex());
				} else {
					showError("Two numbers in the order are the same!");
				}
			});
			arrowDownList.add(arrowDown);
			gameBoard.getChildren().add(arrowDown);
		}

		arrowRight.setSize(gameBoard.getWidth() *Metrics.ARROW_RIGHT_WIDTH, gameBoard.getHeight()* Metrics.ARROW_RIGHT_HEIGHT);
		arrowRight.setVisible(false);
		AnchorPane.setTopAnchor(arrowRight, gameBoard.getHeight()  * 0.0451);
		AnchorPane.setLeftAnchor(arrowRight, gameBoard.getWidth() * 0.601);


		//arrowRight.relocate(primaryStage.getWidth()*Metrics.d_x_comb-110, 35);
		arrowRight.setOnMouseClicked(event -> {
			LaunchClient.getClient().sendMessage("pick");
			arrowRight.setVisible(false);
			redCross.setVisible(false);
			for (String s: board.getTiles().keySet()) {
				Tile t = (Tile) board.getTiles().get(s);
				t.setPickable(false);
			}

			int col = 0;
			for (Arrow a : arrowDownList) {
				if (!bookshelf.isFull(col++)) {
					a.setVisible(true);
				}
			}
		});
		gameBoard.getChildren().add(arrowRight);

		redCross.setSize(gameBoard.getWidth() *Metrics.ARROW_RIGHT_WIDTH*0.8, gameBoard.getHeight()* Metrics.ARROW_RIGHT_HEIGHT*0.8);
		redCross.setVisible(false);
		AnchorPane.setTopAnchor(redCross, gameBoard.getHeight()  * 0.5);
		AnchorPane.setLeftAnchor(redCross, gameBoard.getWidth() * 0.580);
		redCross.setOnMouseClicked(event -> {
			board.clearSelected();
			LaunchClient.getClient().sendMessage("remove");
			redCross.setVisible(false);
			arrowRight.setVisible(false);
		});
		gameBoard.getChildren().add(redCross);

	}
	private void handleEvent() {
		scene.addEventFilter(CustomEvent.TILE_SELECTED, event -> {
			Tile obj = (Tile) event.getObj();
			boolean flag = event.getFlag();
			int x = (int) obj.getPosition().getX();
			int y = (int) obj.getPosition().getY();
			board.select(x, y);
			if (!flag) {
				redCross.setVisible(!board.isSelectedEmpty());
				arrowRight.setVisible(!board.isSelectedEmpty());
				LaunchClient.getClient().sendMessage("select " + x + " " + y);
			}
//			board.select(x, y);

			/* Stop the event here */
			event.consume();
		});

	}

	public void setCommonGoal(Map<Integer, Integer> map) {
		ArrayList<Integer> arr = new ArrayList<>();
		currentToken = new ArrayList<>();
		for (Integer i: map.keySet()) {
			arr.add(i);
			currentToken.add(map.get(i));
		}
		c1 = new CommonGoalGui(arr.get(0)-1, primaryStage);
		c2 = new CommonGoalGui(arr.get(1)-1, primaryStage);
		AnchorPane.setTopAnchor(c1, gameBoard.getHeight() * Metrics.d_y_comm1 );
		AnchorPane.setLeftAnchor(c1, gameBoard.getWidth() * Metrics.d_x_comm);
		AnchorPane.setTopAnchor(c2, gameBoard.getHeight() * Metrics.d_y_comm2 );
		AnchorPane.setLeftAnchor(c2, gameBoard.getWidth() * Metrics.d_x_comm);

		//c1.relocate(Metrics.d_x_comm*primaryStage.getWidth(), Metrics.d_y_comm1*primaryStage.getHeight());
		//c2.relocate(Metrics.d_x_comm*primaryStage.getWidth(), Metrics.d_y_comm2*primaryStage.getHeight());
		gameBoard.getChildren().add(c1);
		gameBoard.getChildren().add(c2);
		setToken();
	}

	private void setToken(){
		scoringToken = new ArrayList<>();
		for(int i = 0; i<currentToken.size(); i++){
			scoringToken.add(new ScoreToken(currentToken.get(i),primaryStage));
			AnchorPane.setLeftAnchor(scoringToken.get(i),6.95*Metrics.d_x_comm*gameBoard.getWidth());
			AnchorPane.setTopAnchor(scoringToken.get(i),(i*(210.0/864) - (8.0/864) + Metrics.d_y_commToken)*gameBoard.getHeight());
			gameBoard.getChildren().add(scoringToken.get(i));
		}
	}


	public void setPersonalGoal(Map<String, String> map, int number) {
		p = new PersonalGoal(number, primaryStage);
		AnchorPane.setTopAnchor(p, gameBoard.getHeight() * Metrics.d_y_pers );
		AnchorPane.setLeftAnchor(p, gameBoard.getWidth() * Metrics.d_x_pers);
		//p.relocate(Metrics.d_x_pers*primaryStage.getWidth(), primaryStage.getHeight()*Metrics.d_y_pers);
		gameBoard.getChildren().add(p);
	}

	public void setBoard(Map<String, String> map) {
		if (board == null) {
			board = new Board(primaryStage);
			AnchorPane.setTopAnchor(board, gameBoard.getHeight() * Metrics.d_y_board );
			AnchorPane.setLeftAnchor(board, gameBoard.getWidth() * Metrics.d_x_board);
			//board.relocate(primaryStage.getWidth()*Metrics.d_x_board, primaryStage.getHeight()*Metrics.d_y_board);
			gameBoard.getChildren().add(board);
		}
		for (String s: map.keySet()) {
			if (!map.get(s).equals("NOCOLOR")) {
				Tile t = new Tile(map.get(s), primaryStage);
				t.setPosition(s);
				board.place(t);
			}

		}
	}

	private void handleArrowDown(MouseEvent event, int column) {
		if (commandBoard.checkSequence()) {
			ArrayList<Node> nodeList = new ArrayList<>();
			Node n;

			if (commandBoard.getNumTile() > bookshelf.getFreeSpace(column)) {
				showError("No space in bookshelf column.");
			} else {

				for (Arrow a : arrowDownList) {
					a.setVisible(false);
				}

				while ((n = commandBoard.getTile()) != null) {
					nodeList.add(n);  //add the tiles picked and ordered
				}

				if (nodeList.size() > 0) {
					bookshelf.insert(nodeList, column);
				}
				try {
					Thread.sleep(1500);
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
				LaunchClient.getClient().sendMessage("insert " + (column+1));
			}
		}
	}

	public void numPlayers(ArrayList<String> names) {
		this.names = new ArrayList<>(names);
		if (numPlayers == 0) {
			numPlayers = names.size();
		}
	}

	public void setPickableTiles(Map<String, String> map, ArrayList<Position> arr, Map<String, String> board) {
		this.board.clearUpdate(map, arr, board);
	}

	public void setPicked(Map<String, String> map) {
		Tile t;
		while (!board.isSelectedEmpty()) {
			t = (Tile) board.getSelected();
			if (t != null) {
				t.setPickable(false);
				commandBoard.addTile(t);
			}
		}
	}

	public void setEndToken(){
		endToken = new ImageView(Resources.endToken());
		endToken.setPreserveRatio(true);
		endToken.setFitWidth(0.31*Metrics.dim_x_comm*primaryStage.getWidth());
		endToken.setFitHeight(0.31*Metrics.dim_y_comm*primaryStage.getHeight());
		// Rotate the ImageView by 10 degrees
		double rotationAngle = 9.5;
		Rotate rotate = new Rotate(rotationAngle, endToken.getFitWidth() / 2, endToken.getFitHeight() / 2);
		endToken.getTransforms().add(rotate);
		AnchorPane.setLeftAnchor(endToken,Metrics.d_x_endToken*gameBoard.getWidth());
		AnchorPane.setTopAnchor(endToken,Metrics.d_y_endToken*gameBoard.getHeight());
		gameBoard.getChildren().add(endToken);

	}

	public void updateBookshelves(Map<String, Map<String, String>> map) {
		if (bookshelves.size() == 0) {
			int j = 0;
			for (int i = 0; i < numPlayers - 1; i++) {
				Bookshelf b = new Bookshelf(Metrics.dim_x_book*primaryStage.getWidth(), Metrics.dim_y_book*primaryStage.getHeight(), primaryStage);
				bookshelves.add(b);
				gameBoard.getChildren().add(bookshelves.get(i));
				bookshelves.get(i).createLabelName(Metrics.dim_x_label*primaryStage.getWidth(),Metrics.dim_y_label*primaryStage.getHeight(), Metrics.dim_x_book*Metrics.d_x_label* primaryStage.getWidth(), Metrics.dim_y_book*Metrics.d_y_label*primaryStage.getHeight());
				switch (numPlayers) {
					case 2:
						AnchorPane.setLeftAnchor(bookshelves.get(i),Metrics.d_x_book2 * primaryStage.getWidth());
						AnchorPane.setTopAnchor(bookshelves.get(i), Metrics.d_y_book2 * primaryStage.getHeight());
						//bookshelves.get(i).relocate(Metrics.d_x_book2*primaryStage.getWidth(), Metrics.d_y_book2*primaryStage.getHeight());
						break;
					case 3:
						AnchorPane.setLeftAnchor(bookshelves.get(i),((329+(329+274)*i)/1536.0)* primaryStage.getWidth());
						AnchorPane.setTopAnchor(bookshelves.get(i), Metrics.d_y_book2 * primaryStage.getHeight());
						//bookshelves.get(i).relocate(((329+(329+274)*i)/1536.0)*primaryStage.getWidth(), Metrics.d_y_book2*primaryStage.getHeight());
						break;
					case 4:
						AnchorPane.setLeftAnchor(bookshelves.get(i),((178+(178+274)*i)/1536.0)* primaryStage.getWidth());
						AnchorPane.setTopAnchor(bookshelves.get(i), Metrics.d_y_book2 * primaryStage.getHeight());
						//bookshelves.get(i).relocate(((178+(178+274)*i)/1536.0)*primaryStage.getWidth(), Metrics.d_y_book2*primaryStage.getHeight());
						break;
				}
				if (names.get(j).equals(nickname)) {
					j += 1;
				}
				bookshelves.get(i).setName(names.get(j));
				j++;
			}
		}
		for (int i = 0; i < bookshelves.size(); i++) {
			if (!bookshelves.get(i).getLabelText().getText().equals(nickname)) {
				Map<String, String > m = map.get(bookshelves.get(i).getLabelText().getText());
				ArrayList<Node> nodelist = new ArrayList<>();
				bookshelves.get(i).resetDepth();
				for (int j = 0; j < 5; j++) {
					for (int k = 0; k < 6; k++) {
						if (m.get("(" + j + "," + k + ")").equals("NOCOLOR")) {
							bookshelves.get(i).modifyDepth(j);
						} else {
//							System.out.println();
							nodelist.add(new Tile(m.get("(" + j + "," + k + ")"), primaryStage));
							bookshelves.get(i).update(nodelist, j);
							System.out.println(j + " --- "+ k);
							nodelist.clear();
						}
					}

				}
			}
		}
	}


}


