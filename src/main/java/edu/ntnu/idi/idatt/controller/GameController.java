package edu.ntnu.idi.idatt.controller;

import edu.ntnu.idi.idatt.model.engine.GameEngine;
import edu.ntnu.idi.idatt.model.game.Player;
import edu.ntnu.idi.idatt.util.exceptionHandling.GameNotInitializedException;
import edu.ntnu.idi.idatt.util.exceptionHandling.NoPlayersException;
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
   * @throws GameNotInitializedException if the engine is not properly initialized
   */
  public GameController(GameEngine engine) {
    if (engine == null) {
      throw new GameNotInitializedException("Game engine cannot be null.");
    }
    this.engine = engine;
  }

  /**
   * Plays a turn by rolling the dice and passing the result to the engine.
   * @throws NoPlayersException if there are no players
   */
  public void playTurn() {
    if (engine.getPlayers().isEmpty()) {
      throw new NoPlayersException("There are no players to play the game.");
    }
    lastRoll = engine.getGame().getDice().roll();
    int total = lastRoll.stream().mapToInt(Integer::intValue).sum();
    engine.handleTurn(total);
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