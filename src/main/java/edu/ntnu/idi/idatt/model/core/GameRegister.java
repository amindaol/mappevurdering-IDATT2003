package edu.ntnu.idi.idatt.model.core;

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
 *
 * @author Aminda Lunde
 * @author Ingrid Opheim
 * @version 1.0
 */
public class GameRegister {

  public final Map<GameMode, GameInformation> gameInformation = new EnumMap<>(GameMode.class);

  /**
   * Registers a new game information object with the specified game mode.
   *
   * @param info the GameInformation object to register
   * @throws IllegalArgumentException if game information is null
   */
  public void register(GameInformation info) {
    if (info == null) {
      throw new IllegalArgumentException("GameInformation cannot be null.");
    }
    gameInformation.put(info.getGameMode(), info);
  }

  /**
   * Retrieves the game information for the specified game mode.
   *
   * @param gameMode the GameMode to retrieve information for
   * @return the GameInformation object associated with the specified game mode
   * @throws IllegalArgumentException if game mode is null
   */
  public GameInformation get(GameMode gameMode) {
    if (gameMode == null) {
      throw new IllegalArgumentException("GameMode cannot be null.");
    }
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
