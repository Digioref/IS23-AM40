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
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * Class that represents the graphic element bookshelf
 */
public class Bookshelf extends AnchorPane {
	private static final int COLUMN_SPACES = 6;
	private double[] colStart = { 39.5/1536.0, 95.5/1536.0, 151.5/1536.0, 207.5/1536.0, 263.5/1536.0 };
	private double[] rowEnd = { 261.0/864.0, 213.0/864.0, 165.0/864.0, 117.0/864.0, 69.0/864.0, 21.0/864.0 };
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

	private double widthStage;
	private double heightStage;
	private Chair chair;
	private double pos_x;
	private double pos_y;
	private PersonalGoalBack pgBack;

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
		heightStage=primaryStage.getHeight();
		widthStage=primaryStage.getWidth();
		System.out.println("WWWWWWW : "+ w + "  HHHHHH: " + h);



		for(int i=0; i<6; i++){
			if(i==5){
				rowEnd[i]=rowEnd[i]*primaryStage.getHeight();
				//System.out.println("ROWEND[" + i + "]:  " + rowEnd[i] + "PRIMARYSTAGE H: "+ primaryStage.getHeight());
			}
			else {
				rowEnd[i] = rowEnd[i] * primaryStage.getHeight();
				colStart[i] = colStart[i] * primaryStage.getWidth();
				//System.out.println("ROWEND[" + i + "]:  " + rowEnd[i] + "  COLSTART[" + i + "]:  " + colStart[i] +  "PRIMARYSTAGE W: "+ primaryStage.getWidth()+ "PRIMARYSTAGE H: "+ primaryStage.getHeight());
			}
		}



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
	public void update(ArrayList<Node> nodeList, int cols){
		double width = this.getWidth();
		double height = this.getHeight();
		System.out.println("WWW : "+ width + "  HHH: " + height);
		System.out.println("COLONNA: "+ col);
		for (int i = 0; i < nodeList.size();) {
			node = nodeList.remove(0);
			AnchorPane.clearConstraints(node);
			//node.prefWidth(274.0/360.0 * Metrics.TILE_WIDTH *widthStage);
			//node.prefHeight(274.0/360.0 * Metrics.TILE_HEIGHT *widthStage);
			//node.setScaleX(274.0/360.0);
			//node.setScaleY(274.0/360);
			Tile t = (Tile) node;
			t.getTileView().setFitWidth(274.0/360 * Metrics.dim_x_tile * widthStage);
			t.getTileView().setFitHeight(274.0/360.0 * Metrics.dim_y_tile * heightStage);
			AnchorPane.setLeftAnchor(t, 274.0/360.0 * colStart[cols]);
			AnchorPane.setTopAnchor(t,  274.0/360 * rowEnd[bookshelf[cols]]);
			bookshelf[cols]++;
			getChildren().add(t);
			bsImage.toFront();
			labelName.toFront();
		}
		/*
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

		 */
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

	public void setChair() {
		chair = new Chair(widthStage, heightStage);
	}

	public Chair getChair() {
		return chair;
	}

	public void setPos_x(double pos_x) {
		this.pos_x = pos_x;
	}

	public void setPos_y(double pos_y) {
		this.pos_y = pos_y;
	}

	public double getPos_x() {
		return pos_x;
	}

	public double getPos_y() {
		return pos_y;
	}

	public void setPersonalGoal(Stage primaryStage, AnchorPane p) {
		pgBack = new PersonalGoalBack(primaryStage);
		AnchorPane.setTopAnchor(pgBack, pos_y+(20/864.0)*p.getHeight());
		AnchorPane.setLeftAnchor(pgBack, pos_x+(Metrics.dim_x_book)*p.getWidth());
	}

	public PersonalGoalBack getPgBack() {
		return pgBack;
	}
}
