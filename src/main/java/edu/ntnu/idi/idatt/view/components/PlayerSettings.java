package edu.ntnu.idi.idatt.view.components;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.util.Objects;

public class PlayerSettings {

  private VBox playerContainer;
  private final TextField nameField;
  private final BirthdaySelector birthdaySelector;
  private final ToggleGroup iconGroup = new ToggleGroup();
  private String selectedToken;
  private final Map<String, RadioButton> tokenButtons = new HashMap<>();


  private final String[] availableTokens = {"cloud", "flower", "heart", "moon", "star"};

  public PlayerSettings(int playerNumber) {

    playerContainer = new VBox();
    playerContainer.getStyleClass().add("player-settings-card");
    playerContainer.getStyleClass().add("player-settings");
    playerContainer.setPrefWidth(220);
    playerContainer.setSpacing(10);
    playerContainer.setPadding(new Insets(12));
    playerContainer.setAlignment(Pos.CENTER);
    playerContainer.getStyleClass().add("player-card");


    nameField = new TextField();
    nameField.getStyleClass().add("player-settings-name-field");
    nameField.setPromptText("Player " + playerNumber);
    nameField.textProperty().addListener((obs, oldText, newText) -> {
      if (newText == null || newText.isBlank()) {
        nameField.setStyle("-fx-border-color: red;");
      } else {
        nameField.setStyle("");
      }
    });


    birthdaySelector = new BirthdaySelector();
    birthdaySelector.getDatePicker().valueProperty().addListener((obs, oldDate, newDate) -> {
      if (newDate == null) {
        birthdaySelector.getDatePicker().setStyle("-fx-border-color: red;");
      } else {
        birthdaySelector.getDatePicker().setStyle("");
      }
    });

    HBox iconChoices = new HBox(6);
    iconChoices.setAlignment(Pos.CENTER);

    for (String token : availableTokens) {
      RadioButton iconBtn = new RadioButton();
      iconBtn.setToggleGroup(iconGroup);
      iconBtn.setUserData(token);
      iconBtn.getStyleClass().add("player-settings-icon");

      Tooltip.install(iconBtn, new Tooltip("Token: " + token));

      Image image = new Image(Objects.requireNonNull(
          getClass().getResourceAsStream("/icons/players/" + token + ".png")),
          32, 32, true, true
      );

      iconBtn.setGraphic(new ImageView(image));
      tokenButtons.put(token, iconBtn);
      iconChoices.getChildren().add(iconBtn);
    }

    if (!iconGroup.getToggles().isEmpty()) {
      iconGroup.selectToggle(iconGroup.getToggles().get(0));
    }

    playerContainer.getChildren().addAll(nameField, birthdaySelector, iconChoices);
  }

 public String getSelectedIconName() {
    return (String) iconGroup.getSelectedToggle().getUserData();
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

  public String getSelectedToken() {
    return selectedToken;
  }

  public void updateDisabledTokens(Set<String> usedTokens) {
    for (Map.Entry<String, RadioButton> entry : tokenButtons.entrySet()) {
      String token = entry.getKey();
      RadioButton button = entry.getValue();
      button.setDisable(usedTokens.contains(token) && !button.isSelected());
    }
  }

  public void setOnTokenSelected(Runnable callback) {
    iconGroup.selectedToggleProperty().addListener((obs, oldVal, newVal) -> {
      if (newVal != null) {
        selectedToken = (String) newVal.getUserData();
        callback.run();
      }
    });
  }

  public void validateNameField() {
    if (getPlayerName().isBlank()) {
      nameField.setStyle("-fx-border-color: red;");
    } else {
      nameField.setStyle("");
    }
  }

  public void validateTokenSelection() {
    if (getSelectedToken() == null) {
      for (Toggle toggle : iconGroup.getToggles()) {
        ((RadioButton) toggle).setStyle("-fx-border-color: red;");
      }
    } else {
      for (Toggle toggle : iconGroup.getToggles()) {
        ((RadioButton) toggle).setStyle(""); // Reset style
      }
    }
  }



}
