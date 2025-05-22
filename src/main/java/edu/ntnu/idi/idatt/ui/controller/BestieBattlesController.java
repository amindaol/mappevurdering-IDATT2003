package edu.ntnu.idi.idatt.ui.controller;

import static edu.ntnu.idi.idatt.observer.BoardGameEvent.GAME_WON;

import edu.ntnu.idi.idatt.model.engine.BestiePointBattlesEngine;
import edu.ntnu.idi.idatt.model.game.BoardGame;
import edu.ntnu.idi.idatt.model.game.Player;
import edu.ntnu.idi.idatt.model.game.Tile;
import edu.ntnu.idi.idatt.observer.BoardGameEvent;
import edu.ntnu.idi.idatt.observer.BoardGameObserver;
import edu.ntnu.idi.idatt.ui.view.components.PlayerIcon;
import edu.ntnu.idi.idatt.ui.view.layouts.BestieBattlesView;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import javafx.scene.layout.Pane;

public class BestieBattlesController implements BoardGameObserver {

  private final BestiePointBattlesEngine engine;
  private final BestieBattlesView view;
  private final Map<Player, PlayerIcon> playerIcons = new HashMap<>();

  public BestieBattlesController(BestiePointBattlesEngine engine, BestieBattlesView view) {
    this.engine = engine;
    this.view = view;

    engine.addObserver(this);

    view.setupBoard();
    setupPlayers();
    setupRolling();
  }

  private void setupPlayers() {
    for (Player player : engine.getPlayers()) {
      String iconPath = "/icons/players/" + player.getToken().getIconFileName();
      Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(iconPath)));
      PlayerIcon icon = new PlayerIcon(player.getName(), image);

      if (playerIcons.containsKey(player)) {
        PlayerIcon oldIcon = playerIcons.get(player);
        if (oldIcon.getParent() instanceof Pane parent) {
          parent.getChildren().remove(oldIcon);
    }
  }
      playerIcons.put(player, icon);
      Tile tile = player.getCurrentTile();
      if (tile != null) {
        view.placePlayerIcon(player.getName(), icon, tile.getTileId());
      }
    }
  }


  private void setupRolling() {
    view.setRollCallback(() -> {
      engine.playOneRound();
      updatePlayerPositions();
      view.updatePlayerInfo();

      if (engine.isFinished()) {
        showWinnerAlert(engine.checkWinCondition());
      }
    });
  }

  private void updatePlayerPositions() {
    for (Player player : engine.getPlayers()) {
      Tile tile = player.getCurrentTile();
      PlayerIcon icon = playerIcons.get(player);
      if (tile != null && icon != null) {
        view.movePlayerIcon(player.getName(), icon, tile.getTileId());
      }
    }
  }

  private void showWinnerAlert(Player winner) {
    if (winner == null) return;

    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("Game Over");
    alert.setHeaderText(null);
    alert.setContentText(winner.getName() + " wins the game with "
        + winner.getPoints() + " points! 🎉");
    alert.showAndWait();
  }

  @Override
  public void onGameStateChange(BoardGame game, BoardGameEvent event) {
    javafx.application.Platform.runLater(() -> {
      switch (event) {
        case GAME_START -> setupPlayers();
        case PLAYER_MOVED -> updatePlayerPositions();
        case GAME_WON -> showWinnerAlert(engine.checkWinCondition());
        default -> {

        }
      }
    });
  }


}
