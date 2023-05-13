package it.polimi.ingsw.am40.GUI;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.image.Image;

public class Resources {

	public static final int TILE_TYPE_FRAME = 1;
	public static final int TILE_TYPE_CAT = 2;
	public static final int TILE_TYPE_GAME = 3;
	public static final int TILE_TYPE_BOOK = 4;
	public static final int TILE_TYPE_FLOWER = 5;
	public static final int TILE_TYPE_TROPHY = 6;

	private static final String pathSeparator = "/";

	private static final String resourcesPath = "17_MyShelfie_BGA";

	private static final String publisherPath = "Publisher material";
	private static final String iconFile = "Icon 50x50px.png";
	private static final String customPath = "custom";
	private static final String arrowdownFile = "arrow_down_orange.png";
	private static final String number1File = "number_1.png";
	private static final String number2File = "number_2.png";
	private static final String number3File = "number_3.png";
	private static final String labelNameFile = "label_name_3.png";
	private static final String miscPath = "misc";
	private static final String backgroundFile = "sfondo parquet.jpg";
	private static final String bagFile = "Sacchetto Chiuso.png";
	private static final String basePageFile = "base_pagina2.jpg";
	private static final String boardsPath = "boards";
	private static final String boardFile = "livingroom.png";
	private static final String bookshelfFile = "bookshelf.png";

	private static final String commonGoalPath = "common goal cards";
	private static final String commonGoalFile[] = { "1.jpg", "2.jpg", "3.jpg", "4.jpg", "5.jpg", "6.jpg", "7.jpg",
			"8.jpg", "9.jpg", "10.jpg", "11.jpg", "12.jpg" };

	private static final String personalGoalPath = "personal goal cards";
	private static final String personalGoalFile[] = { "Personal_Goals.png", "Personal_Goals2.png",
			"Personal_Goals3.png", "Personal_Goals4.png", "Personal_Goals5.png", "Personal_Goals6.png",
			"Personal_Goals7.png", "Personal_Goals8.png", "Personal_Goals9.png", "Personal_Goals10.png",
			"Personal_Goals11.png", "Personal_Goals12.png" };

	private static final String tilesPath = "item tiles";
	private static final String tileFrameFile[] = { "Cornici1.1.png", "Cornici1.2.png", "Cornici1.3.png", };
	private static final String tileCatFile[] = { "Gatti1.1.png", "Gatti1.2.png", "Gatti1.3.png", };
	private static final String tileGameFile[] = { "Giochi1.1.png", "Giochi1.2.png", "Giochi1.3.png", };
	private static final String tileBookFile[] = { "Libri1.1.png", "Libri1.2.png", "Libri1.3.png", };
	private static final String tileFlowerFile[] = { "Piante1.1.png", "Piante1.2.png", "Piante1.3.png", };
	private static final String tileTrophyFile[] = { "Trofei1.1.png", "Trofei1.2.png", "Trofei1.3.png", };

	private static final String titleFile = "Title 2000x618px.png";

