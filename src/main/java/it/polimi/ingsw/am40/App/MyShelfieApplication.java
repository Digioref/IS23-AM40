package it.polimi.ingsw.am40.App;

import it.polimi.ingsw.am40.Client.LaunchClient;
import it.polimi.ingsw.am40.JSONConversion.ServerArgs;
import it.polimi.ingsw.am40.Network.LaunchServer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MyShelfieApplication extends Application {

    /**
     * Start method, passing the stage where to show the user interface
     * @param stage
     * @throws IOException
     */
    @Override
    public void start(Stage stage) throws IOException {
    }

    /**
     * Starts the server or the client based on the args passed
     * @param args
     * @throws IOException
     * @throws InterruptedException
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        if (args.length > 0) {
            String param0 = args[0];
            if (param0.equals( "--server") )
                runAsServer(ServerArgs.ReadServerConfigFromJSON());
            else if (param0.equals( "--client") )
                runAsClient(args);
        }else {
            runAsClient(args);
        }
    }

    /**
     * Launch the main of LaunchClient passing the args
     * @param args
     */
    static void runAsClient(String[] args){
        LaunchClient.main(args);
    }

    /**
     * Launch the main of LaunchServer passing the args
     * @param args
     * @throws IOException
     * @throws InterruptedException
     */
    static void runAsServer(String[] args) throws IOException, InterruptedException {
        LaunchServer.main(args);
    }
}