package it.polimi.ingsw.am40.GUI;

import java.util.ArrayList;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;

public class ViewController extends AnchorPane {

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

	public ViewController() {
		super();

		setPrefSize(Metrics.ROOT_WIDTH, Metrics.ROOT_HEIGHT);

		Image background = Resources.background();
		BackgroundImage bgImg = new BackgroundImage(background, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT,
				BackgroundPosition.CENTER, new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false));

		setBackground(new Background(bgImg));

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

		/* Custom handler */
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

			/* Stop the event here */
			event.consume();
		});

		/* Custom handler */
		addEventFilter(CustomEvent.BOOKSHELF_DONE, event -> {

			/* Stop the event here */
			event.consume();
		});

		/* Custom handler */
		addEventFilter(CustomEvent.BOARD_ADD_TILE, event -> {
			Tile obj = (Tile) event.getObj();

			board.place(obj);

			/* Stop the event here */
			event.consume();
		});

		/* Custom handler */
		addEventFilter(CustomEvent.BOARD_TILE_PICKABLE, event -> {
			int x = event.getX();
			int y = event.getY();

			board.pickable(x, y);

			/* Stop the event here */
			event.consume();
		});
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

}
