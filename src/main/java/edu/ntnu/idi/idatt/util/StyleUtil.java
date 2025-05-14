package edu.ntnu.idi.idatt.util;

import java.net.URL;
import javafx.scene.Scene;

public class StyleUtil {
  public static void applyStyleSheet(Scene scene, String stylesheetPath) {
    URL resource = StyleUtil.class.getResource(stylesheetPath);
    if (resource != null) {
      scene.getStylesheets().add(resource.toExternalForm());
    } else {
      System.err.println("Could not find stylesheet: " + stylesheetPath);
    }
  }
}
