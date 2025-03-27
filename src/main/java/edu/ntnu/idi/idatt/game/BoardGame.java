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
   * Constructs a
   */
  public BoardGame() {
    this.players = new ArrayList<>();
  }

  /**
   * @param player
   */
  public void addPlayer(Player player) {
    players.add(player);
  }

  /**
   *
   */
  public void createBoard() {
    this.board = new Board();

    for (int i = 1; i <= 30; i++) {
      board.addTile(new Tile(i));
    }
  }

  /**
   *
   */
  public void createDice() {
    this.dice = new Dice(1);
  }

  /**
   *
   */
  public void play() {
    System.out.println("Game started with " + players.size() + " players");
  }

  /**
   * @return
   */
  public Player getWinner() {
    return null;
  }

  /**
   * @return
   */
  public Board getBoard() {
    return board;
  }

  /**
   * @return
   */
  public Dice getDice() {
    return dice;
  }

  /**
   * @return
   */
  public List<Player> getPlayers() {
    return players;
  }
}
