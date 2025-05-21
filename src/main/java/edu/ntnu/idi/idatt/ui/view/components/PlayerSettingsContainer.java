package edu.ntnu.idi.idatt.ui.view.components;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.FlowPane;
import javafx.util.Duration;

/**
 * Container holding all player configuration components.
 */
public class PlayerSettingsContainer {

  private FlowPane root;
  private final List<PlayerSettings> playerSettingsList = new ArrayList<>();
  private final Set<String> usedTokens = new HashSet<>();


  /**
   * Initializes player input components (name, birthday, icon).
   *
   * @param players number of players
   */
  public PlayerSettingsContainer(int players) {
    root = new FlowPane();
    root.getStyleClass().add("player-settings-container");
    root.setAlignment(Pos.TOP_CENTER);
    root.setPrefWrapLength(900);
    root.setPadding(new Insets(24));
    root.setHgap(20);
    root.setVgap(20);

    for (int i = 0; i < players; i++) {
      PlayerSettings playerSettings = new PlayerSettings(i + 1);
      playerSettingsList.add(playerSettings);

      Node playerNode = playerSettings.getAsNode();
      root.getChildren().add(playerSettings.getAsNode());

      playerNode.setOpacity(0);
      playerNode.setTranslateY(20);

      FadeTransition fade = new FadeTransition(Duration.millis(300), playerNode);
      fade.setFromValue(0);
      fade.setToValue(1);

      TranslateTransition slide = new TranslateTransition(Duration.millis(300), playerNode);
      slide.setFromY(20);
      slide.setToY(0);

      ParallelTransition combined = new ParallelTransition(fade, slide);
      combined.play();
    }
    for (PlayerSettings ps : playerSettingsList) {
      ps.setOnTokenSelected(() -> {
        usedTokens.clear();
        for (PlayerSettings p : playerSettingsList) {
          String token = p.getSelectedToken();
          if (token != null) usedTokens.add(token);
        }

        for (PlayerSettings p : playerSettingsList) {
          p.updateDisabledTokens(usedTokens);
        }
      });
    }
  }

  /**
   * Returns the root node (used in SettingsContent).
   *
   * @return the root node for this container
   */
  public Node getAsNode() {
    return root;
  }

  /**
   * Returns the player names entered in the GUI.
   *
   * @return a list of player names
   */
  public List<String> getPlayerNames() {
    return playerSettingsList.stream()
        .map(PlayerSettings::getPlayerName)
        .toList();
  }

  /**
   * Returns the selected birthdays from each player input.
   *
   * @return a list of LocalDate objects representing each player's birthday.
   */
  public List<LocalDate> getPlayerBirthdays() {
    return playerSettingsList.stream()
        .map(PlayerSettings::getBirthday)
        .toList();
  }


  public List<String> getSelectedTokens() {
    return playerSettingsList.stream()
        .map(PlayerSettings::getSelectedToken)
        .toList();
  }

  public void validateAllInputs() {
    for (PlayerSettings settings : playerSettingsList) {
      settings.validateNameField();
      settings.validateTokenSelection();
    }
  }


}
