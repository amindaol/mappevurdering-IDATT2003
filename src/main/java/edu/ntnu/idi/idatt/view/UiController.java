package main.java.edu.ntnu.idi.idatt.view;

import javafx.scene.Scene;
import javafx.stage.Stage;
import main.java.edu.ntnu.idi.idatt.view.home.HomeView;

public class UiController {

  private final Stage stage;
  private final Scene homeScene;

  public UiController(Stage stage) {
    this.stage = stage;

    stage.setMaximized(true);

    homeScene = new Scene(HomeView.create(this));
  }

  public void showHomePage() {
    stage.setTitle("Slayboard - Home");
    stage.setScene(homeScene);
  }

}
