package edu.ntnu.idi.idatt.ui.view.layouts;

import edu.ntnu.idi.idatt.model.game.Board;
import edu.ntnu.idi.idatt.model.game.Ladder;
import edu.ntnu.idi.idatt.model.game.Tile;
import edu.ntnu.idi.idatt.ui.view.components.DieContainer;
import edu.ntnu.idi.idatt.ui.view.components.LaddersBoard;
import edu.ntnu.idi.idatt.ui.view.components.PlayerIcon;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class BoardView extends BorderPane {

  private final LaddersBoard board;
  private final DieContainer dieContainer;
  private final Button rollDiceButton = new Button("Roll Dice");
  private final Map<String, PlayerIcon> playerIcons = new HashMap<>();
  ;
  private Runnable onRoll;
  private Consumer<int[]> onDiceRolled;

  public BoardView(int rows, int cols, int diceAmount) {
    this.getStyleClass().add("board-root");

    this.board = new LaddersBoard(rows, cols);
    this.dieContainer = new DieContainer(diceAmount);

    VBox diceBox = new VBox(dieContainer, rollDiceButton);
    diceBox.setAlignment(Pos.CENTER);
    diceBox.setSpacing(10);

    HBox contentBox = new HBox(board.getGrid(), diceBox);
    contentBox.setSpacing(20);
    contentBox.setAlignment(Pos.CENTER);

    ScrollPane scrollPane = new ScrollPane(contentBox);
    scrollPane.setFitToWidth(true);
    scrollPane.setFitToHeight(true);
    scrollPane.setPannable(true);
    scrollPane.getStyleClass().add("board-root");

    this.setCenter(scrollPane);

    configureRollButton();
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

  public void configureRollButton() {
    rollDiceButton.setOnAction(e -> {
      if (onRoll != null) {
        onRoll.run();
      }
    });
  }

  public void setRollCallback(Runnable onRoll) {
    this.onRoll = onRoll;
  }

  public void setDiceResultCallback(Consumer<int[]> onDiceRolled) {
    this.onDiceRolled = onDiceRolled;
  }

  public void showDiceRoll(List<Integer> values) {
    List<Integer> dots = new ArrayList<>();
    for (int v : values) dots.add(v);
    dieContainer.setDotsAllDice(dots);
  }

  public void drawBoard(Board boardModel) {
    for (Tile tile : boardModel.getTiles()) {
      board.getTile(tile.getTileId());
    }

    Platform.runLater(() -> {
      board.drawLadders(boardModel.getLadders());
    });
  }
}
