package edu.ntnu.idi.idatt.ui.view;

import edu.ntnu.idi.idatt.model.game.Player;
import java.util.List;

public class AppState {

  private static List<Player> selectedPlayers;

  public static void setSelectedPlayers(List<Player> players) {
    selectedPlayers = players;
  }

  public static List<Player> getSelectedPlayers() {
    return selectedPlayers;
  }

  public static void clear() {
    selectedPlayers = null;
  }
}
