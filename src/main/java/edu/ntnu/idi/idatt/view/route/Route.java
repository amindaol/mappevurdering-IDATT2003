package edu.ntnu.idi.idatt.view.route;

import java.util.function.Supplier;
import javafx.scene.Node;
import javafx.scene.Parent;

/**
 * Represents a navigable route in the application.
 * Each route includes a name, a supplier for the view content,
 * and a supplier for the navigation bar.
 *
 * Used by {@link Router} to manage view switching in the app.
 *
 * @author Aminda Lunde
 * @author Ingrid Opheim
 * @version 1.0
 */
public class Route {

  private final String name;
  private final Supplier<Parent> viewSupplier;
  private final Supplier<Node> navBarSupplier;

  /**
   * Constructs a new route with view and navigation suppliers.
   *
   * @param name the name/identifier of the route
   * @param viewSupplier a supplier that provides the view node
   * @param navBarSupplier a supplier that provides the navigation bar node
   */
  public Route(String name, Supplier<Parent> viewSupplier, Supplier<Node> navBarSupplier) {
    this.name = name;
    this.viewSupplier = viewSupplier;
    this.navBarSupplier = navBarSupplier;
  }

  /**
   * Returns the name of the route.
   *
   * @return route name
   */
  public String getName() {
    return name;
  }

  /**
   * Returns the view associated with this route.
   *
   * @return the view as a {@link Parent}
   */
  public Parent getView() {
    return viewSupplier.get();
  }

  /**
   * Returns the navigation bar for this route.
   *
   * @return the nav bar as a {@link Node}
   */
  public Node getNavBar() {return navBarSupplier.get();}
}
