package edu.ntnu.idi.idatt.view.route;

import java.util.function.Supplier;
import javafx.scene.Node;
import javafx.scene.Parent;

public class Route {

  private final String name;
  private final Supplier<Parent> viewSupplier;
  private final Supplier<Node> navBarSupplier;

  public Route(String name, Supplier<Parent> viewSupplier, Supplier<Node> navBarSupplier) {
    this.name = name;
    this.viewSupplier = viewSupplier;
    this.navBarSupplier = navBarSupplier;
  }

  public String getName() {
    return name;
  }

  public Parent getView() {
    return viewSupplier.get();
  }

  public Node getNavBar() {return navBarSupplier.get();}
}
