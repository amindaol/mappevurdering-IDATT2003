package edu.ntnu.idi.idatt.config;

import edu.ntnu.idi.idatt.model.engine.GameEngine;
import edu.ntnu.idi.idatt.model.game.Board;
import edu.ntnu.idi.idatt.config.GameMode;
import edu.ntnu.idi.idatt.config.GameConfiguration;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * GameInformation is a configuration class that holds the game name, rules, player limits, engine
 * factory, board options supplier, and game mode. It is used to initialize the game with the
 * specified settings.
 *
 *  @author Aminda Lunde
 *  @author Ingrid Opheim
 *  @version 1.0
 */
public class GameInformation {

  private final String name;
  private final String rules;
  private final int playerMax;
  private final int playerMin;
  private final Function<GameConfiguration, GameEngine> engineFactory;
  private final Supplier<List<Board>> boardOptionsSupplier;
  private final GameMode gameMode;

  /**
   * Constructs a GameInformation with the specified name, rules, player limits, engine factory,
   * board options supplier, and game mode.
   *
   * @param name                 the name of the game
   * @param rules                the rules of the game
   * @param playerMax            the maximum number of players
   * @param playerMin            the minimum number of players
   * @param engineFactory        a function that creates a GameEngine based on GameConfiguration
   * @param boardOptionsSupplier a supplier that provides a list of Board options
   * @param gameMode             the game mode
   * @throws IllegalArgumentException if any of the parameters are null
   */
  public GameInformation(String name, String rules, int playerMax, int playerMin,
      Function<GameConfiguration, GameEngine> engineFactory,
      Supplier<List<Board>> boardOptionsSupplier, GameMode gameMode) {
    if (name == null || engineFactory == null || boardOptionsSupplier == null) {
      throw new IllegalArgumentException("Fields cannot be null");
    }

    this.name = name;
    this.rules = rules;
    this.playerMax = playerMax;
    this.playerMin = playerMin;
    this.engineFactory = engineFactory;
    this.boardOptionsSupplier = boardOptionsSupplier;
    this.gameMode = gameMode;
  }

  /**
   * Returns the game mode of this configuration.
   *
   * @return the game mode
   */
  public GameMode getGameMode() {
    return gameMode;
  }

  /**
   * Returns the name of the game.
   *
   * @return the name of the game
   */
  public String getName() {
    return name;
  }

  /**
   * Returns the rules of the game.
   *
   * @return the rules of the game
   */
  public String getRules() {
    return rules;
  }

  /**
   * Returns the maximum number of players allowed for this game.
   *
   * @return the maximum number of players
   */
  public int getPlayerMax() {
    return playerMax;
  }

  /**
   * Returns the minimum number of players required for this game.
   *
   * @return the minimum number of players
   */
  public int getPlayerMin() {
    return playerMin;
  }

  /**
   * Returns the function that creates a GameEngine based on GameConfiguration.
   *
   * @return the engine factory function
   */
  public Function<GameConfiguration, GameEngine> getEngineFactory() {
    return engineFactory;
  }

  /**
   * Returns the supplier that provides a list of Board options.
   *
   * @return the board options supplier
   */
  public Supplier<List<Board>> getBoardOptionsSupplier() {
    return boardOptionsSupplier;
  }

  /**
   * Creates a GameEngine based on the provided GameConfiguration.
   *
   * @param gameConfiguration the game configuration
   * @return the created GameEngine
   */
  public GameEngine createEngine(GameConfiguration gameConfiguration) {
    return engineFactory.apply(gameConfiguration);
  }

  /**
   * Returns a list of available board options for this game.
   *
   * @return the list of board options
   */
  public List<Board> getBoardOptions() {
    return boardOptionsSupplier.get();
  }

  /**
   * Returns a string representation of the game information.
   *
   * @return a string representation of the game information
   */
  @Override
  public String toString() {
    return name + " (" + playerMin + "–" + playerMax + " players)";
  }

}
