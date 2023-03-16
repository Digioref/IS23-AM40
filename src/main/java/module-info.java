module it.polimi.ingsw.am40 {
    requires javafx.controls;
    requires javafx.fxml;


    opens it.polimi.ingsw.am40 to javafx.fxml;
    exports it.polimi.ingsw.am40;
}