package edu.ntnu.idi.idatt.model.engine;

import edu.ntnu.idi.idatt.model.game.Board;
import edu.ntnu.idi.idatt.model.game.BoardGame;
import edu.ntnu.idi.idatt.model.game.Player;
import edu.ntnu.idi.idatt.observer.BoardGameEvent;
import edu.ntnu.idi.idatt.observer.BoardGameObserver;
import edu.ntnu.idi.idatt.util.exceptionHandling.GameNotInitializedException;
import edu.ntnu.idi.idatt.util.exceptionHandling.NoPlayersException;
import java.util.ArrayList;
import java.util.List;


/**
 * Abstract base class for running game logic in different game modes.
 *
 * @author Aminda Lunde
 * @author Ingrid Opheim
 * @version 1.0
 */
public abstract class GameEngine {

  protected Board board;
  protected final BoardGame game;
  protected List<Player> players;
  protected int currentPlayerIndex;
  protected boolean gameOver;

  private final List<BoardGameObserver> observers = new ArrayList<>();


  /**
   * Constructs a new GameEngine with the specified game.
   *
   * @param game the game to be played
   * @throws GameNotInitializedException if {@code game} is {@code null} or if the game has no board
   * @throws NoPlayersException if the game's player list is {@code null} or empty
   */
  public GameEngine(BoardGame game) {
    if (game == null) {
      throw new GameNotInitializedException("Game cannot be null.");
    }
    if (game.getPlayers() == null || game.getPlayers().isEmpty()) {
      throw new NoPlayersException("Game must have players.");
    }
    if (game.getBoard() == null) {
      throw new GameNotInitializedException("Game must have a board.");
    }
    this.game = game;
    this.board = game.getBoard();
    this.players = game.getPlayers();
    this.currentPlayerIndex = 0;
    this.gameOver = false;
  }

  /**
   * Adds an observer to the game. Observers are notified of game state changes.
   *
   * @param observer the observer to add
   */
  public void addObserver(BoardGameObserver observer) {
    observers.add(observer);
  }

  /**
   * Notifies all observers of a game state change.
   *
   * @param event the event representing the game state change
   */
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
  public abstract void handleTurn(int total);

  /**
   * Checks if a win condition has been met.
   *
   * @return the winning player, or null if none yet
   */
  public abstract Player checkWinCondition();

  /**
   * Ends the game. This method sets the gameOver flag to true and notifies observers of the game
   */
  public void endGame() {
    this.gameOver = true;
    notifyObservers(BoardGameEvent.GAME_WON);
  }

  /**
   * Returns the current player.
   *
   * @return the current player
   */
  public Player getCurrentPlayer() {
    return players.get(currentPlayerIndex);
  }

  /**
   * Returns the last player who took a turn.
   *
   * @return the last player who took a turn
   */
  public Player getLastPlayer() {
    return (currentPlayerIndex == 0) ? players.get(players.size() - 1)
        : players.get(currentPlayerIndex - 1);
  }

  /**
   * Returns the next player in the turn cycle.
   *
   * @return the next player
   */
  public Player getNextPlayer() {
    return players.get((currentPlayerIndex + 1) % players.size());
  }

  /**
   * Advances to the next player in the turn cycle.
   */
  public void nextPlayer() {
    currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
  }

  /**
   * Returns the board of the game.
   *
   * @return the board
   */
  public Board getBoard() {
    return board;
  }

  /**
   * Returns the list of players in the game.
   *
   * @return the list of players
   */
  public List<Player> getPlayers() {
    return players;
  }

  /**
   * Checks if the game is over.
   *
   * @return true if the game is over, false otherwise
   */
  public boolean isGameOver() {
    return gameOver;
  }

  /**
   * Returns the game instance.
   *
   * @return the game
   */
  public BoardGame getGame() {
    return game;
  }

  public void startGame() {
    notifyObservers(BoardGameEvent.GAME_START);
  }


}
