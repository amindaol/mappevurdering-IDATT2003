package edu.ntnu.idi.idatt.controller;


import edu.ntnu.idi.idatt.model.engine.BestiePointBattlesEngine;
import edu.ntnu.idi.idatt.model.game.BestiePlayer;
import edu.ntnu.idi.idatt.model.game.BoardGame;
import edu.ntnu.idi.idatt.model.game.Player;
import edu.ntnu.idi.idatt.model.game.Tile;
import edu.ntnu.idi.idatt.observer.BoardGameEvent;
import edu.ntnu.idi.idatt.observer.BoardGameObserver;
import edu.ntnu.idi.idatt.view.components.PlayerIcon;
import edu.ntnu.idi.idatt.view.layouts.BestieBattlesView;
import java.util.List;
import java.util.Objects;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;

/**
 * Controller for the Bestie PointBattles game mode. Connects the {@link BestiePointBattlesEngine}
 * to the {@link BestieBattlesView}, sets up player icons, handles rolling, and reacts to game
 * events. This controller observes the game and updates the GUI accordingly. It handles visual
 * placement of players and displays the winner when the game ends.
 *
 * @author Aminda Lunde
 * @author Ingrid Opheim
 * @version 1.0
 */
public class BestieBattlesController implements BoardGameObserver {

  private final BestiePointBattlesEngine engine;
  private final BestieBattlesView view;

  /**
   * Creates a new BestieBattlesController, connects it to the view and game engine, sets up the
   * roll button and initializes player icons.
   *
   * @param engine the game engine handling turns
   * @param view   the visual representation of the game
   */
  public BestieBattlesController(BestiePointBattlesEngine engine, BestieBattlesView view) {
    this.engine = engine;
    this.view = view;

    engine.addObserver(this);

    setUpRollButton();
  }

  /**
   * Handles game state changes and updates the view accordingly.
   *
   * @param game  the BoardGame instance that changed
   * @param event the event that occurred
   */
  @Override
  public void onGameStateChange(BoardGame game, BoardGameEvent event) {
    switch (event) {
      case GAME_START -> {
        initializePlayers();
        view.updateSidePanel(engine.getPlayers());
        view.updateCurrentPlayerList(engine.getCurrentPlayer());
      }

      case PLAYER_MOVED -> {
        view.updateCurrentPlayerList(engine.getCurrentPlayer());
      }
      case GAME_WON -> {
        BestiePlayer winner = (BestiePlayer) engine.checkWinCondition();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game Over");
        alert.setContentText(winner.getName() + " wins the game! 🎉");
        alert.showAndWait();
      }
      default -> {
        // Handle other events if needed
      }
    }
  }

  /**
   * Initializes player icons and places them on the board. Updates the side panel with player
   * information.
   */
  private void initializePlayers() {
    for (Player player : engine.getPlayers()) {
      if (player instanceof BestiePlayer bp) {
        Tile startTile = bp.getCurrentTile();
        String iconPath = "/icons/players/" + bp.getToken().getIconFileName();
        Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream(iconPath)));

        PlayerIcon playerIcon = new PlayerIcon(bp.getName(), icon);
        playerIcon.setFitHeight(30);
        playerIcon.setFitWidth(30);

        view.placePlayerIcon(bp.getName(), playerIcon, startTile.getTileId());
      }
    }

    view.updateCurrentPlayerList(engine.getCurrentPlayer());
    view.updateSidePanel(engine.getPlayers());
  }

  /**
   * Sets up the roll button to trigger a dice roll and move the current player. Updates the view
   * with the rolled values and player positions.
   */
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
}
