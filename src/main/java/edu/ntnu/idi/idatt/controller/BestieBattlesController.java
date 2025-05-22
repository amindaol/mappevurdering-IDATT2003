package edu.ntnu.idi.idatt.controller;

import edu.ntnu.idi.idatt.model.engine.BestiePointBattlesEngine;
import edu.ntnu.idi.idatt.model.game.BoardGame;
import edu.ntnu.idi.idatt.model.game.Player;
import edu.ntnu.idi.idatt.model.game.Tile;
import edu.ntnu.idi.idatt.observer.BoardGameEvent;
import edu.ntnu.idi.idatt.observer.BoardGameObserver;
import edu.ntnu.idi.idatt.view.components.PlayerIcon;
import edu.ntnu.idi.idatt.view.layouts.BestieBattlesView;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import javafx.scene.layout.Pane;

/**
 * Controller for the Bestie PointBattles game mode.
 * Connects the {@link BestiePointBattlesEngine} to the {@link BestieBattlesView},
 * sets up player icons, handles rolling, and reacts to game events.
 * <p>
 * This controller observes the game and updates the GUI accordingly.
 * It handles visual placement of players and displays the winner when the game ends.
 * </p>
 *
 * @author Aminda Lunde
 * @author Ingrid Opheim
 * @version 1.0
 */
public class BestieBattlesController implements BoardGameObserver {

  private final BestiePointBattlesEngine engine;
  private final BestieBattlesView view;
  private final Map<Player, PlayerIcon> playerIcons = new HashMap<>();

  /**
   * Creates a new controller for the Bestie PointBattles game mode.
   *
   * @param engine the game engine for Bestie PointBattles
   * @param view the visual representation of the board
   */
  public BestieBattlesController(BestiePointBattlesEngine engine, BestieBattlesView view) {
    this.engine = engine;
    this.view = view;

    engine.addObserver(this);

    view.setupBoard();
    setupPlayers();
    setupRolling();
  }

  /**
   * Sets up the initial placement of player icons on the board.
   */
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

  /**
   * Sets up the dice roll callback to play a round, update the board,
   * and show the winner if the game is finished.
   */
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

  /**
   * Updates the position of all player icons based on their current tiles.
   */
  private void updatePlayerPositions() {
    for (Player player : engine.getPlayers()) {
      Tile tile = player.getCurrentTile();
      PlayerIcon icon = playerIcons.get(player);
      if (tile != null && icon != null) {
        view.movePlayerIcon(player.getName(), icon, tile.getTileId());
      }
    }
  }

  /**
   * Displays a dialog showing the winner of the game and their score.
   *
   * @param winner the winning player
   */
  private void showWinnerAlert(Player winner) {
    if (winner == null) return;

    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("Game Over");
    alert.setHeaderText(null);
    alert.setContentText(winner.getName() + " wins the game with "
        + winner.getPoints() + " points! 🎉");
    alert.showAndWait();
  }

  /**
   * Reacts to game events by updating the GUI accordingly.
   *
   * @param game the game that triggered the event
   * @param event the event that occurred
   */
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
