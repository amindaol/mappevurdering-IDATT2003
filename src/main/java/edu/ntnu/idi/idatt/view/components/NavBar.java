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

/**
 * A horizontal navigation bar with a title and two icon buttons: Home and Help.
 * The home and help buttons trigger the provided actions when clicked.
 * Used at the top of various views in the application to provide consistent navigation.
 *
 * @author Aminda Lunde
 * @author Ingrid Opheim
 * @version 1.0
 */
public class NavBar extends HBox {

  /**
   * Constructs a new navigation bar with a title and two buttons.
   *
   * @param titleText the title text shown on the left side
   * @param onHome the action to run when the home button is clicked
   * @param onHelp the action to run when the help button is clicked
   * @throws IllegalArgumentException if titleText is null or empty
   * @throws NullPointerException if the icon paths for the buttons are invalid or not found
   */
  public NavBar(String titleText, Runnable onHome, Runnable onHelp) {
    super(8);

    if (titleText == null || titleText.isEmpty()) {
      throw new IllegalArgumentException("Title text cannot be null or empty.");
    }

    if (onHome == null || onHelp == null) {
      throw new NullPointerException("Button actions cannot be null.");
    }

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

    Image homeIcon;
    try {
      homeIcon = new Image("/icons/home.png");
    } catch (Exception e) {
      throw new NullPointerException("Icon path cannot be null.");
    }
    Button homeButton = new Button();
    homeButton.setGraphic(new ImageView(homeIcon));
    homeButton.setOnAction(event -> onHome.run());
    homeButton.getStyleClass().add("icon-button");

    Image helpIcon;
    try {
      helpIcon = new Image("/icons/help.png");
    } catch (Exception e) {
      throw new NullPointerException("Help icon image not found at specified path.");
    }
    Button helpButton = new Button();
    helpButton.setGraphic(new ImageView(helpIcon));
    helpButton.setOnAction(event -> onHelp.run());
    helpButton.getStyleClass().add("icon-button");

    HBox rightNavBar = new HBox(10, homeButton, helpButton);
    rightNavBar.setAlignment(Pos.CENTER_RIGHT);

    this.getChildren().addAll(leftNavBar, rightNavBar);
  }
}
