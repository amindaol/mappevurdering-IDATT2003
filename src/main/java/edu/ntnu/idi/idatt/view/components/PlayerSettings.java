package edu.ntnu.idi.idatt.view.components;

import javafx.scene.Node;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class PlayerSettings {

  private RadioButton playerIcon;
  private VBox playerContainer;

  public PlayerSettings(int playerNumber) {

    playerContainer = new VBox();
    playerContainer.getStyleClass().add("player-settings");
    playerIcon = new RadioButton();
    playerIcon.getStyleClass().add("player-settings-icon");
    TextField nameField = new TextField();
    nameField.getStyleClass().add("player-settings-name-field");
    nameField.setPromptText("Player " + playerNumber);

    playerContainer.getChildren().addAll(playerIcon, nameField);
    playerContainer.setSpacing(8);
    playerContainer.setAlignment(javafx.geometry.Pos.CENTER);
  }

  public RadioButton getPlayerIcon() {
    return playerIcon;
  }

  public void setPlayerIcon(ImageView playerIcon) {
    this.playerIcon.setGraphic(playerIcon);
  }

  public Node getAsNode() {
    return playerContainer;
  }

  public String getPlayerName() {
    TextField nameField = (TextField) playerContainer.getChildren().get(1);
    return nameField.getText();
  }
}
