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

public class SettingsView extends VBox {

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
    root.setTop(navBar);

    startGameButton = new Button("Start game");
    startGameButton.setOnAction(event -> onStartGame.run());
    startGameButton.getStyleClass().add("nav-button");

    HBox bottomBar = new HBox(startGameButton);
    bottomBar.setAlignment(Pos.CENTER_LEFT);
    bottomBar.setPadding(new Insets(20));

    VBox scrollContent = new VBox(30, content, bottomBar);
    scrollContent.setPadding(new Insets(20));
    scrollContent.setFillWidth(true);
    VBox.setVgrow(scrollContent, Priority.ALWAYS);

    ScrollPane scrollPane = new ScrollPane(scrollContent);
    scrollPane.setFitToWidth(true);
    scrollPane.setFitToHeight(true);
    scrollPane.setPannable(true);
    scrollPane.setStyle("-fx-background-color: transparent;");
    VBox.setVgrow(scrollPane, Priority.ALWAYS);

    root.setCenter(scrollPane);

    this.getChildren().add(root);
    VBox.setVgrow(root, Priority.ALWAYS);
  }

  public BorderPane getRoot() {
    return root;
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
