package it.polimi.ingsw.am40.GUI;

import it.polimi.ingsw.am40.Client.LaunchClient;
import it.polimi.ingsw.am40.JSONConversion.JSONConverterCtoS;
import it.polimi.ingsw.am40.Model.Position;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.transform.Rotate;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import java.util.regex.Pattern;

/**
 * This is the main class of the GUI. It has all the methods called by the GUI Controller to refresh the GUI
 */

public class Viewer extends Application {
	private Arrow arrowRight;
	private ArrayList<Arrow> arrowDownList;
	private static final int ARROWS_DOWN = 5;
	private String connectionType;
	private static Viewer gui;
	private Stage primaryStage;
	private Scene scene;
	private Pane pane;
	private RedCross redCross;
	private String currentPlayer;
	private AnchorPane gameBoard;
	private String nickname;
	private String firstPlayer;
	private int numPlayers;
	private Board board;
	private CommonGoalGui c1;
	private CommonGoalGui c2;
	private PersonalGoal p;
	private CommandBoard commandBoard;
	private Bookshelf bookshelf;
	private ArrayList<Bookshelf> bookshelves;
	private ArrayList<String> names;
	private Alert alert;
	private boolean isVisible;
	private final VBox chatContainer = new VBox();
	private final AnchorPane chat = new AnchorPane();
	private Circle dotIndicator;

	private VBox messages;

	private ArrayList<Integer> currentToken;

	private int pickedToken;

	private PlayersPointBoard ppboard;
	private CirclePoints cp;

	private final ScrollPane chatScrollPane = new ScrollPane();
	private ComboBox<String> selectReceivers;
	private TextField messageInput;
	private Button sendButton;

	public Viewer() {
	}

