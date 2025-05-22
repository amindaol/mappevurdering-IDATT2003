package edu.ntnu.idi.idatt.view;

import edu.ntnu.idi.idatt.model.game.Player;
import java.util.List;

public class AppState {

  private static List<Player> selectedPlayers;
  private static String selectedBoardFile;
  private static boolean loadPlayersFromFile = false;

  public static void setSelectedPlayers(List<Player> players) {
    selectedPlayers = players;
  }

  public static List<Player> getSelectedPlayers() {
    return selectedPlayers;
  }

  public static void clear() {
    selectedPlayers = null;
  }

  public static void setLoadPlayersFromFile(boolean b) {
    loadPlayersFromFile = b;
  }

  public static boolean isLoadPlayersFromFile() {
    return loadPlayersFromFile;
  }

  public static void setSelectedBoardFile(String boardFile) {
    selectedBoardFile = boardFile;
  }

  public static String getSelectedBoardFile() {
    return selectedBoardFile;
  }
}

