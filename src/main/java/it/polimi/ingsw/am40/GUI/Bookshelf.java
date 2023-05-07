package it.polimi.ingsw.am40.GUI;

import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class Bookshelf extends AnchorPane {
	private static final int COLUMN_SPACES = 6;

	private static final int[] colStart = { 42, 98, 154, 210, 266 };
	private static final int[] rowEnd = { 264, 216, 168, 120, 72, 24 };
	private final int[] bookshelf = { 0, 0, 0, 0, 0, 0 };

	private final Label bsImage;
	private final StackPane labelName;
	private final AnimationTimer animTimer;
	private final Text labelText;

	private int col;
	private int colIndex;
	private int depth;
	private Node node;
	private ArrayList<Node> nodeList;
	private Point2D velocity;

	public Bookshelf() {
		super();

		setPrefSize(Metrics.BOOKSHELF_WIDTH, Metrics.BOOKSHELF_HEIGHT);

		bsImage = new Label();
		Image image = Resources.bookshelf();
		ImageView view = new ImageView(image);
		view.setPreserveRatio(true);
		view.setFitWidth(Metrics.BOOKSHELF_WIDTH);
		view.setFitHeight(Metrics.BOOKSHELF_HEIGHT);
		bsImage.setGraphic(view);

		getChildren().add(bsImage);

		labelName = new StackPane();
		image = Resources.labelName();
		view = new ImageView(image);
		view.setPreserveRatio(true);
		view.setFitWidth(90);
		view.setFitHeight(36);
		labelText = new Text("");
		labelName.getChildren().add(view);
		labelName.getChildren().add(labelText);
		labelName.setAlignment(Pos.CENTER);
		labelName.relocate(132, 316);
		getChildren().add(labelName);

		/* Animation timer */
		animTimer = new AnimationTimer() {
			@Override
			public void handle(long now) {

				if (node == null) {
					node = nodeList.remove(0);
					node.setTranslateX(col);
					node.setTranslateY(0);
					depth = bookshelf[colIndex];
					bookshelf[colIndex]++;
					getChildren().add(node);
					bsImage.toFront();
					labelName.toFront();
				}

				velocity = new Point2D(node.getTranslateX(), rowEnd[depth])
						.subtract(node.getTranslateX(), node.getTranslateY()).normalize()
						.multiply(Metrics.BOOKSHELF_SPEED);

				if (velocity.getY() <= 0) {
					if (nodeList.size() != 0) {
						node = nodeList.remove(0);
						node.setTranslateX(col);
						node.setTranslateY(0);
						depth = bookshelf[colIndex];
						bookshelf[colIndex]++;
						getChildren().add(node);
						bsImage.toFront();
						labelName.toFront();
					} else {
						fireEvent(new CustomEvent(CustomEvent.BOOKSHELF_DONE));
						stop();
						return;
					}
				}
				node.setTranslateX(node.getTranslateX());
				node.setTranslateY(node.getTranslateY() + velocity.getY());
			}
		};
	}

	void setName(String name) {
		labelText.setText(name);
	}

	void addTile(Node node) {
		getChildren().add(node);
		bsImage.toFront();
		labelName.toFront();
	}

	void insert(ArrayList<Node> nodeList, int col) {
		this.node = null;
		this.nodeList = nodeList;
		this.col = colStart[col];
		this.colIndex = col;
		animTimer.start();
	}

	int getFreeSpace(int column) {
		return (COLUMN_SPACES - bookshelf[column]);
	}

	boolean isFull(int column) {
		return (COLUMN_SPACES == bookshelf[column]);
	}

}
