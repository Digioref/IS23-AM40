package it.polimi.ingsw.am40.GUI;

import java.io.*;

import javafx.scene.image.Image;

/**
 * It is a class use dto load all the resources, in particular the images of the graphical elements
 */
public class Resources {
	/**
	 * Number assigned to the tile type FRAME
	 */
	public static final int TILE_TYPE_FRAME = 1;
	/**
	 * Number assigned to the tile type CAT
	 */
	public static final int TILE_TYPE_CAT = 2;
	/**
	 * Number assigned to the tile type GAME
	 */
	public static final int TILE_TYPE_GAME = 3;
	/**
	 * Number assigned to the tile type BOOK
	 */
	public static final int TILE_TYPE_BOOK = 4;
	/**
	 * Number assigned to the tile type FLOWER
	 */
	public static final int TILE_TYPE_FLOWER = 5;
	/**
	 * Number assigned to the tile type TROPHY
	 */
	public static final int TILE_TYPE_TROPHY = 6;
	private static final int SCORE_TWO = 2;

	private static final int SCORE_FOUR = 4;

	private static final int SCORE_SIX = 6;

	private static final int SCORE_EIGHT = 8;

	private static final String iconFile = "Icon 50x50px.png";

	private static final String arrowdownFile = "arrow_down_orange.png";
	private static final String number1File = "number_1.png";
	private static final String number2File = "number_2.png";
	private static final String number3File = "number_3.png";
	private static final String labelNameFile = "label_name_3.png";
	private static final String backgroundFile = "sfondo parquet.jpg";
	private static final String bagFile = "Sacchetto Chiuso.png";
	private static final String boardFile = "livingroom.png";
	private static final String bookshelfFile = "bookshelf.png";
	private static final String redCrossFile = "red_cross.png";
	private static final String chair = "firstplayertoken.png";
	private static final String personalGoalBack = "back.jpg";
	private static final String circlepoints = "points_circle.png";
	private static final String[] commonGoalFile = { "1.jpg", "2.jpg", "3.jpg", "4.jpg", "5.jpg", "6.jpg", "7.jpg",
			"8.jpg", "9.jpg", "10.jpg", "11.jpg", "12.jpg" };
	private static final String[] personalGoalFile = { "Personal_Goals.png", "Personal_Goals2.png",
			"Personal_Goals3.png", "Personal_Goals4.png", "Personal_Goals5.png", "Personal_Goals6.png",
			"Personal_Goals7.png", "Personal_Goals8.png", "Personal_Goals9.png", "Personal_Goals10.png",
			"Personal_Goals11.png", "Personal_Goals12.png" };
	private static final String[] tileFrameFile = { "Cornici1.1.png", "Cornici1.2.png", "Cornici1.3.png", };
	private static final String[] tileCatFile = { "Gatti1.1.png", "Gatti1.2.png", "Gatti1.3.png", };
	private static final String[] tileGameFile = { "Giochi1.1.png", "Giochi1.2.png", "Giochi1.3.png", };
	private static final String[] tileBookFile = { "Libri1.1.png", "Libri1.2.png", "Libri1.3.png", };
	private static final String[] tileFlowerFile = { "Piante1.1.png", "Piante1.2.png", "Piante1.3.png", };
	private static final String[] tileTrophyFile = { "Trofei1.1.png", "Trofei1.2.png", "Trofei1.3.png", };
	private static final String titleFile = "Title 2000x618px.png";
	private static final String[] scoringFile = {"scoring_2.jpg","scoring_4.jpg","scoring_6.jpg","scoring_8.jpg"};

	private static final String endGameToken = "end game.jpg";

	private static Image loadImage(String imageFile){

		InputStream inputStream =Resources.class.getResourceAsStream("/Images/"+imageFile);

		if (inputStream == null) {
			System.out.println("Resource not found: "+ imageFile);
			return null;
		}

		return new Image(inputStream);
	}

	/**
	 * It loads the icon image
 	 * @return icon image
	 */
	public static Image icon() {
		return loadImage(iconFile);
	}

