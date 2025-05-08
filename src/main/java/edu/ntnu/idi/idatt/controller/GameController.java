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

    view.getRoot().applyCss();
    view.getRoot().layout();

    try {
      List<Player> players = game.getPlayers();
      System.out.println("Number of players: " + players.size());

      for (Player p : players) {
        System.out.println("Player: " + p.getName());

        Image image = new Image(
            Objects.requireNonNull(getClass().getResourceAsStream("/icons/players/pawn.png")));
        PlayerIcon icon = new PlayerIcon("Player", image);
        playerIconMap.put(p, icon);
      }

      game.addObserver(this);
      System.out.println("Observer added to game");

      Runnable init = () -> {
        System.out.println("Initializing game...");
        for (Player p : game.getPlayers()) {
          PlayerIcon icon = playerIconMap.get(p);
          if (p.getCurrentTile() == null) {
            System.out.println("Player " + p.getName() + " has no current tile");
            continue;
          }
          int startTileId = p.getCurrentTile().getTileId();
          System.out.println("Placing player " + p.getName() + " on tile " + startTileId);
          view.placePlayerIcon(p.getName(), icon, startTileId);
        }
        view.getRoot().requestLayout();
      };
      Platform.runLater(init);
    } catch (Exception e) {
      System.out.println("Error initializing game: " + e.getMessage());
      e.printStackTrace();
    }
  }

  /**
   * Called by the UI when the user clicks the "Roll Dice" button. Executes a single turn: rolls
   * dice, moves current player, and fires events.
   */
  public void onRollDice() {
    System.out.println("Roll Dice button clicked");
    try {
      game.playOneTurn();

      Platform.runLater(() -> {
        view.getRoot().requestLayout();
        System.out.println("Layout refresh after dice roll");
      });
    } catch (IllegalStateException e) {
      System.out.println("Error during game turn: " + e.getMessage());
      e.printStackTrace();
    }
  }

  /**
   * Receives notifications from the BoardGame. Updates UI accordingly. Ensures UI updates run on
   * the JavaFX Application Thread.
   */
  @Override
  public void onGameStateChange(BoardGame game, BoardGameEvent event) {
    System.out.println("Game state changed: " + event);
    Platform.runLater(() -> {
      try {
        switch (event) {
          case GAME_START:
            for (Player p : game.getPlayers()) {
              PlayerIcon icon = playerIconMap.get(p);
              if (p.getCurrentTile() == null) {
                System.out.println("Player " + p.getName() + " has no current tile at game start");
                continue;
              }
              int startTileId = p.getCurrentTile().getTileId();
              System.out.println("Placing player " + p.getName() + " on tile " + startTileId);
              view.placePlayerIcon(p.getName(), icon, startTileId);
            }
          case DICE_ROLLED:
            try {
              int diceAmount = game.getDice().getDiceAmount();
              int[] diceValues = new int[diceAmount];
              for (int i = 0; i < diceAmount; i++) {
                diceValues[i] = game.getDice().getDie(i).getValue();
                System.out.println("Dice " + i + " rolled: " + diceValues[i]);
              }
            } catch (IllegalStateException e) {
              System.out.println("Error getting dice values: " + e.getMessage());
              e.printStackTrace();
            }
            break;

          case PLAYER_MOVED:
            Player current = game.getCurrentPlayer();
            if (current == null) {
              System.out.println("Current player is null");
              return;
            }

            if (current.getCurrentTile() == null) {
              System.out.println("Current player " + current.getName() + " has no current tile");
              return;
            }

            int currentTileId = current.getCurrentTile().getTileId();
            System.out.println("Moving player " + current.getName() + " to tile " + currentTileId);

            PlayerIcon icon = playerIconMap.get(current);
            if (icon == null) {
              System.out.println("Player icon not found for " + current.getName());
              return;
            }

            view.movePlayerIcon(current.getName(), currentTileId);
            view.getRoot().requestLayout();
            break;
          case GAME_WON:
            break;
          case GAME_ENDED:
            break;
          default:
        }

        view.getRoot().requestLayout();
      } catch (Exception e) {
        System.out.println("Error handling game state change: " + e.getMessage());
        e.printStackTrace();
      }
    });
  }

}
