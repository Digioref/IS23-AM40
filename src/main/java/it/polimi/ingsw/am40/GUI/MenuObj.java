package it.polimi.ingsw.am40.GUI;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

public class MenuObj {

	private static Menu createFileMenu(Stage stage) {
		Menu menuFile = new Menu("_File");

		MenuItem menuItemExit = new MenuItem("Exit");

		menuItemExit.setOnAction(event -> {
			System.exit(0);
		});

		menuFile.getItems().add(menuItemExit);

		return menuFile;
	}

	private static Menu createHelpMenu(Stage stage) {
		Menu menuHelp = new Menu("_Help");

		MenuItem menuItemHelp = new MenuItem("Help Contents");
		MenuItem menuItemAbout = new MenuItem("About");
		menuItemAbout.setOnAction(event -> {
			System.out.println("About menu");
			System.out.println(event.toString());
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("About MyShelfie GUI");
			alert.setHeaderText(null);
			alert.setContentText("MyShelfie GUI version 1.0");

			alert.showAndWait();
		});

		menuHelp.getItems().add(menuItemHelp);
		menuHelp.getItems().add(menuItemAbout);

		return menuHelp;
	}

	public static MenuBar createMenuBar(Stage stage) {

		MenuBar menuBar = new MenuBar();

		Menu menuFile = createFileMenu(stage);
		Menu menuHelp = createHelpMenu(stage);

		menuBar.getMenus().addAll(menuFile, menuHelp);

		return menuBar;
	}
}
