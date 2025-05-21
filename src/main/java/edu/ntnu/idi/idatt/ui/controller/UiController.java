package edu.ntnu.idi.idatt.ui.controller;

import edu.ntnu.idi.idatt.config.GameMode;
import edu.ntnu.idi.idatt.ui.route.Router;
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
    Router.navigateTo("home");
  }

  public void showSettingsPage(GameMode mode) {
    SettingsContent content = new SettingsContent(mode);

    SettingsView view = new SettingsView(
        content.getRoot(),
        () -> launcher.launchGame(content, mode)
    );

    navigator.switchTo(view, "Slayboard – " + mode.name());
  }
}
