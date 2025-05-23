package edu.ntnu.idi.idatt.util;

import javafx.scene.control.Alert;

/**
 * Utility class for displaying alerts in the JavaFX application. This class provides static methods
 * to show different types of alerts.
 *
 * @author Aminda Lunde
 * @author Ingrid Opheim
 * @version 1.0
 */
public class AlertUtil {

  static boolean disableAlertsForTesting = false;

  private AlertUtil() {
  }

  /**
   * Displays an error alert with the specified title and message.
   *
   * @param title the title of the alert
   * @param msg   the message to display in the alert
   */
  public static void showError(String title, String msg) {
    show(Alert.AlertType.ERROR, title, msg);
  }

  /**
   * Displays a confirmation alert with the specified title and message.
   *
   * @param title the title of the alert
   * @param msg   the message to display in the alert
   */
  private static void show(Alert.AlertType type, String title, String msg) {
    if (disableAlertsForTesting) {
      return;
    }


    Alert alert = new Alert(type);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(msg);
    alert.showAndWait();
  }

  /**
   * Shows a help dialog with game-specific instructions based on the game mode.
   *
   * @param gameMode the game mode identifier (e.g. "LoveAndLadders", "PointBattles")
   */
  public static void showGameHelp(String gameMode) {
    if (disableAlertsForTesting)  {
      return;
    }

    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("Help");

    switch (gameMode) {
      case "LoveAndLadders" -> {
        alert.setHeaderText("Love & Ladders");
        alert.setContentText("""
            🎲 Roll the dice to move forward.
            🪜 Landing on a green ladder moves you up.
            🪜 Landing on a red ladder sends you down.
               First player to reach the final tile wins!
        """);
      }
      case "PointBattles" -> {
        alert.setHeaderText("Bestie PointBattles");
        alert.setContentText("""
            🏆 Collect as many points as you can before reaching the goal!
            ✨ Some tiles give or take points.
            🚫 Others skip your turn or send you backwards.
               Highest score wins!
        """);
      }
      default -> {
        alert.setHeaderText("Game Help");
        alert.setContentText("No specific instructions available for this game mode.");
      }
    }

    alert.showAndWait();
  }

  /**
   * Shows a help dialog explaining how to set up a new game.
   */
  public static void showHelpDialog() {
    if (disableAlertsForTesting) {
      return;
    }

    Alert a = new Alert(Alert.AlertType.INFORMATION);
    a.setTitle("Help");
    a.setHeaderText("How to set up the game");
    a.setContentText("""
        1. Choose a board.
        2. Select number of players.
        3. Fill in name and birthday for each.
        4. Choose a unique token per player.
        5. Click 'Start game'!
        """);
    a.showAndWait();
  }
}
