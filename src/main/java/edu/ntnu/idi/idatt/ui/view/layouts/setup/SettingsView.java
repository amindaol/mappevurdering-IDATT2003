package edu.ntnu.idi.idatt.ui.view.layouts.setup;

import edu.ntnu.idi.idatt.ui.view.components.NavBar;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class SettingsView extends BorderPane {

  private final Button startGameButton;

  public SettingsView(
      Node content,
      Runnable onStartGame) {

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
