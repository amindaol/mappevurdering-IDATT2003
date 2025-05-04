package edu.ntnu.idi.idatt.controller;

import edu.ntnu.idi.idatt.model.game.BoardGame;
import edu.ntnu.idi.idatt.model.game.Player;
import edu.ntnu.idi.idatt.model.observer.BoardGameEvent;
import edu.ntnu.idi.idatt.model.observer.BoardGameObserver;
import edu.ntnu.idi.idatt.view.components.PlayerIcon;
import edu.ntnu.idi.idatt.view.layouts.BoardView;
import java.util.Objects;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.shape.Circle;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller that binds the BoardGame model to the BoardView UI. Handles user input (dice roll) and
 * updates player tokens on the board.
 */
public class GameController implements BoardGameObserver {

  private final BoardGame game;
  private final BoardView view;
  private final Map<Player, PlayerIcon> playerIconMap = new HashMap<>();

  /**
   * Constructs a GameController, creates tokens for each player, and registers as observer.
   *
   * @param game the BoardGame model (must be initialized with board, dice, and players)
   * @param view the BoardView UI component
   */
  public GameController(BoardGame game, BoardView view) {
    this.game = game;
    this.view = view;

    List<Player> players = game.getPlayers();
    for (Player p : players) {
      Image image = new Image(
          Objects.requireNonNull(getClass().getResourceAsStream("/icons/players/pawn.png")));
      PlayerIcon icon = new PlayerIcon(p.getName(), image);
      playerIconMap.put(p, icon);
    }
    game.addObserver(this);
  }

  /**
   * Called by the UI when the user clicks the "Roll Dice" button. Executes a single turn: rolls
   * dice, moves current player, and fires events.
   */
  public void onRollDice() {
    game.playOneTurn();
  }

  /**
   * Receives notifications from the BoardGame. Updates UI accordingly. Ensures UI updates run on
   * the JavaFX Application Thread.
   */
  @Override
  public void onGameStateChange(BoardGame game, BoardGameEvent event) {
    Platform.runLater(() -> {
      switch (event) {
        case GAME_START:
          for (Player p : game.getPlayers()) {
            PlayerIcon icon = playerIconMap.get(p);
            int startTileId = p.getCurrentTile().getTileId();
            view.placePlayerIcon(p.getName(), icon, startTileId);
          }
        case DICE_ROLLED:
          break;
        case PLAYER_MOVED:
          Player current = game.getCurrentPlayer();
          int tileId = current.getCurrentTile().getTileId();
          PlayerIcon playerIcon = playerIconMap.get(current);
          view.movePlayerIcon(current.getName(), tileId);
          break;
        case GAME_WON:
          break;
        case GAME_ENDED:
          break;
        default:
      }
    });
  }

}
