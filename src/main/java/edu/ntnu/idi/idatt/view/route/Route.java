package edu.ntnu.idi.idatt.view.route;

import java.util.function.Supplier;
import javafx.scene.Node;
import javafx.scene.Parent;

/**
 * Represents a navigable route in the application.
 * Each route includes a name, a supplier for the view content,
 * and a supplier for the navigation bar.
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
   * @throws IllegalArgumentException if viewSupplier or navBarSupplier are null
   */
  public Route(String name, Supplier<Parent> viewSupplier, Supplier<Node> navBarSupplier) {
    if (viewSupplier == null || navBarSupplier == null) {
      throw new IllegalArgumentException("View supplier and nav bar supplier cannot be null.");
    }
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
   * @throws NullPointerException if the view supplier returns null
   */
  public Parent getView() {
    Parent view = viewSupplier.get();
    if (view == null) {
      throw new NullPointerException("View for route " + name + " cannot be null.");
    }
    return view;
  }

  /**
   * Returns the navigation bar for this route.
   *
   * @return the nav bar as a {@link Node}
   * @throws NullPointerException if the nav bar supplier returns null
   */
  public Node getNavBar() {
    Node navBar = navBarSupplier.get();
    if (navBar == null) {
      throw new NullPointerException("Navigation bar for route " + name + " cannot be null.");
    }
    return navBar;
  }
}
