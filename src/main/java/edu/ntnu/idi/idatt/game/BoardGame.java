package edu.ntnu.idi.idatt.game;

import edu.ntnu.idi.idatt.observer.BoardGameEvent;
import edu.ntnu.idi.idatt.observer.BoardGameObserver;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Represents the main game controller that manages players, the board, and dice. This class is
 * responsible for initializing the game, adding players and starting gameplay.
 */
public class BoardGame {

  private Board board;
  private Player currentPlayer;
  private final List<Player> players;
  private Dice dice;


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
   * Constructs a new BoardGame instance with an empty player list.
   */
  public BoardGame() {
    this.players = new ArrayList<>();
  }

  /**
   * Adds a player to the game.
   *
   * @param player the player to add to the game.
   * @throws NullPointerException if {@code player} is {@code null}.
   */
  public void addPlayer(Player player) {
    if (player == null) {
      throw new NullPointerException("Player cannot be null.");
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
   * @throws IllegalStateException if the board, dice, or players are not initialized.
   */
  public void play() {
    if (board == null || dice == null || players.isEmpty()) {
      throw new IllegalStateException(
          "Board, dice, and players must be initialized before playing.");
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
   */
  public Player getWinner() {
    for (Player player : players) {
      if (player.getCurrentTile() != null && player.getCurrentTile().getTileId() == 30) {
        return player;
      }
    }
    return null;
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
}
