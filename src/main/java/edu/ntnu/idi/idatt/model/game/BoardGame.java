package edu.ntnu.idi.idatt.model.game;

import edu.ntnu.idi.idatt.observer.BoardGameEvent;
import edu.ntnu.idi.idatt.observer.BoardGameObserver;
import edu.ntnu.idi.idatt.util.exceptionHandling.NoPlayersException;
import edu.ntnu.idi.idatt.util.exceptionHandling.TooManyPlayersException;
import edu.ntnu.idi.idatt.util.exceptionHandling.GameNotInitializedException;
import edu.ntnu.idi.idatt.util.exceptionHandling.GameAlreadyFinishedException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Represents the main game controller that manages the board, dice, players, and the turn-based
 * gameplay loop. This class fires observer events and throws domain-specific exceptions on invalid
 * operations.
 */
public class BoardGame {

  public static final int MAX_PLAYERS = 6;

  private final List<Player> players = new ArrayList<>();
  private final List<BoardGameObserver> observers = new CopyOnWriteArrayList<>();

  private Board board;
  private Dice dice;


  /**
   * Constructs a new BoardGame instance with an empty player list.
   */
  public BoardGame(Board board, Dice dice) {
    if (board == null || dice == null) {
      throw new NullPointerException("Board and Dice must not be null.");
    }
    this.board = board;
    this.dice = dice;
  }

  /**
   * Adds an observer to the game. Observers are notified of game state changes.
   *
   * @param observer the observer to add.
   * @throws NullPointerException if {@code observer} is {@code null}.
   */
  public void addObserver(BoardGameObserver observer) {
    if (observer == null) {
      throw new NullPointerException("Observer cannot be null.");
    }
    observers.add(observer);
  }

  /**
   * Removes an observer from the game.
   *
   * @param observer the observer to remove.
   */
  public void removeObserver(BoardGameObserver observer) {
    observers.remove(observer);
  }

  /**
   * Notifies all observers of a game state change.
   */
  protected void notifyObservers(BoardGameEvent event) {
    for (BoardGameObserver observer : observers) {
      observer.onGameStateChange(this, event);
    }
  }


  public List<BoardGameObserver> getObservers() {
    return observers;
  }

  /**
   * Adds a player to the game.
   *
   * @param player the player to add to the game.
   * @throws NullPointerException    if {@code player} is {@code null}.
   * @throws TooManyPlayersException if adding would exceed the maximum of {@value #MAX_PLAYERS}
   */
  public void addPlayer(Player player) {
    if (player == null) {
      throw new NullPointerException("Player cannot be null.");
    }
    if (players.size() >= MAX_PLAYERS) {
      throw new TooManyPlayersException(MAX_PLAYERS);
    }
    players.add(player);
  }

  /**
   * Returns the game board.
   *
   * @return the Board used in the game.
   * @throws GameNotInitializedException if the board has not been created
   */
  public Board getBoard() {
    if (board == null) {
      throw new GameNotInitializedException();
    }
    return board;
  }

  /**
   * Returns the dice used in the game.
   *
   * @return the Dice instance.
   * @throws GameNotInitializedException if the dice has not been created
   */
  public Dice getDice() {
    if (dice == null) {
      throw new GameNotInitializedException();
    }
    return dice;
  }

  /**
   * Returns the list of players in the game.
   *
   * @return a list of Player objects.
   */
  public List<Player> getPlayers() {
    return players;
  }

}
