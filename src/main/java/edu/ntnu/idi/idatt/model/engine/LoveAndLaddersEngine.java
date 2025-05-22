package edu.ntnu.idi.idatt.model.engine;

import edu.ntnu.idi.idatt.core.LinearMovement;
import edu.ntnu.idi.idatt.core.Movement;
import edu.ntnu.idi.idatt.model.game.Board;
import edu.ntnu.idi.idatt.model.game.BoardGame;
import edu.ntnu.idi.idatt.model.game.Dice;
import edu.ntnu.idi.idatt.model.game.Player;
import edu.ntnu.idi.idatt.model.game.Tile;
import edu.ntnu.idi.idatt.observer.BoardGameEvent;
import edu.ntnu.idi.idatt.observer.BoardGameObserver;
import edu.ntnu.idi.idatt.util.exceptionHandling.NoPlayersException;
import edu.ntnu.idi.idatt.util.exceptionHandling.GameNotInitializedException;

import java.util.List;

/**
 * Game engine for "Love and Ladders": - Game ends when a player reaches last tile - Winner is the
 * one who reaches the last tile first
 */
public class LoveAndLaddersEngine extends GameEngine {

  private final Dice dice;
  private final Movement movement = new LinearMovement();


  /**
   * Constructs a new LoveAndLaddersEngine with the specified game and dice.
   *
   * @param game the game to be played
   * @param dice the dice to be used in the game
   * @throws NullPointerException if {@code game} or {@code dice} is {@code null}.
   */
  public LoveAndLaddersEngine(BoardGame game, Dice dice) {
    super(game);
    if (dice == null) {
      throw new NullPointerException("dice cannot be null");
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
    if (board == null || dice == null) {
      throw new GameNotInitializedException();
    }
    if (players == null || players.isEmpty()) {
      throw new NoPlayersException();
    }

    while (!gameOver) {
      handleTurn();
    }
    notifyObservers(BoardGameEvent.GAME_WON);
  }

  /**
   * Handles the turn for the current player. This method rolls the dice, moves the player, checks
   * for win conditions, and notifies observers of the game state changes.
   */
  @Override
  public void handleTurn() {
    Player player = getCurrentPlayer();
    int roll = dice.roll().stream().mapToInt(Integer::intValue).sum();

    if (player.isSkipNextTurn()) {
      player.setSkipNextTurn(false);
      nextPlayer();
      return;
    }

    notifyObservers(BoardGameEvent.DICE_ROLLED);

    movement.move(player, roll);
    notifyObservers(BoardGameEvent.PLAYER_MOVED);

    if (checkWinCondition() != null) {
      notifyObservers(BoardGameEvent.GAME_WON);
      notifyObservers(BoardGameEvent.GAME_ENDED);
      endGame();
    } else {
      nextPlayer();
    }
  }

  /**
   * Checks if a win condition has been met. In this game, the win condition is that a player
   * reaches the last tile on the board.
   *
   * @return the winning player, or null if none yet
   */
  @Override
  public Player checkWinCondition() {
    Tile last = board.getLastTile();
    return players.stream()
        .filter(p -> p.getCurrentTile() == last)
        .findFirst()
        .orElse(null);
  }


  @Override
  public void startGame() {
    Tile startTile = board.getStartTile();
    for (Player player : players) {
      player.placeOnTile(startTile);
    }
    notifyObservers(BoardGameEvent.GAME_START);
  }
}
