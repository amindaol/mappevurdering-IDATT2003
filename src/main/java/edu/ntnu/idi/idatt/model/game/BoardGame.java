package edu.ntnu.idi.idatt.model.game;

import edu.ntnu.idi.idatt.model.observer.BoardGameEvent;
import edu.ntnu.idi.idatt.model.observer.BoardGameObserver;
import edu.ntnu.idi.idatt.util.exceptionHandling.NoPlayersException;
import edu.ntnu.idi.idatt.util.exceptionHandling.TooManyPlayersException;
import edu.ntnu.idi.idatt.util.exceptionHandling.GameNotInitializedException;
import edu.ntnu.idi.idatt.util.exceptionHandling.GameAlreadyFinishedException;
import edu.ntnu.idi.idatt.util.exceptionHandling.PlayerNotFoundException;
import edu.ntnu.idi.idatt.util.exceptionHandling.InvalidMoveException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Represents the main game controller that manages the board, dice, players,
 * and the turn-based gameplay loop. This class fires observer events
 * and throws domain-specific exceptions on invalid operations.
 */
public class BoardGame {

  private Board board;
  private Player currentPlayer;
  private final List<Player> players;
  private Dice dice;
  private static final int MAX_PLAYERS = 6;

  /**
   * Constructs a new BoardGame instance with an empty player list.
   */
  public BoardGame() {
    this.players = new ArrayList<>();
  }

  /**
   * List of observers that are notified of game state changes. Followed
   */
  private final List<BoardGameObserver> observers = new CopyOnWriteArrayList<>();

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
  private void notifyObservers(BoardGameEvent event) {
    for (BoardGameObserver observer : observers) {
      observer.onGameStateChange(this, event);
    }
  }

  /**
   * Adds a player to the game.
   *
   * @param player the player to add to the game.
   * @throws NullPointerException if {@code player} is {@code null}.
   * @throws TooManyPlayersException    if adding would exceed the maximum of {@value #MAX_PLAYERS}
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
   * Initializes the game board with a set number of tiles. Currently creates 30 tiles and adds them
   * to the board.
   */
  public void createBoard() {
    this.board = new Board();

    for (int i = 1; i <= 30; i++) {
      board.addTile(new Tile(i));
    }
  }

  /**
   * Initialized the dice used in the game. Creates a Dice object with 1 die.
   */
  public void createDice() {
    this.dice = new Dice(1);
  }

  /**
   * Starts the game loop. Each player takes turns rolling the dice and moving on the board. The
   * first player to reach the last tile wins the game.
   *
   * @throws GameNotInitializedException if the board or dice are not set up
   * @throws NoPlayersException          if no players have been added
   */
  public void play() {
    if (board == null || dice == null) {
      throw new GameNotInitializedException();
    }

    if (players.isEmpty()) {
      throw new NoPlayersException();
    }

    System.out.println("Game started with " + players.size() + " players!");

    boolean gameWon = false;

    while (!gameWon) {
      for (Player player : players) {
        int steps = dice.roll();
        System.out.println("Player " + player.getName() + " rolled a " + steps);

        player.move(steps);
        System.out.println("Player " + player.getName() + " moved to tile " + player
            .getCurrentTile().getTileId());

        if (player.getCurrentTile().getTileId() == 30) {
          System.out.println("Congratulations " + player.getName() + "! You won!");
          gameWon = true;
          break;
        }
      }
    }
  }

  /**
   * Returns the winner of the game, if any player has reached the last tile.
   *
   * @return the winning player, or null if the game is not finished.
   * @throws GameNotInitializedException if the game is not yet won.
   */
  public Player getWinner() {
    Player winner = players.stream()
        .filter(p -> p.getCurrentTile() != null && p.getCurrentTile().getTileId() == 30)
        .findFirst()
        .orElse(null);
    if (winner == null) {
      throw new GameNotInitializedException();
    }
    return winner;
  }

  /**
   * Returns the game board.
   *
   * @return the Board used in the game.
   * @throws GameNotInitializedException if the board has not been created
   */
  public Board getBoard() {
    if (board == null) throw new GameNotInitializedException();
    return board;
  }

  /**
   * Returns the dice used in the game.
   *
   * @return the Dice instance.
   * @throws GameNotInitializedException if the dice has not been created
   */
  public Dice getDice() {
    if (dice == null) throw new GameNotInitializedException();
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
   * Replaces the current game board with the given board instance.
   *
   * <p>This method is typically used by the factory or loader to inject
   * a fully configured {@link Board} (e.g., loaded from JSON) into the game.
   *
   * @param board the {@link Board} to set as the game’s active board
   * @throws NullPointerException if {@code board} is {@code null}
   */
  public void setBoard(Board board) {
    if (board == null) {
      throw new NullPointerException("Board cannot be null");
    }
    this.board = board;
  }
}
