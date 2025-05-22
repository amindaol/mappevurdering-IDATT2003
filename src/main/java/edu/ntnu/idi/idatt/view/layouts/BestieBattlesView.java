package edu.ntnu.idi.idatt.view.layouts;

import edu.ntnu.idi.idatt.model.game.BestiePlayer;
import edu.ntnu.idi.idatt.model.game.Board;
import edu.ntnu.idi.idatt.model.game.BoardGame;
import edu.ntnu.idi.idatt.model.game.Player;
import edu.ntnu.idi.idatt.view.components.BestieBoard;
import edu.ntnu.idi.idatt.view.components.BestieSidePanel;
import edu.ntnu.idi.idatt.view.components.PlayerIcon;
import java.util.HashMap;
import java.util.Map;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class BestieBattlesView extends BorderPane {

  private final BestieBoard board;
  private final Map<String, PlayerIcon> playerIcons = new HashMap<>();
  private final BestieSidePanel sidePanel;
  private Runnable onRoll;

  public BestieBattlesView(BoardGame game) {
    this.getStyleClass().add("board-root");
    Board boardModel = game.getBoard();
    this.board = new BestieBoard(boardModel.getRows(), boardModel.getCols());
    this.sidePanel = new BestieSidePanel(game);

    sidePanel.setRollCallback(() -> {
      if (onRoll != null) {
        onRoll.run();
      }
    });

    HBox contentBox = new HBox(board.getBoardWithOverlay(), sidePanel);
    contentBox.getStyleClass().add("board-background");
    contentBox.setSpacing(20);
    contentBox.setAlignment(Pos.CENTER);

    ScrollPane scrollPane = new ScrollPane(contentBox);
    scrollPane.setFitToWidth(true);
    scrollPane.setFitToHeight(true);
    scrollPane.setPannable(true);
    scrollPane.getStyleClass().add("board-background");

    this.setCenter(scrollPane);
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

  public void setRollCallback(Runnable onRoll) {
    this.onRoll = onRoll;
  }

  public void updateCurrentPlayer(BestiePlayer player) {
    sidePanel.highlightPlayer(player);
  }

  public BestieBoard getBoard() {
    return board;
  }
}