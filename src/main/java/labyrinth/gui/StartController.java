package labyrinth.gui;

import java.io.IOException;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.tinylog.Logger;

public class StartController {

    @FXML Button buttonStartGame;
    @FXML Button buttonStatistics;
    @FXML Button buttonQuit;
    @FXML TextField inputPlayerName;

    @FXML
    private void handleStartGameButton(ActionEvent event) throws IOException {
        if (!inputPlayerName.getText().trim().isEmpty()) {
            Logger.info("Starting game...");
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/game.fxml"));
            Parent root = fxmlLoader.load();
            GameController controller = fxmlLoader.getController();
            controller.setPlayerNames(inputPlayerName.getText().trim());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    @FXML
    private void handleStatisticsButton(ActionEvent event) throws IOException {
        Logger.info("Switching to statistics...");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/statistics.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void handleQuitButton() {
        Logger.info("Exiting...");
        Platform.exit();
    }



}