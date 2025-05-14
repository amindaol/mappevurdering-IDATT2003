package edu.ntnu.idi.idatt.core;

import edu.ntnu.idi.idatt.config.GameInformation;
import edu.ntnu.idi.idatt.config.GameMode;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class GameRegister {

  public final Map<GameMode, GameInformation> gameInformation = new EnumMap<>(GameMode.class);

  public void register(GameInformation info) {
    gameInformation.put(info.getGameMode(), info);
  }

  public GameInformation get(GameMode gameMode) {
    return gameInformation.get(gameMode);
  }

  public List<GameInformation> getAll() {
    return new ArrayList<>(gameInformation.values());
  }
}
