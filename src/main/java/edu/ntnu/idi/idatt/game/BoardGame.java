package edu.ntnu.idi.idatt.game;

import java.util.ArrayList;
import java.util.List;

public class BoardGame {

  private Board board;
  private Player currentPlayer;
  private List<Player> players;
  private Dice dice;

  public BoardGame() {
    this.players = new ArrayList<>();
  }

  public void addPlayer(Player player) {
    players.add(player);
  }

  public void createBoard() {
    this.board = new Board();

    for (int i = 1; i <= 30; i++) {
      board.addTile(new Tile(i));
    }
  }

  public void createDice() {
    this.dice = new Dice(1);
  }

  public void play() {
    System.out.println("Game started with " + players.size() + " players");
  }

  public Player getWinner() {
    return null;
  }

  public Board getBoard() {
    return board;
  }

  public Dice getDice() {
    return dice;
  }

  public List<Player> getPlayers() {
    return players;
  }
}
