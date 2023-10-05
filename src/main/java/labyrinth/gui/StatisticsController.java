package labyrinth.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import json.JsonBuilder;
import json.PlayerData;
import org.tinylog.Logger;

import java.io.IOException;
import java.util.List;

public class StatisticsController {

    @FXML
    private TableView<PlayerData> tableStats;
    @FXML
    private TableColumn<PlayerData, String> colPlayer;
    @FXML
    private TableColumn<PlayerData, Integer> colMoves;

    JsonBuilder builder = new JsonBuilder();

    @FXML
    private void initialize() throws IOException {
        Logger.trace("Statistics are initializing");
        colPlayer.setCellValueFactory(new PropertyValueFactory<>("name"));
        colMoves.setCellValueFactory(new PropertyValueFactory<>("moves"));
        List<PlayerData> players = builder.readFromFile();
        ObservableList<PlayerData> observableList = FXCollections.observableArrayList();
        observableList.addAll(players);
        tableStats.setItems(observableList);
    }

    @FXML
    private void handleBackButton(ActionEvent event) throws IOException {
        Logger.info("Returning to main menu...");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/start.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}