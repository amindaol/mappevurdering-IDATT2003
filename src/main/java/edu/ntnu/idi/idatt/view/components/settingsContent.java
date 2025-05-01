package edu.ntnu.idi.idatt.view.components;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class settingsContent {

  private final VBox root;

  public settingsContent(String game, int maxPlayers) {
    root = new VBox();
    root.getStyleClass().add("settings-content");

    // TODO: Add css styling to the content

    root.setSpacing(10);
    root.setAlignment(Pos.CENTER);
    root.setPadding(new Insets(10));

    Label boardLabel = new Label("Choose a board:");

    RadioButton boardButton1 = new RadioButton("Board 1");
    RadioButton boardButton2 = new RadioButton("Board 2");
    RadioButton boardButton3 = new RadioButton("Board 3");

    ToggleGroup boardGroup = new ToggleGroup();
    boardButton1.setToggleGroup(boardGroup);
    boardButton2.setToggleGroup(boardGroup);
    boardButton3.setToggleGroup(boardGroup);

    boardButton1.setSelected(true);

    // TODO: Add image of the board to the radio button

    HBox boardButtons = new HBox(boardButton1, boardButton2, boardButton3);
    boardButtons.setSpacing(8);
    boardButtons.setAlignment(Pos.CENTER_LEFT);

    HBox playersBox = new HBox();
    Label playersLabel = new Label("Number of players:");
    for (int i = 2; i <= maxPlayers; i++) {
      RadioButton playerButton = new RadioButton(String.valueOf(i));
      playerButton.setUserData(i);
      if (i == 2) {
        playerButton.setSelected(true);
      }
      playersLabel.setLabelFor(playerButton);
      playersBox.getChildren().add(playerButton);
    }
  }
}
