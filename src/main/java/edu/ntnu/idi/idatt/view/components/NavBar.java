package edu.ntnu.idi.idatt.view.components;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class NavBar extends Node {

  private final HBox root;
  private final Button homeButton;
  private final Button helpButton;

  public NavBar(String titleText, Runnable onHome, Runnable onHelp) {
    root = new HBox(8);

    // TODO: Add .css styling to the navbar

    root.setAlignment(Pos.CENTER_LEFT);
    root.setPadding(new Insets(10));

    Label titleLabel = new Label(titleText);
    titleLabel.getStyleClass().add("title-label");

    Image homeIcon = new Image("/icons/home.png");
    ImageView homeImageView = new ImageView(homeIcon);
    homeButton = new Button();
    homeButton.setPrefSize(homeIcon.getWidth() + 8, homeIcon.getHeight() + 8);
    homeButton.setGraphic(homeImageView);
    homeButton.setOnAction(event -> onHome.run());

    helpButton = new Button();

    helpButton.setOnAction(event -> onHelp.run());

    HBox spacer = new HBox();
    HBox.setHgrow(spacer, javafx.scene.layout.Priority.ALWAYS);

    root.getChildren().addAll(titleLabel, spacer, homeButton, helpButton);
  }

  public Node getRoot() {
    return root;
  }

  public Button getHomeButton() {
    return homeButton;
  }

  public Button getHelpButton() {
    return helpButton;
  }
}