	/**
	 * It is the main method, used to launch the GUI
	 * @param args arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * It returns the unique instance of the GUI
	 * @return instance of the GUI
	 */
	public static Viewer getGUI() {
		if (gui != null) {
			return gui;
		} else {
			(new Thread(Application::launch)).start();
			while(gui == null || !gui.isPrimaryStageOn())
			{
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					System.out.println("Thread not sleeping");
				}
			}
		}
		return gui;
	}

	private boolean isPrimaryStageOn() {
		return primaryStage != null;
	}

	/**
	 * It creates the main data structure necessary to handle the graphical elements of the GUI
	 * @param stage a stage
	 */
	@Override
	public void start(Stage stage) {
		this.primaryStage = stage;
		this.bookshelves = new ArrayList<>();
		arrowRight = new Arrow(Arrow.RIGHT);
		arrowDownList = new ArrayList<>();
		redCross = new RedCross();
		primaryStage.setResizable(true);
		gui = this;
		pickedToken=0;

	}


	/**
	 * It sets the background image
	 * @param pane a pane where to set the background
	 */
	public void setBackground(Pane pane) {
		pane.setPrefSize(15.5,8.7);
		Image backGroundImage = Resources.background();
		ImageView backGroundView = new ImageView(backGroundImage);
		pane.getChildren().add(backGroundView);
		AnchorPane.setTopAnchor(backGroundView,0.0);
		AnchorPane.setRightAnchor(backGroundView,0.0);
		AnchorPane.setLeftAnchor(backGroundView,0.0);
		AnchorPane.setBottomAnchor(backGroundView,0.0);
		backGroundView.fitWidthProperty().bind(scene.widthProperty());
		backGroundView.fitHeightProperty().bind(scene.heightProperty());
	}

	/**
	 * It creates a vbox (vertical box) inside a pane
	 * @param pane the pane where the vbox is created
	 * @return a vbox
	 */
	public VBox createVbox(Pane pane) {
		VBox vbox = new VBox();
		vbox.setSpacing(10);
		vbox.setAlignment(Pos.CENTER);
		vbox.setMaxWidth(primaryStage.getWidth());
		primaryStage.widthProperty().addListener((observable, oldValue, newValue) -> vbox.setMaxWidth((double) newValue));
		pane.getChildren().add(vbox);
		return vbox;
	}

	/**
	 * It sets the title "MY SHELFIE"
	 * @param pane the pane where the title is set
	 */
	public void setTitle(Pane pane) {
		Image title = Resources.title();
		ImageView imageView = new ImageView(title);
		imageView.setFitWidth(primaryStage.getWidth());
		imageView.setFitHeight(primaryStage.getHeight());
		imageView.setPreserveRatio(true);
		pane.getChildren().addAll(imageView);
		primaryStage.widthProperty().addListener((observable, oldValue, newValue) -> imageView.setFitWidth((double)newValue));
		primaryStage.heightProperty().addListener((observable, oldValue, newValue) -> imageView.setFitHeight((double) newValue));
	}

	/**
	 * It adds a text to the provided pane
	 * @param pane pane where the text is added
	 * @param tmp the content of the text
	 * @return it returns a Text
	 */
	public Text addDescription(Pane pane, String tmp) {
		Text text = new Text(tmp);
		text.setFont(Font.font(25));
		pane.getChildren().add(text);
		return text;
	}

	/**
	 * It adds a text field to the provided pane
	 * @param pane a pane where the text field is added
	 * @return a text field
	 */
	public TextField addTextField(Pane pane) {
		TextField textField = new TextField();
		textField.setMaxWidth(200);
		pane.getChildren().add(textField);
		textField.setAlignment(Pos.CENTER);
		return textField;
	}

	/**
	 * It adds a toggle button to the provided pane
	 * @param pane a pane where the toggle button is added
	 * @param tmp the name of the option related to the toggle
	 * @param isSelected a boolean that, if it is true, the toggle button is selected, false otherwise
	 * @return the toggle button
	 */
	public RadioButton addToggle(Pane pane, String tmp, Boolean isSelected) {
		RadioButton button = new RadioButton(tmp);
		button.setFont(Font.font(20));
		button.setSelected(isSelected);
		pane.getChildren().add(button);
		return button;
	}

	/**
	 * It sets the names of the options of the toggle buttons
	 * @param rmi the radio button in order ot choose the RMI option
	 * @param socket the radio button in order to select the Socket option
	 */
	public void setToggles(RadioButton rmi, RadioButton socket) {
		ToggleGroup tg = new ToggleGroup();

		connectionType = "SOCKET";

		socket.setOnAction(e -> connectionType = "SOCKET");
		rmi.setOnAction(e -> connectionType = "RMI");

		socket.setToggleGroup(tg);
		rmi.setToggleGroup(tg);

	}

	/**
	 * It adds a button to the provided pane
	 * @param pane the pane where the button is added
	 * @param tmp the string inside the button
	 * @param isVisible if true, the button is set visible
	 * @return a button
	 */
	public Button addButton(Pane pane, String tmp, Boolean isVisible) {

		Button button = new Button(tmp);
		button.setVisible(isVisible);
		button.setFont(Font.font(20));
		pane.getChildren().add(button);

		button.getStylesheets().add("Button.css");

		return button;

	}

	/**
	 * It sets the connection taking the ip address written by the user and calling a method of the LaunchCLient
	 * @param tf text field where the ip address must be written
	 * @param t1 text that suggests to the user to write the ip address
	 */
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
	}

	/**
	 * It sets the username of the player. It asks the player to write his desired nickname
	 */
	public void setUsername() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			System.out.println("Thread not sleeping");
		}
		pane = new Pane();
		newScene(pane);
		setBackground(pane);

		VBox vbox = createVbox(pane);

		setTitle(vbox);

		Text t1 = addDescription(vbox, "Insert your username: ");

		TextField tf = addTextField(vbox);

		Button b1 = addButton(vbox, "Login", true);
		addDescription(vbox, "");
		primaryStage.setTitle("MY SHELFIE LOGIN");
		primaryStage.getIcons().add(Resources.icon());
		primaryStage.show();

		b1.setOnAction(e -> {
			if (tf.getText().equals("")) {
				t1.setText("Insert an username, please!");
			} else {
				nickname = tf.getText();
				LaunchClient.getClient().sendMessage("login " + tf.getText());
			}
		});

		pane.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER) {
				if (tf.getText().equals("")) {
					t1.setText("Insert an username, please!");
				} else {
					nickname = tf.getText();
					LaunchClient.getClient().sendMessage("login " + tf.getText());
				}
			}
		});
		primaryStage.setOnCloseRequest(we -> {
			if (LaunchClient.getClient() != null) {
				LaunchClient.getClient().sendMessage("Quit");
				LaunchClient.getClient().close();
			}
		});
	}

	/**
	 * It sets and starts the waiting animation when the player is logged in, and he's waiting in the lobby
	 */
	public void waitingAnimation() {
		Image im = Resources.tile(1,0);
		ImageView loadImage = new ImageView(im);
		loadImage.setFitWidth(100);
		loadImage.setPreserveRatio(true);

		RotateTransition rotateTransition = new RotateTransition(Duration.seconds(2), loadImage);
		rotateTransition.setByAngle(360);
		rotateTransition.setCycleCount(Animation.INDEFINITE);
		rotateTransition.setAutoReverse(false);

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
		timeline.setCycleCount(Timeline.INDEFINITE);

		Pane x = (Pane) pane.getChildren().get(1);
		x.getChildren().add(loadImage);
		rotateTransition.play();
		timeline.play();
	}

	/**
	 * It sets a new scene for the primary stage
	 * @param pane pane where the scene is set
	 */
	public void newScene(Pane pane) {
		scene = new Scene(pane, Screen.getPrimary().getVisualBounds().getWidth(), Screen.getPrimary().getVisualBounds().getHeight());
		primaryStage.setScene(scene);
		primaryStage.setFullScreen(true);
		primaryStage.setFullScreenExitHint("");
	}


	/* Test methods */

	/**
	 * It sets the messages in the chat
	 * @param array1 it contains the senders of the messages
	 * @param array2 it contains the receivers of the messages
	 * @param array3 it contains the messages
	 * @param receiver the name of the owner of this GUI, used to notify the correct player
	 */
	public void newMessage(ArrayList<String> array1, ArrayList<String> array2, ArrayList<String> array3, String receiver) {
		if (nickname.equals(receiver)) {
			if (!isVisible) {
				dotIndicator.setVisible(true);
			}
			messages.getChildren().clear();
			NewMessage newMessage = new NewMessage("MyShelfie", nickname,"Welcome to the chat, here you can send messages");
			messages.getChildren().add(newMessage);
			for (int i = 0; i < array3.size(); i++) {
				NewMessage t = new NewMessage(array1.get(i), array2.get(i), array3.get(i));
				messages.getChildren().add(t);
			}

			chatScrollPane.setVvalue(array3.size());
		}
	}

	private void createChatContainer() {

		isVisible = false;
		Button chatButton = new Button();
		chatButton.getStylesheets().add("Button.css");
		chatButton.setFont(Font.font(20));
		chatButton.setVisible(true);
		chatButton.setText("CHAT");

		gameBoard.getChildren().add(chatButton);

		dotIndicator = new Circle(5);
		dotIndicator.setFill(Color.RED);
		dotIndicator.setVisible(true);
		gameBoard.getChildren().add(chat);
		StackPane chatButtonPane = new StackPane();
		chatButtonPane.getChildren().addAll(chatButton, dotIndicator);
		StackPane.setAlignment(dotIndicator, Pos.TOP_RIGHT);
		AnchorPane.setLeftAnchor(chatButtonPane, gameBoard.getWidth()*(1430/1536.0));
		AnchorPane.setTopAnchor(chatButtonPane, gameBoard.getHeight()*(225/864.0));

		gameBoard.getChildren().add(chatButtonPane);

		chatContainer.setStyle("-fx-background-color: #007700; -fx-border-color: #CCCCCC; -fx-border-width: 1px; -fx-padding: 10px; -fx-background-radius: 8px; -fx-border-radius: 8px;");
		chatContainer.setMinWidth(300);
		chatContainer.setMinHeight(150);
		chat.setVisible(isVisible);

		HBox closeButtonPane = new HBox();
		closeButtonPane.setAlignment(Pos.TOP_LEFT);
		closeButtonPane.setTranslateX(5);
		closeButtonPane.setTranslateY(-5);

		Circle closeButtonCircle = new Circle(10, Color.RED);
		closeButtonCircle.setStroke(Color.WHITE);
		closeButtonPane.getChildren().add(closeButtonCircle);
		chatContainer.getChildren().add(closeButtonPane);

		closeButtonCircle.setOnMousePressed( e -> {
				chat.setVisible(false);
				isVisible = false;
		});

		ScrollPane chatScrollPane = new ScrollPane();
		chatScrollPane.setFitToWidth(true);
		chatScrollPane.setFitToHeight(true);
		chatContainer.getChildren().add(chatScrollPane);

		chatButton.setOnAction(e -> showChat());

		messages = new VBox();
		VBox.setVgrow(chatContainer, Priority.ALWAYS);
		VBox.setVgrow(messages, Priority.ALWAYS);
		chatScrollPane.setContent(messages);

		// text field to write the message
		messageInput = new TextField();
		sendButton = new Button("Send");
		sendButton.getStylesheets().add("Button.css");

		// update the full message list, I can add one message at a time if I am notified when I receive one
		sendButton.setOnAction(e -> {
			String sender = nickname;
			String message = messageInput.getText();
			String receiver = selectReceivers.getValue();

			if (!message.isEmpty()) {
				if (receiver.equals("everyOne")) {
					receiver = "all";
				}

				System.out.println("send to: " + receiver);
				JSONConverterCtoS jconv = new JSONConverterCtoS();
				jconv.toJSONChat(receiver, message);
				if (LaunchClient.getClient() != null) {
					LaunchClient.getClient().chat(jconv.toString());
				}

				NewMessage newMessage = new NewMessage(sender, receiver, message);
				messages.getChildren().add(newMessage);
				messageInput.clear();

				chatScrollPane.setVvalue(1.0);

			}
		});


		// Welcome message
		NewMessage newMessage = new NewMessage("MyShelfie", nickname,"Welcome to the chat, here you can send messages");
		messages.getChildren().add(newMessage);

		// Add the chat container to the main pane
		if (!gameBoard.getChildren().contains(chatContainer)) {
			gameBoard.getChildren().add(chatContainer);
			chat.getChildren().add(chatContainer);
		}

		// Set the position of the chat
		chat.setTranslateX(100);
		chat.setTranslateY(100);

		// Make the chat expandable
		Rectangle expRect = new Rectangle();
		AnchorPane.setBottomAnchor(expRect, 0.0);
		AnchorPane.setRightAnchor(expRect, 0.0);
		chat.getChildren().add(expRect);

		expRect.setWidth(10);
		expRect.setHeight(10);
		expRect.setFill(Color.TRANSPARENT);

		expRect.setOnMouseEntered( e -> expRect.setCursor(Cursor.NW_RESIZE));

		expRect.setOnMouseExited( e -> expRect.setCursor(Cursor.DEFAULT));

		final Delta expand = new Delta();
		expRect.setOnMousePressed(event -> {
			expand.x = chatContainer.getWidth() - event.getSceneX();
			expand.y = chatContainer.getHeight() - event.getSceneY();
		});
		expRect.setOnMouseDragged(event -> {
			chatContainer.setPrefWidth(event.getSceneX() + expand.x);
			chatContainer.setPrefHeight(event.getSceneY() + expand.y);
		});

		// Make the chat container draggable
		final Delta dragDelta = new Delta();
		chatContainer.setOnMousePressed(event -> {
			dragDelta.x = chat.getTranslateX() - event.getSceneX();
			dragDelta.y = chat.getTranslateY() - event.getSceneY();
		});
		chatContainer.setOnMouseDragged(event -> {
			if (!expRect.isHover()) {
				chat.setTranslateX(event.getSceneX() + dragDelta.x);
				chat.setTranslateY(event.getSceneY() + dragDelta.y);
			}
		});
	}

	/**
	 * It sets the token of the common goal near the player who already did the common goal
	 *
	 * @param nickname nickname of the player
	 * @param score score obtained by the player
	 */
	public void setPickToken(String nickname, int score) {
		ScoreToken newToken= new ScoreToken(score,primaryStage);
		double rotationAngle = 7.5;
		Rotate rotate = new Rotate(rotationAngle, newToken.getImageview().getFitWidth() / 2, newToken.getImageview().getFitHeight() / 2);
		newToken.getImageview().getTransforms().add(rotate);
		if(nickname.equals(this.nickname)){
			newToken.getImageview().setFitWidth(0.25*Metrics.dim_x_comm*primaryStage.getWidth());
			newToken.getImageview().setFitHeight(0.25*Metrics.dim_y_comm*primaryStage.getHeight());
			AnchorPane.setLeftAnchor(newToken,((14.0/1536.0)*pickedToken + Metrics.d_x_pers)*gameBoard.getWidth());
			AnchorPane.setTopAnchor(newToken,((14.0/864.0)*pickedToken + (502.0/864.0))*gameBoard.getHeight());
			gameBoard.getChildren().add(newToken);
			pickedToken++;
		}

	}

	/**
	 * It sets the tiles already inserted in the booskhelf after player's reconnection
	 * @param map map representing the bookshelf (position -> color)
	 */
	public void reconnectBookshelf(Map<String, String> map) {
		bookshelf.reconnectPlaceTiles(map, primaryStage);
	}

	/**
	 * It is called when the player reconnects
	 */
	public void reconnect() {
		startGame();
	}

	private static class Delta {
		double x, y;
	}

	/**
	 * It sets the current player
	 * @param s name of the current player
	 */
	public void setCurrentPlayer(String s) {
		currentPlayer = s;
		ppboard.setCurrentPlayer(s);
	}

	/**
	 * It sets the scores of the players
	 * @param map map containing the name and the score of each player
	 */
	public void showCurrentScore(Map<String, Integer> map) {
		ppboard.addScores(map);
	}

	/**
	 * It sets the hidden score of the player inside the circle
	 * @param score hidden score of the player
	 */
	public void showHiddenScore(int score) {
		cp.setScore(score);
	}

	/**
	 * It sets the first player of the game, assigning the chair properly
	 * @param nickname nickname of the first player
	 */
	public void setFirstPlayer(String nickname) {
		if (firstPlayer == null) {
			firstPlayer = nickname;
			if (firstPlayer.equals(this.nickname)) {
				bookshelf.setChair();
				gameBoard.getChildren().add(bookshelf.getChair());
				AnchorPane.setLeftAnchor(bookshelf.getChair(), gameBoard.getWidth()*Metrics.d_x_chair);
				AnchorPane.setTopAnchor(bookshelf.getChair(), gameBoard.getHeight()*Metrics.d_y_chair);
			}
			else {
				for (Bookshelf b: bookshelves) {
					if (firstPlayer.equals(b.getLabelText().getText())) {
						b.setChair();
						gameBoard.getChildren().add(b.getChair());
						AnchorPane.setLeftAnchor(b.getChair(), b.getPos_x()+ (Metrics.dim_x_book+(10/1536.0))*gameBoard.getWidth());
						AnchorPane.setTopAnchor(b.getChair(), b.getPos_y()+ (220/864.0)*gameBoard.getHeight());
					}
				}
			}
		}
		chat.toFront();
	}

	/**
	 * It shows the final scores of the players when the game ends
	 * @param map map between names and final scores
	 * @param winner winner of the game
	 */
	public void showFinalScores(Map<String, Integer> map, String winner) {
		primaryStage.setTitle("MY SHELFIE END GAME");
		pane = new Pane();
		newScene(pane);
		setBackground(pane);
		primaryStage.setOnCloseRequest(we -> {
			if (LaunchClient.getClient() != null) {
				LaunchClient.getClient().close();
			}
		});

		VBox vbox = createVbox(pane);

		setTitle(vbox);
		for (String s: map.keySet()) {
			HBox hbox = new HBox();
			addDescription(hbox, s);
			addDescription(hbox, "Score: " + map.get(s));
			hbox.setSpacing(100);
			hbox.setVisible(true);
			hbox.setAlignment(Pos.CENTER);
			vbox.getChildren().add(hbox);
		}
		Text t1 = addDescription(vbox, "WINNER: " + winner);
		t1.setTextAlignment(TextAlignment.CENTER);
		Button b = addButton(vbox, "EXIT", true);
		b.setOnAction(e -> {
			if (LaunchClient.getClient() != null) {
				LaunchClient.getClient().close();
			}
		});
		primaryStage.show();

	}

	/**
	 * It closes the GUI
	 */
	public void quit() {
		primaryStage.close();
		Platform.exit();
	}

	private class NewMessage extends HBox {
		public NewMessage(String from, String to, String message) {
			Label senderLabel = new Label("from " + from + " to " + to + ": ");
			if (from == null || from.equals(nickname)) {
				senderLabel.setStyle("-fx-text-fill: red;");
			} else if (to.equals("all")) {
				senderLabel.setStyle("-fx-text-fill: green;");
			}
			Label messageLabel = new Label(message);
			messageLabel.setWrapText(true);

			getChildren().addAll(senderLabel, messageLabel);

			setStyle("-fx-padding: 0px 10px;");

		}
	}

	private void showChat() {
		dotIndicator.setVisible(false);
		chatScrollPane.setVvalue(1.0);
		isVisible = true;
		chat.setVisible(true);

	}

	/**
	 * It shows the scene where the player chooses the type of connection
	 */
	public void chooseConnection() {
		pane = new Pane();
		newScene(pane);
		setBackground(pane);

		VBox vbox = createVbox(pane);

		setTitle(vbox);

		Text t1 = addDescription(vbox, "Insert the server IP (or localhost [L]):");

		TextField tf = addTextField(vbox);

		addDescription(vbox, "Choose a connection type: ");

		RadioButton r1 = addToggle(vbox,"RMI", true);
		RadioButton r2 = addToggle(vbox,"SOCKET", false);
		setToggles(r1,r2);

		Button b1 = addButton(vbox, "Connect", true);
		addDescription(vbox, "");
		primaryStage.setTitle("MY SHELFIE CONNECTION");
		primaryStage.getIcons().add(Resources.icon());
		primaryStage.show();

		b1.setOnAction(e -> setConnection(tf, t1));

		pane.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER) {
				setConnection(tf, t1);
			}
		});

		primaryStage.setOnCloseRequest(we -> {
			if (LaunchClient.getClient() != null) {
				LaunchClient.getClient().sendMessage("Quit");
				LaunchClient.getClient().close();
			}
		});

	}

	/**
	 * It shows the scene where the player sets the number of players
	 */
	public void setplayers() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		pane = new Pane();
		newScene(pane);
		setBackground(pane);
		VBox vbox = createVbox(pane);

		setTitle(vbox);

		addDescription(vbox, "Select the number of Players: ");

		ComboBox<String> dropdown = new ComboBox<>();
		dropdown.getItems().addAll("2", "3", "4");
		dropdown.getSelectionModel().selectFirst();
		dropdown.setPrefHeight(30);
		dropdown.setPrefWidth(60);

		vbox.getChildren().add(dropdown);

		Button b1 = addButton(vbox, "SetPlayers", true);
		addDescription(vbox, "");
		primaryStage.setTitle("MY SHELFIE SETPLAYERS");
		primaryStage.getIcons().add(Resources.icon());
		primaryStage.show();

		b1.setOnAction(e -> {

			LaunchClient.getClient().sendMessage("setplayers " + dropdown.getValue());
			b1.setDisable(true);
			numPlayers = Integer.parseInt(dropdown.getValue());

		});

		pane.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER) {
				LaunchClient.getClient().sendMessage("setplayers " + dropdown.getValue());
				b1.setDisable(true);
				numPlayers = Integer.parseInt(dropdown.getValue());
			}
		});
		primaryStage.setOnCloseRequest(we -> {
			if (LaunchClient.getClient() != null) {
				LaunchClient.getClient().sendMessage("Quit");
				LaunchClient.getClient().close();
			}
		});
	}

	/**
	 * It shows a message from the server with a popup
	 * @param s message shown
	 */
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

	/**
	 * It shows some suggested nicknames in a popup
	 * @param s nickname desired by the player
	 * @param array4 nicknames suggested
	 */
	public void suggestNicknames(String s, ArrayList<String> array4) {
		alert = new Alert(Alert.AlertType.INFORMATION);
		alert.getDialogPane().getStylesheets().add("Alert.css");
		alert.initModality(Modality.APPLICATION_MODAL);
		alert.initOwner(primaryStage);
		alert.setTitle("Information Dialog");
		StringBuilder r = new StringBuilder(s);
		for (String t: array4) {
			r.append("\n").append(t);
		}
		alert.setContentText(r.toString());
		alert.showAndWait();

	}

	/**
	 * It shows an error message from the server with a popup
	 * @param error error message
	 */
	public void showError(String error) {
		Alert errorAlert = new Alert(Alert.AlertType.ERROR);
		errorAlert.getDialogPane().getStylesheets().add("Error.css");
		errorAlert.initModality(Modality.APPLICATION_MODAL);
		errorAlert.initOwner(primaryStage);
		errorAlert.setTitle("Error Dialog");
		errorAlert.setContentText(error);
		errorAlert.showAndWait();
	}

	/**
	 * When the game is created, It creates and places all the main graphical elements, such as board, common goals, bookshelves,...
	 */
	public void startGame() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		primaryStage.setTitle("MY SHELFIE");
		gameBoard = new AnchorPane();
		newScene(gameBoard);
		setBackground(gameBoard);
		primaryStage.setOnCloseRequest(we -> {
			if (LaunchClient.getClient() != null) {
				LaunchClient.getClient().sendMessage("Quit");
				LaunchClient.getClient().close();
			}
		});
		handleEvent();

		Bag bag = new Bag(primaryStage);
		AnchorPane.setTopAnchor(bag, gameBoard.getHeight() * Metrics.d_y_bag );
		AnchorPane.setLeftAnchor(bag, gameBoard.getWidth() * Metrics.d_x_bag);
		gameBoard.getChildren().add(bag);

		board = new Board(primaryStage);
		AnchorPane.setTopAnchor(board, gameBoard.getHeight() * Metrics.d_y_board );
		AnchorPane.setLeftAnchor(board, gameBoard.getWidth() * Metrics.d_x_board);
		gameBoard.getChildren().add(board);

		commandBoard = new CommandBoard(primaryStage);
		AnchorPane.setTopAnchor(commandBoard, gameBoard.getHeight() * Metrics.d_y_comb );
		AnchorPane.setLeftAnchor(commandBoard, gameBoard.getWidth() * Metrics.d_x_comb);
		gameBoard.getChildren().add(commandBoard);
		setEndToken();

		bookshelf = new Bookshelf(Metrics.dim_x_bookpl*primaryStage.getWidth(), Metrics.dim_y_bookpl*primaryStage.getHeight(), primaryStage);
		AnchorPane.setTopAnchor(bookshelf, gameBoard.getHeight() * Metrics.d_y_bookpl );
		AnchorPane.setLeftAnchor(bookshelf, gameBoard.getWidth() * Metrics.d_x_bookpl);
		bookshelf.createLabelName(Metrics.dim_x_targetname*primaryStage.getWidth(),Metrics.dim_y_targetname*primaryStage.getHeight(), primaryStage.getWidth()*Metrics.dim_x_bookpl*Metrics.d_x_targetname, primaryStage.getHeight()*Metrics.dim_y_bookpl*Metrics.d_y_targetname);
		bookshelf.setName(nickname);
		gameBoard.getChildren().add(bookshelf);
		for (int i = 0; i < ARROWS_DOWN; i++) {
			System.out.println();
			Arrow arrowDown = new Arrow(Arrow.DOWN);
			arrowDown.setUserData(arrowDown);
			arrowDown.setVisible(false);
			arrowDown.setIndex(i);
			arrowDown.setSize(gameBoard.getWidth() * Metrics.ARROW_DOWN_WIDTH,gameBoard.getHeight() * Metrics.ARROW_DOWN_HEIGHT);
			AnchorPane.setLeftAnchor(arrowDown, gameBoard.getWidth() * (1026.0/1536.0) + gameBoard.getWidth()* (i*60)/1536.0);
			AnchorPane.setTopAnchor(arrowDown, gameBoard.getHeight()* 168.0/864.0);
			arrowDown.setOnMouseClicked(event -> {
				Arrow ad = (Arrow) event.getSource();
				StringBuilder s = new StringBuilder("order");
				for (int j = 0; j < commandBoard.getNextTilePos(); j++) {
					s.append(" ").append(commandBoard.getPickupOrder()[j]);
				}
				if (commandBoard.checkSequence()) {
					LaunchClient.getClient().sendMessage(s.toString());
					handleArrowDown(ad.getIndex());
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

		ppboard = new PlayersPointBoard(primaryStage);
		AnchorPane.setTopAnchor(ppboard,gameBoard.getHeight()*Metrics.d_y_ppb);
		AnchorPane.setLeftAnchor(ppboard, gameBoard.getWidth()*Metrics.d_x_ppb);
		gameBoard.getChildren().add(ppboard);

		cp = new CirclePoints();
		gameBoard.getChildren().add(cp);
		AnchorPane.setTopAnchor(cp, gameBoard.getHeight()*Metrics.d_y_cp);
		AnchorPane.setLeftAnchor(cp, gameBoard.getWidth()*Metrics.d_x_cp);

		createChatContainer();

	}
	private void handleEvent() {
		scene.addEventFilter(CustomEvent.TILE_SELECTED, event -> {
			Tile obj = (Tile) event.getObj();
			boolean flag = event.getFlag();
			int x = (int) obj.getPosition().getX();
			int y = (int) obj.getPosition().getY();
			board.select(x, y);
			if (!flag) {
				redCross.setVisible(board.isSelectedNotEmpty());
				arrowRight.setVisible(board.isSelectedNotEmpty());
				LaunchClient.getClient().sendMessage("select " + x + " " + y);
			}
			/* Stop the event here */
			event.consume();
		});

	}

	/**
	 * It places the two common goals with their respective token score
	 * @param map map representing the common goals (number of the common goal -> score available)
	 */
	public void setCommonGoal(Map<Integer, Integer> map) {
		ArrayList<Integer> arr = new ArrayList<>();
		currentToken = new ArrayList<>();
		for (Integer i: map.keySet()) {
			arr.add(i);
			currentToken.add(map.get(i));
		}
		if (c1 != null && c2 != null) {
			gameBoard.getChildren().remove(c1);
			gameBoard.getChildren().remove(c2);
		}
		c1 = new CommonGoalGui(arr.get(0) - 1, primaryStage);
		c2 = new CommonGoalGui(arr.get(1) - 1, primaryStage);
		AnchorPane.setTopAnchor(c1, gameBoard.getHeight() * Metrics.d_y_comm1);
		AnchorPane.setLeftAnchor(c1, gameBoard.getWidth() * Metrics.d_x_comm);
		AnchorPane.setTopAnchor(c2, gameBoard.getHeight() * Metrics.d_y_comm2);
		AnchorPane.setLeftAnchor(c2, gameBoard.getWidth() * Metrics.d_x_comm);
		gameBoard.getChildren().add(c1);
		gameBoard.getChildren().add(c2);
		setToken();

	}

	private void setToken(){
		int t=0;
		ArrayList<ScoreToken> scoringToken = new ArrayList<>();
		for(int i = 0; i<currentToken.size(); i++){
			if(currentToken.get(i)!=0){
				scoringToken.add(new ScoreToken(currentToken.get(i),primaryStage));
				AnchorPane.setLeftAnchor(scoringToken.get(i-t),6.95*Metrics.d_x_comm*gameBoard.getWidth());
				AnchorPane.setTopAnchor(scoringToken.get(i-t),(i*(210.0/864) - (8.0/864) + Metrics.d_y_commToken)*gameBoard.getHeight());
				gameBoard.getChildren().add(scoringToken.get(i-t));
			}
			else{
				t=1;
			}
		}
	}

	/**
	 * It sets the personal goal of the player
	 * @param number number of the personal goal
	 */
	public void setPersonalGoal(int number) {
		if(p==null){
			p = new PersonalGoal(number, primaryStage);
			AnchorPane.setTopAnchor(p, gameBoard.getHeight() * Metrics.d_y_pers );
			AnchorPane.setLeftAnchor(p, gameBoard.getWidth() * Metrics.d_x_pers);
			gameBoard.getChildren().add(p);
		}
	}

	/**
	 * It sets the board
	 * @param map map representing the board (position -> tile)
	 */
	public void setBoard(Map<String, String> map) {
		if (board == null) {
			board = new Board(primaryStage);
			AnchorPane.setTopAnchor(board, gameBoard.getHeight() * Metrics.d_y_board );
			AnchorPane.setLeftAnchor(board, gameBoard.getWidth() * Metrics.d_x_board);
			gameBoard.getChildren().add(board);
		}
		if (!(nickname.equals(currentPlayer))) {
			board.getChildren().clear();
		}
		for (String s: map.keySet()) {
			if (!map.get(s).equals("NOCOLOR")) {
				if (!board.getTiles().containsKey(s)) {
					Tile t = new Tile(map.get(s), primaryStage);
					t.setPosition(s);
					board.place(t);
				} else {
					Tile t = (Tile) board.getTiles().get(s);
					if (!t.getType().equals(map.get(s))) {
						board.getChildren().remove(board.getTiles().remove(s));
						Tile t1 = new Tile(map.get(s), primaryStage);
						t1.setPosition(s);
						board.place(t1);
					}
					if (!board.getChildren().contains(board.getTiles().get(s))) {
						board.getChildren().add(board.getTiles().get(s));
					}
				}
			}

		}
	}

	private void handleArrowDown(int column) {
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
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
				LaunchClient.getClient().sendMessage("insert " + (column+1));
			}
		}
	}

	/**
	 * It sets the number of player
	 * @param names names of the players
	 */
	public void numPlayers(ArrayList<String> names) {
		this.names = new ArrayList<>(names);
		if (numPlayers == 0) {
			numPlayers = names.size();
		}
		if (ppboard.getHboxes().size() != numPlayers) {
			for (String s: names) {
				ppboard.addPlayer(s);
			}
		}
		addNamesChat();

	}

	private void addNamesChat() {
		HBox inputBox = new HBox();
		inputBox.setPadding(new Insets(10, 0, 10, 0));
		inputBox.setSpacing(10);
		inputBox.setAlignment(Pos.CENTER_RIGHT);
		if (!inputBox.getChildren().contains(selectReceivers)) {
			selectReceivers = new ComboBox<>();
			ArrayList<String> sendTo = new ArrayList<>(names);
			sendTo.remove(nickname);
			if (sendTo.size() > 1) {
				sendTo.add("everyOne");
			}
			chatContainer.getChildren().add(inputBox);

			inputBox.getChildren().add(messageInput);
			inputBox.getChildren().add(sendButton);
			selectReceivers.getItems().addAll(sendTo);
			selectReceivers.setValue(sendTo.get(sendTo.size()-1));
			inputBox.getChildren().add(selectReceivers);
		}

	}

	/**
	 * It sets on the board the tiles that can be selected, changing their borders
	 * @param map a map containing the selectable tiles
	 * @param arr positions of the tiles already selected
	 * @param board map representing the board
	 */
	public void setPickableTiles(Map<String, String> map, ArrayList<Position> arr, Map<String, String> board) {
		this.board.clearUpdate(map, arr, board);
	}

	/**
	 *It adds to the command board the tiles picked, removing them from the board
	 */
	public void setPicked() {
		Tile t;
		while (board.isSelectedNotEmpty()) {
			t = (Tile) board.getSelected();
			if (t != null) {
				t.setPickable(false);
				commandBoard.addTile(t);
			}
		}
	}

	/**
	 * It sets the end token inside the board
	 */
	public void setEndToken(){
		ImageView endToken = new ImageView(Resources.endToken());
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

	/**
	 * It sets properly the bookshelves of the other players, that are slightly different
	 * @param map map representing the bookshelves of the players (nickname -> (position -> color))
	 */
	public void updateBookshelves(Map<String, Map<String, String>> map) {
		if (bookshelves.size() == 0) {
			int j = 0;
			for (int i = 0; i < numPlayers - 1; i++) {
				Bookshelf b = new Bookshelf(Metrics.dim_x_book*primaryStage.getWidth(), Metrics.dim_y_book*primaryStage.getHeight(), primaryStage);
				bookshelves.add(b);
				gameBoard.getChildren().add(bookshelves.get(i));
				bookshelves.get(i).createLabelName(Metrics.dim_x_label*primaryStage.getWidth(),Metrics.dim_y_label*primaryStage.getHeight(), Metrics.dim_x_book*Metrics.d_x_label* primaryStage.getWidth(), Metrics.dim_y_book*Metrics.d_y_label*primaryStage.getHeight());
				switch (numPlayers) {
					case 2 -> {
						AnchorPane.setLeftAnchor(bookshelves.get(i), Metrics.d_x_book2 * primaryStage.getWidth());
						AnchorPane.setTopAnchor(bookshelves.get(i), Metrics.d_y_book2 * primaryStage.getHeight());
						bookshelves.get(i).setPos_x(Metrics.d_x_book2 * primaryStage.getWidth());
						bookshelves.get(i).setPos_y(Metrics.d_y_book2 * primaryStage.getHeight());
					}
					case 3 -> {
						AnchorPane.setLeftAnchor(bookshelves.get(i), ((329 + (329 + 274) * i) / 1536.0) * primaryStage.getWidth());
						AnchorPane.setTopAnchor(bookshelves.get(i), Metrics.d_y_book2 * primaryStage.getHeight());
						bookshelves.get(i).setPos_x(((329 + (329 + 274) * i) / 1536.0) * primaryStage.getWidth());
						bookshelves.get(i).setPos_y(Metrics.d_y_book2 * primaryStage.getHeight());
					}
					case 4 -> {
						AnchorPane.setLeftAnchor(bookshelves.get(i), ((178 + (178 + 274) * i) / 1536.0) * primaryStage.getWidth());
						AnchorPane.setTopAnchor(bookshelves.get(i), Metrics.d_y_book2 * primaryStage.getHeight());
						bookshelves.get(i).setPos_x(((178 + (178 + 274) * i) / 1536.0) * primaryStage.getWidth());
						bookshelves.get(i).setPos_y(Metrics.d_y_book2 * primaryStage.getHeight());
					}
				}
				bookshelves.get(i).setPersonalGoal(primaryStage, gameBoard);
				gameBoard.getChildren().add(bookshelves.get(i).getPgBack());
				if (names.get(j).equals(nickname)) {
					j += 1;
				}
				bookshelves.get(i).setName(names.get(j));
				j++;
			}
		}
		for (Bookshelf value : bookshelves) {
			if (!value.getLabelText().getText().equals(nickname)) {
				Map<String, String> m = map.get(value.getLabelText().getText());
				ArrayList<Node> nodelist = new ArrayList<>();
				value.resetDepth();
				for (int j = 0; j < 5; j++) {
					for (int k = 0; k < 6; k++) {
						if (m.get("(" + j + "," + k + ")").equals("NOCOLOR")) {
							value.modifyDepth(j);
						} else {
							nodelist.add(new Tile(m.get("(" + j + "," + k + ")"), primaryStage));
							value.update(nodelist, j);
							nodelist.clear();
						}
					}
				}
			}
		}
	}


}


