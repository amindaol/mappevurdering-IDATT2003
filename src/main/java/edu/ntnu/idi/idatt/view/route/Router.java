package edu.ntnu.idi.idatt.view.route;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import javafx.stage.Stage;

/**
 * A simple routing system for switching between views in the application.
 * Maintains navigation history, handles route registration, and updates the {@link PrimaryScene}.
 * Used to navigate between screens such as Home, Settings, and Board views.
 *
 * @author Aminda Lunde
 * @author Ingrid Opheim
 * @version 1.0
 */
public class Router {

  private static Stage primaryStage;
  private static PrimaryScene primaryScene;
  private static final Map<String, Route> routes = new HashMap<>();
  private static final Stack<Route> history = new Stack<>();

  /**
   * Registers a new route with a name, view, and nav bar supplier.
   *
   * @param route the route to register
   * @throws IllegalArgumentException if a route with the same name already exists
   */
  public static void registerRoute(Route route) {
    if (routes.containsKey(route.getName())) {
      throw new IllegalArgumentException("Route with name " + route.getName() + " already exists.");
    }
    routes.put(route.getName(), route);
  }

  /**
   * Sets the primary stage of the application.
   * Must be called before navigation can occur.
   *
   * @param stage the main stage
   */
  public static void setStage(Stage stage) {
    primaryStage = stage;
  }

  /**
   * Sets the primary scene for the application.
   * Automatically adds the scene to the stage.
   *
   * @param scene the main UI scene
   */
  public static void setScene(PrimaryScene scene) {
    primaryScene = scene;
    primaryStage.setScene(scene);
  }

  /**
   * Navigates back to the previous route if available.
   * If no history exists, falls back to the "home" route.
   */
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

  /**
   * Navigates to a route by name and updates the view and nav bar.
   * Adds the route to navigation history.
   *
   * @param routeName the name of the route to navigate to
   * @throws IllegalArgumentException if the route doesn't exist or is already active
   */
  public static void navigateTo(String routeName) {
    if (!routes.containsKey(routeName)) {
      throw new IllegalArgumentException("Route not found: " + routeName);
    }

    Route route = routes.get(routeName);
    boolean isSameAsCurrent = !history.isEmpty() && routeName.equals(history.peek().getName());
    if (isSameAsCurrent) {
      throw new IllegalArgumentException("Already at " + routeName);
    }

    primaryScene.setView(route.getView());
    primaryScene.setNavBar(route.getNavBar());
    history.push(route);
  }
}
