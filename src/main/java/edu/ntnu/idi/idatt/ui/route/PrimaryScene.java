package edu.ntnu.idi.idatt.ui.route;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class PrimaryScene extends Scene {

  private static final int WIDTH = 1600;
  private static final int HEIGHT =  1000;
  private final BorderPane root;

  public PrimaryScene() {
    super(new StackPane(), WIDTH, HEIGHT);

    this.root = new BorderPane();

    getRoot().setStyle("-fx-background-color: #FFF5FA;");

    ((StackPane) getRoot()).getChildren().add(root);
  }

  public void setView(Parent view) {
    root.setCenter(view);

    if (view instanceof VBox) {
      VBox.setVgrow(view, Priority.ALWAYS);
    }
  }

  public void setNavBar(Node navBar) {
    root.setTop(navBar!= null ? navBar : new VBox());
  }
}
