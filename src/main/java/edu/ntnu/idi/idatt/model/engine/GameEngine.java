package edu.ntnu.idi.idatt.model.engine;

import edu.ntnu.idi.idatt.model.game.Board;
import edu.ntnu.idi.idatt.model.game.Player;
import java.util.List;


/**
 * Abstract base class for running game logic in different game modes.
 */
public abstract class GameEngine {

  protected Board board;
  protected List<Player> players;
  protected int currentPlayerIndex;
  protected boolean gameOver;

  public GameEngine(Board board, List<Player> players) {
    if (board == null || players == null || players.isEmpty()) {
      throw new IllegalArgumentException("Board and player list must not be null or empty.");
    }
    this.board = board;
    this.players = players;
    this.currentPlayerIndex = 0;
    this.gameOver = false;
  }

  /**
   * Starts and runs the game loop until someone wins.
   */
  public abstract void playGame();

  /**
   * Executes a single turn for the current player.
   */
  public abstract void handleTurn();

  /**
   * Checks if a win condition has been met.
   *
   * @return the winning player, or null if none yet
   */
  public abstract Player checkWinCondition();

  /**
   * Ends the game.
   */
  public void endGame() {
    this.gameOver = true;
  }

  public Player getCurrentPlayer() {
    return players.get(currentPlayerIndex);
  }

  public Player getLastPlayer() {
    return (currentPlayerIndex == 0) ? players.get(players.size() - 1)
        : players.get(currentPlayerIndex - 1);

  }

  /**
   * Advances to the next player in the turn cycle.
   */
  public void nextPlayer() {
    currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
  }

  public Board getBoard() {
    return board;
  }

  public List<Player> getPlayers() {
    return players;
  }

  public boolean isGameOver() {
    return gameOver;
  }
}
