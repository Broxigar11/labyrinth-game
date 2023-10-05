package labyrinth.gui;

import javafx.application.Platform;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import json.JsonBuilder;
import labyrinth.state.Direction;
import labyrinth.state.LabyrinthState;
import org.tinylog.Logger;

import java.io.IOException;

public class GameController {
    @FXML
    private GridPane grid;
    private StackPane[][] gridCell = new StackPane[7][7];

    @FXML Label labelPlayerName;
    @FXML Label labelMovesCounter;

    private Image ballImage;
    private Image goalImage;

    JsonBuilder jsonBuilder = new JsonBuilder();

    private LabyrinthState state;

    private IntegerProperty numberOfMoves = new SimpleIntegerProperty();

    @FXML
    private void initialize() {
        Logger.debug("Running initialize");
        loadImages();
        resetGame();
        registerKeyHandler();
        registerEventBindings();
    }

    private void loadImages() {
        Logger.debug("Loading images");
        ballImage = new Image("/images/ball.png");
        goalImage = new Image("/images/goal.png");
    }

    private void resetGame(){
        Logger.info("Resetting game");
        state = new LabyrinthState();
        numberOfMoves.set(0);
        repopulateGrid();
    }

    private void registerEventBindings(){
        Logger.debug("Registering event bindings");
        labelMovesCounter.textProperty().bind(numberOfMoves.asString());
    }

    private void registerKeyHandler() {
        Logger.debug("Registering key handler");
        KeyCombination restartKeyCombination = new KeyCodeCombination(KeyCode.R, KeyCombination.CONTROL_DOWN);
        KeyCombination quitKeyCombination = new KeyCodeCombination(KeyCode.Q, KeyCombination.CONTROL_DOWN);
        Platform.runLater(() -> grid.getScene().setOnKeyPressed(
                keyEvent -> {
                    if (restartKeyCombination.match(keyEvent)) {
                        resetGame();
                    } else if (quitKeyCombination.match(keyEvent)) {
                        Platform.exit();
                    } else if (keyEvent.getCode() == KeyCode.UP) {
                        performMove(Direction.UP);
                    } else if (keyEvent.getCode() == KeyCode.RIGHT) {
                        performMove(Direction.RIGHT);
                    } else if (keyEvent.getCode() == KeyCode.DOWN) {
                        performMove(Direction.DOWN);
                    } else if (keyEvent.getCode() == KeyCode.LEFT) {
                        performMove(Direction.LEFT);
                    }
                }
        ));
    }

    private void repopulateGrid() {
        Logger.debug("Repopulating grid");
        for (var row = 0; row < grid.getRowCount(); row++) {
            for (var col = 0; col < grid.getColumnCount(); col++) {
                var cell = gridCell[col][row]; // clear old cells
                if (cell != null){
                    grid.getChildren().remove(cell);
                }
                if (state.isGoal(col, row)){
                    cell = createGoal( row, col);
                }
                else {
                    cell = createSquare(row, col);
                }
                gridCell[col][row] = cell;
                grid.add(cell, col, row);
            }
        }
    }

    private void performMove(Direction direction){
        if (state.canMove(direction)) {
            Logger.info("Move: {}", direction);
            state.move(direction);
            Logger.trace("New state: {}", state);
            numberOfMoves.set(numberOfMoves.get() + 1);
        } else {
            Logger.warn("Invalid move: {}", direction);
        }
        if(state.reachedGoal()){
            handleGameOver();
        }
    }

    private StackPane createSquare(int row, int col) {
        Logger.trace("Creating grid square");
        var square = new StackPane();
        Pane wallPane = new Pane();
        addStyles(wallPane, row, col);
        square.getChildren().add(wallPane);
        var pieceView = new ImageView(ballImage);
        pieceView.setPreserveRatio(true);
        pieceView.setFitHeight(30);
        pieceView.setFitWidth(30);
        pieceView.visibleProperty().bind(createBallVisibilityBinding(row, col));
        square.getChildren().add(pieceView);
        return square;
    }

    private StackPane createGoal(int row, int col) {
        Logger.trace("Creating grid goal square");
        var square = createSquare(row, col);
        var pieceView = new ImageView(goalImage);
        pieceView.setPreserveRatio(true);
        pieceView.setFitHeight(30);
        pieceView.setFitWidth(30);
        square.getChildren().add(pieceView);
        return square;
    }

    private void handleGameOver() {
        var alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Congratulations!");
        alert.setContentText("You have reached the goal. Returning to Main Menu");
        alert.showAndWait();
        writeGameResults();
        returnToMenu();
    }

    private void writeGameResults() {
        try {
            Logger.info("Saving player data to file");
            jsonBuilder.saveToFile(labelPlayerName.textProperty().getValue(), numberOfMoves.get());
        } catch (IOException e) {
            Logger.error("Failed to save data to file!");
        }
    }

    private void addStyles(Pane p, int row, int col) {
        Logger.trace("Adding borders to square");
        String sizes = "";
        sizes += state.hasWall(col, row, Direction.UP) ? "3px " : " 0px";
        sizes += state.hasWall(col, row, Direction.RIGHT) ? "3px " : " 0px";
        sizes += state.hasWall(col, row, Direction.DOWN) ? "3px " : " 0px";
        sizes += state.hasWall(col, row, Direction.LEFT) ? "3px " : " 0px";
        p.setStyle("-fx-border-color: black; -fx-border-width: " + sizes + ";");
    }

    private BooleanBinding createBallVisibilityBinding(int row, int col) {
        Logger.trace("Creating ball visibility binding");
        return new BooleanBinding() {
            {
                super.bind(state.ballPositionProperty());
            }
            @Override
            protected boolean computeValue() {
                var pos = state.ballPositionProperty().get();
                return pos.y == row && pos.x == col;
            }
        };
    }

    public void setPlayerNames(String name) {
        Logger.trace("Setting player name on label");
        labelPlayerName.textProperty().setValue(name);
    }

    private void returnToMenu() {
        Logger.debug("Returning to main menu");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/start.fxml"));
        Parent root = null;
        try {
            root = fxmlLoader.load();
            Window window = Stage.getWindows().stream().filter(Window::isShowing).findFirst().orElse(null);
            Stage stage = (Stage) window;
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            Logger.error("Failed to return to main menu!");
        }

    }

}
