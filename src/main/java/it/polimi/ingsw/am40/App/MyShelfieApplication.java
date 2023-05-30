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

    @Override
    public void start(Stage stage) throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader(MyShelfieApplication.class.getResource("hello-view.fxml"));
//        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
//        stage.setTitle("Hello!");
//        stage.setScene(scene);
//        stage.show();
    }

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

    static void runAsClient(String[] args){
        LaunchClient.main(args);
    }
    static void runAsServer(String[] args) throws IOException, InterruptedException {
        LaunchServer.main(args);
    }
}