package edu.ntnu.idi.idatt.controller;

import edu.ntnu.idi.idatt.model.engine.GameEngine;
import edu.ntnu.idi.idatt.model.game.Player;
import java.util.List;

/**
 * GameController handles the game flow logic: whose turn it is, dice roll, and movement. It does
 * not handle GUI logic.
 */
public class GameController {

  private final GameEngine engine;
  private List<Integer> lastRoll;

  public GameController(GameEngine engine) {
    this.engine = engine;
  }

  public void playTurn() {
    lastRoll = engine.getGame().getDice().roll();
    int total = lastRoll.stream().mapToInt(Integer::intValue).sum();
    engine.handleTurn(total);
  }

  public boolean isGameOver() {
    return engine.isGameOver();
  }

  public Player getCurrentPlayer() {
    return engine.getCurrentPlayer();
  }

  public Player getWinner() {
    return engine.checkWinCondition();
  }

  public GameEngine getEngine() {
    return engine;
  }

  public List<Player> getPlayers() {
    return engine.getPlayers();
  }

  public List<Integer> getLastRoll() {
    return lastRoll;
  }
}