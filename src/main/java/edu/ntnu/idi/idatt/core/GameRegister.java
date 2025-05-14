package edu.ntnu.idi.idatt.core;

import edu.ntnu.idi.idatt.model.game.GameConfiguration;
import edu.ntnu.idi.idatt.model.game.GameMode;

import java.util.EnumMap;
import java.util.Map;

public class GameRegister {

  public final Map<GameMode, GameConfiguration> gameConfigurations = new EnumMap<>(GameMode.class);

  public void register(GameConfiguration gameConfig) {
    if (gameConfig == null) throw new IllegalArgumentException("GameConfiguration cannot be null");
    gameConfigurations.put(gameConfig.getGameMode(), gameConfig);
  }

  public GameConfiguration get(GameMode gameMode) {
    return gameConfigurations.get(gameMode);
  }

  public boolean isRegistered(GameMode gameMode) {
    return gameConfigurations.containsKey(gameMode);
  }

  public void clear() {
    gameConfigurations.clear();
  }

  public int size() {
    return gameConfigurations.size();
  }

  public Map<GameMode, GameConfiguration> getAll() {
    return Map.copyOf(gameConfigurations);
  }
}
