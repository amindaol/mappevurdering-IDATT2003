package edu.ntnu.idi.idatt.ui.view.layouts;

import edu.ntnu.idi.idatt.model.game.Board;
import edu.ntnu.idi.idatt.model.game.Tile;
import edu.ntnu.idi.idatt.ui.route.Router;
import edu.ntnu.idi.idatt.ui.view.components.DieContainer;
import edu.ntnu.idi.idatt.ui.view.components.LaddersBoard;
import edu.ntnu.idi.idatt.ui.view.components.NavBar;
import edu.ntnu.idi.idatt.ui.view.components.PlayerIcon;
import java.util.HashMap;
import java.util.Map;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class BoardView extends VBox {

  private final BorderPane root;
  private final LaddersBoard board;
  private final Map<String, PlayerIcon> playerIcons = new HashMap<>();
  private final Button rollDiceButton = new Button("Roll Dice");

  public BoardView(int rows, int cols, int diceAmount) {
    getStyleClass().add("board-root");

    NavBar navBar = new NavBar("Game",
        () -> Router.navigateTo("home"),
        this::showGameHelp);

    board = new LaddersBoard(rows, cols);
    DieContainer dieContainer = new DieContainer(diceAmount);

    VBox diceBox = new VBox(dieContainer, rollDiceButton);
    diceBox.setAlignment(Pos.CENTER);
    diceBox.setSpacing(10);

    HBox gameContent = new HBox(board.getGrid(), diceBox);
    gameContent.setSpacing(20);
    gameContent.setAlignment(Pos.CENTER);

    root = new BorderPane();
    root.setTop(navBar);
    root.setCenter(gameContent);

    getChildren().add(root);
    VBox.setVgrow(root, Priority.ALWAYS);
  }

  private void showGameHelp() {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("Game Help");
    alert.setHeaderText("How to play");
    alert.setContentText("Players take turns rolling dice to move. First to the goal wins!");
    alert.showAndWait();
  }

  public Parent getRoot() {
    return this;
  }

  public void placePlayerIcon(String playerName, PlayerIcon icon, int tileId) {
    playerIcons.put(playerName, icon);
    Pane tile = board.getTile(tileId);
    if (tile != null) {
      tile.getChildren().add(icon);
    }
  }

  public void movePlayerIcon(String playerName, int tileId) {
    PlayerIcon icon = playerIcons.get(playerName);
    if (icon == null) {
      return;
    }
    if (icon.getParent() instanceof Pane oldTile) {
      oldTile.getChildren().remove(icon);
    }
    Pane newTile = board.getTile(tileId);
    if (newTile != null) {
      newTile.getChildren().add(icon);
    }
  }

  public void setRollOnDice(Runnable onRollDice) {
    rollDiceButton.setOnAction(e -> {
      try {
        onRollDice.run();
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    });
  }

  public void drawBoard(Board boardModel) {
    for (Tile tile : boardModel.getTiles()) {
      Pane tilePane = board.getTile(tile.getTileId());
    }
  }

}
