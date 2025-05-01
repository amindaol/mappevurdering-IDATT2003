package edu.ntnu.idi.idatt.view.layouts;

import edu.ntnu.idi.idatt.view.components.NavBar;
import edu.ntnu.idi.idatt.view.components.SettingsContent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class SettingsView {

  private final BorderPane root;
  private final NavBar navBar;
  private final Button startGameButton;

  public SettingsView(String gameTitle,
      Runnable onHome,
      Runnable onHelp,
      Node content) {

    root = new BorderPane();
    root.getStyleClass().add("settings-root");

    navBar = new NavBar(gameTitle, onHome, onHelp);
    root.setTop(navBar.getRoot());

    BorderPane.setMargin(content, new Insets(10));
    root.setCenter(content);

    startGameButton = new Button("Start game");
    // TODO: Add .css styling to the button and action
    startGameButton.setOnAction(event -> System.out.println("Start game button clicked"));

    HBox bottomBar = new HBox(startGameButton);
    bottomBar.setAlignment(Pos.CENTER_RIGHT);
    bottomBar.setPadding(new Insets(10));
    // TODO: Add .css styling to the bottom bar
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

}
