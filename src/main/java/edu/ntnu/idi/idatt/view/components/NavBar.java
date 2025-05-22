package edu.ntnu.idi.idatt.view.components;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class NavBar extends HBox {

  private final Button homeButton;
  private final Button helpButton;

  public NavBar(String titleText, Runnable onHome, Runnable onHelp) {
    super(8);
    this.setAlignment(Pos.CENTER);
    this.setPadding(new Insets(10));

    Label titleLabel = new Label(titleText);
    titleLabel.getStyleClass().add("title-label");

    Region spacer = new Region();
    HBox.setHgrow(spacer, Priority.ALWAYS);

    Separator titleUnderline = new Separator();
    titleUnderline.setMaxWidth(Double.MAX_VALUE);
    HBox.setHgrow(titleUnderline, Priority.ALWAYS);

    VBox leftNavBar = new VBox(
        new HBox(titleLabel, spacer),
        titleUnderline
    );
    leftNavBar.setAlignment(Pos.BOTTOM_LEFT);
    HBox.setHgrow(leftNavBar, Priority.ALWAYS);

    Image homeIcon = new Image("/icons/home.png");
    homeButton = new Button();
    homeButton.setGraphic(new ImageView(homeIcon));
    homeButton.setOnAction(event -> onHome.run());
    homeButton.getStyleClass().add("icon-button");

    Image helpIcon = new Image("/icons/help.png");
    helpButton = new Button();
    helpButton.setGraphic(new ImageView(helpIcon));
    helpButton.setOnAction(event -> onHelp.run());
    helpButton.getStyleClass().add("icon-button");

    HBox rightNavBar = new HBox(10, homeButton, helpButton);
    rightNavBar.setAlignment(Pos.CENTER_RIGHT);

    this.getChildren().addAll(leftNavBar, rightNavBar);
  }

  public Button getHomeButton() {
    return homeButton;
  }

  public Button getHelpButton() {
    return helpButton;
  }
}
