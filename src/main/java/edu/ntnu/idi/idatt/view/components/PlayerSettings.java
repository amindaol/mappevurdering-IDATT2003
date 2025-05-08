package edu.ntnu.idi.idatt.view.components;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
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
    playerContainer.getStyleClass().add("player-settings");
    playerContainer.setPrefWidth(180);
    playerContainer.setSpacing(8);
    playerContainer.setAlignment(Pos.CENTER);

    nameField = new TextField();
    nameField.getStyleClass().add("player-settings-name-field");
    nameField.setPromptText("Player " + playerNumber);

    birthdaySelector = new BirthdaySelector();

    HBox iconChoices = new HBox(6);
    iconChoices.setAlignment(Pos.CENTER);

    for (String token : availableTokens) {
      RadioButton iconBtn = new RadioButton();
      iconBtn.setToggleGroup(iconGroup);
      iconBtn.setUserData(token);

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


}
