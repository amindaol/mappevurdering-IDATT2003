package edu.ntnu.idi.idatt.controller;

import static edu.ntnu.idi.idatt.observer.BoardGameEvent.GAME_WON;
import static edu.ntnu.idi.idatt.observer.BoardGameEvent.PLAYER_MOVED;

import edu.ntnu.idi.idatt.model.engine.BestiePointBattlesEngine;
import edu.ntnu.idi.idatt.model.engine.GameEngine;
import edu.ntnu.idi.idatt.model.game.BestiePlayer;
import edu.ntnu.idi.idatt.model.game.BoardGame;
import edu.ntnu.idi.idatt.model.game.Player;
import edu.ntnu.idi.idatt.model.game.Tile;
import edu.ntnu.idi.idatt.observer.BoardGameEvent;
import edu.ntnu.idi.idatt.observer.BoardGameObserver;
import edu.ntnu.idi.idatt.util.exceptionHandling.InvalidMoveException;
import edu.ntnu.idi.idatt.util.exceptionHandling.InvalidPlayerException;
import edu.ntnu.idi.idatt.view.components.PlayerIcon;
import edu.ntnu.idi.idatt.view.layouts.BestieBattlesView;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import java.util.HashMap;
import java.util.Objects;
import javafx.scene.layout.Pane;

/**
 * Controller for the Bestie PointBattles game mode.
 * Connects the {@link BestiePointBattlesEngine} to the {@link BestieBattlesView},
 * sets up player icons, handles rolling, and reacts to game events.
 * This controller observes the game and updates the GUI accordingly.
 * It handles visual placement of players and displays the winner when the game ends.
 *
 * @author Aminda Lunde
 * @author Ingrid Opheim
 * @version 1.0
 */
public class BestieBattlesController implements BoardGameObserver {

  private final BestiePointBattlesEngine engine;
  private final BestieBattlesView view;
  private final Map<Player, PlayerIcon> playerIcons = new HashMap<>();
  private final Logger logger = Logger.getLogger(BestieBattlesController.class.getName());

  public BestieBattlesController(BestiePointBattlesEngine engine, BestieBattlesView view) {
    this.engine = engine;
    this.view = view;

    engine.addObserver(this);

    setUpRollButton();
  }

  @Override
  public void onGameStateChange(BoardGame game, BoardGameEvent event) {
    switch (event) {
      case GAME_START -> {
        initializePlayers();
        view.updateSidePanel(engine.getPlayers());
        view.updateCurrentPlayerList(engine.getCurrentPlayer());
      }
      case DICE_ROLLED -> {
        //view.showDiceRoll();
      }

      case PLAYER_MOVED -> {
        view.updateCurrentPlayerList(engine.getCurrentPlayer());
      }
      // Handle player moved event;
      case GAME_WON -> {
        BestiePlayer winner = (BestiePlayer) engine.checkWinCondition();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game Over");
        alert.setContentText(winner.getName() + " wins the game! 🎉");
        alert.showAndWait();
      }
      default -> {
      }
    }
  }

  private void initializePlayers() {
    for (Player player : engine.getPlayers()) {
      if (player instanceof BestiePlayer bp) {
        Tile startTile = bp.getCurrentTile();
        String iconPath = "/icons/players/" + bp.getToken().getIconFileName();
        Image icon = new Image(getClass().getResourceAsStream(iconPath));

        PlayerIcon playerIcon = new PlayerIcon(bp.getName(), icon);
        playerIcon.setFitHeight(30);
        playerIcon.setFitWidth(30);

        view.placePlayerIcon(bp.getName(), playerIcon, startTile.getTileId());
      }
    }

    view.updateCurrentPlayerList(engine.getCurrentPlayer());
    view.updateSidePanel(engine.getPlayers());
  }

  private void setUpRollButton() {
    view.setRollCallBack(() -> {
      BestiePlayer mover = (BestiePlayer) engine.getCurrentPlayer();

      List<Integer> roll = engine.getDice().roll();

      int total = roll.stream().mapToInt(Integer::intValue).sum();

      System.out.println(mover.getName() + " rolled " + roll + " landed on "
          + mover.getCurrentTile().getTileId());

      view.showDiceRoll(roll);
      engine.handleTurn(total);
      view.movePlayerIcon(
          mover.getName(),
          mover.getCurrentTile().getTileId());

      view.updateSidePanel(engine.getPlayers());
      view.updateCurrentPlayerList(engine.getCurrentPlayer());

    });
  }

  /**
   * Displays an error alert with the specified title and message.
   *
   * @param title the title of the alert
   * @param message the error message
   */
  private void showErrorAlert(String title, String message) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
  }
}
