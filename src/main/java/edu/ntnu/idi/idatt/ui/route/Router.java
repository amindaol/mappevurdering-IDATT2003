package edu.ntnu.idi.idatt.ui.route;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.logging.Logger;
import javafx.stage.Stage;

public class Router {

  private static final Logger logger = Logger.getLogger(Router.class.getName());
  private static Stage primaryStage;
  private static PrimaryScene primaryScene;
  private static final Map<String, Route> routes = new HashMap<>();
  private static final Stack<Route> history = new Stack<>();

  public static void registerRoute(Route route) {
    routes.put(route.getName(), route);
  }

  public static void setStage(Stage stage) {
    primaryStage = stage;
    logger.info("Stage set to: " + stage);
  }

  public static void setScene(PrimaryScene scene) {
    primaryScene = scene;
    primaryStage.setScene(scene);
    logger.fine("Scene set to: " + scene);
  }

  public static void goBack() {
    if (history.size() > 1) {
      history.pop();
      Route previous = history.peek();
      primaryScene.setView(previous.getView());
      primaryScene.setNavBar(previous.getNavBar());
    } else {
      navigateTo("home");
    }
  }

  public static void navigateTo(String routeName) {
    logger.info("Navigating to: " + routeName);
    if (!routes.containsKey(routeName)) {
      logger.warning("Route not found: " + routeName);
      throw new IllegalArgumentException("Route not found: " + routeName);
    }

    Route route = routes.get(routeName);
    boolean isSameAsCurrent = !history.isEmpty() && routeName.equals(history.peek().getName());
    if (isSameAsCurrent) {
      logger.warning("Already at route: " + routeName);
      throw new IllegalArgumentException("Already at " + routeName);
    }

    primaryScene.setView(route.getView());
    primaryScene.setNavBar(route.getNavBar());
    history.push(route);
    logger.info("Navigated to: " + routeName);
  }
}
