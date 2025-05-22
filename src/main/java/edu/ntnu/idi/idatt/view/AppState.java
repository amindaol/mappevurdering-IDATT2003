package edu.ntnu.idi.idatt.view;

import edu.ntnu.idi.idatt.model.game.Player;
import java.util.List;

/**
 * A static class for storing shared application state across views and controllers.
 * Tracks selected players, board file, and whether players should be loaded from a file.
 *
 * Used during setup to persist user choices between screens.
 *
 * @author Aminda Lunde
 * @author Ingrid Opheim
 * @version 1.0
 */
public class AppState {

  private static List<Player> selectedPlayers;
  private static String selectedBoardFile;
  private static boolean loadPlayersFromFile = false;

  /**
   * Sets the list of selected players for the game.
   *
   * @param players the players selected in setup
   */
  public static void setSelectedPlayers(List<Player> players) {
    selectedPlayers = players;
  }

  /**
   * Returns the list of players selected for the game.
   *
   * @return list of {@link Player} objects
   */
  public static List<Player> getSelectedPlayers() {
    return selectedPlayers;
  }

  /**
   * Sets whether to load players from file instead of manual input.
   *
   * @param b true to load from file, false for manual entry
   */
  public static void setLoadPlayersFromFile(boolean b) {
    loadPlayersFromFile = b;
  }

  /**
   * Returns whether players should be loaded from file.
   *
   * @return true if loading from file, false if manual
   */
  public static boolean isLoadPlayersFromFile() {
    return loadPlayersFromFile;
  }

  /**
   * Sets the filename of the selected game board.
   *
   * @param boardFile the name of the board file (e.g. ladderBoard1.json)
   */
  public static void setSelectedBoardFile(String boardFile) {
    selectedBoardFile = boardFile;
  }

  /**
   * Returns the name of the selected board file.
   *
   * @return board file name as string
   */
  public static String getSelectedBoardFile() {
    return selectedBoardFile;
  }
}

