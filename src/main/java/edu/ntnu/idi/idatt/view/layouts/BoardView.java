package edu.ntnu.idi.idatt.view.layouts;

import edu.ntnu.idi.idatt.view.components.LaddersBoard;
import edu.ntnu.idi.idatt.view.components.PlayerIcon;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import java.util.HashMap;
import java.util.Map;

public class BoardView extends StackPane {

  private final LaddersBoard board;
  private final Map<String, PlayerIcon> playerIcons = new HashMap<>();
  private final Button rollDiceButton = new Button("Roll Dice");

  public BoardView(int rows, int cols) {
    this.board = new LaddersBoard(rows, cols);
    this.getChildren().addAll(board.getGrid(), rollDiceButton);

    this.setAlignment(Pos.CENTER);

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
