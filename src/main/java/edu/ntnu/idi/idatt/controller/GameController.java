package edu.ntnu.idi.idatt.controller;

import edu.ntnu.idi.idatt.model.game.BoardGame;
import edu.ntnu.idi.idatt.model.game.Player;
import edu.ntnu.idi.idatt.model.observer.BoardGameEvent;
import edu.ntnu.idi.idatt.model.observer.BoardGameObserver;
import edu.ntnu.idi.idatt.view.layouts.BoardView;
import javafx.application.Platform;
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
  private final Map<Player, Circle> tokenMap = new HashMap<>();

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
      Circle token = new Circle(20);
      token.getStyleClass().add("player-token");
      tokenMap.put(p, token);
      // view.placeToken(1, token);
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
          break;
        case DICE_ROLLED:
          break;
        case PLAYER_MOVED:
          Player current = game.getCurrentPlayer();
          int tileId = current.getCurrentTile().getTileId();
          Circle token = tokenMap.get(current);
          // view.moveToken(token, tileId);
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