	private static Image loadImage(String imageFile) {
		Image image = null;

		try {
			image = new Image(new FileInputStream(imageFile));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return image;
	}

	public static Image icon() {

		String imageFile;

		imageFile = Resources.resourcesPath;
		imageFile += Resources.pathSeparator;
		imageFile += Resources.publisherPath;
		imageFile += Resources.pathSeparator;
		imageFile += Resources.iconFile;

		return loadImage(imageFile);
	}

	public static Image arrowDown() {

		String imageFile;

		imageFile = Resources.resourcesPath;
		imageFile += Resources.pathSeparator;
		imageFile += Resources.customPath;
		imageFile += Resources.pathSeparator;
		imageFile += Resources.arrowdownFile;

		return loadImage(imageFile);
	}

	public static Image number(int num) {

		String imageFile;

		imageFile = Resources.resourcesPath;
		imageFile += Resources.pathSeparator;
		imageFile += Resources.customPath;
		imageFile += Resources.pathSeparator;

		switch (num) {
		case 1:
			imageFile += number1File;
			break;
		case 2:
			imageFile += number2File;
			break;
		case 3:
			imageFile += number3File;
			break;
		default:
			imageFile += number1File;
			break;
		}

		return loadImage(imageFile);
	}

	public static Image labelName() {

		String imageFile;

		imageFile = Resources.resourcesPath;
		imageFile += Resources.pathSeparator;
		imageFile += Resources.customPath;
		imageFile += Resources.pathSeparator;
		imageFile += labelNameFile;

		return loadImage(imageFile);
	}

	public static Image background() {

		String imageFile;

		imageFile = Resources.resourcesPath;
		imageFile += Resources.pathSeparator;
		imageFile += Resources.miscPath;
		imageFile += Resources.pathSeparator;
		imageFile += Resources.backgroundFile;

		return loadImage(imageFile);
	}

	public static Image bag() {

		String imageFile;

		imageFile = Resources.resourcesPath;
		imageFile += Resources.pathSeparator;
		imageFile += Resources.miscPath;
		imageFile += Resources.pathSeparator;
		imageFile += Resources.bagFile;

		return loadImage(imageFile);
	}

	public static Image basePage() {

		String imageFile;

		imageFile = Resources.resourcesPath;
		imageFile += Resources.pathSeparator;
		imageFile += Resources.miscPath;
		imageFile += Resources.pathSeparator;
		imageFile += Resources.basePageFile;

		return loadImage(imageFile);
	}

	public static Image board() {

		String imageFile;

		imageFile = Resources.resourcesPath;
		imageFile += Resources.pathSeparator;
		imageFile += Resources.boardsPath;
		imageFile += Resources.pathSeparator;
		imageFile += Resources.boardFile;

		return loadImage(imageFile);
	}

	public static Image bookshelf() {

		String imageFile;

		imageFile = Resources.resourcesPath;
		imageFile += Resources.pathSeparator;
		imageFile += Resources.boardsPath;
		imageFile += Resources.pathSeparator;
		imageFile += Resources.bookshelfFile;

		return loadImage(imageFile);
	}

	public static Image commonGoal(int index) {

		String imageFile;

		imageFile = Resources.resourcesPath;
		imageFile += Resources.pathSeparator;
		imageFile += Resources.commonGoalPath;
		imageFile += Resources.pathSeparator;
		imageFile += Resources.commonGoalFile[index];

		return loadImage(imageFile);
	}

	public static Image personalGoal(int index) {

		String imageFile;

		imageFile = Resources.resourcesPath;
		imageFile += Resources.pathSeparator;
		imageFile += Resources.personalGoalPath;
		imageFile += Resources.pathSeparator;
		imageFile += Resources.personalGoalFile[index];

		return loadImage(imageFile);
	}

	public static Image tile(int type, int index) {

		String imageFile;

		imageFile = Resources.resourcesPath;
		imageFile += Resources.pathSeparator;
		imageFile += Resources.tilesPath;
		imageFile += Resources.pathSeparator;

		index %= 3;

		switch (type) {
		case TILE_TYPE_FRAME:
			imageFile += tileFrameFile[index];
			break;
		case TILE_TYPE_CAT:
			imageFile += tileCatFile[index];
			break;
		case TILE_TYPE_GAME:
			imageFile += tileGameFile[index];
			break;
		case TILE_TYPE_BOOK:
			imageFile += tileBookFile[index];
			break;
		case TILE_TYPE_FLOWER:
			imageFile += tileFlowerFile[index];
			break;
		case TILE_TYPE_TROPHY:
			imageFile += tileTrophyFile[index];
			break;
		}

		return loadImage(imageFile);
	}

	public static Image title() {
		String imageFile;

		imageFile = Resources.resourcesPath;
		imageFile += Resources.pathSeparator;
		imageFile += Resources.publisherPath;
		imageFile += Resources.pathSeparator;
		imageFile += Resources.titleFile;

		return loadImage(imageFile);
	}
}
