package edu.ntnu.idi.idatt.view.components;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.Node;
import javafx.scene.layout.HBox;

/**
 * Container holding all player configuration components.
 */
public class PlayerSettingsContainer {

  private HBox root;
  private final List<PlayerSettings> playerSettingsList = new ArrayList<>();

  /**
   * Initializes player input components (name, birthday, icon).
   *
   * @param players number of players
   */
  public PlayerSettingsContainer(int players) {
    this.root = new HBox(10);
    for (int i = 0; i < players; i++) {
      PlayerSettings playerSettings = new PlayerSettings(i + 1);
      playerSettings.getPlayerIcon().setUserData(i + 1);
      playerSettingsList.add(playerSettings);
      root.getChildren().add(playerSettings.getAsNode());
    }
  }

  /**
   * Returns the root node (used in SettingsContent).
   */
  public Node getAsNode() {
    return root;
  }

  /**
   * Returns the player names entered in the GUI.
   */
  public List<String> getPlayerNames() {
    return playerSettingsList.stream()
        .map(PlayerSettings::getPlayerName)
        .toList();
  }

  /**
   * Returns the selected birthdays from each player input.
   */
  public List<LocalDate> getPlayerBirthdays() {
    return playerSettingsList.stream()
        .map(PlayerSettings::getBirthday)
        .toList();
  }



}
