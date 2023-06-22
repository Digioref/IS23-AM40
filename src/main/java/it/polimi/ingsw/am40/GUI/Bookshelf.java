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
import javafx.stage.Stage;

public class Bookshelf extends AnchorPane {
	private static final int COLUMN_SPACES = 6;
	private double[] colStart = { 42.0/1536.0, 98.0/1536.0, 154.0/1536.0, 210.0/1536.0, 266.0/1536.0 };
	private double[] rowEnd = { 264.0/864.0, 216.0/864.0, 168.0/864.0, 120.0/864.0, 72.0/864.0, 24.0/864.0 };
	private int[] bookshelf;
	private final Label bsImage;
	private StackPane labelName;
	private final AnimationTimer animTimer;
	private Text labelText;
	private double col;
	private int colIndex;
	private int depth;
	private Node node;
	private ArrayList<Node> nodeList;
	private Point2D velocity;


	public Bookshelf(double w, double h, Stage primaryStage) {
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

		/*
		for(int i=0; i<6; i++){
			if(i==5){
				rowEnd[i]=rowEnd[i]*primaryStage.getHeight();
				System.out.println("ROWEND[" + i + "]:  " + rowEnd[i] + "PRIMARYSTAGE H: "+ primaryStage.getHeight());
			}
			else {
				rowEnd[i] = rowEnd[i] * primaryStage.getHeight();
				colStart[i] = colStart[i] * primaryStage.getWidth();
				System.out.println("ROWEND[" + i + "]:  " + rowEnd[i] + "  COLSTART[" + i + "]:  " + colStart[i] +  "PRIMARYSTAGE W: "+ primaryStage.getWidth()+ "PRIMARYSTAGE H: "+ primaryStage.getHeight());
			}
		}

		 */

		getChildren().add(bsImage);
		bookshelf = new int[]{0, 0, 0, 0, 0, 0};


		/* Animation timer */
		animTimer = new AnimationTimer() {
			@Override
			public void handle(long now) {

				if (node == null) {
					node = nodeList.remove(0);
					AnchorPane.clearConstraints(node);
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
						AnchorPane.clearConstraints(node);
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
		System.out.println("COLSTART[" + col + "] = " + colStart[col]);
		this.col = colStart[col];
		this.colIndex = col;
		animTimer.start();
	}
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

	int getFreeSpace(int column) {
		return (COLUMN_SPACES - bookshelf[column]);
	}

	boolean isFull(int column) {
		return (COLUMN_SPACES == bookshelf[column]);
	}

	public Text getLabelText() {
		return labelText;
	}
	public void modifyDepth(int col) {
		bookshelf[col]++;
	}

	public void resetDepth() {
		this.bookshelf = new int[]{0, 0, 0, 0, 0, 0};
		getChildren().clear();
		getChildren().add(bsImage);
		getChildren().add(labelName);
	}
}
