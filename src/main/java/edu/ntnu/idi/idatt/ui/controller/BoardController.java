package edu.ntnu.idi.idatt.ui.controller;

import edu.ntnu.idi.idatt.model.engine.GameEngine;
import edu.ntnu.idi.idatt.model.game.BoardGame;
import edu.ntnu.idi.idatt.model.game.Player;
import edu.ntnu.idi.idatt.model.game.Tile;
import edu.ntnu.idi.idatt.observer.BoardGameEvent;
import edu.ntnu.idi.idatt.observer.BoardGameObserver;
import edu.ntnu.idi.idatt.ui.view.components.PlayerIcon;
import edu.ntnu.idi.idatt.ui.view.layouts.BoardView;
import java.util.List;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import javafx.scene.layout.Pane;

public class BoardController implements BoardGameObserver {

  private final GameEngine engine;
  private final BoardView boardView;
  private final Map<Player, PlayerIcon> playerIcons = new HashMap<>();
  private final Logger logger = Logger.getLogger(BoardController.class.getName());

  public BoardController(GameController controller, BoardView boardView) {
    this.engine = controller.getEngine();
    this.boardView = boardView;

    boardView.drawBoard(engine.getBoard());
    engine.addObserver(this);

    setUpRollButton(controller);
    initializePlayers();
  }

  private void setUpRollButton(GameController controller) {
    boardView.setRollCallback(() -> {
      Player mover = controller.getCurrentPlayer();
      controller.playTurn();
      Player moved = mover;

      List<Integer> rollResults = controller.getLastRoll();
      logger.info(moved.getName()
          + " rolled "
          + rollResults
          + " landed on "
          + moved.getCurrentTile().getTileId());

      boardView.showDiceRoll(rollResults);
      updatePlayerPositions();

      if (controller.isGameOver()) {
        Player winner = controller.getWinner();
        showWinnerAlert(winner);
      }
    });
  }

  private void initializePlayers() {
    for (Player player : engine.getPlayers()) {
      String iconPath = "/icons/players/" + player.getToken().getIconFileName();
      Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(iconPath)));
      PlayerIcon icon = new PlayerIcon(player.getName(), image);

      if (playerIcons.containsKey(player)) {
        PlayerIcon oldIcon = playerIcons.get(player);
        if (oldIcon.getParent() instanceof Pane oldTile) {
          oldTile.getChildren().remove(oldIcon);
        }
      }

      playerIcons.put(player, icon);

      Tile tile = player.getCurrentTile();
      if (tile != null) {
        boardView.placePlayerIcon(player.getName(), icon, tile.getTileId());
      }
    }
  }

  @Override
  public void onGameStateChange(BoardGame game, BoardGameEvent event) {
    Platform.runLater(() -> {
      switch (event) {
        case GAME_START -> initializePlayers();
        case PLAYER_MOVED -> updatePlayerPositions();
        case GAME_WON -> showWinnerAlert(engine.checkWinCondition());
        default -> {
        }
      }
    });
  }

  private void updatePlayerPositions() {
    for (Player player : engine.getPlayers()) {
      Tile tile = player.getCurrentTile();
      PlayerIcon icon = playerIcons.get(player);
      if (tile != null && icon != null) {
        boardView.movePlayerIcon(player.getName(), tile.getTileId());
      }
    }
  }

  private void showWinnerAlert(Player winner) {
    if (winner == null) {
      return;
    }
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("Game Over");
    alert.setContentText(winner.getName() + " wins the game! 🎉");
    alert.showAndWait();
  }
}
