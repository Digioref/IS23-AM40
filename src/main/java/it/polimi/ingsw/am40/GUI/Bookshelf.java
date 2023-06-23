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
import javafx.stage.Screen;

public class Bookshelf extends AnchorPane {
	private static final int COLUMN_SPACES = 6;
	private static final int[] colStart = { 42, 98, 154, 210, 266 };
	private static final int[] rowEnd = { 264, 216, 168, 120, 72, 24 };
	private int[] bookshelf;
	private final Label bsImage;
	private StackPane labelName;
	private final AnimationTimer animTimer;
	private Text labelText;
	private int col;
	private int colIndex;
	private int depth;
	private Node node;
	private ArrayList<Node> nodeList;
	private Point2D velocity;

	/**
	 * Represents the graphic element bookshelf
	 * @param w is width
	 * @param h is height
	 */
	public Bookshelf(double w, double h) {
		super();

//		double screenHeight = Screen.getPrimary().getVisualBounds().getHeight() * 0.20;
//		setPrefSize(screenHeight, screenHeight);
		setPrefSize(w, h);

		bsImage = new Label();
		Image image = Resources.bookshelf();
		ImageView view = new ImageView(image);
		view.setPreserveRatio(true);
//		view.setFitWidth(screenHeight);
//		view.setFitHeight(screenHeight);
		view.setFitWidth(w);
		view.setFitHeight(h);
		bsImage.setGraphic(view);

		getChildren().add(bsImage);
		bookshelf = new int[]{0, 0, 0, 0, 0, 0};


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

	/**
	 * TODO
	 * @param name
	 */
	void setName(String name) {
		labelText.setText(name);
	}

	/**
	 * TODO
	 * @param node
	 */
	void addTile(Node node) {
		getChildren().add(node);
		bsImage.toFront();
		labelName.toFront();
	}

	/**
	 * TODO
	 * @param nodeList
	 * @param col
	 */
	void insert(ArrayList<Node> nodeList, int col) {
		this.node = null;
		this.nodeList = nodeList;
		this.col = colStart[col];
		this.colIndex = col;
		animTimer.start();
	}

	/**
	 * TODO
	 * @param nodeList
	 * @param col
	 */
	public void update(ArrayList<Node> nodeList, int col){
		this.node = null;
		this.nodeList = nodeList;
		this.col = colStart[col];
		this.colIndex = col;
		for (int i = 0; i < nodeList.size();) {
			node = nodeList.remove(0);
			node.setTranslateX(col);
			node.setTranslateY(0);
			depth = bookshelf[colIndex];
			bookshelf[colIndex]++;
			getChildren().add(node);
			bsImage.toFront();
			labelName.toFront();
		}
	}

	/**
	 * TODO
	 * @param w
	 * @param h
	 * @param x
	 * @param y
	 */
	public void createLabelName(double w, double h, double x, double y) {
		labelName = new StackPane();
		Image image = Resources.labelName();
		ImageView view = new ImageView(image);
		view.setPreserveRatio(true);
		view.setFitWidth(w);
		view.setFitHeight(h);
		labelText = new Text("");
		labelName.getChildren().add(view);
		labelName.getChildren().add(labelText);
		labelName.setAlignment(Pos.CENTER);
		labelName.relocate(x, y);
		getChildren().add(labelName);
	}

	/**
	 * TODO
	 * @param column
	 * @return
	 */
	int getFreeSpace(int column) {
		return (COLUMN_SPACES - bookshelf[column]);
	}

	/**
	 * TODO
	 * @param column
	 * @return
	 */
	boolean isFull(int column) {
		return (COLUMN_SPACES == bookshelf[column]);
	}

	/**
	 * TODO
	 * @return
	 */
	public Text getLabelText() {
		return labelText;
	}

	/**
	 * TODO
	 * @param col
	 */
	public void modifyDepth(int col) {
		bookshelf[col]++;
	}

	/**
	 * TODO
	 */
	public void resetDepth() {
		this.bookshelf = new int[]{0, 0, 0, 0, 0, 0};
		getChildren().clear();
		getChildren().add(bsImage);
		getChildren().add(labelName);
	}
}
