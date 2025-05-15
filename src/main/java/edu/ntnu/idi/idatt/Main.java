package edu.ntnu.idi.idatt;

import edu.ntnu.idi.idatt.ui.route.PrimaryScene;
import edu.ntnu.idi.idatt.ui.route.Route;
import edu.ntnu.idi.idatt.ui.route.Router;
import edu.ntnu.idi.idatt.ui.view.components.NavBar;
import edu.ntnu.idi.idatt.util.StyleUtil;
import edu.ntnu.idi.idatt.ui.view.layouts.HomeController;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

  @Override
  public void start(Stage primaryStage) throws Exception {
    Router.setStage(primaryStage);
    PrimaryScene primaryScene = new PrimaryScene();
    StyleUtil.applyStyleSheet(primaryScene, "styles/global/global.css");
    Router.setScene(primaryScene);

    Router.registerRoute(
        new Route(
            "home",
            () -> null,
            () -> new HomeController().getView()));
    Router.registerRoute(
        new Route(
            "settings",
            () -> new NavBar("Settings", Router::goBack, false),
            () -> null,
    Router.registerRoute(
        new Route(
            "menu",
            () -> new NavBar("Play", Router::goBack, false),
            () -> null,
    Router.registerRoute(
        new Route(
            "load",
            () -> new NavBar(AppState.getSelectedGame().getName(), Router::goBack, false),
            () -> null,
    Router.registerRoute(
        new Route(
            "setup",
            () -> new NavBar(AppState.getSelectedGame().getName(), Router::goBack, false),
            () -> null,
    Router.registerRoute(
        new Route(
            "Snakes and Ladders",
            () -> new NavBar(
                AppState.getSelectedGame().getName(),
                Router::showPauseMenu,
                true),
            () -> null,
    Router.registerRoute(
        new Route(
            "Ludo",
            () -> new NavBar(
                AppState.getSelectedGame().getName(),
                Router::showPauseMenu,
                true),
            () -> null
        )
    );

    Router.navigateTo("home");

    primaryStage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}