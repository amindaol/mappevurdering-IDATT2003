package edu.ntnu.idi.idatt.model.game;

import java.util.List;

public abstract class GameEngine {

  protected Board board;
  protected List<Player> players;
  protected int currentPlayerIndex;
  protected boolean gameOver;

  public GameEngine(Board board, List<Player> players) {
    this.board = board;
    this.players = players;
    this.currentPlayerIndex = currentPlayerIndex;
    this.gameOver = false;
  }

  public abstract void playGame();

  public abstract void handleTurn();

  public abstract Player checkWinCondition();

  public void endGame() {
    this.gameOver = true;
  }

  public Player getCurrentPlayer() {
    return players.get(currentPlayerIndex);
  }

  public Player getLastPlayer() {
    return (currentPlayerIndex == 0) ? players.getLast() : players.get(currentPlayerIndex - 1);
  }

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
