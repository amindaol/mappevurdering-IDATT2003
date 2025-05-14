package edu.ntnu.idi.idatt;

import edu.ntnu.idi.idatt.ui.route.PrimaryScene;
import edu.ntnu.idi.idatt.ui.route.Route;
import edu.ntnu.idi.idatt.ui.route.Router;
import edu.ntnu.idi.idatt.util.StyleUtil;
import edu.ntnu.idi.idatt.ui.view.layouts.home.HomeController;
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
            () -> new HomeController().getView()));


    Router.navigateTo("home");

    primaryStage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}