	/**
	 * It loads the arrow down image
	 * @return arrow down image
	 */
	public static Image arrowDown() {
		return loadImage(arrowdownFile);
	}
	/**
	 * It loads the numbers' images
	 * @return numbers' images
	 */
	public static Image number(int num) {

		String imageFile = switch (num) {
			case 2 -> number2File;
			case 3 -> number3File;
			default -> number1File;
		};

		return loadImage(imageFile);
	}
	/**
	 * It loads the label name image
	 * @return label name image
	 */
	public static Image labelName() {
		return loadImage(labelNameFile);
	}
	/**
	 * It loads the background image
	 * @return background image
	 */
	public static Image background() {
		return loadImage(backgroundFile);
	}
	/**
	 * It loads the bag image
	 * @return bag image
	 */
	public static Image bag() {
		return loadImage(bagFile);
	}

	/**
	 * It loads the board image
	 * @return board image
	 */
	public static Image board() {
		return loadImage(boardFile);
	}
	/**
	 * It loads the bookshelf image
	 * @return bookshelf image
	 */
	public static Image bookshelf() {
		return loadImage(bookshelfFile);
	}
	/**
	 * It loads the common goal image
	 * @return common goal image
	 */
	public static Image commonGoal(int index) {
		return loadImage(commonGoalFile[index]);
	}
	/**
	 * It loads the personal goal image
	 * @return personal goal image
	 */
	public static Image personalGoal(int index) {
		return loadImage(personalGoalFile[index]);
	}
	/**
	 * It loads the red cross image
	 * @return red cross image
	 */
	public static Image redCross() {
		return loadImage(redCrossFile);
	}
	/**
	 * It loads the chair image
	 * @return chair image
	 */
	public static Image chair() {
		return loadImage(chair);
	}

	/**
	 * It loads the specific tile image
	 * @param type color of the tile
	 * @param index index of the image of that type
	 * @return a tile image
	 */
	public static Image tile(String type, int index) {

		String imageFile = null;

		index %= 3;

		switch (type) {
			case "BLUE" -> imageFile = tileFrameFile[index];
			case "GREEN" -> imageFile = tileCatFile[index];
			case "YELLOW" -> imageFile = tileGameFile[index];
			case "WHITE" -> imageFile = tileBookFile[index];
			case "VIOLET" -> imageFile = tileFlowerFile[index];
			case "CYAN" -> imageFile = tileTrophyFile[index];
			default -> {
			}
		}
		if(imageFile==null){
			System.out.println(type + "   " + index);
		}
		return loadImage(imageFile);
	}
	/**
	 * It loads the specific tile image
	 * @param type type of the tile
	 * @param index index of the image of that type
	 * @return a tile image
	 */
	public static Image tile(int type, int index) {

		String imageFile="";
		index %= 3;

		switch (type) {
			case TILE_TYPE_FRAME -> imageFile = tileFrameFile[index];
			case TILE_TYPE_CAT -> imageFile = tileCatFile[index];
			case TILE_TYPE_GAME -> imageFile = tileGameFile[index];
			case TILE_TYPE_BOOK -> imageFile = tileBookFile[index];
			case TILE_TYPE_FLOWER -> imageFile = tileFlowerFile[index];
			case TILE_TYPE_TROPHY -> imageFile = tileTrophyFile[index];
			default -> {
			}
		}

		return loadImage(imageFile);
	}

	/**
	 * It loads the title image
	 * @return title image
	 */
	public static Image title() {
		return loadImage(titleFile);
	}

	/**
	 * It loads the score token image of the common goal
	 * @param index score
	 * @return score token image
	 */
	public static Image score(int index){
		String imagefile = "";
		switch (index) {
			case SCORE_TWO -> imagefile = scoringFile[0];
			case SCORE_FOUR -> imagefile = scoringFile[1];
			case SCORE_SIX -> imagefile = scoringFile[2];
			case SCORE_EIGHT -> imagefile = scoringFile[3];
			default -> {
			}
		}
		return loadImage(imagefile);
	}

	/**
	 * It loads the end token image
	 * @return end token image
	 */
	public static Image endToken(){
		return loadImage(endGameToken);
	}

	/**
	 * It loads the personal goal back image
	 * @return personal goal image back
	 */
	public static Image personalGoalBack() {
		return loadImage(personalGoalBack);
	}

	/**
	 * It loads the circle image
	 * @return circle image
	 */
	public static Image pointsCircle() {
		return loadImage(circlepoints);
	}

}
