package edu.ntnu.idi.idatt.view.components;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;

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
    root.setHgap(16);
    root.setVgap(16);
    root.setAlignment(Pos.CENTER);

    for (int i = 0; i < players; i++) {
      PlayerSettings playerSettings = new PlayerSettings(i + 1);
      playerSettingsList.add(playerSettings);
      root.getChildren().add(playerSettings.getAsNode());
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

  /**
   * Retrieves the selected icon names from each configuration component.
   *
   * @return a list of strings representing the selected icon names for each player.
   */
  public List<String> getSelectedIcons() {
    return playerSettingsList.stream()
        .map(PlayerSettings::getSelectedIconName)
        .toList();
  }

  public List<String> getSelectedTokens() {
    return playerSettingsList.stream()
        .map(PlayerSettings::getSelectedIconName)
        .toList();
  }

}
