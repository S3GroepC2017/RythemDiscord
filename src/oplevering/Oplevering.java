package oplevering;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import logic.*;

import java.io.File;
import java.net.URL;

public class Oplevering extends Application
{
    private ILogic logic;

    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        URL url = new File("src/oplevering/scene.fxml").toURL();

        Parent root = FXMLLoader.load(url);

        Scene scene = new Scene(root);

        logic = new GameManager();

        primaryStage.setTitle("RythemDiscord");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
