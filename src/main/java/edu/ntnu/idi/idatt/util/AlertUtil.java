package edu.ntnu.idi.idatt.util;

import javafx.scene.control.Alert;

/**
 * Utility class for displaying alerts in the JavaFX application. This class provides static methods
 * to show different types of alerts.
 */
public class AlertUtil {

  /**
   * Displays a warning alert with the specified message.
   *
   * @param msg the message to display in the alert
   */
  public static void showWarning(String msg) {
    show(Alert.AlertType.WARNING, "Warning", msg);
  }

  /**
   * Displays an information alert with the specified title and message.
   *
   * @param title the title of the alert
   * @param msg   the message to display in the alert
   */
  public static void showInfo(String title, String msg) {
    show(Alert.AlertType.INFORMATION, title, msg);
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
    Alert alert = new Alert(type);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(msg);
    alert.showAndWait();
  }
}
