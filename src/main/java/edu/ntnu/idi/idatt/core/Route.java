package edu.ntnu.idi.idatt.core;

import java.util.function.Supplier;
import javafx.scene.Parent;

public class Route {

  private final String name;
  private final Supplier<Parent> viewSupplier;

  public Route(String name, Supplier<Parent> viewSupplier) {
    this.name = name;
    this.viewSupplier = viewSupplier;
  }

  public String getName() {
    return name;
  }

  public Parent getView() {
    return viewSupplier.get();
  }
}
