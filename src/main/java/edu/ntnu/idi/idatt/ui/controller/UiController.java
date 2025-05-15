package edu.ntnu.idi.idatt.ui.controller;

import edu.ntnu.idi.idatt.config.GameMode;
import edu.ntnu.idi.idatt.ui.view.components.SettingsContent;
import edu.ntnu.idi.idatt.ui.view.layouts.setup.SettingsView;
import javafx.stage.Stage;

public class UiController {

  private final SceneNavigator navigator;
  private final GameLauncher launcher;

  public UiController(Stage stage) {
    this.navigator = new SceneNavigator(stage);
    this.launcher = new GameLauncher(stage);
  }

  public void showHomePage() {
    // TODO: Implementer en HomeView og bytt til den her
    System.out.println("Viser HomeView (ikke implementert ennå).");
  }

  public void showSettingsPage(GameMode mode) {
    SettingsContent content = new SettingsContent(mode);

    SettingsView view = new SettingsView(
        "Slayboard – " + mode.name().replace("_", " "),
        this::showHomePage,
        content.getRoot(),
        () -> launcher.launchGame(content, mode)
    );

    navigator.switchTo(view.getRoot(), "Slayboard – " + mode.name());
  }
}
