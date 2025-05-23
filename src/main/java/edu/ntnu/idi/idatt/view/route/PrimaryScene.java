package edu.ntnu.idi.idatt.view.route;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * The main JavaFX scene used throughout the application. Wraps a {@link BorderPane} inside a
 * {@link StackPane}, providing consistent layout for view content and an optional navigation bar.
 * Used to switch between views while keeping a consistent window size and background.
 *
 * @author Aminda Lunde
 * @author Ingrid Opheim
 * @version 1.0
 */
public class PrimaryScene extends Scene {

  private static final int WIDTH = 1600;
  private static final int HEIGHT = 1000;
  private final BorderPane root;

  /**
   * Constructs the primary scene with predefined width, height, and background color.
   */
  public PrimaryScene() {
    super(new StackPane(), WIDTH, HEIGHT);

    this.root = new BorderPane();

    getRoot().setStyle("-fx-background-color: #FFF5FA;");

    ((StackPane) getRoot()).getChildren().add(root);
  }

  /**
   * Sets the main content view in the center of the scene.
   *
   * @param view the UI view to display
   * @throws IllegalArgumentException if the view is null
   */
  public void setView(Parent view) {
    if (view == null) {
      throw new IllegalArgumentException("View cannot be null.");
    }
    root.setCenter(view);

    if (view instanceof VBox) {
      VBox.setVgrow(view, Priority.ALWAYS);
    }
  }

  /**
   * Sets the navigation bar at the top of the layout.
   *
   * @param navBar the node to place as the top navigation bar
   * @throws IllegalArgumentException if the navBar is null
   */
  public void setNavBar(Node navBar) {
    if (navBar == null) {
      throw new IllegalArgumentException("Navigation bar cannot be null.");
    }
    root.setTop(navBar);
  }
}
