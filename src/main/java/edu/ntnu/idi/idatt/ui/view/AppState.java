package edu.ntnu.idi.idatt.ui.view;

import edu.ntnu.idi.idatt.config.LadderBoardVariant;
import edu.ntnu.idi.idatt.config.PointBoardVariant;
import edu.ntnu.idi.idatt.model.game.Player;
import java.util.List;

public class AppState {

  private static List<Player> selectedPlayers;
  public static LadderBoardVariant selectedLadderBoard;
  public static PointBoardVariant selectedPointBoard;

  public static void setSelectedPlayers(List<Player> players) {
    selectedPlayers = players;
  }

  public static List<Player> getSelectedPlayers() {
    return selectedPlayers;
  }

  public static void clear() {
    selectedPlayers = null;
    selectedLadderBoard = null;
    selectedPointBoard = null;
  }

  public static void setSelectedLadderBoard(LadderBoardVariant variant) {
    selectedLadderBoard = variant;
  }

  public static void setSelectedPointBoard(PointBoardVariant variant) {
    selectedPointBoard = variant;
  }

  public static LadderBoardVariant getSelectedLadderBoard() {
    return selectedLadderBoard;
  }

  public static PointBoardVariant getSelectedPointBoard() {
    return selectedPointBoard;
  }
}
