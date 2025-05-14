package edu.ntnu.idi.idatt.model.game;

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

  public GameInformation(String name, String rules, int playerMax, int playerMin,
      Function<GameConfiguration, GameEngine> engineFactory,
      Supplier<List<Board>> boardOptionsSupplier) {
    this.name = name;
    this.rules = rules;
    this.playerMax = playerMax;
    this.playerMin = playerMin;
    this.engineFactory = engineFactory;
    this.boardOptionsSupplier = boardOptionsSupplier;
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
}
