package edu.ntnu.idi.idatt.view.components;

import javafx.scene.Node;
import javafx.scene.layout.HBox;

public class PlayerSettingsContainer {

  private HBox root;

  public PlayerSettingsContainer(int players) {
    this.root = new HBox();
    for (int i = 0; i < players; i++) {
      PlayerSettings playerSettings = new PlayerSettings(i + 1);
      playerSettings.getPlayerIcon().setUserData(i + 1);
      root.getChildren().add(playerSettings.getAsNode());
    }
  }

  public Node getAsNode() {
    return root;
  }
}
