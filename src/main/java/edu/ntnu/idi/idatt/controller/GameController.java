package edu.ntnu.idi.idatt.controller;

import edu.ntnu.idi.idatt.model.engine.GameEngine;
import edu.ntnu.idi.idatt.model.game.Player;
import java.util.List;

/**
 * GameController handles the game flow logic: whose turn it is, dice roll, and movement.
 * It delegates core game operations to the GameEngine.
 * This class does not handle any GUI logic.
 *
 * @author Aminda Lunde
 * @author Ingrid Opheim
 * @version 1.0
 */
public class GameController {

  private final GameEngine engine;
  private List<Integer> lastRoll;

  /**
   * Creates a new GameController with the given GameEngine.
   *
   * @param engine the game engine to control
   */
  public GameController(GameEngine engine) {
    this.engine = engine;
  }

  /**
   * Plays a turn by rolling the dice and passing the result to the engine.
   */
  public void playTurn() {
    lastRoll = engine.getGame().getDice().roll();
    int total = lastRoll.stream().mapToInt(Integer::intValue).sum();
    engine.handleTurn(total);
  }

  /**
   * Checks if the game is over.
   *
   * @return true if the game is finished, false otherwise
   */
  public boolean isGameOver() {
    return engine.isGameOver();
  }

  /**
   * Returns the current player.
   *
   * @return the player whose turn it is
   */
  public Player getCurrentPlayer() {
    return engine.getCurrentPlayer();
  }

  /**
   * Returns the winner of the game, if any.
   *
   * @return the winning player, or null if no one has won yet
   */
  public Player getWinner() {
    return engine.checkWinCondition();
  }

  /**
   * Returns the GameEngine instance used by this controller.
   *
   * @return the game engine
   */
  public GameEngine getEngine() {
    return engine;
  }

  /**
   * Returns the list of all players in the game.
   *
   * @return list of players
   */
  public List<Player> getPlayers() {
    return engine.getPlayers();
  }

  /**
   * Returns the result of the most recent dice roll.
   *
   * @return list of integers representing the last roll
   */
  public List<Integer> getLastRoll() {
    return lastRoll;
  }
}