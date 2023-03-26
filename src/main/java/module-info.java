module it.polimi.ingsw.am40{
    requires javafx.controls;
    requires javafx.fxml;
    requires json.simple;
    //requires org.junit.jupiter.api;

    opens it.polimi.ingsw.am40.Model to javafx.fxml;
    exports it.polimi.ingsw.am40.Model;
    exports it.polimi.ingsw.am40.app;
    opens it.polimi.ingsw.am40.app to javafx.fxml;

}