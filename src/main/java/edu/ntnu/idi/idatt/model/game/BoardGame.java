package edu.ntnu.idi.idatt.model.game;

import edu.ntnu.idi.idatt.observer.BoardGameEvent;
import edu.ntnu.idi.idatt.observer.BoardGameObserver;
import edu.ntnu.idi.idatt.util.exceptionHandling.InvalidPlayerException;
import edu.ntnu.idi.idatt.util.exceptionHandling.TooManyPlayersException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Represents the main game controller that manages the board, dice, players, and the turn-based
 * gameplay loop. This class fires observer events and throws domain-specific exceptions on invalid
 * operations.
 *
 * @author Aminda Lunde
 * @author Ingrid Opheim
 * @version 1.0
 */
public class BoardGame {

  public static final int MAX_PLAYERS = 5;

  private final List<Player> players = new ArrayList<>();
  private final List<BoardGameObserver> observers = new CopyOnWriteArrayList<>();

  private final Board board;
  private final Dice dice;


  /**
   * Constructs a new BoardGame instance with an empty player list.
   *
   * @param board the game board to use
   * @param dice  the dice to use in the game
   * @throws NullPointerException if {@code board} or {@code dice} is {@code null}.
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
  protected void notifyObservers() {
    for (BoardGameObserver observer : observers) {
      observer.onGameStateChange(this, BoardGameEvent.GAME_START);
    }
  }

  /**
   * Returns the list of observers.
   *
   * @return a list of BoardGameObserver instances.
   */
  public List<BoardGameObserver> getObservers() {
    return observers;
  }

  /**
   * Adds a player to the game.
   *
   * @param player the player to add to the game.
   * @throws NullPointerException    if {@code player} is {@code null}.
   * @throws TooManyPlayersException if adding would exceed the maximum of {@value #MAX_PLAYERS}
   * @throws InvalidPlayerException  if the player has invalid data (e.g., missing name or token)
   */
  public void addPlayer(Player player) {
    if (player == null) {
      throw new NullPointerException("Player cannot be null.");
    }
    if (players.size() >= MAX_PLAYERS) {
      throw new TooManyPlayersException(MAX_PLAYERS);
    }
    if (player.getName() == null || player.getToken() == null) {
      throw new InvalidPlayerException("Player must have a valid name and token.");
    }
    players.add(player);
  }

  /**
   * Returns the game board.
   *
   * @return the Board used in the game.
   */
  public Board getBoard() {
    return board;
  }

  /**
   * Returns the dice used in the game.
   *
   * @return the Dice instance.
   */
  public Dice getDice() {
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


  /**
   * Clears all observers from the game.
   */
  public void clearObservers() {
    observers.clear();
  }

}
