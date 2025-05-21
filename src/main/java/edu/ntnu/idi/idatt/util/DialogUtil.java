package edu.ntnu.idi.idatt.util;

import javafx.scene.control.Alert;

/**
 * Utility class for displaying dialog boxes in the game.
 */
public class DialogUtil {

  /**
   * Displays help information about the game. This method creates an alert dialog with game
   * instructions
   */
  public static void showGameHelp() {
    Alert help = new Alert(Alert.AlertType.INFORMATION);
    help.setTitle("Pause / Help");
    help.setHeaderText("Game Info");
    help.setContentText("""
            🎲 Click Roll Dice to play your turn.
            🧍‍♀️ Player icons move forward based on your dice.
            🪜 Some tiles have ladders or effects!
            ⏪ Use the home button to go back.
        """);
    help.showAndWait();
  }

}
