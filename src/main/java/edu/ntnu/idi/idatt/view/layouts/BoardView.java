package edu.ntnu.idi.idatt.view.layouts;

import edu.ntnu.idi.idatt.view.components.DieContainer;
import edu.ntnu.idi.idatt.view.components.LaddersBoard;
import edu.ntnu.idi.idatt.view.components.PlayerIcon;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.layout.VBox;

public class BoardView extends BorderPane {

  private final LaddersBoard board;
  private final Map<String, PlayerIcon> playerIcons = new HashMap<>();
  private final Button rollDiceButton = new Button("Roll Dice");

  public BoardView(int rows, int cols, int diceAmount) {
    this.board = new LaddersBoard(rows, cols);

    DieContainer dieContainer = new DieContainer(diceAmount);
    VBox diceBox = new VBox(dieContainer, rollDiceButton);
    diceBox.setAlignment(Pos.CENTER);
    diceBox.setSpacing(10);
    HBox gameContent = new HBox(board.getGrid(), diceBox);
    gameContent.setSpacing(20);
    gameContent.setAlignment(Pos.CENTER);
    setCenter(gameContent);
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
    rollDiceButton.setOnAction(e -> onRollDice.run());
  }
}
