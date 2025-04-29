package edu.ntnu.idi.idatt.view;

import main.java.edu.ntnu.idi.idatt.view.home.HomeView;
import java.util.Objects;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class UiController {

  private final Stage stage;
  private final Scene homeScene;

  public UiController(Stage stage) {
    this.stage = stage;

    stage.setMaximized(true);

    HomeView homeView = new HomeView();
    homeScene = new Scene(homeView.getRoot());
    homeScene.getStylesheets().add(
        Objects.requireNonNull(
                getClass().getResource("/css/styles.css"),
                "Could not find /css/home.css in the classpath"
            )
            .toExternalForm()
    );

    homeView.getLoveAndLaddersButton()
        .setOnAction(event -> {
          System.out.println("Love & Ladders button clicked");
        });
    homeView.getBestieBattlesButton()
        .setOnAction(event -> {
          System.out.println("Bestie Battles button clicked");
        });
  }

  public void showHomePage() {
    stage.setTitle("Slayboard - Home");
    stage.setScene(homeScene);
  }

  public void showLoveAndLaddersPage() {
    // TODO: Implement this method
  }

  public void showBestieBattlesPage() {
    // TODO: Implement this method
  }

}
