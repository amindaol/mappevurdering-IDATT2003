package edu.ntnu.idi.idatt.view.components;

import javafx.scene.layout.HBox;

public class PlayerSettingsContainer {

  public PlayerSettingsContainer(int players) {
    HBox root = new HBox();
    for (int i = 0; i < players; i++) {
      PlayerSettings playerSettings = new PlayerSettings(i + 1);
      playerSettings.getPlayerIcon().setUserData(i + 1);
      root.getChildren().add(playerSettings.getAsNode());
    }
  }

}
