package edu.ntnu.idi.idatt.util;

import java.net.URL;
import javafx.scene.Scene;

/**
 * Utility class for applying stylesheets to JavaFX scenes.
 */
public class StyleUtil {

  /**
   * Applies a stylesheet to the given JavaFX scene.
   *
   * @param scene          the JavaFX scene to which the stylesheet will be applied
   * @param stylesheetPath the path to the stylesheet file
   */
  public static void applyStyleSheet(Scene scene, String stylesheetPath) {
    URL resource = StyleUtil.class.getResource(stylesheetPath);
    if (resource != null) {
      scene.getStylesheets().add(resource.toExternalForm());
    } else {
      System.err.println("Could not find stylesheet: " + stylesheetPath);
    }
  }
}
