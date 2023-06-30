package it.polimi.ingsw.am40.GUI;

import it.polimi.ingsw.am40.Model.Position;
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
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Map;

/**
 * Class that represents the graphic element bookshelf
 */
public class Bookshelf extends AnchorPane {
	private static final int COLUMN_SPACES = 6;
	private final double[] colStart = new double[]{Metrics.c1 , Metrics.c2, Metrics.c3, Metrics.c4, Metrics.c5};
	private final double[] rowEnd =new double[] {Metrics.r1 , Metrics.r2, Metrics.r3, Metrics.r4, Metrics.r5, Metrics.r6 };
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
	private final double widthStage;
	private final double heightStage;
	private Chair chair;
	private double pos_x;
	private double pos_y;
	private PersonalGoalBack pgBack;


	/**
	 * COnstructor of the class that sets also the animation for tiles when they are inserted in the bookshelf
	 * @param w width of the bookshelf
	 * @param h height of the bookshelf
	 * @param primaryStage stage where the bookshelf is displayed
	 */
	public Bookshelf(double w, double h, Stage primaryStage) {
		super();
		setPrefSize(w, h);
		bsImage = new Label();
		Image image = Resources.bookshelf();
		ImageView view = new ImageView(image);
		view.setPreserveRatio(true);
		view.setFitWidth(w);
		view.setFitHeight(h);
		bsImage.setGraphic(view);
		heightStage=primaryStage.getHeight();
		widthStage=primaryStage.getWidth();
		System.out.println("WWWWWWW : "+ w + "  HHHHHH: " + h);



		for(int i=0; i<6; i++){
			if(i==5){
				rowEnd[i]=rowEnd[i]*primaryStage.getHeight();
			}
			else {
				rowEnd[i] = rowEnd[i] * primaryStage.getHeight();
				colStart[i] = colStart[i] * primaryStage.getWidth();
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

	/**
	 * It sets the label with the name of the player
	 * @param name name of the player
	 */
	void setName(String name) {
		labelText.setText(name);
	}

	/**
	 * It places the tiles in the bookshelf when the player reconnects
	 * @param map map representing the bookshelf of the player
	 * @param primaryStage stage where the bookshelf is displayed
	 */

	public void reconnectPlaceTiles(Map<String, String> map, Stage primaryStage) {
		for (String s: map.keySet()) {
			if (!map.get(s).equals("NOCOLOR")) {
				Position p = new Position();
				p.convertKey(s);
				Tile t = new Tile(map.get(s), primaryStage);
				AnchorPane.clearConstraints(t);
				AnchorPane.setLeftAnchor(t, colStart[p.getX()]);
				AnchorPane.setTopAnchor(t, rowEnd[p.getY()]);
				getChildren().add(t);
				bsImage.toFront();
				labelName.toFront();
				bookshelf[p.getX()]++;
			}

		}
	}

	/**
	 * It inserts the tiles into the bookshelf
	 * @param nodeList array of nodes representing the tiles
	 * @param col column where the tiles are inserted
	 */
	public void insert(ArrayList<Node> nodeList, int col) {
		this.node = null;
		this.nodeList = nodeList;
		System.out.println("COLSTART[" + col + "] = " + colStart[col]);
		this.col = colStart[col];
		this.colIndex = col;
		animTimer.start();
	}

	/**
	 * It updates the bookshelves of the other players
	 * @param nodeList array of nodes representing the tiles
	 * @param cols column where the tiles are placed
	 */
	public void update(ArrayList<Node> nodeList, int cols){
		double width = this.getWidth();
		double height = this.getHeight();
		System.out.println("WWW : "+ width + "  HHH: " + height);
		System.out.println("COLONNA: "+ col);
		for (int i = 0; i < nodeList.size();) {
			node = nodeList.remove(0);
			AnchorPane.clearConstraints(node);
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
	}

	/**
	 * It creates the label for the name
	 * @param w width of the label
	 * @param h height of the label
	 * @param x x distance of the label from the origin of the bookshelf
	 * @param y y distance of the label from the origin of the bookshelf
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
	 * It returns the number of free positions in the column
	 * @param column column
	 * @return integer representing how many tiles can be inserted in the column
	 */
	public int getFreeSpace(int column) {
		return (COLUMN_SPACES - bookshelf[column]);
	}

	/**
	 * It returns true if the column is full
	 * @param column column
	 * @return true if the column is full, false otherwise
	 */
	public boolean isFull(int column) {
		return (COLUMN_SPACES == bookshelf[column]);
	}

	/**
	 * It returns the label where there is the name
	 * @return label where there is the name
	 */
	public Text getLabelText() {
		return labelText;
	}

	/**
	 * It modifies the depth of the column by one, so a tile has been inserted
	 * @param col column
	 */
	public void modifyDepth(int col) {
		bookshelf[col]++;
	}

	/**
	 * It resets the depth of each column, eliminating also the images of the tiles previously in the bookshelf
	 */
	public void resetDepth() {
		this.bookshelf = new int[]{0, 0, 0, 0, 0, 0};
		getChildren().clear();
		getChildren().add(bsImage);
		getChildren().add(labelName);
	}

	/**
	 * It sets the chair symbolizing the first player near the bookshelf
	 */
	public void setChair() {
		chair = new Chair(widthStage, heightStage);
	}

	/**
	 * It returns the chair
	 * @return the chair
	 */
	public Chair getChair() {
		return chair;
	}

	/**
	 * It sets the x position of the personal goal
	 * @param pos_x x position of the personal goal
	 */
	public void setPos_x(double pos_x) {
		this.pos_x = pos_x;
	}
	/**
	 * It sets the y position of the personal goal
	 * @param pos_y y position of the personal goal
	 */
	public void setPos_y(double pos_y) {
		this.pos_y = pos_y;
	}
	/**
	 * It returns the x position of the personal goal
	 * @return x position of the personal goal
	 */
	public double getPos_x() {
		return pos_x;
	}
	/**
	 * It returns the y position of the personal goal
	 * @return y position of the personal goal
	 */
	public double getPos_y() {
		return pos_y;
	}

	/**
	 * It sets the personal goal of the player near the bookshelf
	 * @param primaryStage stage where the personal goal is displayed
	 * @param p the main anchor pane
	 */
	public void setPersonalGoal(Stage primaryStage, AnchorPane p) {
		pgBack = new PersonalGoalBack(primaryStage);
		AnchorPane.setTopAnchor(pgBack, pos_y+(20/864.0)*p.getHeight());
		AnchorPane.setLeftAnchor(pgBack, pos_x+(Metrics.dim_x_book)*p.getWidth());
	}

	/**
	 * It returns the back of the perosnal goal
	 * @return back of the perosnal goal
	 */
	public PersonalGoalBack getPgBack() {
		return pgBack;
	}
}
