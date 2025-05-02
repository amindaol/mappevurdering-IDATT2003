package edu.ntnu.idi.idatt.view.components;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class SettingsContent {

  private final VBox root;
  private PlayerSettingsContainer playerSettingsContainer;

  public SettingsContent(int maxPlayers) {
    root = new VBox();
    root.getStyleClass().add("settings-content");

    root.setSpacing(10);
    root.setAlignment(Pos.CENTER);
    root.setPadding(new Insets(10));

    Label boardLabel = new Label("Choose a board:");
    boardLabel.getStyleClass().add("settings-content-label");

    RadioButton boardButton1 = new RadioButton("Board 1");
    RadioButton boardButton2 = new RadioButton("Board 2");
    RadioButton boardButton3 = new RadioButton("Board 3");

    ToggleGroup boardGroup = new ToggleGroup();
    boardButton1.setToggleGroup(boardGroup);
    boardButton2.setToggleGroup(boardGroup);
    boardButton3.setToggleGroup(boardGroup);

    boardButton1.getStyleClass().add("settings-content-radio-button");
    boardButton2.getStyleClass().add("settings-content-radio-button");
    boardButton3.getStyleClass().add("settings-content-radio-button");

    boardButton1.setSelected(true);

    // TODO: Add image of the board to the radio button??

    HBox boardButtons = new HBox(boardButton1, boardButton2, boardButton3);
    boardButtons.setSpacing(8);
    boardButtons.setAlignment(Pos.CENTER_LEFT);

    BorderPane playerSettings = new BorderPane();
    playerSettingsContainer = new PlayerSettingsContainer(2);
    playerSettings.setBottom(playerSettingsContainer.getAsNode());

    HBox playersButtons = new HBox();
    Label playersLabel = new Label("Number of players:");
    playersLabel.getStyleClass().add("settings-content-label");
    playersLabel.setAlignment(Pos.CENTER_LEFT);
    ToggleGroup playersGroup = new ToggleGroup();
    for (int i = 2; i <= maxPlayers; i++) {
      RadioButton playerButton = new RadioButton(String.valueOf(i));
      playerButton.setUserData(i);
      if (i == 2) {
        playerButton.setSelected(true);
      }
      playersLabel.setLabelFor(playerButton);
      playersButtons.getChildren().add(playerButton);
      playerButton.setToggleGroup(playersGroup);
      playerButton.getStyleClass().add("settings-content-radio-button");

      playerButton.setOnAction(event -> {
        int selectedPlayers = (int) playerButton.getUserData();
        PlayerSettingsContainer newplayerSettingsContainer =
            new PlayerSettingsContainer(selectedPlayers);
        playerSettings.setBottom(newplayerSettingsContainer.getAsNode());

      });
    }

    playerSettings.setCenter(playersButtons);
    playerSettings.setTop(playersLabel);

    playersButtons.setSpacing(8);

    root.getChildren().addAll(boardLabel, boardButtons, playerSettings);
  }

  public Node getRoot() {
    return root;
  }
}
