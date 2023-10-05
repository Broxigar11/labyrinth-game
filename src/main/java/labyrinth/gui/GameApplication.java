package labyrinth.gui;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.tinylog.Logger;

public class GameApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        Logger.info("Starting new game session");
        Parent root = FXMLLoader.load(getClass().getResource("/start.fxml"));
        stage.setTitle("Labyrinth Game");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
