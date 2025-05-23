package edu.ntnu.idi.idatt.view.layouts;


import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * The home screen view for the application.
 * Displays the game title and buttons to start either Love & Ladders or Bestie Battles.
 * Used as the entry point of the game UI.
 *
 * @author Aminda Lunde
 * @author Ingrid Opheim
 * @version 1.0
 */
public class HomeView extends StackPane {

  private final Button loveAndLaddersButton;
  private final Button bestieBattlesButton;

  /**
   * Constructs the HomeView with title and two game mode buttons.
   */
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

  /**
   * Sets the callback to run when the Love & Ladders button is clicked.
   *
   * @param runnable the action to run
   * @throws IllegalArgumentException if the runnable is null
   */
  public void setOnClickLoveAndLaddersButton(Runnable runnable) {
    if (runnable == null) {
      throw new IllegalArgumentException("Runnable cannot be null.");
    }
    loveAndLaddersButton.setOnAction(e -> runnable.run());
  }

  /**
   * Sets the callback to run when the Bestie Battles button is clicked.
   *
   * @param runnable the action to run
   * @throws IllegalArgumentException if the runnable is null
   */
  public void setOnClickBestieBattlesButton(Runnable runnable) {
    if (runnable == null) {
      throw new IllegalArgumentException("Runnable cannot be null.");
    }
    bestieBattlesButton.setOnAction(e -> runnable.run());
  }
}
