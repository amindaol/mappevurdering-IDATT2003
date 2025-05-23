package edu.ntnu.idi.idatt.util;

import edu.ntnu.idi.idatt.util.exceptionHandling.StyleSheetNotFoundException;
import java.net.URL;
import javafx.scene.Scene;

/**
 * Utility class for applying stylesheets to JavaFX scenes.
 *
 * @author Aminda Lunde
 * @author Ingrid Opheim
 * @version 1.0
 */
public class StyleUtil {

  private StyleUtil() {

  }

  /**
   * Applies a stylesheet to the given JavaFX scene.
   *
   * @param scene          the JavaFX scene to which the stylesheet will be applied
   * @param stylesheetPath the path to the stylesheet file
   * @throws StyleSheetNotFoundException if the stylesheet cannot be found
   */
  public static void applyStyleSheet(Scene scene, String stylesheetPath) {
    URL resource = StyleUtil.class.getResource(stylesheetPath);
    if (resource != null) {
      scene.getStylesheets().add(resource.toExternalForm());
    } else {
      throw new StyleSheetNotFoundException("Could not find stylesheet: " + stylesheetPath);
    }
  }
}
