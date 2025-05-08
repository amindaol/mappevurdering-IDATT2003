package edu.ntnu.idi.idatt.view.components;

import java.time.LocalDate;
import javafx.scene.Node;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class PlayerSettings {

  private RadioButton playerIcon;
  private VBox playerContainer;
  private final BirthdaySelector birthdaySelector;


  public PlayerSettings(int playerNumber) {

    playerContainer = new VBox();
    playerContainer.getStyleClass().add("player-settings");

    playerIcon = new RadioButton();
    playerIcon.getStyleClass().add("player-settings-icon");

    TextField nameField = new TextField();
    nameField.getStyleClass().add("player-settings-name-field");
    nameField.setPromptText("Player " + playerNumber);

    birthdaySelector = new BirthdaySelector();

    playerContainer.getChildren().addAll(playerIcon, nameField, birthdaySelector);
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

  public LocalDate getBirthday() {
    return birthdaySelector.getBirthday();
  }

}
