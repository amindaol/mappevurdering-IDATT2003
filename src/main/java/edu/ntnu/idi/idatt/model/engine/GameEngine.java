package edu.ntnu.idi.idatt.model.engine;

import edu.ntnu.idi.idatt.model.game.Board;
import edu.ntnu.idi.idatt.model.game.BoardGame;
import edu.ntnu.idi.idatt.model.game.Player;
import edu.ntnu.idi.idatt.observer.BoardGameEvent;
import edu.ntnu.idi.idatt.observer.BoardGameObserver;
import java.util.ArrayList;
import java.util.List;


/**
 * Abstract base class for running game logic in different game modes.
 */
public abstract class GameEngine {

  protected Board board;
  protected final BoardGame game;
  protected List<Player> players;
  protected int currentPlayerIndex;
  protected boolean gameOver;

  private final List<BoardGameObserver> observers = new ArrayList<>();


  public GameEngine(BoardGame game) {
    if (game == null || game.getPlayers() == null || game.getBoard() == null) {
      throw new IllegalArgumentException("Game, players and board must not be null.");
    }
    this.game = game;
    this.board = game.getBoard();
    this.players = game.getPlayers();
    this.currentPlayerIndex = 0;
    this.gameOver = false;
  }

  public void addObserver(BoardGameObserver observer) {
    observers.add(observer);
  }

  public void notifyObservers(BoardGameEvent event) {
    for (BoardGameObserver observer : observers) {
      observer.onGameStateChange(getGame(), event);
    }
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
    notifyObservers(BoardGameEvent.GAME_WON);
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


  public BoardGame getGame() {
    return game;
  }
}
