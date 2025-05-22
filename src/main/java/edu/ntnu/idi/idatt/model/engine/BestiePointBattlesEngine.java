package edu.ntnu.idi.idatt.model.engine;

import edu.ntnu.idi.idatt.model.core.LinearMovement;
import edu.ntnu.idi.idatt.model.core.Movement;
import edu.ntnu.idi.idatt.model.game.BoardGame;
import edu.ntnu.idi.idatt.model.game.Dice;
import edu.ntnu.idi.idatt.model.game.Player;
import edu.ntnu.idi.idatt.model.game.Tile;
import edu.ntnu.idi.idatt.observer.BoardGameEvent;
import edu.ntnu.idi.idatt.util.exceptionHandling.GameNotInitializedException;

import java.util.Comparator;
import java.util.List;


/**
 * Game engine for "Bestie Point Battles": - Game ends when a player reaches last tile - Winner is
 * the one with the highest score at that moment
 *
 * @author Aminda Lunde
 * @author Ingrid Opheim
 * @version 1.0
 */
public class BestiePointBattlesEngine extends GameEngine {

  private final Dice dice;
  private final Movement movement = new LinearMovement();

  /**
   * Constructs a new BestiePointBattlesEngine with the specified game and dice.
   *
   * @param game the game to be played
   * @param dice the dice to be used in the game
   * @throws NullPointerException if {@code game} or {@code dice} is {@code null}.
   */
  public BestiePointBattlesEngine(BoardGame game, Dice dice) {
    super(game);
    if (dice == null) {
      throw new NullPointerException("Dice cannot be null.");
    }
    this.dice = dice;
  }

  /**
   * Plays the game. This method initializes the game state, places players on the start tile,
   * notifies observers of the game start, and enters the main game loop. The game loop continues
   * until a player reaches the last tile, at which point the game ends and the winner is declared.
   *
   * @throws GameNotInitializedException if the game is not properly initialized with players or
   *                                     board.
   */
  @Override
  public void playGame() {
    if (board == null || players == null || players.isEmpty()) {
      throw new GameNotInitializedException();
    }

    Tile start = board.getStartTile();
    for (Player player : players) {
      player.placeOnTile(start);
    }

    notifyObservers(BoardGameEvent.GAME_START);

    while (!gameOver) {
      handleTurn(0);
    }
  }

  /**
   * Handles the turn for the current player. This method rolls the dice, moves the player, checks
   * for win conditions, and notifies observers of the game state changes.
   */
  public void handleTurn(int steps) {
    Player player = getCurrentPlayer();

    if (player.isSkipNextTurn()) {
      player.setSkipNextTurn(false);
      nextPlayer();
      return;
    }

    notifyObservers(BoardGameEvent.DICE_ROLLED);

    movement.move(player, steps);
    notifyObservers(BoardGameEvent.PLAYER_MOVED);

    if (checkWinCondition() != null) {
      notifyObservers(BoardGameEvent.GAME_ENDED);
      notifyObservers(BoardGameEvent.GAME_WON);
      endGame();
    } else {
      nextPlayer();
    }
  }

  /**
   * Checks the win condition for the game. In this case, it checks if the game is over and returns
   * the player with the highest score. If the game is not over, it returns null.
   *
   * @return the winning player, or null if the game is not over.
   */
  @Override
  public Player checkWinCondition() {
    if (!gameOver) {
      return null;
    }
    return players.stream()
        .max(Comparator.comparingInt(Player::getPoints))
        .orElse(null);
  }

  /**
   * Plays one round of the game by rolling the dice,
   * summing the result, and handling the player's turn.
   * Does nothing if the game is already over.
   */
  public void playOneRound() {
    if (gameOver) return;

    List<Integer> values = dice.roll();
    int steps = values.stream().mapToInt(Integer::intValue).sum();
    handleTurn(steps);
  }


  /**
   * Checks whether the game is finished.
   *
   * @return true if the game is over, false otherwise
   */
  public boolean isFinished() {
    return gameOver;
  }


}
