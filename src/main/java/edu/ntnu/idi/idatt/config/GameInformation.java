package edu.ntnu.idi.idatt.config;

import edu.ntnu.idi.idatt.model.engine.GameEngine;
import edu.ntnu.idi.idatt.model.game.Board;
import edu.ntnu.idi.idatt.config.GameMode;
import edu.ntnu.idi.idatt.config.GameConfiguration;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public class GameInformation {

  private final String name;
  private final String rules;
  private final int playerMax;
  private final int playerMin;
  private final Function<GameConfiguration, GameEngine> engineFactory;
  private final Supplier<List<Board>> boardOptionsSupplier;
  private final GameMode gameMode;

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

  public GameMode getGameMode() {
    return gameMode;
  }

  public String getName() {
    return name;
  }

  public String getRules() {
    return rules;
  }

  public int getPlayerMax() {
    return playerMax;
  }

  public int getPlayerMin() {
    return playerMin;
  }

  public Function<GameConfiguration, GameEngine> getEngineFactory() {
    return engineFactory;
  }

  public Supplier<List<Board>> getBoardOptionsSupplier() {
    return boardOptionsSupplier;
  }

  public GameEngine createEngine(GameConfiguration gameConfiguration) {
    return engineFactory.apply(gameConfiguration);
  }

  public List<Board> getBoardOptions() {
    return boardOptionsSupplier.get();
  }

  @Override
  public String toString() {
    return name + " (" + playerMin + "–" + playerMax + " players)";
  }

}
