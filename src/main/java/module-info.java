module it.polimi.ingsw.am40{
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.fxml;
    requires json.simple;
    requires java.rmi;
    //requires org.junit.jupiter.api;

    //opens it.polimi.ingsw.am40.Model to javafx.fxml;
    //exports it.polimi.ingsw.am40.Model;
    opens it.polimi.ingsw.am40.App to javafx.fxml, javafx.controls, javafx.graphics;
    exports it.polimi.ingsw.am40.App;
    //opens it.polimi.ingsw.am40.CLI to javafx.fxml;
    //exports it.polimi.ingsw.am40.CLI;
    exports it.polimi.ingsw.am40.Network.RMI;
    opens it.polimi.ingsw.am40.GUI to javafx.fxml, javafx.controls, javafx.graphics;
    exports it.polimi.ingsw.am40.GUI;
    exports it.polimi.ingsw.am40.Client;
    exports it.polimi.ingsw.am40.Controller;
    exports it.polimi.ingsw.am40.Model;
    exports it.polimi.ingsw.am40.JSONConversion;
    exports it.polimi.ingsw.am40.Network.Commands;
    exports it.polimi.ingsw.am40.Network;
}
