package edu.ntnu.idi.idatt.util;

import javafx.scene.control.Alert;

public class AlertUtil {
  public static void showWarning(String msg) {
    show(Alert.AlertType.WARNING, "Warning", msg);
  }

  public static void showInfo(String title, String msg) {
    show(Alert.AlertType.INFORMATION, title, msg);
  }

  public static void showError(String title, String msg) {
    show(Alert.AlertType.ERROR, title, msg);
  }

  private static void show(Alert.AlertType type, String title, String msg) {
    Alert alert = new Alert(type);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(msg);
    alert.showAndWait();
  }
}
