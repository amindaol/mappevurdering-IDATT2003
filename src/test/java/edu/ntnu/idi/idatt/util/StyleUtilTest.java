package edu.ntnu.idi.idatt.util;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StyleUtilTest {

  @BeforeAll
  public static void initToolkit() {
    Platform.startup(() -> {});
  }

  @Test
  void testApplyStyleSheetDoesNothingIfFileNotFound() {
    Scene scene = new Scene(new Pane());
    StyleUtil.applyStyleSheet(scene, "/no/such/file.css");

    assertTrue(scene.getStylesheets().isEmpty());
  }

  @Test
  void testApplyStyleSheetAddsStylesheet() {
    Scene scene = new Scene(new Pane());
    StyleUtil.applyStyleSheet(scene, "/styles/test.css");

    assertFalse(scene.getStylesheets().isEmpty());
  }
}
