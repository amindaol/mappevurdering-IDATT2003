package edu.ntnu.idi.idatt.view;

import edu.ntnu.idi.idatt.view.layouts.HomeView;
import edu.ntnu.idi.idatt.view.layouts.SettingsView;
import java.util.Objects;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
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
        .setOnAction(event -> showLoveAndLaddersPage());
    homeView.getBestieBattlesButton()
        .setOnAction(event -> {
          System.out.println("Bestie Battles button clicked");
        });

    SettingsView loveAndLaddersView = new SettingsView("Love & Ladders",
        () -> showHomePage(),
        () -> System.out.println("Help button clicked"),
        new Label("Love & Ladders content"));
  }

  public void showHomePage() {
    stage.setTitle("Slayboard - Home");
    stage.setScene(homeScene);
  }

  public void showLoveAndLaddersPage() {
    stage.setTitle("Slayboard - Love & Ladders");
    stage.setScene(new Scene(new Label("Love & Ladders content")));
  }

  public void showBestieBattlesPage() {
    // TODO: Implement this method
  }

}
