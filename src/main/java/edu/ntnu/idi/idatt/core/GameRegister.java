package edu.ntnu.idi.idatt.core;

import edu.ntnu.idi.idatt.config.GameInformation;
import edu.ntnu.idi.idatt.config.GameMode;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * The GameRegister class is responsible for registering and managing game information. It allows
 * for the registration of different game modes and provides methods to retrieve game information
 * based on the game mode.
 */
public class GameRegister {

  public final Map<GameMode, GameInformation> gameInformation = new EnumMap<>(GameMode.class);

  /**
   * Registers a new game information object with the specified game mode.
   *
   * @param info the GameInformation object to register
   */
  public void register(GameInformation info) {
    gameInformation.put(info.getGameMode(), info);
  }

  /**
   * Retrieves the game information for the specified game mode.
   *
   * @param gameMode the GameMode to retrieve information for
   * @return the GameInformation object associated with the specified game mode
   */
  public GameInformation get(GameMode gameMode) {
    return gameInformation.get(gameMode);
  }

  /**
   * Retrieves all registered game information objects.
   *
   * @return a list of all GameInformation objects
   */
  public List<GameInformation> getAll() {
    return new ArrayList<>(gameInformation.values());
  }
}
