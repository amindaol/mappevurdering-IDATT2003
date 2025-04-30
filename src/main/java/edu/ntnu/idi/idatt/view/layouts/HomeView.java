package edu.ntnu.idi.idatt.view.layouts;


import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class HomeView {

  private final StackPane root;
  private final Button loveAndLaddersButton;
  private final Button bestieBattlesButton;

  public HomeView() {
    root = new StackPane();
    root.getStyleClass().add("home-root");

    Label titleLabel = new Label("Slayboard");
    titleLabel.getStyleClass().add("title-label");

    loveAndLaddersButton = new Button("Love & Ladders");
    bestieBattlesButton = new Button("Bestie Battles");
    loveAndLaddersButton.getStyleClass().add("nav-button");
    bestieBattlesButton.getStyleClass().add("nav-button");

    HBox buttons = new HBox(10, loveAndLaddersButton, bestieBattlesButton);
    buttons.setAlignment(Pos.CENTER);

    VBox content = new VBox(20, titleLabel, buttons);
    content.setAlignment(Pos.CENTER);

    root.getChildren().add(content);

  }

  public StackPane getRoot() {
    return root;
  }

  public Button getLoveAndLaddersButton() {
    return loveAndLaddersButton;
  }

  public Button getBestieBattlesButton() {
    return bestieBattlesButton;
  }
}
