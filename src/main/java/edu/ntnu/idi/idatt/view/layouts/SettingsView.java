package edu.ntnu.idi.idatt.view.layouts;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * A layout for the game setup screen.
 * Displays scrollable content with a start button at the bottom.
 * Used to wrap {@link edu.ntnu.idi.idatt.view.components.SettingsContent} and display it
 * with padding, scrolling, and a start game action.
 *
 * @author Aminda Lunde
 * @author Ingrid Opheim
 * @version 1.0
 */
public class SettingsView extends BorderPane {

  private final Button startGameButton;

  /**
   * Constructs a SettingsView layout with given content and start button action.
   *
   * @param content the main content node (usually a SettingsContent component)
   * @param onStartGame the action to run when "Start game" is clicked
   * @throws IllegalArgumentException if content or onStartGame is null
   */
  public SettingsView(
      Node content,
      Runnable onStartGame) {

    if (content == null) {
      throw new IllegalArgumentException("Content cannot be null.");
    }
    if (onStartGame == null) {
      throw new IllegalArgumentException("Start game action cannot be null.");
    }

    this.getStyleClass().add("settings-root");

    startGameButton = new Button("Start game");
    startGameButton.setOnAction(event -> onStartGame.run());
    startGameButton.getStyleClass().add("nav-button");

    HBox bottomBar = new HBox(startGameButton);
    bottomBar.setAlignment(Pos.CENTER_LEFT);
    bottomBar.setPadding(new Insets(20));


    VBox scrollContent = new VBox(30, content, bottomBar);
    scrollContent.setPadding(new Insets(20));
    scrollContent.setFillWidth(true);

    ScrollPane scrollPane = new ScrollPane(scrollContent);
    scrollPane.setFitToWidth(true);
    scrollPane.setFitToHeight(false);
    scrollPane.setPannable(true);
    scrollPane.getStyleClass().add("settings-root");

    this.setCenter(scrollPane);
  }
}
