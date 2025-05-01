package edu.ntnu.idi.idatt.controller;

import edu.ntnu.idi.idatt.view.components.SettingsContent;
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
  private final Scene loveAndLaddersScene;

  public UiController(Stage stage) {
    this.stage = stage;

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

    SettingsView loveAndLaddersView = new SettingsView(
        "Love & Ladders",
        this::showHomePage,
        () -> System.out.println("Help button clicked"),
        new SettingsContent(4).getRoot()
    );

    loveAndLaddersScene = new Scene(loveAndLaddersView.getRoot());
    loveAndLaddersScene.getStylesheets().add(
        Objects.requireNonNull(
                getClass().getResource("/css/styles.css"),
                "Could not find /css/loveAndLadders.css in the classpath"
            )
            .toExternalForm()
    );
  }

  public void showHomePage() {
    stage.setTitle("Slayboard - Home");
    stage.setMaximized(true);
    stage.setScene(homeScene);
  }

  public void showLoveAndLaddersPage() {
    stage.setTitle("Slayboard - Love & Ladders");
    stage.setMaximized(true);
    stage.setScene(loveAndLaddersScene);
  }

  public void showBestieBattlesPage() {
    // TODO: Implement this method
  }

}
