package it.polimi.ingsw.am40.GUI;

import java.util.ArrayList;
import java.util.Map;

import it.polimi.ingsw.am40.CLI.View;
import it.polimi.ingsw.am40.Client.LaunchClient;
import it.polimi.ingsw.am40.Client.SocketClient;
import it.polimi.ingsw.am40.Model.Position;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Screen;


public class ViewController extends AnchorPane implements View {

	private static final int ARROWS_DOWN = 5;

	private final CommandBoard commandBoard = new CommandBoard();
	private final Bookshelf bookshelf = new Bookshelf();
	private final Arrow arrowRight = new Arrow(Arrow.RIGHT);
	private final ArrayList<Arrow> arrowDownList = new ArrayList<Arrow>();
	private final Board board = new Board();
	private final Bag bag = new Bag();
	private final CommonGoal cg1 = new CommonGoal(3);
	private final CommonGoal cg2 = new CommonGoal(8);
	private final PersonalGoal persGoal = new PersonalGoal(4);

	private String connectionType;
	private String connectionIp;

	public ViewController() {
		super();

		double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
		double screenHeight = Screen.getPrimary().getVisualBounds().getHeight();
		setPrefSize(screenWidth, screenHeight);

		Image background = Resources.background();
		BackgroundImage bgImg = new BackgroundImage(background, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT,
				BackgroundPosition.DEFAULT, new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false));

		setBackground(new Background(bgImg));

		VBox vbox = new VBox();
		vbox.setSpacing(10);
		vbox.setAlignment(Pos.CENTER);

		Image title = Resources.title();
		//Image pg = Resources.personalGoal(1);
		ImageView imageView = new ImageView(title);
		imageView.setFitWidth(screenWidth);
		imageView.setPreserveRatio(true);
		vbox.getChildren().add(imageView);

		ToggleGroup tg = new ToggleGroup();
		RadioButton socket = new RadioButton("Socket");
		RadioButton rmi = new RadioButton("RMI      ");
		socket.setSelected(true);
		connectionType = "SOCKET";

		socket.setOnAction(e -> {
			connectionType = "SCOKET";
		});
		rmi.setOnAction(e -> {
			connectionType = "RMI";
		});

		socket.setToggleGroup(tg);
		rmi.setToggleGroup(tg);

		vbox.getChildren().addAll(socket,rmi);

		TextField ip = new TextField();
		ip.setMaxWidth(100);
		vbox.getChildren().add(ip);

		Text text = new Text("scegli una connessione bro");

		Button conferma = new Button("Continua");

		conferma.setOnAction(e -> {
			connectionIp = ip.getText();
			if (!connectionIp.equals("")) {
				if (connectionIp.equalsIgnoreCase("L")) {
					connectionIp = "localhost";
				}
				LaunchClient.startConnection(connectionType, connectionIp);
			} else {
				text.setText("Devi selezionare un indirizzo ip se no non ti mando avanti");
			}
		});

		vbox.getChildren().add(conferma);
		vbox.getChildren().add(text);

		getChildren().add(vbox);




