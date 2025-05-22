package edu.ntnu.idi.idatt.view.components;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
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

/**
 * A GUI component for configuring a single player.
 * Includes fields for name, birthday, and token (icon) selection.
 *
 * Used within {@link PlayerSettingsContainer} to configure multiple players.
 * Provides validation and ensures each player selects a unique token.
 *
 * @author Aminda Lunde
 * @author Ingrid Opheim
 * @version 1.0
 */
public class PlayerSettings extends VBox {

  private final TextField nameField = new TextField();
  private final BirthdaySelector birthdaySelector = new BirthdaySelector();
  private final ToggleGroup iconGroup = new ToggleGroup();
  private final Map<String, RadioButton> tokenButtons = new HashMap<>();
  private String selectedToken;
  private final String[] availableTokens = {"cloud", "flower", "heart", "moon", "star"};

  /**
   * Creates a new player settings component with fields for name, birthday,
   * and selectable token icons.
   *
   * @param playerNumber the player's number (used as default name hint)
   */
  public PlayerSettings(int playerNumber) {
    this.setSpacing(10);
    this.setPadding(new Insets(12));
    this.setAlignment(Pos.CENTER);
    this.setPrefWidth(300);
    this.setMaxWidth(300);
    this.setMinWidth(300);

    this.getStyleClass().addAll("player-settings-card", "player-settings", "player-card");

    nameField.setPromptText("Player " + playerNumber);
    nameField.getStyleClass().add("player-settings-name-field");
    nameField.textProperty().addListener((obs, oldText, newText) -> {
      if (newText == null || newText.isBlank()) {
        nameField.setStyle("-fx-border-color: red;");
      } else {
        nameField.setStyle("");
      }
    });

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
      RadioButton btn = new RadioButton();
      btn.setToggleGroup(iconGroup);
      btn.setUserData(token);
      btn.getStyleClass().add("player-settings-icon");

      Tooltip.install(btn, new Tooltip("Token: " + token));

      Image image = new Image(Objects.requireNonNull(
          getClass().getResourceAsStream("/icons/players/" + token + ".png")),
          32, 32, true, true);

      btn.setGraphic(new ImageView(image));
      tokenButtons.put(token, btn);
      iconChoices.getChildren().add(btn);
    }

    if (!iconGroup.getToggles().isEmpty()) {
      iconGroup.selectToggle(iconGroup.getToggles().get(0));
      selectedToken = (String) iconGroup.getSelectedToggle().getUserData();
    }

    iconGroup.selectedToggleProperty().addListener((obs, oldVal, newVal) -> {
      if (newVal != null) {
        selectedToken = (String) newVal.getUserData();
      }
    });

    this.getChildren().addAll(nameField, birthdaySelector, iconChoices);
  }

  /**
   * Returns this component as a JavaFX Node.
   *
   * @return the visual node
   */
  public Node getAsNode() {
    return this;
  }

  /**
   * Returns the name entered by the player.
   *
   * @return player name as a string
   */
  public String getPlayerName() {
    return nameField.getText();
  }

  /**
   * Returns the selected birthday.
   *
   * @return the player's birthday
   */
  public LocalDate getBirthday() {
    return birthdaySelector.getBirthday();
  }

  /**
   * Returns the selected token name.
   *
   * @return the selected token string or null if none selected
   */
  public String getSelectedToken() {
    Toggle selected = iconGroup.getSelectedToggle();
    return selected != null ? (String) selected.getUserData() : null;
  }

  /**
   * Validates that the player name field is not empty.
   * Applies a red border if validation fails.
   */
  public void validateNameField() {
    if (getPlayerName().isBlank()) {
      nameField.setStyle("-fx-border-color: red;");
    } else {
      nameField.setStyle("");
    }
  }

  /**
   * Validates that a token has been selected.
   * Applies a red border if none is selected.
   */
  public void validateTokenSelection() {
    if (getSelectedToken() == null) {
      for (Toggle toggle : iconGroup.getToggles()) {
        ((RadioButton) toggle).setStyle("-fx-border-color: red;");
      }
    } else {
      for (Toggle toggle : iconGroup.getToggles()) {
        ((RadioButton) toggle).setStyle("");
      }
    }
  }

  /**
   * Disables tokens that are already used by other players.
   *
   * @param usedTokens set of tokens already selected by others
   */
  public void updateDisabledTokens(Set<String> usedTokens) {
    for (Map.Entry<String, RadioButton> entry : tokenButtons.entrySet()) {
      String token = entry.getKey();
      RadioButton btn = entry.getValue();
      btn.setDisable(usedTokens.contains(token) && !btn.isSelected());
    }
  }

  /**
   * Sets a callback to be triggered when the token selection changes.
   *
   * @param callback the callback to run on token selection
   */
  public void setOnTokenSelected(Runnable callback) {
    iconGroup.selectedToggleProperty().addListener((obs, oldVal, newVal) -> {
      if (newVal != null) {
        selectedToken = (String) newVal.getUserData();
        callback.run();
      }
    });
  }
}