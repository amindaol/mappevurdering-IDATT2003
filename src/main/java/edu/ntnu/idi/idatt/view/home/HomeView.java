package main.java.edu.ntnu.idi.idatt.view.home;


import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

public class HomeView {

  private final StackPane root;
  private final Button loveAndLaddersButton;
  private final Button bestieBattlesButton;

  public HomeView() {
    root = new StackPane();
    root.getStyleClass().add("body");

    Label titleLabel = new Label("Slayboard");
    titleLabel.getStyleClass().add("title-label");

    loveAndLaddersButton = new Button("Love & Ladders");
    bestieBattlesButton = new Button("Bestie Battles");
    loveAndLaddersButton.getStyleClass().add("nav-button");
    bestieBattlesButton.getStyleClass().add("nav-button");


  }

  public StackPane getRoot() {
    return root;
  }
}
