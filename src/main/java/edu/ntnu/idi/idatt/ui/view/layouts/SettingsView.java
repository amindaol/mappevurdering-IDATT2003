package edu.ntnu.idi.idatt.ui.view.layouts;

import edu.ntnu.idi.idatt.ui.view.components.NavBar;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class SettingsView {

  private final BorderPane root;
  private final NavBar navBar;
  private final Button startGameButton;

  public SettingsView(String gameTitle,
      Runnable onHome,
      Node content,
      Runnable onStartGame) {

    root = new BorderPane();
    root.getStyleClass().add("settings-root");

    navBar = new NavBar(gameTitle, onHome, this::showHelpDialog);
    root.setTop(navBar.getRoot());

    BorderPane.setMargin(content, new Insets(10));
    root.setCenter(content);

    ScrollPane scrollPane = new ScrollPane(content);
    scrollPane.setFitToWidth(true);
    scrollPane.setFitToHeight(true);
    scrollPane.setStyle("-fx-background-color: transparent;");
    BorderPane.setMargin(scrollPane, new Insets(10));
    root.setCenter(scrollPane);

    startGameButton = new Button("Start game");
    startGameButton.setOnAction(event -> {
      System.out.println("Start Game button clicked");
      onStartGame.run();
    });
    startGameButton.getStyleClass().add("nav-button");

    HBox bottomBar = new HBox(startGameButton);
    bottomBar.setAlignment(Pos.CENTER_LEFT);
    bottomBar.setPadding(new Insets(20));
    startGameButton.getStyleClass().add("nav-button");

    root.setBottom(bottomBar);
  }

  public BorderPane getRoot() {
    return root;
  }

  public Button getHomeButton() {
    return navBar.getHomeButton();
  }

  public Button getHelpButton() {
    return navBar.getHelpButton();
  }

  private void showHelpDialog() {
    Alert helpAlert = new Alert(Alert.AlertType.INFORMATION);
    helpAlert.setTitle("Help");
    helpAlert.setHeaderText("How to set up the game");
    helpAlert.setContentText("""
  1. Choose a board.
  2. Select number of players.
  3. Fill in name and birthday for each.
  4. Choose a unique token per player.
  5. Click 'Start game'!
  """);
    helpAlert.showAndWait();
  }
}
