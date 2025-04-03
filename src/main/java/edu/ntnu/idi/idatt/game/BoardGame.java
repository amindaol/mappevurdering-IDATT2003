package edu.ntnu.idi.idatt.game;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the main game controller that manages players, the board, and dice.
 * <p>
 * This class is responsible for initializing the game, adding players and starting gameplay.
 */
public class BoardGame {

  private Board board;
  private Player currentPlayer;
  private List<Player> players;
  private Dice dice;

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
   */
  public void addPlayer(Player player) {
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
   * Starts the game loop.
   * <p>
   * This method should contain the main logic for playing turns, rolling dice, and determining the
   * winner. Currently, it just prints a message.
   */
  public void play() {
    System.out.println("Game started with " + players.size() + " players");
  }

  /**
   * Returns the winner of the game.
   *
   * @return the winning player, or null if the game is not finished.
   */
  public Player getWinner() {
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
