package edu.ntnu.idi.idatt.view.layouts;


import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class HomeView extends StackPane {

  private final Button loveAndLaddersButton;
  private final Button bestieBattlesButton;

  public HomeView() {
    StackPane root = new StackPane();
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

    StackPane.setAlignment(content, Pos.CENTER);
    this.setPrefHeight(600);
    getChildren().add(content);

  }

  public void setOnClickLoveAndLaddersButton(Runnable runnable) {
    loveAndLaddersButton.setOnAction(e -> runnable.run());
  }

  public void setOnClickBestieBattlesButton(Runnable runnable) {
    bestieBattlesButton.setOnAction(e -> runnable.run());
  }
}
