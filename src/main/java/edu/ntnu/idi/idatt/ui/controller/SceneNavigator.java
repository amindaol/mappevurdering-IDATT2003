package edu.ntnu.idi.idatt.ui.controller;


import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneNavigator {

  private final Stage stage;

  public SceneNavigator(Stage stage) {
    this.stage = stage;
  }

  public void switchTo(Parent root, String title) {
    Scene scene = new Scene(root);
    scene.getStylesheets().add(
        getClass().getResource("/style.css").toExternalForm()
    );
    stage.setTitle(title);
    stage.setScene(scene);
    stage.sizeToScene();
    stage.show();
  }
}