		/*

		showCurrentPlayer("Dai figaaaa");

		bag.relocate(50, 60);
		getChildren().add(bag);

		cg1.relocate(20, 200);
		getChildren().add(cg1);

		cg2.relocate(20, 350);
		getChildren().add(cg2);

		persGoal.relocate(1150, 250);
		getChildren().add(persGoal);

		board.relocate(230, 40);
		getChildren().add(board);

		bookshelf.relocate(786, 180);
		bookshelf.setName("Francesco");
		getChildren().add(bookshelf);

		int numPlayers = 3;
		int start = 612;
		for (int i = 0; i < numPlayers - 1; i++) {
			Bookshelf b = new Bookshelf();
			b.relocate(460, (start + 380*i));
			String nome = "nome " + i;
			b.setName(nome);
			getChildren().add(b);
		}

		commandBoard.relocate(850, 40);
		getChildren().add(commandBoard);

		for (int i = 0; i < ARROWS_DOWN; i++) {
			Arrow arrowDown = new Arrow(Arrow.DOWN);
			arrowDown.setUserData(arrowDown);
			arrowDown.setVisible(false);
			arrowDown.setIndex(i);
			arrowDown.setSize(Metrics.ARROW_DOWN_WIDTH, Metrics.ARROW_DOWN_HEIGHT);
			arrowDown.relocate(830 + (i * 60), 148);
			arrowDown.setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					Arrow ad = (Arrow) event.getSource();
					handleArrowDown(event, ad.getIndex());
				}
			});
			arrowDownList.add(arrowDown);
			getChildren().add(arrowDown);
		}

		arrowRight.setSize(Metrics.ARROW_RIGHT_WIDTH, Metrics.ARROW_RIGHT_HEIGHT);
		arrowRight.setVisible(false);
		arrowRight.relocate(770, 50);
		arrowRight.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				Tile t;

				while (!board.isSelectedEmpty()) {
					t = (Tile) board.getSelected();
					t.setPickable(false);
					commandBoard.addTile(t);
				}

				arrowRight.setVisible(false);

				int col = 0;
				for (Arrow a : arrowDownList) {
					if (!bookshelf.isFull(col++)) {
						a.setVisible(true);
					}
				}
			}
		});
		getChildren().add(arrowRight);

		// Custom handler
		addEventFilter(CustomEvent.TILE_SELECTED, event -> {
			Tile obj = (Tile) event.getObj();
			boolean flag = event.getFlag();
			int x = (int) obj.getPosition().getX();
			int y = (int) obj.getPosition().getY();

			board.select(flag, x, y);

			if (board.isSelectedEmpty()) {
				arrowRight.setVisible(false);
			} else {
				arrowRight.setVisible(true);
			}

			// Stop the event here
			event.consume();
		});

		// Custom handler
		addEventFilter(CustomEvent.BOOKSHELF_DONE, event -> {

			// Stop the event here
			event.consume();
		});

		// Custom handler
		addEventFilter(CustomEvent.BOARD_ADD_TILE, event -> {
			Tile obj = (Tile) event.getObj();

			board.place(obj);

			// Stop the event here
			event.consume();
		});

		// Custom handler
		addEventFilter(CustomEvent.BOARD_TILE_PICKABLE, event -> {
			int x = event.getX();
			int y = event.getY();

			board.pickable(x, y);

			// Stop the event here
			event.consume();
		});
		*/
	}

	private void handleArrowDown(MouseEvent event, int column) {
		if (commandBoard.checkSequence()) {
			ArrayList<Node> nodeList = new ArrayList<Node>();
			Node n;

			if (commandBoard.getNumTile() > bookshelf.getFreeSpace(column)) {
				System.out.println("No space in bookshelf column.");
			} else {

				for (Arrow a : arrowDownList) {
					a.setVisible(false);
				}

				while ((n = commandBoard.getTile()) != null) {
					nodeList.add(n);
				}

				if (nodeList.size() > 0) {
					bookshelf.insert(nodeList, column);
				}
			}
		}
	}

	@Override
	public void chooseConnection() {

	}

	@Override
	public void showCurrentPlayer(String s) {
		Text labelText = new Text(s);
		labelText.relocate(0,0);
		getChildren().add(labelText);
	}

	@Override
	public void showCurrentScore(Map<String, Integer> map) {

	}

	@Override
	public void showHiddenScore(int score) {

	}

	@Override
	public void showCommonGoals(Map<Integer, Integer> map) {

	}

	@Override
	public void showPersonalGoal(Map<String, String> map) {

	}

	@Override
	public void showBoard(Map<String, String> map) {

	}

	@Override
	public void showCurrentBookshelf(Map<String, String> map) {
		bookshelf.relocate(786, 180);
		bookshelf.setName("Francesco");
		getChildren().add(bookshelf);
	}

	@Override
	public void showAllBookshelves(Map<String, Map<String, String>> map) {

	}

	@Override
	public void showBookshelf(Map<String, String> map) {

	}

	@Override
	public void showBoardPickable(Map<String, String> map, ArrayList<Position> arr, Map<String, String> board) {

	}

	@Override
	public void showSelectedTiles(Map<String, String> map, String s) {

	}

	@Override
	public void showPickedTiles(Map<String, String> map, String s) {

	}

	@Override
	public void showFinalScore(Map<String, Integer> map, String winner) {

	}

	@Override
	public void showPlayers(ArrayList<String> names) {

	}

	@Override
	public void printMessage(String s) {

	}

	@Override
	public void chat(SocketClient socketClient) {

	}

	@Override
	public void showChat(ArrayList<String> array1, ArrayList<String> array2, ArrayList<String> array3, String nickname) {

	}

}
