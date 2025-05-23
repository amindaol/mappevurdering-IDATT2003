package edu.ntnu.idi.idatt.controller;

import edu.ntnu.idi.idatt.model.engine.GameEngine;
import edu.ntnu.idi.idatt.model.game.BoardGame;
import edu.ntnu.idi.idatt.model.game.Player;
import edu.ntnu.idi.idatt.model.game.Tile;
import edu.ntnu.idi.idatt.observer.BoardGameEvent;
import edu.ntnu.idi.idatt.observer.BoardGameObserver;
import edu.ntnu.idi.idatt.util.exceptionHandling.GameNotInitializedException;
import edu.ntnu.idi.idatt.view.components.PlayerIcon;
import edu.ntnu.idi.idatt.view.layouts.BoardView;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

/**
 * Controls the board view during gameplay. Connects the {@link GameEngine} with the
 * {@link BoardView}, listens for game events, updates player positions, and handles dice roll
 * actions. Also shows the winner when the game ends. This controller observes the game and reacts
 * to state changes via the {@link BoardGameObserver} interface. It does not contain game logic,
 * only GUI updates and event binding.
 *
 * @author Aminda Lunde
 * @author Ingrid Opheim
 * @version 1.0
 */
public class BoardController implements BoardGameObserver {

  private final GameEngine engine;
  private final BoardView boardView;
  private final Map<Player, PlayerIcon> playerIcons = new HashMap<>();
  private final Logger logger = Logger.getLogger(BoardController.class.getName());
  private boolean winnerShown = false;

  /**
   * Creates a new BoardController, connects it to the view and game engine, sets up the roll button
   * and initializes player icons.
   *
   * @param controller the game controller handling turns
   * @param boardView  the visual representation of the board
   * @throws GameNotInitializedException if the game engine is not properly initialized
   */
  public BoardController(GameController controller, BoardView boardView) {
    if (controller.getEngine() == null) {
      throw new GameNotInitializedException("Game engine not initialized.");
    }
    this.engine = controller.getEngine();
    this.boardView = boardView;

    boardView.drawBoard(engine.getBoard());
    engine.addObserver(this);

    setUpRollButton(controller);
    initializePlayers();
  }

  /**
   * Sets up the dice roll button to trigger a game turn and update the view.
   *
   * @param controller the game controller to play turns
   */
  private void setUpRollButton(GameController controller) {
    boardView.setRollCallback(() -> {
      Player mover = controller.getCurrentPlayer();
      controller.playTurn();

      List<Integer> rollResults = controller.getLastRoll();
      logger.info(mover.getName()
          + " rolled "
          + rollResults
          + " landed on "
          + mover.getCurrentTile().getTileId());

      boardView.showDiceRoll(rollResults);
      updatePlayerPositions();

      boardView.updateCurrentPlayerList(engine.getCurrentPlayer());
    });
  }


  /**
   * Initializes player icons and places them on their starting tiles. Replaces existing icons if
   * reinitialized.
   */
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

  /**
   * Reacts to changes in the game state.
   *
   * @param game  the game that triggered the event
   * @param event the event that occurred
   */
  @Override
  public void onGameStateChange(BoardGame game, BoardGameEvent event) {
    Platform.runLater(() -> {
      switch (event) {
        case GAME_START -> {
          winnerShown = false;
          initializePlayers();
          boardView.updateCurrentPlayerList(engine.getCurrentPlayer());
        }
        case PLAYER_MOVED -> updatePlayerPositions();
        case GAME_WON -> showWinnerAlert(engine.checkWinCondition());
        default -> {
          // Handle other events if necessary
        }
      }
    });
  }

  /**
   * Updates all player icons based on their current tile positions.
   */
  private void updatePlayerPositions() {
    for (Player player : engine.getPlayers()) {
      Tile tile = player.getCurrentTile();
      PlayerIcon icon = playerIcons.get(player);
      if (tile != null && icon != null) {
        boardView.movePlayerIcon(player.getName(), tile.getTileId());
      }
    }
  }

  /**
   * Displays an alert showing the winning player.
   *
   * @param winner the player who won the game
   */
  private void showWinnerAlert(Player winner) {
    if (winnerShown || winner == null) {
      return;
    }
    winnerShown = true;
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("Game Over");
    alert.setContentText(winner.getName() + " wins the game! 🎉");
    alert.showAndWait();
  }
}
